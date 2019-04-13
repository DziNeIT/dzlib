/*
 * This file is part of dzlib, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014-2019 Oliver Stanley <http://ollie.pw>
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
package pw.ollie.dzlib.reflect.sun;

import sun.reflect.ReflectionFactory;

import pw.ollie.dzlib.reflect.ReflectException;

/**
 * Utility for creating objects without calling their constructor. Note that
 * whether or not usage of {@link sun.misc.Unsafe} is avoided, this class will
 * not work on all JVMs (sun.reflect is used even when avoiding Unsafe).
 *
 * sun.reflect code from http://www.javaspecialists.eu/archive/Issue175.html
 *
 * @author Heinz Kabutz [kabutz] (sun.reflect code)
 * @author Ollie [DziNeIT] (sun.misc.Unsafe code)
 */
public final class SilentInstantiator {
    private static final ReflectionFactory FACTORY = ReflectionFactory.getReflectionFactory();

    /**
     * Creates a new object of type T, without calling the constructor.
     *
     * @param clazz the {@link Class} of the type to silently instantiate
     * @param <T> the type of the class to instantiate
     * @return an instance of the given class, no constructor called
     */
    public static <T> T newInstance(Class<T> clazz) {
        return newInstance(clazz, Object.class);
    }

    /**
     * Creates a new object of type T, without calling the constructor.
     * sun.reflect is used to create a serialization constructor to
     * create an object.
     *
     * @param clazz the class to silently create an instance of
     * @param parent a superclass of the class of which we do want the
     *        constructor called. Most of the time this can be Object.class
     * @param <T> the type which the class is
     * @return an instance of the given class, created without calling any
     *         constructor
     */
    public static <T> T newInstance(Class<? extends T> clazz, Class<? super T> parent) {
        // If we can't create an instance with Unsafe, instead create a new
        // serialization constructor for the class and create it that way. This
        // code adapted from code written by Heinz Kabutz
        try {
            return clazz.cast(FACTORY
                    .newConstructorForSerialization(clazz,
                            parent.getDeclaredConstructor()).newInstance());
        } catch (Exception e) {
            throw new ReflectException("Failed to silently create object", e);
        }
    }

    private SilentInstantiator() {
        // somewhat ironic that i'm trying to prevent instantiation of this
        // class via reflection by throwing an exception despite the fact that
        // the very contents of the static methods in this class can be used to
        // create an object of this class without the constructor being called
        // at all...
        throw new UnsupportedOperationException();
    }
}
