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
package pw.ollie.dzlib.concurrent;

import java.util.concurrent.ThreadLocalRandom;

/**
 * General utility methods for helping with concurrency.
 */
public final class ConcurrencyHelper {
    /**
     * Gets a {@link ConstantFuture} - a {@link Future} with a constant value -
     * with the given {@code value}.
     *
     * @param value the value to assign to the {@link ConstantFuture}
     * @param <T> the type of the Future
     * @return a new ConstantFuture with the given constant value
     */
    public static <T> ConstantFuture<T> constantFuture(T value) {
        return new ConstantFuture<>(value);
    }

    /**
     * Gets the {@link ThreadLocalRandom} for the current {@link Thread}.
     *
     * @return the current Thread's ThreadLocalRandom
     */
    public static ThreadLocalRandom concurrentRandom() {
        return ThreadLocalRandom.current();
    }

    private ConcurrencyHelper() {
        throw new UnsupportedOperationException(":(");
    }
}
