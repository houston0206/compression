package edu.ttap.bsts;

import java.util.ArrayList;
import java.util.List;

/**
 * A binary tree that satisifies the binary search tree invariant.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    ///// From the reading

    /**
     * A node of the binary search tree.
     */
    private static class Node<T> {
        public T value;

        public Node<T> left;
        
        public Node<T> right;

        /**
         * @param value the value of the node
         * @param left  the left child of the node
         * @param right the right child of the node
         */
        public Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * @param value the value of the node
         */
        public Node(T value) {
            this(value, null, null);
        }
    }

    private Node<T> root;

    /**
     * Constructs a new empty binary search tree.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * @param node the root of the tree
     * @return the number of elements in the specified tree
     */
    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }

    /**
     * @return the number of elements in this tree
     */
    public int size() {
        return sizeH(root);
    }

    ///// Part 1: Insertion

    /**
     * @param node the binary search tree
     * @param v    the value to insert
     * @return the node that was inserted
     */
    private Node<T> insertH(Node<T> node, T v) {
        if (node == null) {
            return new Node<>(v);
        } else {
            if (v.compareTo(node.value) < 0) {
                node.left = insertH(node.left, v);
            } else {
                node.right = insertH(node.right, v);
            }
            return node;
        }
    }

    /**
     * Inserts the given value into this binary search tree.
     * 
     * @param v the value to insert
     */
    public void insert(T v) {
        root = insertH(root, v);
    }

    ///// Part 2: Contains

    /**
     * @param node the binary search tree
     * @param v    the value to insert
     * @return a boolean determining if the BST contains the given value
     */
    private boolean containH(Node<T> node, T v) {
        if (node == null) {
            return false;
        } else {
            if (v.compareTo(node.value) == 0) {
                return true;
            } else if (v.compareTo(node.value) < 0) {
                return containH(node.left, v);
            } else {
                return containH(node.right, v);
            }
        }
    }

    /**
     * Check iff v is contained within the given tree.
     * 
     * @param v the value to find
     * @return true iff this tree contains <code>v</code>
     */
    public boolean contains(T v) {
        return containH(root, v);
    }

    ///// Part 3: Ordered Traversals

    /**
     * @param node the binary search tree
     * @return the string representation of the node
     */
    private String toStringH(Node<T> node) {
        if (node == null) {
            return "";
        }
        String leftSide = toStringH(node.left);
        String rightSide = toStringH(node.right);

        if (leftSide.isEmpty() && rightSide.isEmpty()) {
            return node.value.toString();
        } else if (leftSide.isEmpty()) {
            return node.value + ", " + rightSide;
        } else if (rightSide.isEmpty()) {
            return leftSide + ", " + node.value;
        } else {
            return leftSide + ", " + node.value + ", " + rightSide;
        }
    }

    /**
     * Display values in a tree as string.
     * 
     * @return the (linearized) string representation of this BST
     */
    @Override
    public String toString() {
        return "[" + toStringH(root) + "]";
    }

    /**
     * @param node the binary search tree
     * @param list the list we record the elements of the tree
     */
    private void toListH(Node<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        toListH(node.left, list);
        list.add(node.value);
        toListH(node.right, list);
    }

    /**
     * Display the elements of the tree as a list.
     * 
     * @return a list contains the elements of this BST in-order.
     */
    public List<T> toList() {
        List<T> list = new ArrayList();
        toListH(root, list);
        return list;
    }

    ///// Part 4: BST Sorting
    /**
     * @param <T> the carrier type of the lists
     * @param lst the list to sort
     * @return a copy of <code>lst</code> but sorted
     * @implSpec <code>sort</code> runs in O(n logn) time if the tree remains balanced.
     */
    public static <T extends Comparable<? super T>> List<T> sort(List<T> lst) {
        BinarySearchTree<T> newBst = new BinarySearchTree<>();
        for (int i = 0; i < lst.size(); i++) {
            newBst.insert(lst.get(i));
        }
        return newBst.toList();
    }

    ///// Part 5: Deletion

    /*
     * The three cases of deletion are:
     * 1. Both left and right are null
     * 2. The node has one child, either left or right
     * 3. The node has two children, both left and right
     */

    /**
     * Find the minimum value in the right child
     * so that it'll be the alternative of the deleted node
     *
     * @param node the node to check
     * @return the minimum value
     */
    private T findMin(Node<T> node) {
        if (node.left == null) {
            return node.value;
        }
        return findMin(node.left);
    }

    /**
     * @param node the node to delete
     * @param v the value we are looking for
     * @return the deleted node, or null if it didn't exist
     */
    private Node<T> deleteH(Node<T> node, T v) {
        if (node == null) {
            return null;
        } else if (v.compareTo(node.value) == 0) {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node<T> newNode = node.right;
                node.value = findMin(newNode);
                node.right = deleteH(node.right, node.value);
            }
            return node;
        } else if (v.compareTo(node.value) < 0) {
            node.left = deleteH(node.left, v);
            return node;
        } else {
            node.right = deleteH(node.right, v);
            return node;
        }
    }

    /**
     * Modifies the tree by deleting the first occurrence of <code>value</code>
     * found in the tree.
     *
     * @param value the value to delete
     */
    public void delete(T value) {
        root = deleteH(root, value);
    }
}
