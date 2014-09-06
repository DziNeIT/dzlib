package pw.ollie.sprint.test.reflect;

import org.junit.Assert;
import org.junit.Test;

import pw.ollie.sprint.reflect.sun.UnsafeReflection;

public class ModifyStringTest {
    @Test
    public void runTest() {
        char[] charArray = { 'g', 'o', 'o', 'd', 'b', 'y', 'e' };
        UnsafeReflection.modifyString("hello", "goodbye");
        check("hello".toCharArray(), charArray);
    }

    private void check(char[] one, char[] two) {
        if (one.length != two.length) {
            Assert.fail("length (" + one.length + " vs " + two.length + ")");
        }

        for (int i = 0; i < one.length; i++) {
            if (!(one[i] == two[i])) {
                Assert.fail(one[i] + " vs " + two[i] + " @ " + i);
            }
        }
    }
}
