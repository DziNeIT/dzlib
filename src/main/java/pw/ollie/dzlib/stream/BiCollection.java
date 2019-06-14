package pw.ollie.dzlib.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Arrays.*;
import static java.util.Objects.*;
import static pw.ollie.dzlib.stream.BiStream.*;

/**
 * {@code BiCollection} to {@link BiStream} is like {@code Iterable} to {@code Iterator}:
 * a re-streamable collection of pairs. Suitable when the pairs aren't logically a {@code Map}
 * or {@code Multimap}.
 * <p>
 * This class is thread-safe if the underlying collection is thread-safe. For example:
 * <pre>  {@code
 *   BiStream.zip(dtos, domains).toBiCollection()
 * }</pre> doesn't guarantee thread safety; whereas
 * <pre>  {@code
 *   BiStream.zip(dtos, domains).toBiCollection(ImmutableList::toImmutableList)
 * }</pre> is guaranteed to be immutable and thread safe.
 */
public final class BiCollection<L, R> {
    private static final BiCollection<?, ?> EMPTY = from(Collections.emptyList());

    private final Collection<? extends Map.Entry<L, R>> entries;

    private BiCollection(Collection<? extends Map.Entry<L, R>> underlying) {
        this.entries = requireNonNull(underlying);
    }

    /**
     * Returns an empty {@code BiCollection}.
     */
    @SuppressWarnings("unchecked")
    public static <L, R> BiCollection<L, R> of() {
        return (BiCollection<L, R>) EMPTY;
    }

    /**
     * Returns a {@code BiCollection} for {@code left} and {@code right}.
     */
    public static <L, R> BiCollection<L, R> of(L left, R right) {
        return from(asList(kv(left, right)));
    }

    /**
     * Returns a {@code BiCollection} for two pairs.
     */
    public static <L, R> BiCollection<L, R> of(L left1, R right1, L left2, R right2) {
        return from(asList(kv(left1, right1), kv(left2, right2)));
    }

    /**
     * Returns a {@code BiCollection} for three pairs.
     */
    public static <L, R> BiCollection<L, R> of(L left1, R right1, L left2, R right2, L left3, R right3) {
        return from(asList(kv(left1, right1), kv(left2, right2), kv(left3, right3)));
    }

    /**
     * Returns a {@code BiCollection} for four pairs.
     */
    public static <L, R> BiCollection<L, R> of(
            L left1, R right1, L left2, R right2, L left3, R right3, L left4, R right4) {
        return from(asList(kv(left1, right1), kv(left2, right2), kv(left3, right3), kv(left4, right4)));
    }

    /**
     * Returns a {@code BiCollection} for five pairs.
     */
    public static <L, R> BiCollection<L, R> of(
            L left1, R right1, L left2, R right2, L left3, R right3, L left4, R right4, L left5, R right5) {
        return from(asList(kv(left1, right1), kv(left2, right2), kv(left3, right3), kv(left4, right4), kv(left5, right5)));
    }

    /**
     * Wraps {@code entries} in a {@code BiCollection}.
     */
    public static <L, R> BiCollection<L, R> from(Collection<Map.Entry<L, R>> entries) {
        return new BiCollection<>(entries);
    }

    /**
     * Returns a {@code Collector} that extracts the pairs from the input stream,
     * and then collects them into a {@code BiCollection}.
     *
     * @param leftFunction  extracts the first element of each pair
     * @param rightFunction extracts the second element of each pair
     */
    public static <T, L, R> Collector<T, ?, BiCollection<L, R>> toBiCollection(Function<? super T, ? extends L> leftFunction, Function<? super T, ? extends R> rightFunction) {
        requireNonNull(leftFunction);
        requireNonNull(rightFunction);
        Function<T, Map.Entry<L, R>> toEntry = x -> kv(leftFunction.apply(x), rightFunction.apply(x));
        Collector<T, ?, ? extends Collection<Map.Entry<L, R>>> entryCollector = Collectors.mapping(toEntry, Collectors.toList());
        return Collectors.collectingAndThen(entryCollector, BiCollection::from);
    }

    /**
     * Returns the size of the collection.
     */
    public int size() {
        return entries.size();
    }

    /**
     * Streams over this {@code BiCollection}.
     */
    public BiStream<L, R> stream() {
        return BiStream.from(entries.stream());
    }

    @Override
    public int hashCode() {
        return entries.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BiCollection<?, ?>) {
            BiCollection<?, ?> that = (BiCollection<?, ?>) obj;
            return entries.equals(that.entries);
        }
        return false;
    }

    @Override
    public String toString() {
        return entries.toString();
    }

    /**
     * Builds {@link BiCollection}.
     */
    public static final class Builder<L, R> {
        private final List<Map.Entry<L, R>> pairs = new ArrayList<>();

        /**
         * Adds a new pair of {@code left} and {@code right}.
         */
        public Builder<L, R> add(L left, R right) {
            pairs.add(kv(left, right));
            return this;
        }

        /**
         * Adds all key-value pairs from {@code map} into this builder.
         */
        public Builder<L, R> addAll(Map<? extends L, ? extends R> map) {
            return addAll(map.entrySet());
        }

        /**
         * Adds all key-value pairs from {@code entries} into this builder.
         */
        public Builder<L, R> addAll(Collection<? extends Map.Entry<? extends L, ? extends R>> entries) {
            for (Map.Entry<? extends L, ? extends R> entry : entries) {
                pairs.add(kv(entry.getKey(), entry.getValue()));
            }
            return this;
        }

        /**
         * Adds all key-value pairs from {@code entries} into this builder.
         */
        public Builder<L, R> addAll(BiCollection<? extends L, ? extends R> entries) {
            entries.stream().forEachOrdered(this::add);
            return this;
        }

        /**
         * Returns a new {@link BiCollection} encapsulating the snapshot of pairs in this builder
         * at the time {@code build()} is invoked.
         */
        public BiCollection<L, R> build() {
            return from(new ArrayList<>(pairs));
        }
    }
}
