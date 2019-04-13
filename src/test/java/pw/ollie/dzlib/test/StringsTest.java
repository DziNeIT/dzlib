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
package pw.ollie.dzlib.test;

import org.junit.Assert;
import org.junit.Test;

import pw.ollie.dzlib.util.Strings;

import java.util.HashMap;
import java.util.Map;

public class StringsTest {
    @Test
    public void runTest() {
        final String hippo = "hippo";
        final String gyppo = "gyppo";
        Assert.assertEquals("Levenshtein failed", 2,
                Strings.getLevenshteinDistance(hippo, gyppo));

        final Map<String, String> map = new HashMap<String, String>() {
            {
                put("one", "1");
                put("two", "2");
                put("three", "3");
                put("four", "4");
            }
        };

        Assert.assertEquals(Strings.lookup(map, "on", true).get(), "1");

        final String[] original = { "hi", "bye" };
        Assert.assertEquals(original.length,
                Strings.joinQuoted(original).length);
        final String[] withQuotes = { "'hello", "world'" };
        final String[] j = Strings.joinQuoted(withQuotes);
        Assert.assertEquals(1, j.length);
        Assert.assertEquals(j[0], "hello world");


        final String[] args = { "hello", "\"world", "is", "cool\"", "nope" };
        final String[] joined = Strings.joinQuoted(args);
        Assert.assertEquals(joined.length, 3);
        Assert.assertEquals(joined[0], "hello");
        Assert.assertEquals(joined[1], "world is cool");
        Assert.assertEquals(joined[2], "nope");
    }
}
