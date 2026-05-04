package edu.ttap.bsts;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BinarySearchTreeTest {

    private BinarySearchTree<Integer> mkSampleTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        // TODO: make a sample tree consisting of 3, 5, 2, 6, and 4
        //       through repeated calls to insert.
        tree.insert(3);
        tree.insert(5);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(4);
        return tree;
    }

    @Test
    public void emptyTreeTest() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertEquals(0, tree.size());
    }

    @Test
    public void basicSizeInsertTest() {
        BinarySearchTree<Integer> tree = mkSampleTree();
        assertEquals(6, tree.size());
    }

    @Test
    public void basicContainsTest() {
        BinarySearchTree<Integer> tree = mkSampleTree();
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(2));
        assertFalse(tree.contains(8));
    }

    @Test
    public void basicToStringTest() {
        assertEquals("[2, 3, 4, 5, 6]", mkSampleTree().toString());
    }

    @Test
    public void basicToListTest() {
        // N.B., need to upcast the Integer[] array to avoid a ClassCastException that
        // arises from downcasting the result of toArray to Integer[].
        assertArrayEquals((Object[]) new Integer[] {2, 3, 4, 5, 6},
                          mkSampleTree().toList().toArray());
    }

    @Test
    public void basicSortTest() {
        assertArrayEquals(
            (Object[]) new Integer[] {0, 5, 6, 7, 11},
            BinarySearchTree.<Integer> sort(
                java.util.Arrays.asList(0, 5, 6, 7, 11)).toArray());
    }


    // Deletion Case 1(No children)
    @Test
    public void DeleteTest1() {
        BinarySearchTree<Integer> tree = mkSampleTree();
        assertEquals(6, tree.size());
        assertTrue(tree.contains(6));
        tree.delete(6);
        assertEquals(5, tree.size());
        assertFalse(tree.contains(6));
    }

    // Deletion Case 2(One child)
    @Test
    public void DeleteTest2() {
        BinarySearchTree<Integer> tree = mkSampleTree();
        assertEquals(6, tree.size());
        assertTrue(tree.contains(2));
        tree.delete(2);
        assertEquals(5, tree.size());
        assertFalse(tree.contains(2));
    }

    // Deletion Case 3(Two children in both sides)
    @Test
    public void DeleteTest3() {
        BinarySearchTree<Integer> tree = mkSampleTree();
        assertEquals(6, tree.size());
        assertTrue(tree.contains(5));
        tree.delete(5);
        assertEquals(5, tree.size());
        assertFalse(tree.contains(5));
    }
}
