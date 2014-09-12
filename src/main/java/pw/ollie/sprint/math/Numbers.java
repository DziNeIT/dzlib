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

import java.util.Collection;

/**
 * Simple number-related helper methods.
 */
// not in MathHelper because it would just clog up the class and be annoying
public class Numbers {
    /**
     * Gets the sum of all of the given numbers.
     *
     * @param numbers the numbers to get the sum of
     * @return the sum of all the given numbers
     */
    public static double sum(Collection<? extends Number> numbers) {
        double sum = 0;
        for (Number num : numbers) {
            sum += num.doubleValue();
        }
        return sum;
    }

    /**
     * Gets the product of all of the given numbers.
     *
     * @param numbers the numbers to get the product of
     * @return the product of all the given numbers
     */
    public static double product(Collection<? extends Number> numbers) {
        double product = 1;
        for (Number num : numbers) {
            product *= num.doubleValue();
        }
        return product;
    }

    /**
     * Gets the mean average of all of the given numbers.
     *
     * @param numbers the numbers to get the mean average of
     * @return the mean average of all the given numbers
     */
    public static double mean(Collection<? extends Number> numbers) {
        return sum(numbers) / numbers.size();
    }

    /**
     * Gets the standard deviation of all of the given numbers.
     *
     * @param numbers the numbers to get the standard deviation of
     * @return the standard deviation of all the given numbers
     */
    public static double stddev(Collection<? extends Number> numbers) {
        double mean = mean(numbers);
        double result = 0;
        for (Number num : numbers) {
            double dev = num.doubleValue() - mean;
            result += dev * dev;
        }
        return Math.sqrt(result / numbers.size());
    }

    /**
     * Gets the sum of all of the given numbers.
     *
     * @param numbers the numbers to get the sum of
     * @return the sum of all the given numbers
     */
    public static double sum(Number... numbers) {
        double sum = 0;
        for (Number num : numbers) {
            sum += num.doubleValue();
        }
        return sum;
    }

    /**
     * Gets the product of all of the given numbers.
     *
     * @param numbers the numbers to get the product of
     * @return the product of all the given numbers
     */
    public static double product(Number... numbers) {
        double product = 1;
        for (Number num : numbers) {
            product *= num.doubleValue();
        }
        return product;
    }

    /**
     * Gets the mean average of all of the given numbers.
     *
     * @param numbers the numbers to get the mean average of
     * @return the mean average of all the given numbers
     */
    public static double mean(Number... numbers) {
        return sum(numbers) / numbers.length;
    }

    /**
     * Gets the standard deviation of all of the given numbers.
     *
     * @param numbers the numbers to get the standard deviation of
     * @return the standard deviation of all the given numbers
     */
    public static double stddev(Number... numbers) {
        double mean = mean(numbers);
        double result = 0;
        for (Number num : numbers) {
            double dev = num.doubleValue() - mean;
            result += dev * dev;
        }
        return Math.sqrt(result / numbers.length);
    }

    private Numbers() {
        throw new UnsupportedOperationException();
    }
}
