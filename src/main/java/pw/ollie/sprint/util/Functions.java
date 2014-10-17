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

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Various utility methods for creation of {@link FunctionalInterface} types
 * which have a specific function.
 */
public final class Functions {
    /**
     * Creates a new {@link Predicate} to check that the accepted {@link T} is
     * of the given {@link Class} type.
     *
     * @param clazz the class to check the accepted object is an instance of
     * @param <T> the type of object to accept in the {@link Predicate}
     * @return a Predicate to check that accepted objects are of the given type
     */
    public static <T> Predicate<T> isInstance(Class<? extends T> clazz) {
        return clazz::isInstance;
    }

    /**
     * Creates a new {@link Predicate} to check that the accepted {@link T} is
     * not of the given {@link Class} type.
     *
     * @param clazz the class to check the accepted object isn't an instance of
     * @param <T> the type of object to accept in the {@link Predicate}
     * @return a Predicate to check that accepted objects aren't of given type
     */
    public static <T> Predicate<T> notInstance(Class<? extends T> clazz) {
        return (e) -> !clazz.isInstance(e);
    }

    /**
     * Gets a {@link BiPredicate} which checks for the two given objects being
     * equal to each other.
     *
     * @param <T> the type of object to compare
     * @return a BiPredicate checking for two given objects being equal
     * @see {@link #areEqualNullable()}
     */
    public static <T> BiPredicate<T, T> areEqual() {
        return (a, b) -> a.equals(b);
    }

    /**
     * Gets a {@link BiPredicate} which checks for the two given objects being
     * equal to each other, classing two {@code null} objects as equal.
     *
     * @param <T> the type of object to compare
     * @return a BiPredicate checking for two given objects being equal
     * @see {@link #areEqual()}
     */
    public static <T> BiPredicate<T, T> areEqualNullable() {
        return (a, b) -> {
            if (a == null || b == null) {
                return a == null && b == null;
            } else {
                return a.equals(b);
            }
        };
    }

    /**
     * Gets a {@link Consumer} which does nothing every time.
     *
     * @param <T> the type of object to consume
     * @return a Consumer which does nothing every time
     */
    public static <T> Consumer<T> voidConsumer() {
        return t -> {};
    }

    /**
     * Gets a {@link BiConsumer} which does nothing every time.
     *
     * @param <T> the first type of object to consume
     * @param <U> the second type of object to consume
     * @return a BiConsumer which does nothing every time
     */
    public static <T, U> BiConsumer<T, U> voidBiConsumer() {
        return (t, u) -> {};
    }

    /**
     * Gets a {@link Supplier} which returns {@code null} every time.
     *
     * @param <T> the type for the Supplier to return
     * @return a Supplier which returns {@code null} every time
     */
    public static <T> Supplier<T> nullSupplier() {
        return () -> null;
    }

    /**
     * Gets a {@link Supplier} which returns {@code value} every time.
     *
     * @param <T> the type for the Supplier to return
     * @return a Supplier which returns {@code value} every time
     */
    public static <T> Supplier<T> constantSupplier(T value) {
        return () -> value;
    }

    /**
     * Gets a {@link Predicate} which returns {@code true} every time.
     *
     * @param <T> the type for the Predicate to accept
     * @return a Predicate which returns {@code true} every time
     */
    public static <T> Predicate<T> truePredicate() {
        return (a) -> true;
    }

    /**
     * Gets a {@link Predicate} which returns {@code false} every time.
     *
     * @param <T> the type for the Predicate to accept
     * @return a Predicate which returns {@code false} every time
     */
    public static <T> Predicate<T> falsePredicate() {
        return (a) -> false;
    }

    private Functions() {
    }
}
