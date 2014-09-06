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
package pw.ollie.sprint.reflect.sun;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Provides access to {@link sun.misc.Unsafe}. Should only be used in very rare
 * circumstances, and only when you understand what you are doing and the
 * connotations of messing with it. Improper usage of {@link sun.misc.Unsafe}
 * <em>WILL</em> crash the JVM.
 *
 * @author Ollie [DziNeIT]
 */
public class UnsafeAccessor {
    /**
     * The {@link Class} object for {@link Unsafe}.
     */
    public static final Class<Unsafe> unsafeType = Unsafe.class;

    /**
     * An iterate of possible field names for the instance of {@link Unsafe}. This
     * is used because on different JVMs, the field name for {@link Unsafe}'s
     * instance may be different - for example, on Dalvik the field name is
     * THE_ONE, but on the Hotspot JVM, the field is {@link Unsafe#theUnsafe}.
     */
    private static final String[] FIELD_NAMES = {
            "theUnsafe", // Oracle JVM
            "THE_ONE" // Dalvik JVM
    };

    /**
     * The {@link sun.misc.Unsafe} instance. Do not use this unless you know
     * what you are doing. Improper usage of this class <em>WILL</em> crash the
     * JVM.
     */
    private static Unsafe unsafe = null;
    /**
     * Whether we have already failed to get the {@link sun.misc.Unsafe}
     * instance. If true, {@code null} will always be returned by {@link #get()}
     * - otherwise {@link #get()} will attempt to acquire the instance if it
     * has not already been acquired.
     */
    private static boolean failed = false;

    /**
     * Gets the instance of {@link sun.misc.Unsafe}. Will attempt to retrieve
     * the instance via reflection if it is possible - if the field cannot be
     * retrieved, {@code null} <em>WILL</em> be returned.
     *
     * You should not use {@link sun.misc.Unsafe} unless you <em>REALLY</em>
     * know what you are doing and understand the connotations of messing with
     * the class. Improper usage <em>WILL</em> crash the JVM.
     *
     * @return the {@link sun.misc.Unsafe} instance, or null if not available
     */
    public static Unsafe get() {
        if (unsafe != null || failed) {
            return unsafe;
        }

        for (String fieldName : FIELD_NAMES) {
            try {
                Field field = unsafeType.getDeclaredField(fieldName);
                boolean previous = field.isAccessible();
                field.setAccessible(true);
                unsafe = (Unsafe) field.get(null);
                field.setAccessible(previous);
            } catch (Exception ignore) {
            }
        }

        if (unsafe == null) {
            failed = true;
        }

        return unsafe;
    }
}
