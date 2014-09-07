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

import pw.ollie.sprint.math.MathHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;

/**
 * Utility methods for manipulation of {@link String}s
 *
 * @author Ollie [DziNeIT]
 * (some code is from the internet and isn't attributed because I don't know
 * who wrote it, and there are different people claiming ownership of it)
 */
public class Strings {
    /**
     * All the standard ASCII alphanumeric characters, both cases.
     */
    private static final char[] ALPHANUM = new char[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
    };

    /**
     * All the consonants, both cases. This isn't currently used.
     */
    private static final char[] CONSONANTS = new char[] {
            'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q',
            'r', 's', 't', 'v', 'w', 'x', 'z', 'B', 'C', 'D', 'F', 'G', 'H',
            'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Z'
    };

    /**
     * Gets all consonants. This method calls {@link Object#clone()} and thus
     * should not be called frequently. Cache the value.
     *
     * @return an array of all consonants
     */
    public static char[] consonants() {
        return CONSONANTS.clone();
    }

    /**
     * Generates a random <code>String</code> of the specified length
     *
     * @param length the length of the String
     * @param random the random number generator to use
     * @return a randomly generator {@link String} of the given length
     * @see {@link #randomString()} redirect to this method
     * @see {@link #randomString(int)} redirect to this method
     */
    public static String randomString(int length, Random random) {
        if (length < 0) {
            throw new IllegalArgumentException(
                    "Cannot generate string of length smaller than 0!");
        } else if (length == 0) {
            return "";
        } else if (random == null) {
            random = MathHelper.random();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUM[random.nextInt(ALPHANUM.length)]);
        }
        return sb.toString();
    }

    /**
     * Joins all of the arguments in the given String[], starting from the given
     * {@code start}, separating each string by the provided {@code delimiter}.
     *
     * @param strings the String[] to retrieve the arguments from
     * @param start the index to start joining arguments from
     * @param delimiter the String to separate elements with
     * @return a string of elements in the given String[], starting at the
     *         specified start index
     * @see {@link #join(String[])} redirect to this method
     * @see {@link #join(String[], int)} redirect to this method
     * @see {@link #join(String[], String)} redirect to this method
     */
    public static String join(String[] strings, int start, String delimiter) {
        if (strings.length <= start) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < strings.length; i++) {
            builder.append(strings[i]).append(delimiter);
        }
        return builder.toString().trim();
    }

    /**
     * Checks whether the given characters are equal, ignoring case.
     *
     * @param one a character
     * @param two another character
     * @return whether the two given characters are equal, ignoring case
     */
    public static boolean equalsIgnoreCase(char one, char two) {
        return Character.toLowerCase(one) == Character.toLowerCase(two);
    }

    /**
     * Returns true if {@code s1} contains {@code s2}, ignoring the case of the
     * strings.
     *
     * @param s1 the string to check the contents of
     * @param s2 the string for which the {@code s1} is checked
     * @return whether {@code s1} contains {@code s2}, ignoring case
     */
    public static boolean containsIgnoreCase(String s1, String s2) {
        return s1.toLowerCase().contains(s2.toLowerCase());
    }

    /**
     * Checks whether the given {@code str} {@link String} starts with the given
     * {@code prefix} {@link String}, ignoring case.
     *
     * @param str the main string
     * @param prefix the string to test for the presence of
     * @return whether {@code str} starts with {@code prefix} ignoring case
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str == null || prefix == null || prefix.length() > str.length()) {
            return false;
        }

        for (int i = 0; i < prefix.length(); i++) {
            if (!equalsIgnoreCase(prefix.charAt(i), str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the given {@link String} repeated the given {@code amount} of
     * times, with each repetition separated by the {@link String} given as the
     * {@code delimiter}.
     *
     * @param repeat the {@link String} to repeat
     * @param amount the amount of times to repeat the given string
     * @param delimiter the separator for each repetition
     * @return the given string repeated the given amount of times separated by
     *         the given delimiter
     * @see {@link #repeated(String, int)} for shortcut method
     */
    public static String repeated(String repeat, int amount, String delimiter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            builder.append(repeat).append(delimiter);
        }
        return builder.toString();
    }

    /**
     * Find Levenshtein distance between two Strings.
     *
     * From https://github.com/thorikawa/levenshtein-distance-search-sample/
     *
     * @param s the first String, not  {@code null}
     * @param t the second String, not  {@code null}
     * @return the result of calculating the levenshtein distance between them
     * @throws IllegalArgumentException if either param is {@code null}
     */
    public static int getLevenshteinDistance(String s, String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        int n = s.length(), m = t.length();

        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }

        int[] p = new int[n + 1], d = new int[n + 1], _d;
        int i, j, cost;
        char tj;

        for (i = 0; i <= n; ++i) {
            p[i] = i;
        }

        for (j = 1; j <= m; ++j) {
            tj = t.charAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; ++i) {
                cost = s.charAt(i - 1) == tj ? 0 : 1;
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1]
                        + cost);
            }

            _d = p;
            p = d;
            d = _d;
        }

        return p[n];
    }

    /**
     * Looks up the given name in the given {@link Map}, for which the keys must
     * be {@link String}s. If {@code fuzzy} is {@code true}, levenshtein
     * distance will be used to find the closest name to the given name in the
     * given {@link Map}.
     *
     * @param lookup the {@link Map} to perform the lookup in
     * @param name the name to lookup in the {@link Map}
     * @param fuzzy whether to perform a fuzzy search
     * @param <T> the type of value the {@link Map} contains
     * @param tolerance how far from the given name the key is allowed to be
     *        if a levenshtein search is performed
     * @return the value for the given name in the {@link Map}, or the value for
     *         the closest key to the given name in the {@link Map} if fuzzy is
     *         {@code true}
     * @see {@link #lookup(Map, String, boolean)} for shortcut method
     */
    public static <T> Optional<T> lookup(Map<String, T> lookup, String name,
            boolean fuzzy, int tolerance) {
        name = name.replaceAll("[ _]", "").toLowerCase();

        T type = lookup.get(name);
        if (type != null) {
            return Optional.of(type);
        }
        if (!fuzzy) {
            return Optional.empty();
        }

        int lowest = -1;
        for (Entry<String, T> entry : lookup.entrySet()) {
            String key = entry.getKey();
            if (key.charAt(0) != name.charAt(0)) {
                continue;
            }

            int dist = getLevenshteinDistance(key, name);
            if (dist < tolerance && (dist < lowest || lowest == -1)) {
                lowest = dist;
                type = entry.getValue();
            }
        }

        return Optional.of(type);
    }

    /**
     * Takes in a String[] & joins quoted elements, returning a new String[]
     * whereby multiple elements in the original String[] which are enclosed in
     * quotations become a single element in the new iterate.
     *
     * This method supports both single quotes ('') and double quotes ("").
     *
     * @param strings the original strings to join by quotes
     * @return a String[] from the given String[] but with quotation-enclosed
     *         elements as one element
     */
    public static String[] joinQuoted(String[] strings) {
        List<String> parsed = new ArrayList<>(strings.length);
        List<String> fallback = new ArrayList<>(strings.length);
        StringBuilder quoted = new StringBuilder();
        int currentState = 0; // 0 = none, 1 = single quotes, 2 = double quotes

        for (String string : strings) {
            if (string.length() == 0) {
                continue;
            }

            fallback.add(string);
            if (currentState == 0) {
                char first = string.charAt(0);
                if (first == '\'' || first == '\"') {
                    currentState = first == '\'' ? 1 : 2;
                    string = string.length() > 1 ? string.substring(1) : "";
                    char last = string.charAt(string.length() - 1);
                    if (currentState == 1 && last == '\'' && (string.length()
                            <= 1 || string.charAt(string.length() - 2)
                            != '\\')) {
                        string = string.length() > 1 ? string.substring(0,
                                string.length() - 1) : "";
                        quoted.append(string);
                        parsed.add(quoted.toString());
                        quoted.delete(0, quoted.length());
                        fallback.clear();
                        currentState = 0;
                    } else if (currentState == 2 && last == '\"' && (string
                            .length() <= 1 || string.charAt(string.length() - 2)
                            != '\\')) {
                        string = string.length() > 1 ?
                                string.substring(0, string.length() - 1) : "";
                        quoted.append(string);
                        parsed.add(quoted.toString());
                        quoted.delete(0, quoted.length());
                        fallback.clear();
                        currentState = 0;
                    }

                    quoted.append(string);
                } else {
                    parsed.add(string);
                }
            } else {
                quoted.append(' ');
                char last = string.charAt(string.length() - 1);
                if ((last == '\'' && currentState == 1 || last == '\"' &&
                        currentState == 2) && (string.length() <= 1 ||
                        string.charAt(string.length() - 2) != '\\')) {
                    string = string.length() > 1 ? string.substring(0,
                            string.length() - 1) : "";
                    quoted.append(string);
                    parsed.add(quoted.toString());
                    quoted.delete(0, quoted.length());
                    fallback.clear();
                    currentState = 0;
                }
                quoted.append(string);
            }
        }
        if (currentState != 0 && !fallback.isEmpty()) {
            parsed.addAll(fallback);
        }
        return parsed.toArray(new String[parsed.size()]);
    }

    /**
     * Utility method for creating a {@link String} {@link List} of the given
     * {@link String}s
     *
     * @param strings the strings to make a {@link List} from
     * @return a {@link List} of the given strings
     */
    public static List<String> list(String... strings) {
        return Arrays.asList(strings);
    }

    // Shortcut methods are all below this point

    /**
     * Generates a random {@link String} of up to 10 characters
     *
     * @return a random {@link String} of length up to 10
     * @see {@link Strings#randomString(int)}
     */
    public static String randomString() {
        Random rand = MathHelper.random();
        return randomString(rand.nextInt(10), rand);
    }

    /**
     * Generates a random <code>String</code> of the specified length
     *
     * @param length the length of the String
     * @return a randomly generator {@link String} of the given length
     * @see {@link #randomString(int, Random)}
     */
    public static String randomString(int length) {
        return randomString(length, MathHelper.random());
    }

    /**
     * Joins all of the elements in the given String[], separating each element
     * by a single whitespace character.
     *
     * @param strings the String[] to join
     * @return a joined String of all elements in the given String[]
     * @see {@link #join(String[], int, String)}
     */
    public static String join(String[] strings) {
        return join(strings, 0, " ");
    }

    /**
     * Joins all of the arguments in the given String[], starting from the
     * specified start index. All arguments are separated from each other by a
     * single whitespace character. If there are no arguments covered by the
     * range after evaluating the value of {@code start}, {@code null} is
     * returned.
     *
     * @param strings the String[] to retrieve the arguments from
     * @param start the index to start joining arguments from
     * @return a string of elements in the given String[], starting at the
     *         specified start index
     * @see {@link #join(String[], int, String)}
     */
    public static String join(String[] strings, int start) {
        return join(strings, start, " ");
    }

    /**
     * Joins all of the arguments in the given String[], separating each string
     * by the provided {@code delimiter}.
     *
     * @param strings the String[] to retrieve the arguments from
     * @param delimiter the String to separate elements with
     * @return a string of elements in the given String[], starting at the
     *         specified start index
     * @see {@link #join(String[], int, String)}
     */
    public static String join(String[] strings, String delimiter) {
        return join(strings, 0, delimiter);
    }

    /**
     * Returns the given {@link String} repeated the given {@code amount} of
     * times.
     *
     * @param repeat the {@link String} to repeat
     * @param amount the amount of times to repeat the given string
     * @return the given amount of repetitions of the given string
     * @see {@link #repeated(String, int, String)}
     */
    public static String repeated(String repeat, int amount) {
        return repeated(repeat, amount, "");
    }

    /**
     * Looks up the given name in the given {@link Map}, for which the keys must
     * be {@link String}s. If {@code fuzzy} is {@code true}, levenshtein
     * distance will be used to find the closest name to the given name in the
     * given {@link Map}.
     *
     * @param lookup the {@link Map} to perform the lookup in
     * @param name the name to lookup in the {@link Map}
     * @param fuzzy whether to perform a fuzzy search
     * @param <T> the type of value the {@link Map} contains
     * @return the value for the given name in the {@link Map}, or the value for
     *         the closest key to the given name in the {@link Map} if fuzzy is
     *         {@code true}
     * @see {@link #lookup(Map, String, boolean, int)}
     */
    public static <T> Optional<T> lookup(Map<String, T> lookup, String name,
            boolean fuzzy) {
        // default to a tolerance of 2 characters
        return lookup(lookup, name, fuzzy, 2);
    }

    private Strings() {
        throw new UnsupportedOperationException();
    }
}
