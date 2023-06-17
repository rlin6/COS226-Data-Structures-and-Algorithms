/* *****************************************************************************
 *  Name:    Ricky Lin
 *  NetID:   rlin6
 *  Precept: P09
 *
 *  Description:  Implements CircularSuffixArray data structure. It is
 *                represented by an array of length equal to string's length,
 *                with vals n to length - 1 to represent the starting character
 *                of the string. The constructor initializes this and then sort
 *                the circular suffixes.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {

    // extended ASCII alphabet size
    private static final int R = 256;

    // length of arr
    private final int length;

    // underlying container for suffixes
    private final int[] arr;

    // input string
    private final String str;

    // circular suffix array of s
    public CircularSuffixArray(String s) {

        if (s == null) {
            throw new IllegalArgumentException("null args");
        }

        str = s;
        length = s.length();
        arr = new int[length];

        // each val is implicit pointer to starting char in string (aka offset)
        for (int i = 0; i < length(); i++) {
            arr[i] = i;
        }

        // run sorting
        sort(arr, length());
    }

    // MSD sorting based on textbook examples
    private void sort(int[] a, int w) {
        // create helping array and run recursive sort
        int[] aux = new int[length()];
        sort(a, aux, w, 0, length() - 1, 0);
    }

    // sort from a[lo] to a[hi], starting at the dth character
    private void sort(int[] a, int[] aux, int w, int lo, int hi, int d) {

        if (hi <= lo || d == w) return;

        // compute frequency counts
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i++) {
            // difference: finds corresponding char character based on offset
            int c = charAt(d, arr[i]);
            count[c + 2]++;
        }

        // transform counts to indicies
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];

        // distribute
        for (int i = lo; i <= hi; i++) {
            int c = charAt(d, arr[i]);
            aux[count[c + 1]++] = a[i];
        }

        // copy back
        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];

        // recursively sort for each character (excludes sentinel -1)
        for (int r = 0; r < R; r++) {
            sort(a, aux, w, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }
    }

    // return dth character of s taking into account the circular offset,
    // -1 if d = length of string
    private int charAt(int d, int offset) {
        assert d >= 0 && d <= length();
        if (d == length()) return -1;
        // allows for wrap around to front
        return str.charAt((d + offset) % length());
    }

    // length of s
    public int length() {
        return length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i > arr.length - 1 || i < 0) {
            throw new IllegalArgumentException("null args");
        }
        return arr[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        String in = StdIn.readString();
        CircularSuffixArray test = new CircularSuffixArray(in);

        // testing abracadabra
        StdOut.println(test.length);
        StdOut.println(test.index(0));
        StdOut.println(test.index(1));
        StdOut.println(test.index(2));
        StdOut.println(test.index(3));
        StdOut.println(test.index(4));
        StdOut.println(test.index(5));
        StdOut.println(test.index(6));
        StdOut.println(test.index(7));
        StdOut.println(test.index(8));
        StdOut.println(test.index(9));
        StdOut.println(test.index(10));
        StdOut.println(test.index(11));
    }
}
