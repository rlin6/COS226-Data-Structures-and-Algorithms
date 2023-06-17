/* *****************************************************************************
 *  Name:    Ricky Lin
 *  NetID:   rickyl
 *  Precept: P09
 *
 *  Partner Name:    Darin Avila
 *  Partner NetID:   dgavila
 *  Partner Precept: P09
 *
 *  Description:  Takes a directed acyclic graph as an input and allows the user
 *                to calculate the shortest common ancestor of and the distance
 *                between two vertices or two sets of vertices.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class ShortestCommonAncestor {

    // defensive copy of Digraph
    private final Digraph d;

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {

        // if the graph has a directed cycle, it is not acyclic
        // and is thus not a valid input (uses DirectedCycle.java)
        DirectedCycle check = new DirectedCycle(G);
        if (check.hasCycle()) {
            throw new IllegalArgumentException();
        }

        boolean root = false;

        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0 && !(root)) {
                root = true;
            }
            else if (G.outdegree(i) == 0 && root) {
                throw new IllegalArgumentException();
            }
        }

        d = new Digraph(G);
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {

        if (v < 0 || w < 0) {
            throw new IllegalArgumentException("negative vertex");
        }

        // run two breadth first search on both vertices
        BreadthFirstDirectedPaths x = new BreadthFirstDirectedPaths(d, v);
        BreadthFirstDirectedPaths y = new BreadthFirstDirectedPaths(d, w);

        // find the upper bound for the distance, which is dist to root
        int best = 0;
        for (int i = 0; i < d.V(); i++) {
            if (x.hasPathTo(i) && y.hasPathTo(i)) {
                if (d.outdegree(i) == 0) {
                    best = x.distTo(i) + y.distTo(i);
                }
            }
        }

        // iterate through vertices and look for a vertex that v & w both have
        // a path to & has a distance shorter than current best
        for (int i = 0; i < d.V(); i++) {
            if (x.hasPathTo(i) && y.hasPathTo(i)) {
                if (x.distTo(i) + y.distTo(i) < best) {
                    best = x.distTo(i) + y.distTo(i);
                }
            }
        }

        return best;
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {

        if (v < 0 || w < 0) {
            throw new IllegalArgumentException("negative vertex");
        }

        // same as length but includes variable recent to keep track of vertex
        // the current best is to
        BreadthFirstDirectedPaths x = new BreadthFirstDirectedPaths(d, v);
        BreadthFirstDirectedPaths y = new BreadthFirstDirectedPaths(d, w);

        int best = 0;
        int recent = 0;
        for (int i = 0; i < d.V(); i++) {
            if (x.hasPathTo(i) && y.hasPathTo(i)) {
                if (d.outdegree(i) == 0) {
                    best = x.distTo(i) + y.distTo(i);
                    recent = i;
                }
            }
        }

        for (int i = 0; i < d.V(); i++) {
            if (x.hasPathTo(i) && y.hasPathTo(i)) {
                if (x.distTo(i) + y.distTo(i) < best) {
                    best = x.distTo(i) + y.distTo(i);
                    recent = i;
                }
            }
        }

        return recent;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {

        if (subsetA == null || subsetB == null) {
            throw new IllegalArgumentException("null");
        }

        // exception check for empty iterators
        if (!(subsetA.iterator().hasNext()) || !(subsetB.iterator().hasNext())) {
            throw new IllegalArgumentException("empty iterable");
        }

        // exception check for any null item
        for (Integer i : subsetA) {
            if (i == null) {
                throw new IllegalArgumentException("null item");
            }
        }
        for (Integer i : subsetB) {
            if (i == null) {
                throw new IllegalArgumentException("null item");
            }
        }

        // same as length but with source subsets
        BreadthFirstDirectedPaths x = new BreadthFirstDirectedPaths(d, subsetA);
        BreadthFirstDirectedPaths y = new BreadthFirstDirectedPaths(d, subsetB);

        int best = 0;
        for (int i = 0; i < d.V(); i++) {
            if (x.hasPathTo(i) && y.hasPathTo(i)) {
                if (d.outdegree(i) == 0) {
                    best = x.distTo(i) + y.distTo(i);
                }
            }
        }

        for (int i = 0; i < d.V(); i++) {
            if (x.hasPathTo(i) && y.hasPathTo(i)) {
                if (x.distTo(i) + y.distTo(i) < best) {
                    best = x.distTo(i) + y.distTo(i);
                }
            }
        }

        return best;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {

        if (subsetA == null || subsetB == null) {
            throw new IllegalArgumentException("null");
        }

        // same as lengthSubset but with ancestor
        if (!(subsetA.iterator().hasNext()) || !(subsetB.iterator().hasNext())) {
            throw new IllegalArgumentException("empty iterable");
        }

        for (Integer i : subsetA) {
            if (i == null) {
                throw new IllegalArgumentException("null item");
            }
        }
        for (Integer i : subsetB) {
            if (i == null) {
                throw new IllegalArgumentException("null item");
            }
        }

        BreadthFirstDirectedPaths x = new BreadthFirstDirectedPaths(d, subsetA);
        BreadthFirstDirectedPaths y = new BreadthFirstDirectedPaths(d, subsetB);

        int best = 0;
        int recent = 0;
        for (int i = 0; i < d.V(); i++) {
            if (x.hasPathTo(i) && y.hasPathTo(i)) {
                if (d.outdegree(i) == 0) {
                    best = x.distTo(i) + y.distTo(i);
                    recent = i;
                }
            }
        }

        for (int i = 0; i < d.V(); i++) {
            if (x.hasPathTo(i) && y.hasPathTo(i)) {
                if (x.distTo(i) + y.distTo(i) < best) {
                    best = x.distTo(i) + y.distTo(i);
                    recent = i;
                }
            }
        }

        return recent;
    }

    // unit testing (required)
    public static void main(String[] args) {

        Digraph test = new Digraph(8);
        test.addEdge(0, 1);
        test.addEdge(0, 4);
        test.addEdge(1, 2);
        test.addEdge(1, 7);
        test.addEdge(2, 3);
        test.addEdge(3, 7);
        test.addEdge(3, 5);
        test.addEdge(4, 5);
        test.addEdge(5, 7);
        test.addEdge(6, 7);

        Digraph cycl = new Digraph(3);
        cycl.addEdge(0, 1);
        cycl.addEdge(1, 2);
        cycl.addEdge(2, 0);

        ShortestCommonAncestor t = new ShortestCommonAncestor(test);
        // expect exception
        // ShortestCommonAncestor w = new ShortestCommonAncestor(cycl);

        // negative vertex exception
        // StdOut.println(t.length(-1, -4));
        // StdOut.println(t.ancestor(-1, -4));

        StdOut.println(t.length(1, 4));
        StdOut.println(t.ancestor(1, 4));

        Queue<Integer> q = new Queue<>();
        Queue<Integer> p = new Queue<>();

        // empty iterable
        // StdOut.println(t.lengthSubset(q, p));
        // StdOut.println(t.ancestorSubset(q, p));

        q.enqueue(0);
        p.enqueue(2);

        // null item
        // q.enqueue(null);
        // p.enqueue(null);
        // StdOut.println(t.lengthSubset(q, p));
        // StdOut.println(t.ancestorSubset(q, p));

        q.enqueue(6);
        p.enqueue(4);

        StdOut.println(t.lengthSubset(q, p));
        StdOut.println(t.ancestorSubset(q, p));
    }
}
