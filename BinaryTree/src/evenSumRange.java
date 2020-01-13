import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class evenSumRange {

    /**
     * Node class.
     *
     * @author Xingyue Zhao
     *
     */
    class Node {
        /**
         * Data in node.
         */
        int value;

        /**
         * Left subtree.
         */
        Node left;

        /**
         * Right subtree.
         */
        Node right;

        Node(int value) {
            this.value = value;
            this.right = null;
            this.left = null;
        }
    }

    Node root;

    /**
     * No argument constructor.
     */
    private evenSumRange() {
    }

    private Node insertNode(Node root, int value) {
        if (root == null) {
            root = new Node(value);
        } else if (value <= root.value) {
            root.left = this.insertNode(root.left, value);
        } else if (value > root.value) {
            root.right = this.insertNode(root.right, value);
        }
        return root;
    }

    public void insert(int value) {
        this.root = this.insertNode(this.root, value);
    }

    public boolean btreeEvenSumRange(int min, int max, Node root) {
        boolean even = true;
        if (root != null) {
            if (min <= root.value) {
                even = (even == this.btreeEvenSumRange(min, max, root.left));
            }
            if (min <= root.value && max >= root.value) {
                if (root.value % 2 == 0) {
                    even = (even == true);
                } else {
                    even = (even == false);
                }
            }
            if (max >= root.value) {
                even = (even == this.btreeEvenSumRange(min, max, root.right));
            }
        }
        return even;
    }

    public static void main(String[] args) throws IOException {
        File dataX = new File(args[0]);
        File rangeX = new File(args[1]);
        Scanner dt = new Scanner(dataX);
        Scanner rg = new Scanner(rangeX);
        evenSumRange bst = new evenSumRange();
        while (dt.hasNextLine()) {
            int dataTemp = Integer.parseInt(dt.nextLine());
            bst.insert(dataTemp);
        }
        while (rg.hasNext()) {
            int min = Integer.parseInt(rg.next());
            int max = Integer.parseInt(rg.next());
            boolean even = bst.btreeEvenSumRange(min, max, bst.root);
            if (even) {
                System.out.println("Range [" + min + "," + max + "]: even sum");
            } else {
                System.out.println("Range [" + min + "," + max + "]: odd sum");
            }
        }

    }

}
