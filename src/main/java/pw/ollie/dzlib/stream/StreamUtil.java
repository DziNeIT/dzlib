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
package pw.ollie.dzlib.stream;

import pw.ollie.dzlib.function.CheckedConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.*;

/**
 * https://github.com/google/mug/tree/master/core/src/main/java/com/google/mu/util/stream
 */
public final class StreamUtil {
    /**
     * Returns a Stream produced by iterative application of {@code step} to the initial
     * {@code seed}, producing a Stream consisting of seed, elements of step(seed),
     * elements of step(x) for each x in step(seed), etc.
     * <p>
     * While {@code Stream.generate(supplier)} can be used to generate infinite streams,
     * it's not as easy to generate a <em>finite</em> stream unless the size can be pre-determined.
     * This method can be used to generate finite streams: just return an empty stream when the
     * {@code step} determines that there's no more elements to be generated.
     * <p>
     * At every step, 0, 1 or more elements can be generated into the resulting stream.
     * As discussed above, returning an empty stream leads to eventual termination of the stream;
     * returning 1-element stream is equivalent to {@code Stream.generate(supplier)};
     * while returning more than one elements allows a single element to fan out to multiple
     * elements.
     */
    public static <T> Stream<T> generate(T seed, Function<? super T, ? extends Stream<? extends T>> step) {
        // flatMap() here won't honor short-circuiting such as limit(), because it internally
        // uses forEach() on the passed-in stream. See https://bugs.openjdk.java.net/browse/JDK-8075939
        return Stream.concat(Stream.of(seed), flatten(step.apply(seed).map(n -> generate(n, step))));
    }

    /**
     * Flattens {@code streamOfStream} and returns an unordered sequential stream of the nested
     * elements.
     * <p>
     * Logically, {@code stream.flatMap(fanOut)} is equivalent to
     * {@code MoreStreams.flatten(stream.map(fanOut))}.
     * Due to this <a href="https://bugs.openjdk.java.net/browse/JDK-8075939">JDK bug</a>,
     * {@code flatMap()} uses {@code forEach()} internally and doesn't support short-circuiting for
     * the passed-in stream. {@code flatten()} supports short-circuiting and can be used to
     * flatten infinite streams.
     *
     * @since 1.9
     */
    public static <T> Stream<T> flatten(Stream<? extends Stream<? extends T>> streamOfStream) {
        return mapBySpliterator(streamOfStream.sequential(), 0, FlattenedSpliterator::new);
    }

    /**
     * Iterates through {@code stream} <em>only once</em>. It's strongly recommended
     * to avoid assigning the return value to a variable or passing it to any other method because
     * the returned {@code Iterable}'s {@link Iterable#iterator iterator()} method can only be called
     * once. Instead, always use it together with a for-each loop, as in:
     * <pre>{@code
     *   for (Foo foo : iterateOnce(stream)) {
     *     ...
     *     if (...) continue;
     *     if (...) break;
     *     ...
     *   }
     * }</pre>
     * <p>
     * The above is equivalent to manually doing:
     * <pre>{@code
     *   Iterable<Foo> foos = stream::iterator;
     *   for (Foo foo : foos) {
     *     ...
     *   }
     * }</pre>
     * except using this API eliminates the need for a named variable that escapes the scope of the
     * for-each loop. And code is more readable too.
     * <p>
     * Note that {@link #iterateThrough iterateThrough()} should be preferred whenever possible
     * due to the caveats mentioned above. This method is still useful when the loop body needs to
     * use control flows such as {@code break} or {@code return}.
     */
    public static <T> Iterable<T> iterateOnce(Stream<T> stream) {
        return stream::iterator;
    }

    /**
     * Iterates through {@code stream} sequentially and passes each element to {@code consumer}
     * with exceptions propagated. For example:
     *
     * <pre>{@code
     *   void writeAll(Stream<?> stream, ObjectOutput out) throws IOException {
     *     iterateThrough(stream, out::writeObject);
     *   }
     * }</pre>
     */
    public static <T, E extends Throwable> void iterateThrough(Stream<? extends T> stream, CheckedConsumer<? super T, E> consumer) throws E {
        requireNonNull(consumer);
        for (T element : iterateOnce(stream)) {
            consumer.accept(element);
        }
    }

    /**
     * Dices {@code stream} into smaller chunks each with up to {@code maxSize} elements.
     * <p>
     * For a sequential stream, the first N-1 chunk's will contain exactly {@code maxSize}
     * elements and the last chunk may contain less (but never 0).
     * However for parallel streams, it's possible that the stream is split in roughly equal-sized
     * sub streams before being diced into smaller chunks, which then will result in more than one
     * chunks with less than {@code maxSize} elements.
     * <p>
     * This is an <a href="https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html#StreamOps">
     * intermediary operation</a>.
     *
     * @param stream  the source stream to be diced
     * @param maxSize the maximum size for each chunk
     * @return Stream of diced chunks each being a list of size up to {@code maxSize}
     * @throws IllegalStateException if {@code maxSize <= 0}
     */
    public static <T> Stream<List<T>> dice(Stream<? extends T> stream, int maxSize) {
        requireNonNull(stream);
        if (maxSize <= 0) throw new IllegalArgumentException();
        return mapBySpliterator(stream, Spliterator.NONNULL, it -> dice(it, maxSize));
    }

    /**
     * Dices {@code spliterator} into smaller chunks each with up to {@code maxSize} elements.
     *
     * @param spliterator the source spliterator to be diced
     * @param maxSize     the maximum size for each chunk
     * @return Spliterator of diced chunks each being a list of size up to {@code maxSize}
     * @throws IllegalStateException if {@code maxSize <= 0}
     */
    public static <T> Spliterator<List<T>> dice(Spliterator<? extends T> spliterator, int maxSize) {
        requireNonNull(spliterator);
        if (maxSize <= 0) throw new IllegalArgumentException();
        return new DicedSpliterator<T>(spliterator, maxSize);
    }

    /**
     * Returns a collector that collects/merges {@link Map} instances by key, using {@code valueMerger}
     * to merge values that are mapped to the same key. For example:
     * <pre>{@code
     *   interface Page {
     *     Map<Day, Long> getTrafficHistogram();
     *   }
     *
     *   Map<Day, Long> totalTrafficHistogram = pages.stream()
     *       .map(Page::getTrafficHistogram)
     *       .collect(mergingValues((a, b) -> a + b));
     * }</pre>
     * <p>
     * Use {@link #uniqueKeys} if the keys are guaranteed to be unique.
     *
     * @since 1.13
     */
    public static <K, V> Collector<Map<K, V>, ?, Map<K, V>> mergingValues(BinaryOperator<V> valueMerger) {
        requireNonNull(valueMerger);
        return collectingEach(m -> m.entrySet().stream(), Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, valueMerger));
    }

    /**
     * Returns a collector that collects {@link Map} entries into a combined map. Duplicate keys cause {@link
     * IllegalStateException}. For example:
     * <pre>{@code
     *   Map<FacultyId, Account> allFaculties = departments.stream()
     *       .map(Department::getFacultyMap)
     *       .collect(uniqueKeys());
     * }</pre>
     * <p>
     * Use {@link #mergingValues} if there are duplicate keys.
     *
     * @since 1.13
     */
    public static <K, V> Collector<Map<K, V>, ?, Map<K, V>> uniqueKeys() {
        return collectingEach(m -> m.entrySet().stream(), Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static <F, T, A, R> Collector<F, A, R> collectingEach(Function<? super F, ? extends Stream<? extends T>> toStream, Collector<T, A, R> collector) {
        BiConsumer<A, T> accumulator = collector.accumulator();
        return Collector.of(collector.supplier(),
                (a, f) -> toStream.apply(f).forEachOrdered(v -> accumulator.accept(a, v)),
                collector.combiner(),
                collector.finisher(),
                collector.characteristics().toArray(new Collector.Characteristics[0]));
    }

    static <F, T> Stream<T> mapBySpliterator(Stream<F> stream, int characteristics, Function<? super Spliterator<F>, ? extends Spliterator<T>> mapper) {
        requireNonNull(mapper);
        Stream<T> mapped = StreamSupport.stream(() -> mapper.apply(stream.spliterator()), characteristics, stream.isParallel());
        mapped.onClose(stream::close);
        return mapped;
    }

    private static <F, T> T splitThenWrap(Spliterator<F> from, Function<? super Spliterator<F>, ? extends T> wrapper) {
        Spliterator<F> it = from.trySplit();
        return it == null ? null : wrapper.apply(it);
    }

    private static final class DicedSpliterator<T> implements Spliterator<List<T>> {
        private final Spliterator<? extends T> underlying;
        private final int maxSize;

        DicedSpliterator(Spliterator<? extends T> underlying, int maxSize) {
            this.underlying = requireNonNull(underlying);
            this.maxSize = maxSize;
        }

        @Override
        public boolean tryAdvance(Consumer<? super List<T>> action) {
            requireNonNull(action);
            List<T> chunk = new ArrayList<>(chunkSize());
            for (int i = 0; i < maxSize && underlying.tryAdvance(chunk::add); i++) {
            }
            if (chunk.isEmpty()) return false;
            action.accept(chunk);
            return true;
        }

        @Override
        public Spliterator<List<T>> trySplit() {
            return splitThenWrap(underlying, it -> new DicedSpliterator<>(it, maxSize));
        }

        @Override
        public long estimateSize() {
            long size = underlying.estimateSize();
            return size == Long.MAX_VALUE ? Long.MAX_VALUE : estimateChunks(size);
        }

        @Override
        public long getExactSizeIfKnown() {
            return -1;
        }

        @Override
        public int characteristics() {
            return Spliterator.NONNULL;
        }

        private int chunkSize() {
            long estimate = underlying.estimateSize();
            if (estimate <= maxSize) {
                return (int) estimate;
            }
            return estimate == Long.MAX_VALUE ? Math.min(maxSize, 8192) : maxSize;
        }

        private long estimateChunks(long size) {
            long lower = size / maxSize;
            return lower + ((size % maxSize == 0) ? 0 : 1);
        }
    }

    private static final class FlattenedSpliterator<T> implements Spliterator<T> {
        private final Spliterator<? extends Stream<? extends T>> blocks;

        private Spliterator<? extends T> currentBlock;

        private final Consumer<Stream<? extends T>> nextBlock = block -> currentBlock = block.spliterator();

        FlattenedSpliterator(Spliterator<? extends Stream<? extends T>> blocks) {
            this.blocks = requireNonNull(blocks);
        }

        private FlattenedSpliterator(Spliterator<? extends Stream<? extends T>> blocks, Spliterator<? extends T> currentBlock) {
            this.blocks = requireNonNull(blocks);
            this.currentBlock = currentBlock;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            requireNonNull(action);
            if (currentBlock == null && !tryAdvanceBlock()) {
                return false;
            }
            boolean advanced;
            while ((!(advanced = currentBlock.tryAdvance(action))) && tryAdvanceBlock()) {
            }
            return advanced;
        }

        @Override
        public Spliterator<T> trySplit() {
            return splitThenWrap(blocks, it -> {
                Spliterator<T> result = new FlattenedSpliterator<>(it, currentBlock);
                currentBlock = null;
                return result;
            });
        }

        @Override
        public long estimateSize() {
            return Long.MAX_VALUE;
        }

        @Override
        public long getExactSizeIfKnown() {
            return -1;
        }

        @Override
        public int characteristics() {
            return 0;
        }

        private boolean tryAdvanceBlock() {
            return blocks.tryAdvance(nextBlock);
        }
    }

    private StreamUtil() {
        throw new UnsupportedOperationException();
    }
}
