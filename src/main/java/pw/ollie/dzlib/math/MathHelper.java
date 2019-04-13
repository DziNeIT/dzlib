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
package pw.ollie.dzlib.math;

import java.util.concurrent.ThreadLocalRandom;

/**
 * General helper functions for math.
 *
 * @author Ollie [DziNeIT]
 */
public final class MathHelper {
    /**
     * Gets the difference between the two values. Will never be negative as the
     * smaller value will be subtracted from the larger one.
     *
     * @param l1 the first value
     * @param l2 the second value
     * @return the difference between the two values
     */
    public static long getDifference(long l1, long l2) {
        if (l1 > l2) {
            return l1 - l2;
        } else {
            return l2 - l1;
        }
    }

    /**
     * Converts a a pair of values from the cartesian coordinates system to the
     * polar coordinates system.
     *
     * http://en.wikipedia.org/wiki/Cartesian_coordinate_system
     * http://en.wikipedia.org/wiki/Polar_coordinate_system
     *
     * @param x cartesian x
     * @param y cartesian y
     * @return the given cartesian coordinates converted to polar coordinates
     */
    public static double[] cartesianToPolar(double x, double y) {
        double r = Math.sqrt((x * x) + (y * y));
        double theta = Math.toDegrees(Math.atan2(x, y));
        return new double[] { r, theta };
    }

    /**
     * Slightly modify the given float pseudo-randomly.
     *
     * @param f the float that should be modified slightly
     * @return a slightly modified version of given float
     */
    public static float randomise(float f) {
        float ret = f - 0.2f;
        ret += (2 * 0.2f) * random().nextFloat();
        return ret;
    }

    /**
     * Gets the {@link ThreadLocalRandom} instance for the current thread. This
     * should be used in concurrent applications. As of Java 7, we should never
     * use <code>Math.random()</code>
     *
     * Below text from http://www.javaspecialists.eu/archive/Issue198.html
     *
     * <p>Math.random() method delegates to a shared mutable instance of Random.
     * Since they use atomics, rather than locking, it is impossible for the
     * compound nextDouble() method to be atomic. Thus it is possible that
     * between calls to next(27) and next(26), other threads call next()</p>
     *
     * <p>If you had thousands of threads calling Math.random() at the same
     * time, your thread could be swapped out for long enough, so that
     * (int) (Math.random() + 1) could return 2.</p>
     *
     * @return the current thread's {@link ThreadLocalRandom}
     */
    public static ThreadLocalRandom random() {
        return ThreadLocalRandom.current();
    }

    private MathHelper() {
        throw new UnsupportedOperationException();
    }
}
