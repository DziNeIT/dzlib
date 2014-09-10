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
package pw.ollie.sprint.iterate;

import pw.ollie.sprint.util.Util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EnumIterator<E extends Enum> implements Iterator<E> {
    private final ArrayIterator<E> backingIterator;
    private final E[] array;

    public EnumIterator(Class<E> enumType) {
        this(Util.getEnumValues(enumType));
    }

    public EnumIterator(E[] array) {
        this.array = array;
        backingIterator = new ArrayIterator<>(array);
    }

    @Override
    public boolean hasNext() {
        return backingIterator.hasNext();
    }

    @Override
    public E next() throws NoSuchElementException {
        return backingIterator.next();
    }

    @Override
    public void remove() {
        backingIterator.remove();
    }

    public EnumIterator<E> reset() {
        return new EnumIterator<>(array);
    }
}
