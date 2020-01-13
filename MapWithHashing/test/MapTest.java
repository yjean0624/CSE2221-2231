import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Runting Shao, Xingyue Zhao
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @param
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /*
     * Tests for constructor
     */
    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.constructorTest();
        Map<String, String> mExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIntArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.constructorTest();
        Map<String, String> mExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /*
     * Tests for kernel methods
     */

    @Test
    public final void testAdd() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "B");
        m.add("C", "D");
        Map<String, String> mExpected = this.createFromArgsRef("A", "B", "C",
                "D");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddFive() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "B");
        m.add("C", "D");
        m.add("E", "F");
        m.add("G", "H");
        m.add("I", "J");
        m.add("K", "L");
        Map<String, String> mExpected = this.createFromArgsRef("A", "B", "C",
                "D", "E", "F", "G", "H", "I", "J", "K", "L");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddTwenty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "a");
        m.add("B", "b");
        m.add("C", "c");
        m.add("D", "d");
        m.add("E", "e");
        m.add("F", "f");
        m.add("G", "g");
        m.add("H", "h");
        m.add("I", "i");
        m.add("J", "j");
        m.add("K", "k");
        m.add("L", "l");
        m.add("M", "m");
        m.add("N", "n");
        m.add("O", "o");
        m.add("P", "p");
        m.add("Q", "q");
        m.add("R", "r");
        m.add("S", "s");
        m.add("T", "t");
        m.add("U", "u");
        Map<String, String> mExpected = this.createFromArgsRef("A", "a", "B",
                "b", "C", "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h",
                "I", "i", "J", "j", "K", "k", "L", "l", "M", "m", "N", "n", "O",
                "o", "P", "p", "Q", "q", "R", "r", "S", "s", "T", "t", "U",
                "u");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest();
        m.add("A", "B");
        Map<String, String> mExpected = this.createFromArgsRef("A", "B");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveToZero() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "B");
        m.remove("A");
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemove() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "B", "C", "D");
        m.remove("A");
        Map<String, String> mExpected = this.createFromArgsRef("C", "D");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFive() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "B", "C", "D", "E",
                "F", "G", "H", "I", "J", "K", "L");
        m.remove("A");
        m.remove("C");
        m.remove("E");
        m.remove("G");
        m.remove("I");
        Map<String, String> mExpected = this.createFromArgsRef("K", "L");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveTwenty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "a", "B", "b", "C",
                "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i",
                "J", "j", "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P",
                "p", "Q", "q", "R", "r", "S", "s", "T", "t", "U", "u");
        m.remove("A");
        m.remove("B");
        m.remove("C");
        m.remove("D");
        m.remove("E");
        m.remove("F");
        m.remove("G");
        m.remove("H");
        m.remove("I");
        m.remove("J");
        m.remove("K");
        m.remove("L");
        m.remove("M");
        m.remove("N");
        m.remove("O");
        m.remove("P");
        m.remove("Q");
        m.remove("R");
        m.remove("S");
        m.remove("T");
        Map<String, String> mExpected = this.createFromArgsRef("U", "u");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveAny() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "a", "B", "b");
        Map<String, String> mExpected = this.createFromArgsRef("A", "a", "B",
                "b");
        Pair<String, String> re = m.removeAny();
        String key = re.key();
        String value = re.value();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.hasKey(key));
        Assert.assertTrue(mExpected.hasValue(value));
        mExpected.remove(key);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveAnyToZero() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "a");
        Map<String, String> mExpected = this.createFromArgsRef("A", "a");
        Pair<String, String> re = m.removeAny();
        String key = re.key();
        String value = re.value();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.hasKey(key));
        Assert.assertTrue(mExpected.hasValue(value));
        mExpected.remove(key);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);

    }

    @Test
    public final void testRemoveAnyFive() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "a", "B", "b", "C",
                "c", "D", "d", "E", "e", "F", "f");
        Map<String, String> mExpected = this.createFromArgsRef("A", "a", "B",
                "b", "C", "c", "D", "d", "E", "e", "F", "f");
        //the first removeAny pair
        Pair<String, String> re1 = m.removeAny();
        String key1 = re1.key();
        String value1 = re1.value();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.hasKey(key1));
        Assert.assertTrue(mExpected.hasValue(value1));
        mExpected.remove(key1);
        //the second removeAny pair
        Pair<String, String> re2 = m.removeAny();
        String key2 = re2.key();
        String value2 = re2.value();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.hasKey(key2));
        Assert.assertTrue(mExpected.hasValue(value2));
        mExpected.remove(key2);
        //the third removeAny pair
        Pair<String, String> re3 = m.removeAny();
        String key3 = re3.key();
        String value3 = re3.value();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.hasKey(key3));
        Assert.assertTrue(mExpected.hasValue(value3));
        mExpected.remove(key3);
        //the fourth removeAny pair
        Pair<String, String> re4 = m.removeAny();
        String key4 = re4.key();
        String value4 = re4.value();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.hasKey(key4));
        Assert.assertTrue(mExpected.hasValue(value4));
        mExpected.remove(key4);
        //the fifth removeAny pair
        Pair<String, String> re5 = m.removeAny();
        String key5 = re5.key();
        String value5 = re5.value();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.hasKey(key5));
        Assert.assertTrue(mExpected.hasValue(value5));
        mExpected.remove(key5);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);

    }

    @Test
    public final void testValue() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "B");
        Map<String, String> mExpected = this.createFromArgsRef("A", "B");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(m.value("A"), mExpected.value("A"));
        assertEquals(mExpected, m);
    }

    @Test
    public final void testHasKeyTrue() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "B");
        Map<String, String> mExpected = this.createFromArgsRef("A", "B");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(m.hasKey("A"), true);
    }

    @Test
    public final void testHasKeyFalse() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "B");
        Map<String, String> mExpected = this.createFromArgsRef("A", "B");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(m.hasKey("B"), false);
    }

    @Test
    public final void testSizeZero() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(m.size(), 0);
    }

    @Test
    public final void testSizeFive() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "B", "C", "D", "E",
                "F", "G", "H", "I", "J");
        Map<String, String> mExpected = this.createFromArgsRef("A", "B", "C",
                "D", "E", "F", "G", "H", "I", "J");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(m.size(), 5);
    }

    @Test
    public final void testSizeTwenty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "a", "B", "b", "C",
                "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i",
                "J", "j", "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P",
                "p", "Q", "q", "R", "r", "S", "s", "T", "t");
        Map<String, String> mExpected = this.createFromArgsRef("A", "a", "B",
                "b", "C", "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h",
                "I", "i", "J", "j", "K", "k", "L", "l", "M", "m", "N", "n", "O",
                "o", "P", "p", "Q", "q", "R", "r", "S", "s", "T", "t");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(m.size(), 20);
    }

    @Test
    public final void testSizeTwo() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "B", "C", "D");
        Map<String, String> mExpected = this.createFromArgsRef("A", "B", "C",
                "D");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(m.size(), 2);
    }
}
