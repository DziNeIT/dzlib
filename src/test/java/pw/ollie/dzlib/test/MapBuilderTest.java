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

import pw.ollie.dzlib.collect.builder.MapBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapBuilderTest {
    @Test
    @SuppressWarnings("unchecked")
    public void runTest() {
        Assert.assertTrue(new MapBuilder<>(new HashMap<>()).build() instanceof HashMap);

        final Map<String, String> map = new MapBuilder<String, String>().put(Arrays.asList("key1", "key2"), Arrays.asList("val1", "val2")).build();
        Assert.assertTrue(map.containsKey("key1"));
        Assert.assertTrue(map.containsKey("key2"));
        Assert.assertTrue(map.containsValue("val1"));
        Assert.assertTrue(map.containsValue("val2"));

        final Map<String, String> vanilla = new HashMap<>();
        vanilla.put("key1", "val1");
        vanilla.put("key2", "val2");
        Assert.assertEquals(map.toString(), vanilla.toString());
    }
}
