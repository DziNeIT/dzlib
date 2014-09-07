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
package pw.ollie.sprint.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility for building maps of any type - used to replace code like this:
 *
 * <code>
 * Map<String, String> map = new HashMap<>();
 * map.put("mystring", "myvalue");
 * map.put("string2", "value2");
 * map.put("stringification", "this_method_sucks");
 * map.put("yeah i hate this", "lets make a builder instead");
 * </code>
 *
 * With this:
 *
 * <code>
 * Map<String, String> map = new MapBuilder<>().with(
 *                    Util.list("mystring", "string2", "stringification", "this is great"),
 *                    Util.list("myvalue", "value2", "this method works", "builders rock")).build();
 * </code>
 *
 * Or this:
 *
 * <code>
 *     Map<String, String> map = new MapBuilder<>()
 *                                   .with("mystring", "myvalue").with("string2", "value")
 *                                   .with("stringification", "this method works").with("this is great", "builders rock")
 *                                   .build();
 * </code>
 *
 * The above list util method is {@link Util#list(Object[])}. Note that the
 * {@link #with(List, List)} method isn't the only way of adding elements to the
 * builder. There is also {@link #with(Collection)}, which takes a {@link
 * Collection} of {@link Entry}s, {@link #with(Object, Object)}, which adds an
 * individual key and value, and {@link #withAll(Map)}, which puts the contents
 * of the {@link Map} to this builder.
 *
 * Using the MapType enum, you can also create other types of Maps - the
 * {@link #MapBuilder()} constructor will create a {@link HashMap}, but the
 * {@link #MapBuilder(MapType)} constructor can create many other map types,
 * including, but not limited to {@link ConcurrentHashMap}s or {@link TreeMap}s.
 *
 * @param <K> the key type for the built {@link Map}
 * @param <V> the value type for the built {@link Map}
 */
public final class MapBuilder<K, V> {
    /**
     * The backing {@link Map} which the builder appends to, and then returns
     * when the {@link #build()} method is called.
     */
    private final Map<K, V> map;
    /**
     * The {@link MapType} for this {@link MapBuilder}. Each {@link MapType}
     * represents a different implementation of {@link Map}.
     */
    private final MapType type;

    /**
     * Constructs a new {@link MapBuilder} - this MapBuilder will use a {@link
     * HashMap} for the backing {@link Map}, and will therefore return an
     * instance of {@link HashMap} when the {@link #build()} method is called.
     */
    public MapBuilder() {
        this(MapType.HashMap);
    }

    /**
     * Constructs a new {@link MapBuilder}, which will use the {@link Map}
     * implementation which is represented by the given {@link MapType} for its
     * backing {@link Map}, and will therefore return an instance of that {@link
     * Map} implementation when the {@link #build()} method is called.
     *
     * @param type the {@link MapType} of the {@link Map} implementation to
     *        use
     */
    public MapBuilder(MapType type) {
        this.type = type;
        map = type.newMap();
    }

    /**
     * Constructs a new {@link MapBuilder}, which will use the given {@link Map}
     * as a backing {@link Map} to 'build' added elements into. This method
     * can be used to use the {@link MapBuilder} to create a Map of any
     * implementation type, at the cost of a little bit of convenience.
     *
     * @param map the {@link Map} to use as a backing {@link Map}
     */
    public MapBuilder(Map<K, V> map) {
        this.map = map;
        this.type = null;
    }

    /**
     * Adds all of the given {@code keys} and all of the given {@code vals} to
     * this {@link MapBuilder}. The key at index n in the {@code keys} {@link
     * List} is treated as the key for the value at index n in the {@code vals}
     * {@link List}.
     *
     * @param keys a {@link List} of the keys to add
     * @param vals a {@link List} of the values to add
     * @return this {@link MapBuilder} object
     */
    public MapBuilder<K, V> put(List<K> keys, List<V> vals) {
        if (keys == null || vals == null || keys.size() != vals.size()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), vals.get(i));
        }
        return this;
    }

    /**
     * Adds the given key and value to this {@link MapBuilder}
     *
     * @param key the key to add
     * @param val the value to add
     * @return this {@link MapBuilder} object
     */
    public MapBuilder<K, V> put(K key, V val) {
        map.put(key, val);
        return this;
    }

    /**
     * Adds the keys and values in the given {@link Entry}s to this {@link
     * MapBuilder}.
     *
     * @param entries the {@link Entry}s to add
     * @return this {@link MapBuilder} object
     */
    public MapBuilder<K, V> put(Collection<Entry<K, V>> entries) {
        if (entries == null) {
            return null;
        }
        for (Entry<K, V> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * Adds all of the keys and values contained within the given {@link Map} to
     * this {@link MapBuilder}. This method has the same behaviour as the method
     * {@link Map#putAll(Map)}, as that method is used to accomplish this
     * functionality.
     *
     * @param map the {@link Map} to add elements from
     * @return this {@link MapBuilder} object
     */
    public MapBuilder<K, V> putAll(Map<K, V> map) {
        this.map.putAll(map);
        return this;
    }

    /**
     * 'Builds' the contents of this {@link MapBuilder} into a {@link Map}. This
     * method will return the backing {@link Map} used during the building of
     * the {@link Map}, and will therefore return an instance of whatever
     * implementation was specified when this {@link MapBuilder} object was
     * created - {@link HashMap} if none was specified.
     *
     * If a fresh {@link Map} object is required, or you wish to create a {@link
     * Map} object which is of a different type to the {@link MapType} provided
     * when this {@link MapBuilder} object was instantiated, you should instead
     * use either {@link #buildNew()} {@link #build(MapType)}
     *
     * Note that the {@link Map} returned by this method will change if this
     * {@link MapBuilder} is changed too, so in situations where you do not want
     * this to happen, the usage of {@link #buildNew()} would be necessary.
     *
     * @return the backing {@link Map} for this {@link MapBuilder}
     */
    public Map<K, V> build() {
        return map;
    }

    /**
     * Builds a new {@link Map} of the type being used for this {@link
     * MapBuilder}, as opposed to simply returning the {@link Map} used to
     * contain the values added to the builder as they were added, which is the
     * functionality of {@link #build()}.
     *
     * @return a new {@link Map} of this builder's {@link MapType} containing
     *         all elements added to this builder
     */
    public Map<K, V> buildNew() {
        Map<K, V> map = type.newMap();
        map.putAll(this.map);
        return map;
    }

    /**
     * Returns a newly instantiated {@link Map} implementation of the {@link
     * MapType} which is provided, containing all of the contents of the backing
     * {@link Map} used in this {@link MapBuilder}.
     *
     * @param type the {@link MapType} of {@link Map} to return
     * @return a new {@link Map} of given {@link MapType} with the contents
     *         which were added to this {@link MapBuilder}
     */
    public Map<K, V> build(MapType type) {
        Map<K, V> retVal = type.newMap();
        retVal.putAll(buildNew());
        return retVal;
    }

    /**
     * Gets an unmodifiable {@link Map} for this {@link MapBuilder}'s {@link
     * Map} via {@link Collections#unmodifiableMap(Map)} and returns it.
     *
     * @return an unmodifiable {@link Map} with the elements in this {@link
     *         MapBuilder}
     */
    public Map<K, V> buildUnmodifiable() {
        return Collections.unmodifiableMap(buildNew());
    }

    /**
     * Adds all of the built entries to the given {@link Map}.
     *
     * @param map the {@link Map} to add entries to
     * @return the given {@link Map}
     */
    public Map<K, V> buildTo(Map<K, V> map) {
        map.putAll(this.map);
        return map;
    }

    /**
     * Represents an implementation of the Java {@link Map} interface. Used for
     * {@link MapBuilder}.
     */
    public static enum MapType {
        /**
         * Represents the {@link HashMap} implementation of {@link Map}.
         */
        HashMap(HashMap.class) {
            @Override
            public <K, V> Map<K, V> newMap() {
                return new HashMap<>();
            }
        },
        /**
         * Represents the {@link ConcurrentHashMap} implementation of {@link
         * Map}.
         */
        ConcurrentHashMap(ConcurrentHashMap.class) {
            @Override
            public <K, V> Map<K, V> newMap() {
                return new ConcurrentHashMap<>();
            }
        },
        /**
         * Represents the {@link TreeMap} implementation of {@link Map}.
         */
        TreeMap(TreeMap.class) {
            @Override
            public <K, V> Map<K, V> newMap() {
                return new TreeMap<>();
            }
        },
        /**
         * Represents the {@link LinkedHashMap} implementation of {@link Map}.
         */
        LinkedHashMap(LinkedHashMap.class) {
            @Override
            public <K, V> Map<K, V> newMap() {
                return new LinkedHashMap<>();
            }
        },
        /**
         * Represents the {@link WeakHashMap} implementation of {@link Map}.
         */
        WeakHashMap(WeakHashMap.class) {
            @Override
            public <K, V> Map<K, V> newMap() {
                return new WeakHashMap<>();
            }
        },
        /**
         * Represents the {@link IdentityHashMap} implementation of {@link Map}.
         */
        IdentityHashMap(IdentityHashMap.class) {
            @Override
            public <K, V> Map<K, V> newMap() {
                return new IdentityHashMap<>();
            }
        },
        /**
         * Represents the {@link Hashtable} implementation of {@link Map}.
         */
        Hashtable(Hashtable.class) {
            @Override
            public <K, V> Map<K, V> newMap() {
                return new Hashtable<>();
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
        private MapType(Class<? extends Map> type) {
            this.type = type;
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
    }
}
