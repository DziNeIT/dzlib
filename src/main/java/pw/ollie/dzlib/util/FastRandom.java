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
