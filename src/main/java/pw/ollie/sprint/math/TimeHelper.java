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
package pw.ollie.sprint.math;

import java.util.concurrent.TimeUnit;

/**
 * Utilities for dealing with time and time measurements.
 */
public class TimeHelper {
    /**
     * Calculates the difference, in milliseconds, between the provided time and
     * the current time. If the provided time is later than the current time,
     * the result will be negative.
     *
     * @param from the time to calculate the difference from
     * @return the difference, in millis, between {@code from} and the current
     *         time
     */
    public static long getTimeDifference(long from) {
        return MathHelper.getDifference(from, getCurrentTimeMillis());
    }

    /**
     * Gets the current time in milliseconds, converting from the nanoseconds
     * returned by {@link System#nanoTime()} with {@link TimeUnit#convert(long,
     * TimeUnit)}
     *
     * @return the current time, in milliseconds
     */
    public static long getCurrentTimeMillis() {
        return TimeUnit.MILLISECONDS.convert(System.nanoTime(),
                TimeUnit.NANOSECONDS);
    }

    private TimeHelper() {
        throw new UnsupportedOperationException();
    }
}
