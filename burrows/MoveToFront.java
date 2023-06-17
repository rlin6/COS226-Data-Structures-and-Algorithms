/* *****************************************************************************
 *  Name:    Ricky Lin
 *  NetID:   rlin6
 *  Precept: P09
 *
 *  Description:  Implements MoveToFront coding that generates a sequence of
 *                ASCII extended code with indices & values 0-255. The encoding
 *                portion matches the read character with its approriate index,
 *                prints the index, and moves the ASCII value up to the front.
 *                The decoding reads an index, finds it corresponding value in
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    // create sequence
    private static int[] seq() {
        int[] seq = new int[256];
        for (int i = 0; i < seq.length; i++) {
            seq[i] = i;
        }
        return seq;
    }

    // linear search for index
    private static int index(int[] seq, char c) {
        for (char i = 0; i < seq.length; i++) {
            if (seq[i] == c) {
                return i;
            }
        }
        return -1;
    }

    // move char to front and sift everything over
    private static void move(int[] seq, char index, char c) {
        for (int i = index; i > 0; i--) {
            seq[i] = seq[i - 1];
        }
        seq[0] = c;
    }

    // apply move-to-front encoding, reading from stdin and writing to stdout
    public static void encode() {
        int[] seq = seq();
        while (!BinaryStdIn.isEmpty()) {

            // read char from input and find its index
            char c = BinaryStdIn.readChar();
            char index = (char) index(seq, c);

            // write and shift to front
            BinaryStdOut.write(index);
            move(seq, index, c);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from stdin and writing to stdout
    public static void decode() {
        int[] seq = seq();
        while (!BinaryStdIn.isEmpty()) {

            // read index from input and find its char
            char index = BinaryStdIn.readChar();
            char c = (char) seq[index];

            // write char and move to front
            BinaryStdOut.write(c);
            move(seq, index, c);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        }
        if (args[0].equals("+")) {
            decode();
        }
    }
}
