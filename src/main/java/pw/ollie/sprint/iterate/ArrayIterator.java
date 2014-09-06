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

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<E> implements Iterator<E> {
    /**
     * The array being iterated by this {@link ArrayIterator}.
     */
    private E[] subject;
    /**
     * The current index of the {@link ArrayIterator}.
     */
    private int curIndex = 0;

    /**
     * Constructs a new {@link ArrayIterator}.
     *
     * @param subject the iterate to iterate
     */
    public ArrayIterator(E[] subject) {
        this.subject = subject;
    }

    @Override
    public boolean hasNext() {
        return (curIndex < subject.length);
    }

    @Override
    public E next() throws NoSuchElementException {
        if (curIndex >= subject.length) {
            // throw a NoSuchElementException now instead of letting an
            // ArrayIndexOutOfBoundsException be thrown later on in the method
            throw new NoSuchElementException("No element at: " + curIndex);
        }

        E object = subject[curIndex];
        curIndex++;
        return object;
    }

    @Override
    public void remove() {
        if (curIndex == 0) {
            // we haven't visited any elements yet
            throw new IllegalStateException();
        }
        subject[curIndex - 1] = null;
    }
}
