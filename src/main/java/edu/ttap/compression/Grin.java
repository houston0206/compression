package edu.ttap.compression;

import java.io.*;

/**
 * The driver for the Grin compression program.
 */
public class Grin {
    /**
     * Decodes the .grin file denoted by infile and writes the output to the
     * .grin file denoted by outfile.
     * @param infile the file to decode
     * @param outfile the file to ouptut to
     */
    public static void decode(String infile, String outfile) throws IOException {
        BitInputStream in = new BitInputStream(infile);
        BitOutputStream out = new BitOutputStream(outfile);
        if (in.readBits(32) != 1846) {
            throw new IllegalArgumentException("Magic number not correct.");
        }
        HuffmanTree tree = new HuffmanTree(in);
        tree.decode(in, out);
        in.close();
        out.close();
    }

    /**
     * The entry point to the program.
     * @param args the command-line arguments.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java Grin <infile> <outfile>");
            System.exit(0);
        } else {
            decode(args[0], args[1]);
        }
    }
}