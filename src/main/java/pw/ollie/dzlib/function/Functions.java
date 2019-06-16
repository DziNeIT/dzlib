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
package pw.ollie.dzlib.function;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Various utility methods for creation of {@link FunctionalInterface} types
 * which have a specific function.
 */
public final class Functions {
    /**
     * Transforms the given {@link Collection} into the given {@link Map}, where
     * the keys are the value returned by applying the given {@link Function} to
     * the value they are mapped to. The values are the elements contained in
     * the given Collection.
     *
     * @param collection the {@link Collection} to map
     * @param function the {@link Function} to use to determine keys
     * @param <A> the type of key for the map
     * @param <B> the type of value for the map
     * @return a {@link Map} of {@code function}-determined keys to given values
     */
    public static <A, B> Map<A, B> map(Collection<B> collection, Map<A, B> map, Function<B, A> function) {
        collection.forEach((l) -> map.put(function.apply(l), l));
        return map;
    }

    /**
     * Transforms the given {@link Collection} into a new {@link Map}, where
     * the keys are the value returned by applying the given {@link Function} to
     * the value they are mapped to. The values are the elements contained in
     * the given Collection.
     *
     * This is equivalent to calling {@link #map(Collection, Map, Function)}
     * with {@code new HashMap<>} provided for the {@link Map} argument.
     *
     * @param collection the {@link Collection} to map
     * @param function the {@link Function} to use to determine keys
     * @param <A> the type of key for the map
     * @param <B> the type of value for the map
     * @return a {@link Map} of {@code function}-determined keys to given values
     */
    public static <A, B> Map<A, B> map(Collection<B> collection, Function<B, A> function) {
        return map(collection, new HashMap<>(), function);
    }

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
     * equal to each other, classing two {@code null} objects as equal.
     *
     * @param <T> the type of object to compare
     * @return a BiPredicate checking for two given objects being equal
     * @see {@link #areEqual()}
     */
    public static <T> BiPredicate<T, T> areEqualNullable() {
        return EQUAL_NULLABLE_PREDICATE;
    }

    /**
     * Gets a {@link Consumer} which does nothing every time.
     *
     * @param <T> the type of object to consume
     * @return a Consumer which does nothing every time
     */
    public static <T> Consumer<T> voidConsumer() {
        return VOID_CONSUMER;
    }

    /**
     * Gets a {@link BiConsumer} which does nothing every time.
     *
     * @param <T> the first type of object to consume
     * @param <U> the second type of object to consume
     * @return a BiConsumer which does nothing every time
     */
    public static <T, U> BiConsumer<T, U> voidBiConsumer() {
        return VOID_BICONSUMER;
    }

    /**
     * Gets a {@link Supplier} which returns {@code null} every time.
     *
     * @param <T> the type for the Supplier to return
     * @return a Supplier which returns {@code null} every time
     */
    public static <T> Supplier<T> nullSupplier() {
        return NULL_SUPPLIER;
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
        return TRUE_PREDICATE;
    }

    /**
     * Gets a {@link Predicate} which returns {@code false} every time.
     *
     * @param <T> the type for the Predicate to accept
     * @return a Predicate which returns {@code false} every time
     */
    public static <T> Predicate<T> falsePredicate() {
        return FALSE_PREDICATE;
    }

    // Various FunctionalInterface implementations

    private static final Consumer VOID_CONSUMER = (e) -> {
    };
    private static final BiConsumer VOID_BICONSUMER = (a, b) -> {
    };
    private static final Supplier NULL_SUPPLIER = () -> null;
    private static final Predicate TRUE_PREDICATE = (e) -> true;
    private static final Predicate FALSE_PREDICATE = (e) -> false;
    private static final BiPredicate EQUAL_NULLABLE_PREDICATE = (a, b) -> {
        if (a == null || b == null) {
            return a == null && b == null;
        } else {
            return a.equals(b);
        }
    };

    private Functions() {
    }
}
