import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

public class StringReassemblyTest {

    /*
     * Tests of combination
     */

    //routine
    @Test
    public void testCombination_understand_standing_5() {
        String str1 = "understand";
        String str2 = "standing";
        int overlap = 5;
        String combination = StringReassembly.combination(str1, str2, overlap);
        assertEquals(combination, "understanding");
        assertEquals(str1, "understand");
        assertEquals(str2, "standing");
        assertEquals(overlap, 5);
    }

    //boundary
    @Test
    public void testCombination_understand_ing_0() {
        String str1 = "understand";
        String str2 = "ing";
        int overlap = 0;
        String combination = StringReassembly.combination(str1, str2, overlap);
        assertEquals(combination, "understanding");
        assertEquals(str1, "understand");
        assertEquals(str2, "ing");
        assertEquals(overlap, 0);
    }

    //boundary
    @Test
    public void testCombination_understand_understand_10() {
        String str1 = "understand";
        String str2 = "understand";
        int overlap = 10;
        String combination = StringReassembly.combination(str1, str2, overlap);
        assertEquals(combination, "understand");
        assertEquals(str1, "understand");
        assertEquals(str2, "understand");
        assertEquals(overlap, 10);
    }

    //challenging
    @Test
    public void testCombination_standing_understand_0() {
        String str1 = "standing";
        String str2 = "understand";
        int overlap = 0;
        String combination = StringReassembly.combination(str1, str2, overlap);
        assertEquals(combination, "standingunderstand");
        assertEquals(str1, "standing");
        assertEquals(str2, "understand");
        assertEquals(overlap, 0);
    }

    /*
     * Tests of addToSetAvoidSubstrings
     */

    //normal
    @Test
    public void testAddToSetAvoidSubstrings_strSet1_under() {
        Set<String> strSet = new Set1L<>();
        strSet.add("stand");
        strSet.add("ing");
        String str = "under";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        Set<String> expect = new Set1L<>();
        expect.add("stand");
        expect.add("ing");
        expect.add("under");
        assertEquals(strSet, expect);
    }

    //challenging
    @Test
    public void testAddToSetAvoidSubstrings_strSet2_under() {
        Set<String> strSet = new Set1L<>();
        strSet.add("understand");
        strSet.add("ing");
        String str = "stand";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        Set<String> expect = new Set1L<>();
        expect.add("understand");
        expect.add("ing");
        assertEquals(expect, strSet);
    }

    //challenging
    @Test
    public void testAddToSetAvoidSubstrings_strSet3_under() {
        Set<String> strSet = new Set1L<>();
        strSet.add("under");
        strSet.add("ing");
        String str = "understand";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        Set<String> expect = new Set1L<>();
        expect.add("understand");
        expect.add("ing");
        assertEquals(expect, strSet);
    }

    /*
     * Tests of linesFromInput
     */

    //challenging
    @Test
    public void testLinesFromInput_input1() {
        Set<String> lines = new Set1L<>();
        SimpleReader input = new SimpleReader1L("data/sample1.txt");
        lines = StringReassembly.linesFromInput(input);
        Set<String> expect = new Set1L<>();
        expect.add("this");
        expect.add("a");
        expect.add("test");
        assertEquals(expect, lines);
    }

    //boundary
    @Test
    public void testLinesFromInput_input2() {
        Set<String> lines = new Set1L<>();
        SimpleReader input = new SimpleReader1L("data/sample2.txt");
        lines = StringReassembly.linesFromInput(input);
        Set<String> expect = new Set1L<>();
        assertEquals(expect, lines);
    }

    //normal
    @Test
    public void testLinesFromInput_input3() {
        Set<String> lines = new Set1L<>();
        SimpleReader input = new SimpleReader1L("data/sample3.txt");
        lines = StringReassembly.linesFromInput(input);
        Set<String> expect = new Set1L<>();
        expect.add("can");
        expect.add("you");
        expect.add("hear");
        assertEquals(expect, lines);
    }

}
