/*
 * This file is part of sprintj, licensed under the MIT License (MIT).
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
package pw.ollie.sprint.reflect.util;

import pw.ollie.sprint.reflect.ReflectException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Helper methods for finding classes in jar files / directories
 *
 * @author Ollie [DziNeIT]
 * @author Ian [simplyianm]
 */
public class FindClasses {
    /**
     * Gets all of the classes in a package.
     *
     * @param pkgName the name of the package to get classes for
     * @param loader the {@link ClassLoader} to get resources from
     * @param includeChildren whether to include children of the package given
     * @return a {@link List} of classes in the given package
     */
    public static List<Class<?>> getClasses(String pkgName, ClassLoader loader,
            boolean includeChildren) {
        List<Class<?>> classes = new ArrayList<>();
        String relPath = pkgName.replace('.', '/');
        // Get a File object for the package
        URL resource = loader.getResource(relPath);
        if (resource == null) {
            throw new ReflectException("Unexpected problem: No resource for "
                    + relPath);
        }

        if (resource.toString().startsWith("jar:")) {
            processJarfile(resource, pkgName, classes);
        } else {
            processDirectory(new File(resource.getPath()), pkgName, classes);
        }

        if (includeChildren) {
            for (Package pack : Package.getPackages()) {
                if (pack.getName().startsWith(pkgName) && !pack.getName()
                        .equals(pkgName)) {
                    classes.addAll(getClasses(pack.getName(), loader, false));
                }
            }
        }

        return classes;
    }

    /**
     * @param pkgName the name of the package to get classes for
     * @return {@link #getClasses(String, ClassLoader, boolean)} called with
     *         {@code pkgName}, {@link Class#getClassLoader()} and {@code true}
     *         as arguments
     */
    public static List<Class<?>> getClasses(String pkgName) {
        return getClasses(pkgName, FindClasses.class.getClassLoader(), true);
    }

    /**
     * @param pkgName the name of the package to get classes for
     * @param loader the {@link ClassLoader} to get resources from
     * @return {@link #getClasses(String, ClassLoader, boolean)} called with
     *         {@code pkgName}, {@code loader} and {@code true} as arguments
     */
    public static List<Class<?>> getClasses(String pkgName,
            ClassLoader loader) {
        return getClasses(pkgName, loader, true);
    }

    /**
     * @param pkgName the name of the package to get classes for
     * @param includeChildren whether to include children of the package given
     * @return {@link #getClasses(String, ClassLoader, boolean)} called with
     *         {@code pkgName}, {@link Class#getClassLoader()} and {@code
     *         includeChildren} as arguments
     */
    public static List<Class<?>> getClasses(String pkgName,
            boolean includeChildren) {
        return getClasses(pkgName, FindClasses.class.getClassLoader(),
                includeChildren);
    }

    private static void processDirectory(File directory, String pkgname,
            List<Class<?>> classes) {
        // Iterate through files in the directory
        for (String fileName : directory.list()) {
            // We only want .class files
            if (fileName.endsWith(".class")) {
                // Load the class without the .class extension
                classes.add(loadClass(pkgname + '.' + fileName.substring(0,
                        fileName.length() - 6)));
            }

            File subdir = new File(directory.toString(), fileName);
            if (subdir.isDirectory()) {
                // Process subdirectory of the current directory
                processDirectory(subdir, pkgname + '.' + fileName, classes);
            }
        }
    }

    private static void processJarfile(URL resource, String pkgname,
            List<Class<?>> classes) {
        String relPath = pkgname.replace('.', '/');
        String resPath = resource.getPath();
        String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar")
                .replaceFirst("file:", "");

        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                String className;
                if (entryName.endsWith(".class") && entryName.startsWith(
                        relPath) && entryName.length() > relPath.length() + "/"
                        .length()) {
                    className = entryName.replace('/', '.').replace('\\', '.')
                            .replace(".class", "");
                    classes.add(loadClass(className));
                }
            }
        } catch (IOException e) {
            throw new ReflectException("Failed to get JarFile object for path:"
                    + jarPath, e);
        }
    }

    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ReflectException("Couldn't find class: " + className, e);
        }
    }

    private FindClasses() {
        throw new UnsupportedOperationException();
    }
}
