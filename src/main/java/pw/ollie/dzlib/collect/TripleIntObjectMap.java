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

import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.set.TLongSet;

import java.util.Collection;

/**
 * Maps three integers to one object.
 * <p>
 * https://github.com/flow/commons/blob/develop/src/main/java/com/flowpowered/commons/map/TripleIntObjectMap.java
 *
 * @param <T> the type of object stored
 */
public interface TripleIntObjectMap<T> {
    /**
     * Gets the value for the given (x, y, z) key, or null if none
     *
     * @return the value
     */
    T get(int x, int y, int z);

    /**
     * Returns true if this map contains a mapping for the specified key. More formally, returns <code>true</code> if and only if this map contains a mapping for a key <code>k</code> such that
     * <code>key.equals(k)</code>. (There can be at most one such mapping.)
     *
     * @param x an <code>int</code> value
     * @param y an <code>int</code> value
     * @param z an <code>int</code> value
     * @return <code>true</code> if this map contains a mapping for the specified <code>key(x, y, z)</code>.
     */
    boolean containsKey(int x, int y, int z);

    /**
     * Removes all of the mappings from this map (optional operation). The map will be empty after this call returns.
     */
    void clear();

    /**
     * Returns <code>true</code> if this map contains a mapping for the specified key. More formally, returns <code>true</code> if and only if this map contains a mapping for a key <code>k</code> such
     * that <code>key.equals(k)</code>. (There can be at most one such mapping.)
     *
     * @param val value whose presence in this map is to be tested
     * @return <code>true</code> if this map maps one or more keys to the specified value
     */
    boolean containsValue(T val);

    /**
     * Returns <code>true</code> if this map contains no key-value mappings.
     *
     * @return <code>true</code> if this map contains no key-value mappings.
     */
    boolean isEmpty();

    /**
     * Returns a {@see TLongObjectIterator} with access to this map's keys and values.
     *
     * @return a {@see TLongObjectIterator} with access to this map's keys and values.
     */
    TLongObjectIterator<T> iterator();

    /**
     * Returns a {@see TLongSet} view of the keys contained in this map. The set is backed by the map, so changes to the map are reflected in the set, and vice-versa. If the map is modified while an
     * iteration over the set is in progress (except through the iterator's own remove operation), the results of the iteration are undefined. The set supports element removal, which removes the
     * corresponding mapping from the map, via the <code>Iterator.remove</code>, <code>Set.remove</code>, <code>removeAll</code>, <code>retainAll</code>, and <code>clear</code> operations. It does not
     * support the add or addAll operations.
     *
     * @return a set view of the keys contained in this map.
     */
    TLongSet keySet();

    /**
     * Returns a copy of the keys of the map as an array. Changes to the array of keys will not be reflected in the map nor vice-versa.
     *
     * @return a copy of the keys of the map as an array.
     */
    long[] keys();

    /**
     * Removes the key/value pair for the given (x, y, z) key
     *
     * @return the value removed, or null on failure
     */
    T remove(int x, int y, int z);

    /**
     * Adds the given key/value pair to the map
     *
     * @param value the non-null value
     * @return the old value
     */
    T put(int x, int y, int z, T value);

    /**
     * Adds the given key/value pair to the map, but only if the key does not already map to a value
     *
     * @param value the non-null value
     * @return the current value, or null on success
     */
    T putIfAbsent(int x, int y, int z, T value);

    /**
     * Returns the number of key-value mappings in this map. If the map contains more than <code>Integer.MAX_VALUE</code> elements, returns <code>Integer.MAX_VALUE</code>.
     *
     * @return the number of key-value mappings in this map
     */
    int size();

    /**
     * Returns a collection containing all the values in the Map
     */
    Collection<T> valueCollection();
}
