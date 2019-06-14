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

import java.util.List;

/**
 * Represents a {@link List} which can be split up into different pages.
 *
 * @param <E> the type of element stored in the {@link List}
 */
public interface PagedList<E> extends List<E> {
    /**
     * Gets the sub-{@link List} of this PagedList which is comprised of the elements on the given page.
     *
     * @param page the page number of the page to get
     * @return the given page
     */
    List<E> getPage(int page);

    /**
     * Gets the number of pages this PagedList has with the current elements per page setting.
     *
     * @return this list's current number of pages
     */
    int pages();

    /**
     * Gets the currently set number of elements per page.
     *
     * @return the elements per page for this PagedList
     */
    int getElementsPerPage();

    /**
     * Gets whether the pages of this PagedList will automatically update when changes are made to the List. When many
     * changes are about to be made at once it is recommended to disable this until the end, then call
     * {@link #recalculatePages()}.
     *
     * @return whether this PagedList automatically updates pages when changes are made
     */
    boolean isAutoRefresh();

    /**
     * Gets whether this PagedList will refresh pages when accessed. Usually recommended to be disabled.
     *
     * @return whether pages are refreshed whenever the PagedList is accessed
     */
    boolean isRefreshOnGet();

    /**
     * Sets the number of elements per page.
     *
     * @param elementsPerPage the new number of elements per page
     */
    void setElementsPerPage(int elementsPerPage);

    /**
     * Sets whether the PagedList should refresh pages whenever written to.
     *
     * @param autoRefresh whether to refresh pages on write
     */
    void setAutoRefresh(boolean autoRefresh);

    /**
     * Sets whether the PagedList should refresh pages whenever read.
     *
     * @param refreshOnGet whether to refresh pages on read
     */
    void setRefreshOnGet(boolean refreshOnGet);

    /**
     * Force refresh the pages in this PagedList.
     */
    void recalculatePages();
}
