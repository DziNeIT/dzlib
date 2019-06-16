/*
 * This file is part of dzlib, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014-2019 Oliver Stanley
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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents an implementation of the Java {@link Map} interface. Used for {@link MapBuilder}.
 */
public abstract class MapType {
    /**
     * All {@link MapType}s, mapped to the {@link Class} of the {@link Map} type
     * they represent.
     */
    private static final Map<Class<? extends Map>, MapType> types = new HashMap<>();

    /**
     * Represents the {@link HashMap} implementation of {@link Map}.
     */
    public static final MapType HashMap = new MapType(HashMap.class) {
        @Override
        public <K, V> Map<K, V> newMap() {
            return new HashMap<>();
        }

        @Override
        public <K, V> Map<K, V> newMap(int size) {
            return new HashMap<>(size);
        }
    };
    /**
     * Represents the {@link ConcurrentHashMap} implementation of {@link Map}.
     */
    public static final MapType ConcurrentHashMap = new MapType(
            ConcurrentHashMap.class) {
        @Override
        public <K, V> Map<K, V> newMap() {
            return new ConcurrentHashMap<>();
        }

        @Override
        public <K, V> Map<K, V> newMap(int size) {
            return new ConcurrentHashMap<>(size);
        }
    };
    /**
     * Represents the {@link TreeMap} implementation of {@link Map}.
     */
    public static final MapType TreeMap = new MapType(TreeMap.class) {
        @Override
        public <K, V> Map<K, V> newMap() {
            return new TreeMap<>();
        }

        @Override
        public <K, V> Map<K, V> newMap(int size) {
            return newMap(); // size not supported
        }
    };
    /**
     * Represents the {@link LinkedHashMap} implementation of {@link Map}.
     */
    public static final MapType LinkedHashMap = new MapType(
            LinkedHashMap.class) {
        @Override
        public <K, V> Map<K, V> newMap() {
            return new LinkedHashMap<>();
        }

        @Override
        public <K, V> Map<K, V> newMap(int size) {
            return new LinkedHashMap<>(size);
        }
    };
    /**
     * Represents the {@link WeakHashMap} implementation of {@link Map}.
     */
    public static final MapType WeakHashMap = new MapType(WeakHashMap.class) {
        @Override
        public <K, V> Map<K, V> newMap() {
            return new WeakHashMap<>();
        }

        @Override
        public <K, V> Map<K, V> newMap(int size) {
            return new WeakHashMap<>(size);
        }
    };
    /**
     * Represents the {@link IdentityHashMap} implementation of {@link Map}.
     */
    public static final MapType IdentityHashMap = new MapType(
            IdentityHashMap.class) {
        @Override
        public <K, V> Map<K, V> newMap() {
            return new IdentityHashMap<>();
        }

        @Override
        public <K, V> Map<K, V> newMap(int size) {
            return new IdentityHashMap<>(size);
        }
    };
    /**
     * Represents the {@link Hashtable} implementation of {@link Map}.
     */
    public static final MapType Hashtable = new MapType(Hashtable.class) {
        @Override
        public <K, V> Map<K, V> newMap() {
            return new Hashtable<>();
        }

        @Override
        public <K, V> Map<K, V> newMap(int size) {
            return new Hashtable<>(size);
        }
    };

    /**
     * The {@link Class} of the {@link Map} implementation which this {@link
     * MapType} represents.
     */
    private Class<? extends Map> type;

    /**
     * Constructs a new {@link MapType} representing the {@link Map}
     * implementation whose {@link Class} is the provided type.
     *
     * @param type the {@link Class} representing the implementation of
     *        {@link Map} this {@link MapType} represents
     */
    public MapType(Class<? extends Map> type) {
        this.type = type;
        types.put(type, this);
    }

    /**
     * Gets the {@link Class} of the {@link Map} implementation which this
     * {@link MapType} represents.
     */
    public Class<? extends Map> getMapType() {
        return type;
    }

    /**
     * Constructs a new instance of the {@link Map} implementation which is
     * represented by this {@link MapType}.
     *
     * @param <K> the key type for the new {@link Map}
     * @param <V> the value type for the new {@link Map}
     * @return a new {@link Map} of the implementation type specified by
     *         this {@link MapType}
     */
    public abstract <K, V> Map<K, V> newMap();

    /**
     * Constructs a new instance of the {@link Map} implementation which is
     * represented by this {@link MapType}. The {@link Map} should be of the
     * provided {@code size} should the implementation allow it.
     *
     * Should the implementation not provide for predetermined sizes, this
     * method will simply return the same as {@link #newMap()}.
     *
     * @param size the size for the new {@link Map}
     * @param <K> the key type for the new {@link Map}
     * @param <V> the value type for the new {@link Map}
     * @return a new {@link Map} of the implementation type specified by this
     *         {@link MapType}
     */
    public abstract <K, V> Map<K, V> newMap(int size);

    /**
     * Gets the {@link MapType} associated with the given {@link Map} class, or
     * {@code null} if there isn't one.
     *
     * @param cls the {@link Class} of the {@link Map} implementation to get the
     *        type for
     * @return the {@link MapType} associated with the given {@link Map}
     *         implementation
     */
    public static MapType get(Class<? extends Map> cls) {
        return types.get(cls);
    }
}
