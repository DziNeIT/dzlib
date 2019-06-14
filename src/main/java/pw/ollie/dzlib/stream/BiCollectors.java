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
package pw.ollie.dzlib.stream;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Objects.*;

public class BiCollectors {
    /**
     * Returns a {@link BiCollector} that collects the key-value pairs into a {@link Map}.
     *
     * <p>Entries are collected in encounter order.
     */
    public static <K, V> BiCollector<K, V, Map<K, V>> toMap() {
        return toMap((a, b) -> {
            throw new IllegalArgumentException("Duplicate values encountered");
        });
    }

    /**
     * Returns a {@link BiCollector} that collects the key-value pairs into a {@link Map}
     * using {@code valueMerger} to merge values of duplicate keys.
     *
     * <p>Entries are collected in encounter order.
     */
    public static <K, V> BiCollector<K, V, Map<K, V>> toMap(BinaryOperator<V> valueMerger) {
        requireNonNull(valueMerger);
        return new BiCollector<K, V, Map<K, V>>() {
            @Override
            public <E> Collector<E, ?, Map<K, V>> bisecting(Function<E, K> toKey, Function<E, V> toValue) {
                return Collectors.collectingAndThen(Collectors.toMap(toKey, toValue, valueMerger, LinkedHashMap::new), Collections::unmodifiableMap);
            }
        };
    }

    /**
     * Returns a {@link BiCollector} that collects the key-value pairs into a {@code Map} using
     * {@code valueCollector} to collect values of identical keys into a final value of type
     * {@code V}.
     * <p>
     * For example, the following calculates total population per state from city demographic data:
     * <pre>{@code
     * Map<StateId, Integer> statePopulations = BiStream.from(cityToDemographicDataMap)
     *     .mapKeys(City::getState)
     *     .collect(toMap(summingInt(DemographicData::getPopulation)));
     * }</pre>
     * <p>
     * Entries are collected in encounter order.
     */
    public static <K, V1, V> BiCollector<K, V1, Map<K, V>> toMap(Collector<V1, ?, V> valueCollector) {
        requireNonNull(valueCollector);
        return new BiCollector<>() {
            @Override
            public <E> Collector<E, ?, Map<K, V>> bisecting(Function<E, K> toKey, Function<E, V1> toValue) {
                return Collectors.collectingAndThen(Collectors.groupingBy(toKey, LinkedHashMap::new, Collectors.mapping(toValue, valueCollector)), Collections::unmodifiableMap);
            }
        };
    }

    /**
     * Returns a {@link Collector} that will flatten the map entries derived from the
     * input elements using {@code getEntries} function and then pass each key-value pair to
     * {@code downstream} collector. For example, the following code flattens each (employee, task)
     * entry to collect the sum of task hours per employee:
     * <pre>{@code
     * Map<Employee, Integer> employeeTotalTaskHours = projects.stream()
     *   .map(Project::getTaskAssignmentsMultimap)  // stream of Multimap<Employee, Task>
     *   .collect(flattening(Multimap::entries, toMap(summingInt(Task::getHours))));
     * }</pre>
     */
    public static <E, K, V, R> Collector<E, ?, R> flattening(Function<? super E, ? extends Collection<? extends Map.Entry<K, V>>> getEntries, BiCollector<? super K, ? super V, R> downstream) {
        return Collectors.flatMapping(getEntries.andThen(Collection::stream), downstream.bisecting(Map.Entry::getKey, Map.Entry::getValue));
    }

    private BiCollectors() {
    }
}
