import components.array.Array;
import components.array.Array1L;
import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program to test the {@code siftDown} on {@code Array<Integer>}.
 *
 * @mathdefinitions <pre>
 * SUBTREE_IS_HEAP (
 *   a: ARRAY_MODEL,
 *   start: integer,
 *   stop: integer,
 *   r: binary relation on T
 *  ) : boolean is
 *  [the subtree of a (when a is interpreted as a complete binary tree) rooted
 *   at index start and only through entry stop of a satisfies the heap
 *   ordering property according to the relation r]
 *
 * SUBTREE_ARRAY_ENTRIES (
 *   a: ARRAY_MODEL,
 *   start: integer,
 *   stop: integer
 *  ) : finite multiset of T is
 *  [the multiset of entries in a that belong to the subtree of a
 *   (when a is interpreted as a complete binary tree) rooted at
 *   index start and only through entry stop]
 * </pre>
 *
 * @author Put your name here
 *
 */
public final class ArraySiftDownMain {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ArraySiftDownMain() {
    }

    /**
     * Number of junk entries at the end of the array.
     */
    private static final int JUNK_SIZE = 5;

    /**
     * Checks if the subtree of the given {@code Array} rooted at the given
     * {@code top} is a heap.
     *
     * @param array
     *            the complete binary tree
     * @param top
     *            the index of the root of the "subtree"
     * @param last
     *            the index of the last entry in the heap
     * @return true if the subtree of the given {@code Array} rooted at the
     *         given {@code top} is a heap; false otherwise
     * @requires <pre>
     * 0 <= top  and  last < |array.entries|  and
     * |array.examinableIndices| = |array.entries|  and
     * [subtree rooted at {@code top} is a complete binary tree]
     * </pre>
     * @ensures isHeap = SUBTREE_IS_HEAP(heap, top, last, <=)
     */
    private static boolean isHeap(Array<Integer> array, int top, int last) {
        assert array != null : "Violation of: array is not null";
        assert 0 <= top : "Violation of: 0 <= top";
        assert last < array.length() : "Violation of: last < |array.entries|";
        for (int i = 0; i < array.length(); i++) {
            assert array.mayBeExamined(i) : ""
                    + "Violation of: |array.examinableIndices| = |array.entries|";
        }
        /*
         * No need to check the other requires clause, because it must be true
         * when using the Array representation for a complete binary tree.
         */
        int left = 2 * top + 1;
        boolean isHeap = true;
        if (left <= last) {
            isHeap = (array.entry(top) <= array.entry(left))
                    && isHeap(array, left, last);
            int right = left + 1;
            if (isHeap && (right <= last)) {
                isHeap = (array.entry(top) <= array.entry(right))
                        && isHeap(array, right, last);
            }
        }
        return isHeap;
    }

    /**
     * Given an {@code Array} that represents a complete binary tree and an
     * index referring to the root of a subtree that would be a heap except for
     * its root, sifts the root down to turn that whole subtree into a heap.
     *
     * @param array
     *            the complete binary tree
     * @param top
     *            the index of the root of the "subtree"
     * @param last
     *            the index of the last entry in the heap
     * @updates array.entries
     * @requires <pre>
     * 0 <= top  and  last < |array.entries|  and
     * |array.examinableIndices| = |array.entries|  and
     * [subtree rooted at {@code top} is a complete binary tree]  and
     * SUBTREE_IS_HEAP(array, 2 * top + 1, last, <=)  and
     * SUBTREE_IS_HEAP(array, 2 * top + 2, last, <=)
     * </pre>
     * @ensures <pre>
     * SUBTREE_IS_HEAP(array, top, last, <=)  and
     * perms(array.entries, #array.entries)  and
     * SUBTREE_ARRAY_ENTRIES(array, top, last) =
     *  SUBTREE_ARRAY_ENTRIES(#array, top, last)  and
     * [the other entries in array.entries are the same as in #array.entries]
     * </pre>
     */
    private static void siftDown(Array<Integer> array, int top, int last) {
        assert array != null : "Violation of: array is not null";
        assert 0 <= top : "Violation of: 0 <= top";
        assert last < array.length() : "Violation of: last < |array.entries|";
        for (int i = 0; i < array.length(); i++) {
            assert array.mayBeExamined(i) : ""
                    + "Violation of: |array.examinableIndices| = |array.entries|";
        }
        assert isHeap(array, 2 * top + 1, last) : ""
                + "Violation of: SUBTREE_IS_HEAP(array, 2 * top + 1, last, <=)";
        assert isHeap(array, 2 * top + 2, last) : ""
                + "Violation of: SUBTREE_IS_HEAP(array, 2 * top + 2, last, <=)";
        /*
         * No need to check the other requires clause, because it must be true
         * when using the Array representation for a complete binary tree.
         */
        int leftPos = 2 * top + 1;
        int rightPos = 2 * top + 2;
        int root = array.entry(top);
        if (last >= leftPos && last >= rightPos) {
            int left = array.entry(leftPos);
            int right = array.entry(rightPos);
            if (left <= right && root > left) {
                array.exchangeEntries(top, leftPos);
                siftDown(array, leftPos, last);
            } else if (right <= left && root > right) {
                array.exchangeEntries(top, rightPos);
                siftDown(array, rightPos, last);
            }
        } else if (last >= leftPos) {
            if (array.entry(top) > array.entry(2 * top + 1)) {
                array.exchangeEntries(top, leftPos);
            }
        }

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Input array size from user
         */
        out.print("Enter (non-negative) heap size: ");
        int heapSize = in.nextInteger();
        /*
         * Construct array (complete binary tree) of pseudo-random integers in
         * the range [0, heapSize); this array has length heapSize + JUNK_SIZE.
         * Only the first heapSize elements of this array represent the heap;
         * the remaining elements are junk. Many of the last JUNK_SIZE elements
         * of this array, about half (the first half), are -1.
         */
        Random rnd = new Random1L();
        Array<Integer> array = new Array1L<Integer>(heapSize + JUNK_SIZE);
        for (int i = 0; i < heapSize; i++) {
            int entry = (int) (rnd.nextDouble() * heapSize);
            array.setEntry(i, entry);
        }
        for (int i = heapSize; i < heapSize + JUNK_SIZE; i++) {
            int entry;
            if (heapSize + JUNK_SIZE / 2 < i) {
                entry = (int) (rnd.nextDouble() * heapSize);
            } else {
                entry = -1;
            }
            array.setEntry(i, entry);
        }
        /*
         * Output initial array
         */
        out.println("Initial array:   " + array);
        /*
         * Heapify array by repeatedly calling siftDown (this is an iterative
         * implementation of heapify)
         */
        for (int i = heapSize - 2; i >= 0; i--) {
            siftDown(array, i, heapSize - 1);
        }
        /*
         * Make sure array is now a heap
         */
        assert isHeap(array, 0, heapSize - 1) : ""
                + "Violation of: SUBTREE_IS_HEAP(array, 0, heapSize - 1, <=)";
        /*
         * Make sure the junk at the far end of the array was ignored by
         * siftDown
         */
        assert 0 == heapSize || 0 <= array.entry(0) : ""
                + "Method siftDown did not ignore the junk at "
                + "the far end of the array.";
        /*
         * If everything worked, output the heapified array
         */
        out.println("Heapified array: " + array);
        /*
         * Close streams
         */
        in.close();
        out.close();
    }

}
