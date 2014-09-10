package pw.ollie.sprint.test;

import org.junit.Assert;
import org.junit.Test;

import pw.ollie.sprint.iterate.ArrayIterator;
import pw.ollie.sprint.iterate.EnumIterator;
import pw.ollie.sprint.util.MapBuilder.MapType;

public class IterationTest {
    @Test
    public void runTest() {
        int expected = MapType.values().length;
        EnumIterator<MapType> iterator = new EnumIterator<>(MapType.class);
        int currentCount = 0;

        while (iterator.hasNext()) {
            currentCount++;
            iterator.next();
        }

        Assert.assertEquals(expected, currentCount);

        int currentArrayCount = 0;
        ArrayIterator arrayIt = new ArrayIterator<>(MapType.values());

        while (arrayIt.hasNext()) {
            currentArrayCount++;
            arrayIt.next();
        }

        Assert.assertEquals(currentArrayCount, currentCount);
    }
}
