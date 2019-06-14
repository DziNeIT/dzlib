package pw.ollie.dzlib.function;

import static java.util.Objects.*;

/**
 * A consumer which is able to throw checked exceptions.
 */
@FunctionalInterface
public interface CheckedConsumer<T, E extends Throwable> {
    void accept(T input) throws E;

    default CheckedConsumer<T, E> andThen(CheckedConsumer<? super T, E> that) {
        requireNonNull(that);

        return input -> {
            accept(input);
            that.accept(input);
        };
    }
}
