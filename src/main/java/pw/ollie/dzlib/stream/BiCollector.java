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
