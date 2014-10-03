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

import java.util.ArrayList;
import java.util.List;

public final class Util {
    /**
     * Gets the {@code values()} for the given {@link Enum} type.
     *
     * @param type the {@link Enum} type
     * @param <E> the {@link Enum} type
     * @return all values for the given {@link Enum} type
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum> E[] getEnumValues(Class<E> type) {
        try {
            return (E[]) type.getDeclaredMethod("values").invoke(null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a {@link List} from the given objects.
     *
     * @param objs the objects to make a {@link List} from
     * @return an {@link ArrayList} of given keys
     */
    @SuppressWarnings("unchecked")
    public static <E> List<E> list(E... objs) {
        List<E> res = new ArrayList<>();
        for (Object object : objs) {
            res.add((E) object);
        }
        return res;
    }

    private Util() {
        throw new UnsupportedOperationException();
    }
}
