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
package pw.ollie.sprint.util;

/**
 * Object-related comparison methods.
 */
public final class ObjectUtils {
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
        for (Object curObject : objects) {
            if (object == null) {
                if (curObject == null) {
                    return true;
                }
            } else if (object.equals(curObject)) {
                return true;
            }
        }
        return false;
    }

    private ObjectUtils() {
        throw new UnsupportedOperationException();
    }
}
