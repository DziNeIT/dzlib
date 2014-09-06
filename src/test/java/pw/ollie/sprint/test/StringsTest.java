package pw.ollie.sprint.test;

import org.junit.Assert;
import org.junit.Test;

import pw.ollie.sprint.util.Strings;

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
    }
}
