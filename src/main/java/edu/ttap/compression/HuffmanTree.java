package edu.ttap.compression;

/**
 * A HuffmanTree derives a space-efficient coding of a collection of byte
 * values.
 *
 * The huffman tree encodes values in the range 0--255 which would normally
 * take 8 bits.  However, we also need to encode a special EOF character to
 * denote the end of a .grin file.  Thus, we need 9 bits to store each
 * byte value.  This is fine for file writing (modulo the need to write in
 * byte chunks to the file), but Java does not have a 9-bit data type.
 * Instead, we use the next larger primitive integral type, short, to store
 * our byte values.
 */
public class HuffmanTree {

    /**
     * A node of the binary tree.
     */
    public static class Node {
        public short leafValue;
        public Node left;
        public Node right;

        /**
         * @param value the value of the node
         * @param left the left child of the node
         * @param right the right child of the node
         */
        Node(short value, Node left, Node right) {
            this.leafValue = value;
            this.left = left;
            this.right = right;
        }

        /**
         * Constructs a leaf node
         * @param value the value of the node
         */
        Node(short value) {
            this.leafValue = value;
        }

        /**
         * Constructs an interior node
         * @param left the left child of the node
         * @param right the right child of the node
         */
        Node(Node left, Node right) {
            this.left = left;
            this.right = right;
        }
    }

    private Node root;

    /**
     * Constructs a new HuffmanTree from the given file.
     * @param in the input file (as a BitInputStream)
     */
    private Node huffmanHelper(BitInputStream in) {
        int bit = in.readBit();
        if (bit == -1) {
            throw new IllegalStateException("Unexpected end of input");
        }
        if (bit == 0) {
            int newCh = in.readBits(9);     
            return new Node((short)newCh);
        } else {
            Node newLeft = huffmanHelper(in);
            Node newRight = huffmanHelper(in);
            return new Node(newLeft, newRight);
        }
    }

    /**
     * Constructs a new HuffmanTree from the given file.
     * @param in the input file (as a BitInputStream)
     */
    public HuffmanTree(BitInputStream in) {
        this.root = huffmanHelper(in);
    }

    /**
     * Decodes a stream of huffman codes from a file given as a stream of
     * bits into their uncompressed form, saving the results to the given
     * output stream. Note that the EOF character is not written to out
     * because it is not a valid 8-bit chunk (it is 9 bits).
     * @param in the file to decompress.
     * @param out the file to write the decompressed output to.
     */
    public void decode(BitInputStream in, BitOutputStream out) {
        Node node = root;
        while (in.hasBits()) {
            int bit = in.readBit();
            if (bit == 0)
                node = node.left;
            if (bit == 1)
                node = node.right;
            if (node.left == null && node.right == null) {
                if(node.leafValue == 256) {
                    break;
                } else { 
                    out.writeBits(node.leafValue, 8);
                    node = root;
                }
            }
        }
    }
}
