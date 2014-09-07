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
