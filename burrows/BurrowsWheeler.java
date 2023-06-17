/* *****************************************************************************
 *  Name:    Ricky Lin
 *  NetID:   rlin6
 *  Precept: P09
 *
 *  Description:  Implements BurrowsWheeler Transform and Inverse Transform.
 *                For encoding, it creates a CircularSuffixArray from input
 *                string and iterates through the array. It adds the last char
 *                of each sorted CircularSuffix to a String and writes the
 *                position of the original array and the final String.
 *                The inverse Transform reads the original position and the last
 *                characters and uses key-indexed counting to sort the last chars.
 *                Then it traces through the next[] starting from the first char
 *                to reconstruct the original string.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    // extended ASCII alphabet size
    private static final int R = 256;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {

        // read and create circular suffixes
        String in = BinaryStdIn.readString();
        CircularSuffixArray suf = new CircularSuffixArray(in);

        // String of the transformed text
        StringBuilder trans = new StringBuilder();

        // iterate through entire suffix array
        for (int i = 0; i < suf.length(); i++) {

            // if at original string, write its position and add its last char
            if (suf.index(i) == 0) {
                BinaryStdOut.write(i);
                trans.append(in.charAt(suf.length() - 1));
            }
            // otherwise just add last char
            else {
                trans.append(in.charAt(suf.index(i) - 1));
            }
        }
        BinaryStdOut.write(String.valueOf(trans));
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {

        // read position of original and string and make suffix array
        int first = BinaryStdIn.readInt();
        String in = BinaryStdIn.readString();

        // next & count array
        int[] next = new int[in.length()];
        int[] count = new int[R + 2];

        // key indexed sorting on string
        for (int i = 0; i < in.length(); i++) {
            count[in.charAt(i) + 2]++;
        }
        // transform counts to indicies
        for (int r = 0; r < R + 1; r++) {
            count[r + 1] += count[r];
        }
        // distribute
        for (int i = 0; i < in.length(); i++) {
            next[count[in.charAt(i) + 1]++] = i;
        }

        // trace path through next array
        for (int i = 0; i < in.length(); i++) {
            first = next[first];
            BinaryStdOut.write(in.charAt(first));
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        }
        if (args[0].equals("+")) {
            inverseTransform();
        }
    }
}
