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
package pw.ollie.sprint.collect.concurrent;

import pw.ollie.sprint.iterate.UnmodifiableListIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A {@link List} which delegates all methods to another {@link List}, but with
 * all methods {@code synchronized}. This can be better than a {@link
 * java.util.concurrent.CopyOnWriteArrayList} in situations where there are a
 * lot of writes to not as many reads. In a situation where there are many reads
 * and not so many writes, {@link java.util.concurrent.CopyOnWriteArrayList} is
 * definitely the better option.
 *
 * @param <E> the type of element stored in this {@link SynchronizedList}
 */
public class SynchronizedList<E> implements List<E> {
    private final List<E> delegate;

    public SynchronizedList() {
        delegate = new ArrayList<>();
    }

    public SynchronizedList(List<E> list) {
        this.delegate = list;
    }

    @Override
    public synchronized int size() {
        return delegate.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public synchronized boolean contains(Object o) {
        return delegate.contains(o);
    }

    @Override
    public synchronized Iterator iterator() {
        return delegate.iterator();
    }

    @Override
    public synchronized Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public synchronized <T> T[] toArray(T[] a) {
        return delegate.toArray(a);
    }

    @Override
    public synchronized boolean add(E o) {
        return delegate.add(o);
    }

    @Override
    public synchronized boolean remove(Object o) {
        return delegate.remove(o);
    }

    @Override
    public synchronized boolean containsAll(Collection c) {
        return delegate.containsAll(c);
    }

    @Override
    public synchronized boolean addAll(Collection c) {
        return delegate.addAll(c);
    }

    @Override
    public synchronized boolean addAll(int index, Collection c) {
        return delegate.addAll(index, c);
    }

    @Override
    public synchronized boolean removeAll(Collection c) {
        return delegate.removeAll(c);
    }

    @Override
    public synchronized boolean retainAll(Collection c) {
        return delegate.retainAll(c);
    }

    @Override
    public synchronized void clear() {
        delegate.clear();
    }

    @Override
    public synchronized boolean equals(Object o) {
        return delegate.equals(o);
    }

    @Override
    public synchronized E get(int index) {
        return delegate.get(index);
    }

    @Override
    public synchronized E set(int index, E element) {
        return delegate.set(index, element);
    }

    @Override
    public synchronized void add(int index, E element) {
        delegate.add(index, element);
    }

    @Override
    public synchronized E remove(int index) {
        return delegate.remove(index);
    }

    @Override
    public synchronized int indexOf(Object o) {
        return delegate.indexOf(o);
    }

    @Override
    public synchronized int lastIndexOf(Object o) {
        return delegate.lastIndexOf(o);
    }

    @Override
    public synchronized ListIterator<E> listIterator() {
        return new UnmodifiableListIterator<>(this);
    }

    @Override
    public synchronized ListIterator<E> listIterator(int index) {
        return new UnmodifiableListIterator<>(this, index);
    }

    @Override
    public synchronized List<E> subList(int fromIndex, int toIndex) {
        return new SynchronizedList<>(delegate.subList(fromIndex, toIndex));
    }
}
