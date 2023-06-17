/* *****************************************************************************
 *  Name:    Ricky Lin
 *  NetID:   rickyl
 *  Precept: P09
 *
 *  Description:  Creates a new Randomized Queue and enqueues all the input
 *                read from the given file. Then, it dequeues a given k
 *                amount at random from the queue and prints them.
 *
 *  Written:       9/23/2019
 *  Last updated:  9/23/2019
 *
 *  % javac-algs4 Permutation.java
 *  % java-algs4 Permuatation.java k > file.txt
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Permutation {
    public static void main(String[] args) {

        RandomizedQueue<String> permutation = new RandomizedQueue<String>();

        int k = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            String val = StdIn.readString();
            permutation.enqueue(val);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(permutation.dequeue());
        }
    }
}
