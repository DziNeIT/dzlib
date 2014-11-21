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
package pw.ollie.dzlib.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Contains various utility methods for {@link Collection}s.
 */
public final class CollectionUtil {
    /**
     * Removes all duplicates from the given {@link Collection}. What is / isn't
     * a duplicate is determined by the method used by a {@link ArrayList}'s
     * {@link List#contains(Object)} implementation.
     *
     * @param collection the {@link Collection} to remove duplicates from
     * @param <T> the type of the input {@link Collection}
     * @return the given {@link Collection}, without duplicates
     */
    public static <T extends Collection<?>> T trimDuplicates(T collection) {
        List<Object> noDuplicates = new ArrayList<>(collection.size());
        collection.forEach((object) -> {
            if (noDuplicates.contains(object)) {
                collection.remove(object);
            } else {
                noDuplicates.add(object);
            }
        });
        return collection;
    }

    private CollectionUtil() {
        throw new UnsupportedOperationException();
    }
}
