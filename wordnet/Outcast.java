/* *****************************************************************************
 *  Name:    Ricky Lin
 *  NetID:   rickyl
 *  Precept: P09
 *
 *  Partner Name:    Darin Avila
 *  Partner NetID:   dgavila
 *  Partner Precept: P09
 *
 *  Description:  Uses WordNet to find an outlier in terms of distance from other
 *                nouns in an array of nouns.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    // Wordnet object
    private final WordNet out;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        out = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        // stores lowerbound and related vertex
        int best = 0;
        String recent = "";

        for (int i = 0; i < nouns.length; i++) {
            int sum = 0;

            // calculate sum from one noun to all the others
            for (int j = 0; j < nouns.length; j++) {
                sum += out.distance(nouns[i], nouns[j]);
            }

            // if sum is higher than current best, replace it
            if (sum > best) {
                best = sum;
                recent = nouns[i];
            }
        }

        return recent;
    }

    // test client (see below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
