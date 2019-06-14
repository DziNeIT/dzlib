package pw.ollie.dzlib.hashing;

/**
 * A class for hashing 3 21 bit integers into a long, and vice-versa.
 * <p>
 * https://github.com/flow/commons/blob/develop/src/main/java/com/flowpowered/commons/hashing/Int21TripleHashed.java
 */
public final class Int21TripleHashed {
    /**
     * Packs the most significant and the twenty least significant of each int into a <code>long</code>
     *
     * @param x an <code>int</code> value
     * @param y an <code>int</code> value
     * @param z an <code>int</code> value
     * @return the most significant and the twenty least significant of each int packed into a <code>long</code>
     */
    public static long key(int x, int y, int z) {
        return ((long) ((x >> 11) & 0x100000 | x & 0xFFFFF)) << 42 | ((long) ((y >> 11) & 0x100000 | y & 0xFFFFF)) << 21 | ((z >> 11) & 0x100000 | z & 0xFFFFF);
    }

    /**
     * Gets the first 21-bit integer value from a long key
     *
     * @param key to get from
     * @return the first 21-bit integer value in the key
     */
    public static int key1(long key) {
        return keyInt((key >> 42) & 0x1FFFFF);
    }

    /**
     * Gets the second 21-bit integer value from a long key
     *
     * @param key to get from
     * @return the second 21-bit integer value in the key
     */
    public static int key2(long key) {
        return keyInt((key >> 21) & 0x1FFFFF);
    }

    /**
     * Gets the third 21-bit integer value from a long key
     *
     * @param key to get from
     * @return the third 21-bit integer value in the key
     */
    public static int key3(long key) {
        return keyInt(key & 0x1FFFFF);
    }

    private static int keyInt(long key) {
        return (int) (key - ((key & 0x100000) << 1));
    }

    private Int21TripleHashed() {
        throw new UnsupportedOperationException();
    }
}
