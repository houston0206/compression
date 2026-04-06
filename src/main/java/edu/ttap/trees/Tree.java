package edu.ttap.trees;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
    /**
     * A node of the binary tree.
     */
    public static class Node<T> {
        public T value;
        public Node<T> left;
        public Node<T> right;

        /**
         * @param value the value of the node
         * @param left the left child of the node
         * @param right the right child of the node
         */
        Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * @param value the value of the node
         */
        Node(T value) {
            this(value, null, null);
        }
    }

    private Node<T> root;

    /**
     * Constructs a new, empty binary tree.
     */
    public Tree() {
        this.root = null;
    }

    /**
     * @return a sample binary tree for testing purposes
     */
    public static Tree<Integer> makeSampleTree() {
        Tree<Integer> tree = new Tree<Integer>();
        tree.root = new Node<>(
            5,
            new Node<>(2,
                new Node<>(1),
                new Node<>(3)
            ),
            new Node<>(8,
                new Node<>(7,
                    new Node<>(6),
                    null),
                new Node<>(9,
                    null,
                    new Node<>(10)))
        );
        return tree;
    }


    /**
     * @param node the root of the tree 
     * @return the number elements found in this tree rooted at node
     */
    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }

    /** @return the number of elements in the tree */
    public int size() {
        return sizeH(root);
    }


    ///// Part 1: Contains
    /**
     * @param node the root of the tree
     * @param value the value to search for
     * @return the number of times the value is found
     */
    private boolean containsH(Node<T> node, T value) {
        if (node == null) {
            return false;
        } else if (node.value == value) {
            return true;
        } else {
            return containsH(node.left, value) | containsH(node.right, value);
        }
    }

    /**
     * @param value the value to search for
     * @return true iff the tree contains <code>value</code>
     */
    public boolean contains(T value) {
        return containsH(root, value);
    }


    ///// Part 2: Traversals
    /**
     * @param lst the list to add values to
     * @param node the root of the tree
     */
    private void inOrderH(List<T> lst, Node<T> node) {
        if (node == null) {
            return;
        } else {
            inOrderH(lst, node.left);
            lst.add(node.value);
            inOrderH(lst, node.right);
        }
    }

    /**
     * @return the elements of this tree collected via an in-order traversal
     */
    public List<T> toListInorder() {
        List<T> lst = new ArrayList<>();
        inOrderH(lst, root);
        return lst;
    }

    /**
     * @param lst the list to add values to
     * @param node the root of the tree
     */
    private void preorderH(List<T> lst, Node<T> node) {
        if (node == null) {
            return;
        } else {
            lst.add(node.value);
            preorderH(lst, node.left);
            preorderH(lst, node.right);
        }
    }

    /**
     * @return the elements of this tree collected via a pre-order traversal
     */
    public List<T> toListPreorder() {
        List<T> lst = new ArrayList<>();
        preorderH(lst, root);
        return lst;
    }

    /**
     * @param lst the list to add values to
     * @param node the root of the tree
     */
    private void postorderH(List<T> lst, Node<T> node) {
        if (node == null) {
            return;
        } else {
            postorderH(lst, node.left);
            postorderH(lst, node.right);
            lst.add(node.value);
        }
    }

    /**
     * @return the elements of this tree collected via a post-order traversal
     */
    public List<T> toListPostorder() {
        List<T> lst = new ArrayList<>();
        postorderH(lst, root);
        return lst;
    }


    ///// Part 3: Stringifying Trees
    /**
     * @param node the root of the tree
     * @param buf the buffer to append strings of the values to
     */
    private void toStringH(Node<T> node, StringBuffer buf) {
        if (node == null) {
            return;
        } else {
            buf.append(", ");
            buf.append(node.value);
            toStringH(node.left, buf);
            toStringH(node.right, buf);
        }
    }

    /**
     * @return a string represent of this tree in the form, "[x1, ..., xk]."
     * The order of the elements is left unspecified.
     */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer("[");
        if (this.size() > 0) {
            buf.append(root.value);
            toStringH(root.left, buf);
            toStringH(root.right, buf);
        }
        buf.append("]");
        return buf.toString();
    }


    ///// Extra: Pretty Printing
    /**
     * @param node the root of the tree
     * @param buf the buffer to append strings of the values to
     * @param indent the indentation level
     */
    private void toPrettyH(Node<T> node, StringBuffer buf, int indent) {
        if (node == null) {
            return;
        } else {
            for (int i = 0; i < indent; i++) {
                buf.append("\t");
            }
            buf.append("-");
            buf.append(node.value);
            buf.append("\n");
            toPrettyH(node.left, buf, indent+1);
            toPrettyH(node.right, buf, indent+1);
        }
    }

    /**
     * @return a string represent of this tree in bulleted list form.
     */
    public String toPrettyString() {
        StringBuffer buf = new StringBuffer("[");
        if (this.size() > 0) {
            buf.append("-");
            buf.append(root.value);
            buf.append("\n");
            toPrettyH(root.left, buf, 1);
            toPrettyH(root.right, buf, 1);
        }
        buf.append("]");
        return buf.toString();
    }
}
