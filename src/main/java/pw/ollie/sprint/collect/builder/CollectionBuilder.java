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
package pw.ollie.sprint.collect.builder;

import pw.ollie.sprint.collect.CollectionType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Utility for building {@link Collection}s. Many different implementations of
 * {@link Collection} are supported, including most which are present in the
 * Java standard libraries.
 *
 * @param <E> the type for the {@link Collection} being build
 */
public class CollectionBuilder<T extends Collection, E> {
    /**
     * The elements build in the {@link CollectionBuilder} so far. These are
     * stored in an {@link ArrayList} by default to make sure that the order is
     * retained in case it is needed by the user.
     */
    private final T elements;

    /**
     * Constructs a new {@link CollectionBuilder}, using a {@link Collection} of
     * the given {@link pw.ollie.sprint.collect.CollectionType} for building.
     *
     * @param collectionType the type of {@link Collection} to use
     */
    public CollectionBuilder(CollectionType<T> collectionType) {
        this.elements = collectionType.instantiate();
    }

    /**
     * Constructs a new {@link CollectionBuilder}, using the given {@link
     * Collection} for the backing store. Note that the {@link Collection} which
     * is passed is the one which is actually used, so any modifications to the
     * {@link Collection} after this constructor is called <em>will</em> affect
     * this builder, and vice versa.
     *
     * @param backing the {@link Collection} to use to store built elements
     */
    public CollectionBuilder(T backing) {
        this.elements = backing;
    }

    /**
     * Adds the given object, which <em>MUST</em> be of type E, to the builder.
     * Should the element not be of type E, you <em>WILL</em> get a {@link
     * ClassCastException}.
     *
     * @param element the element to add
     * @return this {@link CollectionBuilder} object
     */
    public CollectionBuilder<T, E> add(E element) {
        elements.add(element);
        return this;
    }

    /**
     * Adds all of the given objects, which <em>MUST</em> be of type E, to the
     * builder.
     *
     * @param elements the elements to add
     * @return this {@link CollectionBuilder} object
     */
    public CollectionBuilder<T, E> add(E... elements) {
        if (elements == null || elements.length < 1) {
            throw new IllegalArgumentException();
        }
        Collections.addAll(this.elements, elements);
        return this;
    }

    /**
     * Sorts the elements of this {@link CollectionBuilder}, ONLY if the backing
     * {@link Collection} being used is a {@link List}.
     *
     * @return this {@link CollectionBuilder} object
     * @see {@link Collections#sort(List)}
     */
    public CollectionBuilder<T, E> sort() {
        if (elements instanceof List) {
            Collections.sort((List) elements);
        }
        return this;
    }

    /**
     * Sorts the elements of this {@link CollectionBuilder}, ONLY if the backing
     * {@link Collection} being used is a {@link List}. The {@code comparator}
     * given is used to compare elements.
     *
     * @param comparator the {@link Comparator} to compare elements
     * @return this {@link CollectionBuilder} object
     * @see {@link Collections#sort(List, Comparator)}
     */
    public CollectionBuilder<T, E> sort(Comparator<E> comparator) {
        if (elements instanceof List) {
            Collections.sort((List) elements, comparator);
        }
        return this;
    }

    /**
     * Gets a {@link Collection} containing all of the elements added to this
     * builder.
     *
     * @return a {@link Collection} containing all built elements
     */
    public T build() {
        return elements;
    }

    /**
     * Builds an immutable {@link Collection} containing all of the elements
     * which are contained in this {@link CollectionBuilder}. {@link
     * Collections#unmodifiableCollection(Collection)} is used to acquire the
     * unmodifiable {@link Collection}.
     *
     * @return an immutable Collection of elements in this CollectionBuilder
     */
    public Collection<E> toImmutableCollection() {
        return Collections.unmodifiableCollection(elements);
    }

    /**
     * Builds the elements added to this builder into a new {@link Collection}
     * of the given {@link CollectionType}.
     *
     * @param type the {@link CollectionType} to use
     * @return a {@link Collection} of elements added to this builder
     */
    public T build(CollectionType<T> type) {
        T result = type.instantiate();
        result.addAll(elements);
        return result;
    }
}
