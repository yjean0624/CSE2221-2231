import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.stack.Stack;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new stack
     * @ensures constructorTest = <>
     */
    protected abstract Stack<String> constructorTest();

    /**
     * Invokes the appropriate {@code Stack} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new stack
     * @ensures constructorRef = <>
     */
    protected abstract Stack<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Stack<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsTest = [entries in args]
     */
    private Stack<String> createFromArgsTest(String... args) {
        Stack<String> stack = this.constructorTest();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     *
     * Creates and returns a {@code Stack<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsRef = [entries in args]
     */
    private Stack<String> createFromArgsRef(String... args) {
        Stack<String> stack = this.constructorRef();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     * Test the no argument constructor.
     */
    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> test = this.createFromArgsTest();
        Stack<String> ref = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(test, ref);
    }

    /**
     * Test the one argument constructor.
     */
    @Test
    public final void testOneArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> test = this.createFromArgsTest("test 1");
        Stack<String> ref = this.createFromArgsRef("test 1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(test, ref);
    }

    /**
     * Test the multiple argument constructor.
     */
    @Test
    public final void testMultipleArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> test = this.createFromArgsTest("test 1", "test 2",
                "test 3");
        Stack<String> ref = this.createFromArgsRef("test 1", "test 2",
                "test 3");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(test, ref);
    }

    /*
     * Test cases for push
     */

    /**
     * Test empty push with empty String.
     */
    @Test
    public final void pushEmptyWithEmpty() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> test = this.createFromArgsTest();
        Stack<String> ref = this.createFromArgsRef();
        /*
         * Call method under test
         */
        test.push("");
        ref.push("");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(test, ref);
    }

    /**
     * Test empty push with non empty String.
     */
    @Test
    public final void pushEmptyWithNonEmpty() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> test = this.createFromArgsTest();
        Stack<String> ref = this.createFromArgsRef();
        /*
         * Call method under test
         */
        test.push("test 1");
        ref.push("test 1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(test, ref);
    }

    /**
     * Test non empty push with non empty String.
     */
    @Test
    public final void pushNonEmptyWithNonEmpty() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> test = this.createFromArgsTest("test 1");
        Stack<String> ref = this.createFromArgsRef("test 1");
        /*
         * Call method under test
         */
        test.push("test 2");
        ref.push("test 2");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(test, ref);
    }

    /**
     * Test multiple non empty push with non empty String.
     */
    @Test
    public final void pushMultipleNonEmptyWithNonEmpty() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> test = this.createFromArgsTest("test 1", "test 2",
                "test 3");
        Stack<String> ref = this.createFromArgsRef("test 1", "test 2",
                "test 3");
        /*
         * Call method under test
         */
        test.push("test 4");
        ref.push("test 4");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(test, ref);
    }

    /*
     * Test cases for pop
     */

    /**
     * Test pop one leaving empty.
     */
    @Test
    public final void popOneLeavingEmpty() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> test = this.createFromArgsTest("test 1");
        Stack<String> ref = this.createFromArgsRef("test 1");
        /*
         * Call method under test
         */
        String testT = test.pop();
        String testR = ref.pop();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(test, ref);
        assertEquals(testT, testR);
    }

    /**
     * Test pop one leaving one.
     */
    @Test
    public final void popOneLeavingOneNonEmpty() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> test = this.createFromArgsTest("test 1", "test 2");
        Stack<String> ref = this.createFromArgsRef("test 1", "test 2");
        /*
         * Call method under test
         */
        String testT = test.pop();
        String testR = ref.pop();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(test, ref);
        assertEquals(testT, testR);
    }

    /**
     * Test pop one leaving multiple non empty.
     */
    @Test
    public final void popOneLeavingMultipleNonEmpty() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> test = this.createFromArgsTest("test 1", "test 2",
                "test 3", "test 4");
        Stack<String> ref = this.createFromArgsRef("test 1", "test 2", "test 3",
                "test 4");
        /*
         * Call method under test
         */
        String testT = test.pop();
        String testR = ref.pop();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(test, ref);
        assertEquals(testT, testR);
    }

    /*
     * Test cases for size
     */

    /**
     * Test length on empty set.
     */
    @Test
    public final void lengthEmpty() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> test = this.createFromArgsTest();
        /*
         * Call method under test
         */
        int length = test.length();
        int size = 0;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(length, size);
    }

    /**
     * Test length on set size 1.
     */
    @Test
    public final void lengthOneNonEmpty() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> test = this.createFromArgsTest("test 1");
        /*
         * Call method under test
         */
        int length = test.length();
        int size = 1;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(size, length);
    }

    /**
     * Test length on multiple size set.
     */
    @Test
    public final void lengthMultipleNonEmpty() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> test = this.createFromArgsTest("test 1", "test 2",
                "test 3");
        /*
         * Call method under test
         */
        int length = test.length();
        int size = 3;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(size, length);
    }

}
