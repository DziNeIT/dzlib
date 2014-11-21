/*
 * This file is part of dzlib, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 Oliver Stanley <http://ollie.pw>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pw.ollie.dzlib.reflect.util;

import pw.ollie.dzlib.reflect.ReflectException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

/**
 * Helper methods for finding classes in jar files / directories.
 *
 * @author Ollie [DziNeIT]
 * @author Ian [simplyianm]
 */
public final class ClassFinder {
    /**
     * Gets all of the classes in a package.
     *
     * @param pkgName the name of the package to get classes for
     * @param loader the {@link ClassLoader} to get resources from
     * @param includeChildren whether to include children of the package given
     * @return a {@link List} of classes in the given package
     */
    public static List<Class<?>> find(String pkgName, ClassLoader loader,
            boolean includeChildren) {
        List<Class<?>> result = new ArrayList<>();
        String relPath = pkgName.replace('.', '/');

        URL resource = loader.getResource(relPath);
        if (resource == null) {
            throw new ReflectException("No resource found for " + relPath);
        }

        if (resource.toString().startsWith("jar:")) {
            checkJar(resource, pkgName, result);
        } else {
            checkDirectory(new File(resource.getPath()), pkgName, result);
        }

        if (includeChildren) {
            Reflection.loopPackages(
                    (n) -> n.startsWith(pkgName) && !n.equals(pkgName),
                    (p) -> result.addAll(find(p.getName(), loader, false)));
        }

        return result;
    }

    /**
     * @param pkgName the name of the package to get classes for
     * @return {@link #find(String, ClassLoader, boolean)} called with
     *         {@code pkgName}, {@link Class#getClassLoader()} and {@code true}
     *         as arguments
     */
    public static List<Class<?>> find(String pkgName) {
        return find(pkgName, ClassFinder.class.getClassLoader(), true);
    }

    /**
     * @param pkgName the name of the package to get classes for
     * @param loader the {@link ClassLoader} to get resources from
     * @return {@link #find(String, ClassLoader, boolean)} called with
     *         {@code pkgName}, {@code loader} and {@code true} as arguments
     */
    public static List<Class<?>> find(String pkgName,
            ClassLoader loader) {
        return find(pkgName, loader, true);
    }

    /**
     * @param pkgName the name of the package to get classes for
     * @param includeChildren whether to include children of the package given
     * @return {@link #find(String, ClassLoader, boolean)} called with
     *         {@code pkgName}, {@link Class#getClassLoader()} and {@code
     *         includeChildren} as arguments
     */
    public static List<Class<?>> find(String pkgName,
            boolean includeChildren) {
        return find(pkgName, ClassFinder.class.getClassLoader(),
                includeChildren);
    }

    private static void checkDirectory(File directory, String pkgname,
            List<Class<?>> classes) {
        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }

        for (String fileName : directory.list()) {
            if (!fileName.endsWith(".class")) {
                checkDirectory(new File(directory, fileName),
                        pkgname + '.' + fileName, classes);
            } else {
                classes.add(loadClass(pkgname + '.' + fileName.substring(0,
                        fileName.length() - 6)));
            }
        }
    }

    private static void checkJar(URL resource, String pkgname,
            List<Class<?>> classes) {
        String jarPath = resource.getPath()
                .replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");

        try (JarFile jarFile = new JarFile(jarPath)) {
            String relPath = pkgname.replace('.', '/');

            jarFile.stream().forEach((entry) -> {
                String entryName = entry.getName();
                if (entryName.endsWith(".class") && entryName.startsWith(
                        relPath) && entryName.length() > relPath.length() + "/"
                        .length()) {
                    classes.add(loadClass(entryName.replace('/', '.')
                            .replace('\\', '.').replace(".class", "")));
                }
            });
        } catch (IOException e) {
            throw new ReflectException(e);
        }
    }

    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ReflectException(e);
        }
    }

    private ClassFinder() {
        throw new UnsupportedOperationException();
    }
}
