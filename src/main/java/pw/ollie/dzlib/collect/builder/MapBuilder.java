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
package pw.ollie.dzlib.collect.builder;

import pw.ollie.dzlib.collect.MapType;
import pw.ollie.dzlib.collect.SingleEntryMap;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
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
 * Map<String, String> map = new MapBuilder<>()
 *                       .with("mystring", "myvalue").with("string2", "value")
 *                       .with("stringification", "this method works").with("this is great", "builders rock")
 *                       .build();
 * </code>
 *
 * Note that the {@link #put(List, List)} method isn't the only way of adding elements
 * to the builder. There is also {@link #put(Collection)}, which takes a {@link
 * Collection} of {@link Entry}s, {@link #put(Object, Object)}, which adds an
 * individual key and value, and {@link #putAll(Map)}, which puts the contents
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
     * Constructs a new {@link MapBuilder}, which will use the {@link Map}
     * implementation which is represented by the given {@link MapType} for its
     * backing {@link Map}, and will therefore return an instance of that {@link
     * Map} implementation when the {@link #build()} method is called.
     *
     * The size of the builder is set to the given {@code size} value.
     *
     * @param type the {@link MapType} of the {@link Map} implementation to
     *        use
     * @param size the expected size of the {@link Map} to be built
     */
    public MapBuilder(MapType type, int size) {
        this.type = type;
        map = type.newMap(size);
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
        this.type = MapType.get(map.getClass());
    }

    private MapBuilder(Map<K, V> map, boolean noLookup) {
        this.map = map;
        this.type = noLookup ? null : MapType.get(map.getClass());
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
     * Create a new {@link MapBuilder} object of the given key and value types.
     *
     * @param <K> the type of key for the builder's map
     * @param <V> the type of value for the builder's map
     * @return a new {@link MapBuilder}
     */
    public static <K, V> MapBuilder<K, V> builder() {
        return new MapBuilder<>();
    }

    /**
     * Creates a new {@link MapBuilder} object of type {@link MapType#HashMap}
     * and the given initial size. Can be used to prevent expansion costs.
     *
     * @param initialSize the initial size of the builder
     * @param <K> the type of key for the builder's map
     * @param <V> the type of value for the builder's map
     * @return a new {@link MapBuilder} with the given {@code initialSize}
     */
    public static <K, V> MapBuilder<K, V> builder(int initialSize) {
        return new MapBuilder<>(MapType.HashMap, initialSize);
    }

    /**
     * Creates a new {@link MapBuilder} object of the given {@link MapType},
     * with the given key and value types.
     *
     * @param type the {@link MapType} for the type of {@link Map} to use
     * @param <K> the type of key for the builder's map
     * @param <V> the type of value for the builder's map
     * @return a new {@link MapBuilder} of the given {@code type}
     */
    public static <K, V> MapBuilder<K, V> builder(MapType type) {
        return new MapBuilder<>(type);
    }

    /**
     * Creates a new {@link MapBuilder} which wraps the given {@link Map}, also
     * finding the {@link MapType} for the given {@link Map} if there is one.
     *
     * @param map the {@link Map} to use for the builder
     * @param <K> the type of key for the builder's map
     * @param <V> the type of value for the builder's map
     * @return a new {@link MapBuilder} wrapping the given {@link Map}
     */
    public static <K, V> MapBuilder builder(Map<K, V> map) {
        return new MapBuilder<>(map);
    }

    /**
     * Creates a new {@link MapBuilder} wrapping the given {@link Map}, but
     * without looking up the {@link MapType} for the {@link Map}. This is
     * literally never necessary, unless you are literally in a situation where
     * a map lookup & call to {@link #getClass()} is too much. If you have to
     * use this you probably have other, bigger problems.
     *
     * @param map the {@link Map} to wrap
     * @param <K> the type of key for the builder's map
     * @param <V> the type of value for the builder's map
     * @return a new {@link MapBuilder} wrapping the given {@link Map}
     * @see {@link MapBuilder#MapBuilder(Map, boolean)}
     */
    public static <K, V> MapBuilder<K, V> rawBuilder(Map<K, V> map) {
        return new MapBuilder<>(map, true);
    }

    /**
     * Utility method for the creation of a {@link SingleEntryMap} with the
     * given {@code key} and the given {@code val}.
     *
     * @param key the key for the single entry in the map
     * @param val the value for the single entry in the map
     * @param <K> the type of the map key
     * @param <V> the type of the map value
     * @return a {@link SingleEntryMap} with the given key and value
     */
    public static <K, V> Map<K, V> singleEntryMap(K key, V val) {
        return new SingleEntryMap<>(key, val, false);
    }

    /**
     * Utility method for the creation of an immutable {@link SingleEntryMap}
     * with the given {@code key} and the given {@code val}.
     *
     * @param key the key for the single entry in the map
     * @param val the value for the single entry in the map
     * @param <K> the type of the map key
     * @param <V> the type of the map value
     * @return an immutable {@link SingleEntryMap} with the given key and value
     */
    public static <K, V> Map<K, V> immutableSingleEntryMap(K key, V val) {
        return new SingleEntryMap<>(key, val, true);
    }
}
