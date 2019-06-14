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
package pw.ollie.dzlib.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * An implementation of a {@link PagedList} which wraps around an {@link
 * ArrayList} to store elements and then provides the ability to page those
 * elements.
 *
 * @param <E> the type of element stored
 */
public class PagedArrayList<E> implements PagedList<E> {
    private final List<E> delegate;
    private final Map<Integer, List<E>> pages;

    private int elementsPerPage = 8;
    private boolean autoRefresh = true;
    private boolean refreshOnGet = false;

    public PagedArrayList() {
        this(new ArrayList<>());
    }

    public PagedArrayList(List<E> delegate) {
        this.delegate = delegate;
        this.pages = new HashMap<>();

        calculatePages();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized List<E> getPage(int page) {
        if (refreshOnGet) {
            calculatePages();
        }
        return pages.get(page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int pages() {
        if (refreshOnGet) {
            calculatePages();
        }
        return pages.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getElementsPerPage() {
        return elementsPerPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAutoRefresh() {
        return autoRefresh;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRefreshOnGet() {
        return refreshOnGet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setElementsPerPage(int elementsPerPage) {
        this.elementsPerPage = elementsPerPage;
        if (autoRefresh) {
            calculatePages();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAutoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRefreshOnGet(boolean refreshOnGet) {
        this.refreshOnGet = refreshOnGet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void recalculatePages() {
        calculatePages();
    }

    // synchronized to prevent pages being calculated multiple times at once
    // due to how calculatePages() works this would cause big problems
    private synchronized void calculatePages() {
        pages.clear();
        if (isEmpty()) {
            return;
        }

        int amtPages = (int) Math.ceil((float) size() / elementsPerPage);
        for (int page = 1; page <= amtPages; page++) {
            int pageStart = (page - 1) * elementsPerPage;
            pages.put(page, subList(pageStart, Math.min(pageStart + elementsPerPage, size())));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return delegate.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return delegate.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return delegate.toArray(a);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E e) {
        boolean result = delegate.add(e);
        if (autoRefresh) {
            calculatePages();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        boolean result = delegate.remove(o);
        if (autoRefresh) {
            calculatePages();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return delegate.containsAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = delegate.addAll(c);
        if (autoRefresh) {
            calculatePages();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean result = delegate.addAll(index, c);
        if (autoRefresh) {
            calculatePages();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = delegate.removeAll(c);
        if (autoRefresh) {
            calculatePages();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = delegate.retainAll(c);
        if (autoRefresh) {
            calculatePages();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        delegate.clear();
        if (autoRefresh) {
            calculatePages();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E get(int index) {
        return delegate.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E set(int index, E element) {
        E result = delegate.set(index, element);
        if (autoRefresh) {
            calculatePages();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, E element) {
        delegate.add(index, element);
        if (autoRefresh) {
            calculatePages();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E remove(int index) {
        E result = delegate.remove(index);
        if (autoRefresh) {
            calculatePages();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int indexOf(Object o) {
        return delegate.indexOf(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int lastIndexOf(Object o) {
        return delegate.lastIndexOf(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator() {
        return delegate.listIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return delegate.listIterator(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return delegate.subList(fromIndex, toIndex);
    }
}
