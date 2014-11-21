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

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SingleEntryMap<K, V> implements Map<K, V> {
    private SimpleEntry<K, V> entry;
    private boolean immutable;

    public SingleEntryMap() {
    }

    public SingleEntryMap(K key, V val) {
        this(key, val, false);
    }

    public SingleEntryMap(K key, V val, boolean immutable) {
        this.immutable = immutable;
        entry = new SimpleEntry<>(key, val);
    }

    @Override
    public int size() {
        return entry == null ? 0 : 1;
    }

    @Override
    public boolean isEmpty() {
        return entry == null;
    }

    @Override
    public boolean containsKey(Object key) {
        return entry.getKey().equals(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return entry.getValue().equals(value);
    }

    @Override
    public V get(Object key) {
        if (entry.getKey().equals(key)) {
            return entry.getValue();
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (immutable) {
            return null;
        }
        V ret = entry.getValue();
        entry = new SimpleEntry<>(key, value);
        return ret;
    }

    @Override
    public V remove(Object key) {
        if (entry.getKey().equals(key)) {
            V ret = entry.getValue();
            entry = null;
            return ret;
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        if (immutable) {
            return;
        }
        entry = null;
    }

    @Override
    public Set<K> keySet() {
        Set<K> result = new HashSet<>();
        result.add(entry.getKey());
        return result;
    }

    @Override
    public Collection<V> values() {
        Set<V> result = new HashSet<>();
        result.add(entry.getValue());
        return result;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> result = new HashSet<>();
        if (immutable) {
            result.add(new SimpleEntry<>(entry.getKey(), entry.getValue()));
        } else {
            result.add(entry);
        }
        return result;
    }
}
