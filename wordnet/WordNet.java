/* *****************************************************************************
 *  Name:    Ricky Lin
 *  NetID:   rickyl
 *  Precept: P09
 *
 *  Partner Name:    Darin Avila
 *  Partner NetID:   dgavila
 *  Partner Precept: P09
 *
 *  Description:  Creates a WordNet object by parsing through a synset file,
 *                creating 2 hashtables (one relating ids to synsets, one
 *                relating nouns to all synset ids containing it), and a hyperhym
 *                file, creating a Digraph that is passed into
 *                ShortestCommonAncestor constructor. Using these data structures,
 *                WordNet can find all the nouns, check if a noun is in the WordNet,
 *                and find the shortest common ancestor and its distance.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    // hashtable relating ids to a string of its respective synset
    private final LinearProbingHashST<Integer, String> synset;

    // hashtable relating a noun to all ids with a synset containing it
    private final LinearProbingHashST<String, Integer[]> noun;

    // digraph ShortestCommonAncestor to calculate sac
    private final ShortestCommonAncestor anc;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        // exception check for null
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("null");
        }

        // initialize structs, parse text file, and keep track of size
        synset = new LinearProbingHashST<Integer, String>();
        noun = new LinearProbingHashST<String, Integer[]>();
        In in = new In(synsets);
        int size = 0;

        size = syn(in, size);

        // temp digraph to store hypernyms
        Digraph hymset = new Digraph(size);
        In hym = new In(hypernyms);

        while (hym.hasNextLine()) {

            // split each line and create edges from the first vertex to
            // all of its hypernyms
            String[] h = hym.readLine().split(",");
            int v = Integer.parseInt(h[0]);
            for (int i = 1; i < h.length; i++) {
                hymset.addEdge(v, Integer.parseInt(h[i]));
            }
        }

        // create the sac object from hymset
        anc = new ShortestCommonAncestor(hymset);
    }

    // parses synsets
    private int syn(In in, int n) {
        while (in.hasNextLine()) {
            // split line based on ,. Key is the integer id &
            // val is the following string. Add to table and increase size
            String[] line = in.readLine().split(",");
            int key = Integer.parseInt(line[0]);
            String val = line[1];
            synset.put(key, val);
            n++;

            // split the synset into its individual nouns
            for (String s : val.split(" ")) {
                Integer[] copy;

                // if noun already in noun table, create a copy of its id array
                // and add the new key
                if (noun.contains(s)) {
                    copy = new Integer[noun.get(s).length + 1];
                    for (int i = 0; i < noun.get(s).length; i++) {
                        copy[i] = noun.get(s)[i];
                    }
                    copy[noun.get(s).length] = key;
                }

                // if new noun, create an id array with the new key
                else {
                    copy = new Integer[] { key };
                }

                // add the noun and new id array
                noun.put(s, copy);
            }
        }
        return n;
    }

    // all WordNet nouns
    public Iterable<String> nouns() {
        return noun.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("null");
        }
        return noun.contains(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        if (!(isNoun(noun1)) || !(isNoun(noun2))) {
            throw new IllegalArgumentException("null");
        }

        // iterables to store all ids containing the nouns
        Queue<Integer> subA = new Queue<Integer>();
        Queue<Integer> subB = new Queue<Integer>();

        // add related ids into subsets
        for (int i = 0; i < noun.get(noun1).length; i++) {
            subA.enqueue(noun.get(noun1)[i]);
        }

        for (int i = 0; i < noun.get(noun2).length; i++) {
            subB.enqueue(noun.get(noun2)[i]);
        }

        // calculate the ancestor vertex and get its related synset
        return synset.get(anc.ancestorSubset(subA, subB));
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (!(isNoun(noun1)) || !(isNoun(noun2))) {
            throw new IllegalArgumentException("null");
        }

        // same as sac but find length instead
        Queue<Integer> subA = new Queue<Integer>();
        Queue<Integer> subB = new Queue<Integer>();

        for (int i = 0; i < noun.get(noun1).length; i++) {
            subA.enqueue(noun.get(noun1)[i]);
        }

        for (int i = 0; i < noun.get(noun2).length; i++) {
            subB.enqueue(noun.get(noun2)[i]);
        }

        return anc.lengthSubset(subA, subB);
    }

    public static void main(String[] args) {

        // null args
        // WordNet excep = new WordNet(null, null);

        WordNet test = new WordNet("synsets.txt", "hypernyms.txt");

        for (String s : test.nouns()) {
            StdOut.println(s);
        }

        // exception
        // StdOut.println(test.isNoun(null));

        StdOut.println(test.isNoun("fdasfdas"));
        StdOut.println(test.isNoun("entity"));

        // nonsense words
        // StdOut.println(test.distance("asdfadsfae", "aereasdf"));
        // StdOut.println(test.sca("fdasfadf", "fasdfaafd"));

        StdOut.println(test.distance("Black_Plague", "black_marlin"));
        StdOut.println(test.distance("Black_Plague", "Black_Plague"));
        StdOut.println(test.sca("individual", "edible_fruit"));
        StdOut.println(test.sca("Black_Plague", "Black_Plague"));

    }
}
