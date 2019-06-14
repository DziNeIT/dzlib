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

import java.util.function.Function;
import java.util.stream.Collector;

/**
 * See https://github.com/google/mug/blob/master/core/src/main/java/com/google/mu/util/stream/BiCollector.java
 * <p>
 * Logically, a {@code BiCollector} collects "pairs of things", just as a {@link Collector} collects
 * "things".
 * <p>
 * {@code BiCollector} is usually passed to {@link BiStream#collect}. For example, to collect the
 * input pairs to a {@code ConcurrentMap}:
 * <pre>{@code
 * ConcurrentMap<String, Integer> map = BiStream.of("a", 1).collect(Collectors::toConcurrentMap);
 * }</pre>
 * <p>
 * In addition, {@code BiCollector} can be used to directly collect a stream of "pair-wise" data
 * structures.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @param <R> the result type
 */
@FunctionalInterface
public interface BiCollector<K, V, R> {
    /**
     * Returns a {@code Collector} that will first bisect the input elements using {@code toKey} and
     * {@code toValue} and subsequently collect the bisected parts through this {@code BiCollector}.
     *
     * @param <E> used to abstract away the underlying pair/entry type used by {@link BiStream}.
     */
    <E> Collector<E, ?, R> bisecting(Function<E, K> toKey, Function<E, V> toValue);
}
