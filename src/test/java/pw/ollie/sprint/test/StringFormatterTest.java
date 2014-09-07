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
package pw.ollie.sprint.test;

import org.junit.Assert;
import org.junit.Test;

import pw.ollie.sprint.format.BatchStringFormatter;
import pw.ollie.sprint.format.LowerCaseStringFormatter;
import pw.ollie.sprint.format.NullStringFormatter;
import pw.ollie.sprint.format.PrefixedStringFormatter;
import pw.ollie.sprint.format.SuffixedStringFormatter;
import pw.ollie.sprint.format.TrimmingStringFormatter;
import pw.ollie.sprint.format.UpperCaseStringFormatter;
import pw.ollie.sprint.util.Strings;

public class StringFormatterTest {
    @Test
    public void runTest() {
        // Test Strings.randomString too because why not
        String testStr = Strings.randomString(10);
        Assert.assertTrue(testStr.length() < 11);
        System.out.println(testStr);

        // Test some individual StringFormatters
        UpperCaseStringFormatter uc = new UpperCaseStringFormatter();
        Assert.assertEquals(uc.format(testStr), testStr.toUpperCase());

        LowerCaseStringFormatter lc = new LowerCaseStringFormatter();
        Assert.assertEquals(lc.format(testStr), testStr.toLowerCase());

        PrefixedStringFormatter prefixed = new PrefixedStringFormatter("BOB ");
        Assert.assertEquals(prefixed.format("test"), "BOB test");

        SuffixedStringFormatter suffixed = new SuffixedStringFormatter(" NO");
        Assert.assertEquals(suffixed.format("test"), "test NO");

        TrimmingStringFormatter trimmer = new TrimmingStringFormatter();
        Assert.assertEquals(trimmer.format("  f "), "f");

        NullStringFormatter same = new NullStringFormatter();
        Assert.assertEquals(same.format("f"), "f");

        // Test BatchStringFormatter
        BatchStringFormatter batch = new BatchStringFormatter();
        batch.add(uc).add(prefixed).add(suffixed);
        Assert.assertEquals(batch.format("tester"), "BOB TESTER NO");
    }
}
