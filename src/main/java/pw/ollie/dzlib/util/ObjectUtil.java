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
package pw.ollie.dzlib.util;

import java.util.Collection;

/**
 * Object-related comparison methods.
 */
public final class ObjectUtil {
    /**
     * Checks whether {@code object} equals any of {@code objects}, using the
     * {@link Object#equals(Object)} method in {@code object} for comparisons.
     *
     * Two {@code null} objects are treated as equal by this method.
     *
     * @param object the object to test
     * @param objects the objects to test
     * @return whether {@code object} equals any of {@code objects}
     */
    public static boolean equalsAny(Object object, Object... objects) {
        for (Object o : objects) {
            if (object == null && o == null) {
                return true;
            } else if (object != null && object.equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether {@code object} equals any of {@code objects}, using the
     * {@link Object#equals(Object)} method in {@code object} for comparisons.
     *
     * Two {@code null} objects are treated as equal by this method.
     *
     * @param object the object to test
     * @param objects the objects to test
     * @return whether {@code object} equals any of {@code objects}
     */
    public static boolean equalsAny(Object object, Collection<Object> objects) {
        for (Object o : objects) {
            if (object == null && o == null) {
                return true;
            } else if (object != null && object.equals(o)) {
                return true;
            }
        }
        return false;
    }

    private ObjectUtil() {
        throw new UnsupportedOperationException();
    }
}
