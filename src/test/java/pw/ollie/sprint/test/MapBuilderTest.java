package pw.ollie.sprint.test;

import org.junit.Assert;
import org.junit.Test;

import pw.ollie.sprint.util.MapBuilder;
import pw.ollie.sprint.util.Util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilderTest {
    @Test
    @SuppressWarnings("unchecked")
    public void runTest() {
        Assert.assertTrue(
                new MapBuilder<>(new HashMap<>()).build() instanceof HashMap);

        final Map<String, String> map = new MapBuilder<String, String>().put(
                Util.list("key1", "key2"), Util.list("val1", "val2")).build();
        Assert.assertTrue(map.containsKey("key1"));
        Assert.assertTrue(map.containsKey("key2"));
        Assert.assertTrue(map.containsValue("val1"));
        Assert.assertTrue(map.containsValue("val2"));

        final Map<String, String> vanilla = new HashMap<>();
        vanilla.put("key1", "val1");
        vanilla.put("key2", "val2");
        Assert.assertTrue(map.toString().equals(vanilla.toString()));
    }
}
