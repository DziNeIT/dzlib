/*
 * This file is part of dzlib, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014-2019 Oliver Stanley
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
package pw.ollie.dzlib.reflect;

import java.lang.reflect.Field;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * General reflection utility methods.
 */
public final class Reflection {
    /**
     * Gets the {@link Field} object for the field with the given name in the
     * given {@link Class}, setting it to be accessible before returning it.
     * Returns {@code null} if the field doesn't exist.
     *
     * @param clazz the {@link Class} the field is in
     * @param field the name of the field to get
     * @return Field with given name in given Class, null if it doesn't exist
     */
    public static Field getAccessible(Class<?> clazz, String field) {
        try {
            Field result = clazz.getDeclaredField(field);
            result.setAccessible(true);
            return result;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * Loop through all {@link Package}s loaded.
     *
     * @param consumer the {@link Consumer} for the {@link Package}s
     */
    public static void loopPackages(Consumer<Package> consumer) {
        for (Package curPackage : Package.getPackages()) {
            consumer.accept(curPackage);
        }
    }

    /**
     * Loop through all {@link Package}s loaded which pass the test of the given.
     *
     * @param packageFilter filter packages
     * @param consumer the {@link Consumer} for the {@link Package}s
     */
    public static void loopPackages(Predicate<Package> packageFilter, Consumer<Package> consumer) {
        for (Package curPackage : Package.getPackages()) {
            if (packageFilter.test(curPackage)) {
                consumer.accept(curPackage);
            }
        }
    }

    /**
     * Gets the {@link Package} for the given {@link Class}.
     *
     * @param clazz the {@link Class} to get the {@link Package} of
     * @return the {@link Package} of the given {@link Class}
     */
    public static Package getPackage(Class<?> clazz) {
        return Package.getPackage(getPackageName(clazz));
    }

    /**
     * Gets the {@link Package} of the Class with the given name.
     *
     * @param classname the name of the Class to get the package of
     * @return the {@link Package} for the class with the given name
     */
    public static Package getPackage(String classname) {
        return Package.getPackage(getPackageName(classname));
    }

    /**
     * Gets the name of the package the given {@link Class} resides in.
     *
     * @param clazz the {@link Class} to get the package from
     * @return the given {@link Class}' package name
     */
    public static String getPackageName(Class<?> clazz) {
        return getPackageName(clazz.getName());
    }

    /**
     * Gets the name of the package the Class with the given name resides in.
     *
     * @param classname the name of the Class to get the package from
     * @return the given Class' package name
     */
    public static String getPackageName(String classname) {
        int last = classname.lastIndexOf('.');
        return (last == -1) ? "" : classname.substring(0, last);
    }

    private Reflection() {
        throw new UnsupportedOperationException();
    }
}
