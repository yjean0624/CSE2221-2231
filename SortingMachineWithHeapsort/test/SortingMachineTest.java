import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Runting Shao & Xingyue Zhao
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddFromEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

    @Test
    public final void testAddFive() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "one", "two", "three", "four", "five");
        m.add("one");
        m.add("two");
        m.add("three");
        m.add("four");
        m.add("five");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddFifth() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one",
                "two", "three", "four");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "one", "two", "three", "four", "five");
        m.add("five");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "one");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeFive() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one",
                "two", "three", "four", "five");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "one", "two", "three", "four", "five");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstToEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(m.removeFirst(), "one");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one",
                "first");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "one");
        assertEquals(m.removeFirst(), "first");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstFive() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one",
                "two", "three", "four", "five", "six");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "two");
        assertEquals(m.removeFirst(), "five");
        assertEquals(m.removeFirst(), "four");
        assertEquals(m.removeFirst(), "one");
        assertEquals(m.removeFirst(), "six");
        assertEquals(m.removeFirst(), "three");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstFifth() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one",
                "two", "three", "four", "five", "six");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "one", "two", "three", "four", "six");
        assertEquals(m.removeFirst(), "five");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeTrueEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertEquals(m.isInInsertionMode(), true);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeTrueOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "one");
        assertEquals(m.isInInsertionMode(), true);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeTrueFive() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one",
                "two", "three", "four", "five");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "one", "two", "three", "four", "five");
        assertEquals(m.isInInsertionMode(), true);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeFalseEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(m.isInInsertionMode(), false);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeFalseOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "one");
        assertEquals(m.isInInsertionMode(), false);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeFalseFive() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one",
                "two", "three", "four", "five");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "one", "two", "three", "four", "five");
        assertEquals(m.isInInsertionMode(), false);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderInsertionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        m.order();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderInsertionModeOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "one");
        m.order();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderInsertionModeFive() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one",
                "two", "three", "four", "five");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "five", "four", "one", "three", "two");
        m.order();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.order();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderExtractionModeOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "one");
        m.order();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderExtractionModeFive() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one",
                "two", "three", "four", "five");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "five", "four", "one", "three", "two");
        m.order();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeEmptyInsertion() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertEquals(mExpected, m);
        assertEquals(m.size(), 0);
    }

    @Test
    public final void testSizeOneInsertion() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "one");
        assertEquals(mExpected, m);
        assertEquals(m.size(), 1);
    }

    @Test
    public final void testSizeFiveInsertion() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one",
                "two", "three", "four", "five");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "one", "two", "three", "four", "five");
        assertEquals(mExpected, m);
        assertEquals(m.size(), 5);
    }

    @Test
    public final void testSizeEmptyExtraction() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(mExpected, m);
        assertEquals(m.size(), 0);
    }

    @Test
    public final void testSizeOneExtraction() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "one");
        assertEquals(mExpected, m);
        assertEquals(m.size(), 1);
    }

    @Test
    public final void testSizeFiveExtraction() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one",
                "two", "three", "four", "five");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "one", "two", "three", "four", "five");
        assertEquals(mExpected, m);
        assertEquals(m.size(), 5);
    }
}
