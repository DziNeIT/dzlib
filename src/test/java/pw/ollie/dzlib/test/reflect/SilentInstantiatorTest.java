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
package pw.ollie.dzlib.test.reflect;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import pw.ollie.dzlib.reflect.sun.SilentInstantiator;

@RunWith(JUnit4.class)
public class SilentInstantiatorTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void runTest() {
        // if constructor was called, we'd get UnsupportedOperationException
        CantInstantiate cannot = SilentInstantiator.newInstance(
                CantInstantiate.class);

        Assert.assertNotNull("cannot null - creation failed", cannot);
        Assert.assertEquals("u wot m8 how u do dis",
                cannot.coolMethodWhichMakesYourLifeOneHundredTimesBetterButYouCanNeverCallItBecauseYouCannotGetAnInstanceOfCantInstantiateLolLolLol());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        exception.expect(NullPointerException.class);
        System.out.println(SilentInstantiator.newInstance(String.class));
    }

    private final class CantInstantiate {
        private boolean instantiated;

        private CantInstantiate() {
            instantiated = true;
            // haha! you can't instantiate this class!
            throw new UnsupportedOperationException();
        }

        public String coolMethodWhichMakesYourLifeOneHundredTimesBetterButYouCanNeverCallItBecauseYouCannotGetAnInstanceOfCantInstantiateLolLolLol() {
            if (!instantiated) {
                return "u wot m8 how u do dis";
            } else {
                return "haha u cant fool me with your method of bringing back the object after an exception in constructor!";
            }
        }
    }
}
