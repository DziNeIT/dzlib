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
package pw.ollie.dzlib.util;

/**
 * Fast pseudo-random values.
 * <p>
 * https://github.com/matheus23/Utils/blob/master/Utils/src/org/matheusdev/util/FastRand.java
 */
public class FastRandom {
    private long seed;

    public FastRandom(long seed) {
        this.seed = seed;
    }

    public FastRandom() {
        this.seed = generateSeed();
    }

    public long nextLong() {
        seed ^= (seed << 21);
        seed ^= (seed >>> 35);
        seed ^= (seed << 4);
        return seed;
    }

    public long nextLong(long max) {
        return nextAbsLong() % max;
    }

    public long nextLong(long min, long max) {
        return nextLong(max - min) + min;
    }

    public long nextAbsLong() {
        return Math.abs(nextLong());
    }

    public int nextInt() {
        return (int) nextLong();
    }

    public int nextInt(int max) {
        return nextAbsInt() % max;
    }

    public int nextInt(int min, int max) {
        return nextInt(max - min) + min;
    }

    public int nextAbsInt() {
        return Math.abs((int) nextLong());
    }

    public double nextDouble() {
        return nextLong() / (Long.MAX_VALUE - 1d);
    }

    public double nextDouble(double min, double max) {
        return nextAbsDouble() * (max - min) + min;
    }

    public double nextAbsDouble() {
        return (nextDouble() + 1.0) / 2.0;
    }

    public float nextFloat() {
        return nextLong() / (Long.MAX_VALUE - 1f);
    }

    public float nextFloat(float min, float max) {
        return nextAbsFloat() * (max - min) + min;
    }

    public float nextAbsFloat() {
        return (nextFloat() + 1.0f) / 2.0f;
    }

    public boolean nextBoolean() {
        return nextLong() > 0;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    private static long generateSeed() {
        return System.currentTimeMillis() + System.nanoTime();
    }
}
