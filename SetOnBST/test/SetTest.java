import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Runting Shao & Xingyue Zhao
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size

    /*
     * Tests for constructor
     */
    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Set<String> m = this.constructorTest();
        Set<String> mExpected = this.constructorRef();
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
        Set<String> m = this.createFromArgsTest("A", "B");
        m.add("C");
        Set<String> mExpected = this.createFromArgsRef("A", "B", "C");
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
        Set<String> m = this.createFromArgsTest("A", "B");
        m.add("C");
        m.add("D");
        m.add("E");
        m.add("F");
        m.add("G");
        Set<String> mExpected = this.createFromArgsRef("A", "B", "C", "D", "E",
                "F", "G");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddFromEmpty() {
        /*
         * Set up variables and call method under test
         */
        Set<String> m = this.createFromArgsTest();
        m.add("A");
        Set<String> mExpected = this.createFromArgsRef("A");
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
        Set<String> m = this.createFromArgsTest("A", "B", "C", "D");
        m.remove("A");
        Set<String> mExpected = this.createFromArgsRef("B", "C", "D");
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
        Set<String> m = this.createFromArgsTest("A", "B", "C", "D", "E", "F");
        m.remove("A");
        m.remove("B");
        m.remove("C");
        m.remove("D");
        m.remove("E");
        Set<String> mExpected = this.createFromArgsRef("F");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveToEmpty() {
        /*
         * Set up variables and call method under test
         */
        Set<String> m = this.createFromArgsTest("A");
        m.remove("A");
        Set<String> mExpected = this.createFromArgsRef();
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
        Set<String> m = this.createFromArgsTest("A", "B");
        Set<String> mExpected = this.createFromArgsRef("A", "B");
        String re = m.removeAny();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.contains(re));
        mExpected.remove(re);
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
        Set<String> m = this.createFromArgsTest("A", "B", "C", "D", "E", "F");
        Set<String> mExpected = this.createFromArgsRef("A", "B", "C", "D", "E",
                "F");
        //the first removeAny pair
        String re1 = m.removeAny();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.contains(re1));
        mExpected.remove(re1);
        //the second removeAny pair
        String re2 = m.removeAny();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.contains(re2));
        mExpected.remove(re2);
        //the third removeAny pair
        String re3 = m.removeAny();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.contains(re3));
        mExpected.remove(re3);
        //the fourth removeAny pair
        String re4 = m.removeAny();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.contains(re4));
        mExpected.remove(re4);
        //the fifth removeAny pair
        String re5 = m.removeAny();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.contains(re5));
        mExpected.remove(re5);
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
        Set<String> m = this.createFromArgsTest("A");
        Set<String> mExpected = this.createFromArgsRef("A");
        String re = m.removeAny();
        /*
         * Assert those conditions are true
         */
        Assert.assertTrue(mExpected.contains(re));
        mExpected.remove(re);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testContainsTrue() {
        /*
         * Set up variables and call method under test
         */
        Set<String> m = this.createFromArgsTest("A");
        Set<String> mExpected = this.createFromArgsRef("A");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(m.contains("A"), mExpected.contains("A"));
    }

    @Test
    public final void testContainsFalse() {
        /*
         * Set up variables and call method under test
         */
        Set<String> m = this.createFromArgsTest("A");
        Set<String> mExpected = this.createFromArgsRef("A");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(m.contains("B"), mExpected.contains("B"));
    }

    @Test
    public final void testContainsTrueEmpty() {
        /*
         * Set up variables and call method under test
         */
        Set<String> m = this.createFromArgsTest();
        Set<String> mExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(m.contains(""), mExpected.contains(""));
    }

    @Test
    public final void testContainsFalseEmpty() {
        /*
         * Set up variables and call method under test
         */
        Set<String> m = this.createFromArgsTest();
        Set<String> mExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(m.contains("A"), mExpected.contains("A"));
    }

    @Test
    public final void testSizeZero() {
        /*
         * Set up variables and call method under test
         */
        Set<String> m = this.createFromArgsTest();
        Set<String> mExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(m.size(), 0);
    }

    @Test
    public final void testSizeTwo() {
        /*
         * Set up variables and call method under test
         */
        Set<String> m = this.createFromArgsTest("A", "B");
        Set<String> mExpected = this.createFromArgsRef("A", "B");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(m.size(), 2);
    }

    @Test
    public final void testSizeTen() {
        /*
         * Set up variables and call method under test
         */
        Set<String> m = this.createFromArgsTest("A", "B", "C", "D", "E", "F",
                "G", "H", "I", "J");
        Set<String> mExpected = this.createFromArgsRef("A", "B", "C", "D", "E",
                "F", "G", "H", "I", "J");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(m.size(), 10);
    }

}
