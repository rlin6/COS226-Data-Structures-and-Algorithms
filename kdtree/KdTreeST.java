/* *****************************************************************************
 *  Name:    Darin
 *  NetID:   dgavila
 *  Precept: P09
 *
 *  Partner Name:    Ricky Lin
 *  Partner NetID:   rickyl
 *  Partner Precept: P09
 *
 *  Description:  Creates a KdTree with an inner class Node storing a Point2D
 *                p, left child, right child, and a value. The KdTree stores
 *                the root node, the size, and a champion node and double min
 *                for nearest neighbors. The methods include checking if empty,
 *                returning size, adding to tree, getting val from a point,
 *                check if tree contains a point, return an iterable, the range
 *                of points in a rectangle, and the nearest neighbors.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Objects;

public class KdTreeST<Value> {

    // stores the root of tree
    private Node root;

    // stores size
    private int n;

    // closest node to query node so far (for nearest)
    private Node champion;

    // smallest distance so far (for nearest)
    private double min;

    // keeps track for client testing
    private int counter = 0;

    // trees made of class Nodes
    private class Node {

        // point as key of Node
        private final Point2D p;

        // val associated with a point
        private Value val;

        // its left child
        private Node left;

        // its right child
        private Node right;

        // constructs a node given a point, its value, and its two children
        public Node(Point2D point, Value v, Node lt, Node rt) {
            p = point;
            val = v;
            left = lt;
            right = rt;
        }
    }

    // construct an empty symbol table of points
    public KdTreeST() {
        root = null;
        n = 0;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points
    public int size() {
        return n;
    }

    // throw exceptions when null
    private void exceptions(Object obj) {
        // throw exception if argument null
        if (obj == null) {
            throw new IllegalArgumentException("null argument(s)");
        }
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {

        // throw exceptions
        exceptions(p);
        exceptions(val);

        // run recursive put with root, new point-val pair, and isVertical?
        root = put(root, p, val, true);
    }

    // recursive method of adding based on BST.java that adds a point-val pair
    private Node put(Node x, Point2D p, Value val, boolean isVertical) {

        // base case where you insert a new node that is at end of tree
        if (x == null) {
            n++;
            return new Node(p, val, null, null);
        }

        int cmp;

        // compare the x-cor if vertical split, y-cor if horizontal split
        if (isVertical) {
            cmp = Double.compare(p.x(), x.p.x());
        }
        else {
            cmp = Double.compare(p.y(), x.p.y());
        }

        // insert into left or right and move to next node & switch split
        if (cmp < 0) {
            x.left = put(x.left, p, val, !isVertical);
        }
        else if (cmp > 0) {
            x.right = put(x.right, p, val, !isVertical);
        }

        // if both x or y are the same, check other pair of coordinates
        // if other pair is diff, move to the right
        // if exact same coordinate, update the val
        else {
            if (Double.compare(p.y(), x.p.y()) != 0
                    || Double.compare(p.x(), x.p.x()) != 0) {
                x.right = put(x.right, p, val, !isVertical);
            }
            else {
                x.val = val;
            }
        }

        return x;
    }

    // value associated with point p
    public Value get(Point2D p) {

        // throw exception
        exceptions(p);

        // return recursive method of get
        return get(root, p, true);
    }

    // recursive method of searching in a tree based on BST.java
    private Value get(Node x, Point2D p, boolean isVertical) {

        // if you've reached the end of the tree, value is not there
        if (x == null) return null;

        int cmp;

        // compare the x-cor if vertical split, y-cor if horizontal split
        if (isVertical) {
            cmp = Double.compare(p.x(), x.p.x());
        }
        else {
            cmp = Double.compare(p.y(), x.p.y());
        }

        // go down left or right subtree & switch split if not found
        if (cmp < 0) {
            return get(x.left, p, !isVertical);
        }
        else if (cmp > 0) {
            return get(x.right, p, !isVertical);
        }

        // if both x or y are the same, check other pair of coordinates
        // if other pair is diff, move to the right
        // if exact same coordinate, you found it
        else {
            if (Double.compare(p.y(), x.p.y()) != 0
                    || Double.compare(p.x(), x.p.x()) != 0) {
                return get(x.right, p, !isVertical);
            }
            else {
                return x.val;
            }
        }
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {

        // throw exception if null
        exceptions(p);

        // search using get to see if in table
        return get(p) != null;
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {

        // tracking keys and nodes based on BST.java
        Queue<Point2D> keys = new Queue<Point2D>();
        Queue<Node> queue = new Queue<Node>();

        // add root node to start search
        queue.enqueue(root);

        while (!queue.isEmpty()) {

            // remove node, if null, continue, and add its key to keys queue
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x.p);

            // add all the children and continue
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }

        return keys;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

        exceptions(rect);

        // create queue storing all points in range
        Queue<Point2D> pq = new Queue<Point2D>();

        // run recursive range method
        range(root, rect, pq, true);
        return pq;
    }

    // Recursive search for points in the rectangle
    private void range(Node a, RectHV rect, Queue<Point2D> aq, boolean isVertical) {

        // base case to stop when you've reached end of tree
        if (a == null) {
            return;
        }

        // vertical splits
        if (isVertical) {

            // recurse on left/right if out of x-range of rectangle
            if (a.p.x() > rect.xmin() && a.p.x() > rect.xmax()) {
                a = a.left;
                range(a, rect, aq, false);
            }
            else if (a.p.x() < rect.xmin() && a.p.x() < rect.xmax()) {
                a = a.right;
                range(a, rect, aq, false);
            }

            // if within x-range of rect
            else if (a.p.x() >= rect.xmin() && a.p.x() <= rect.xmax()) {

                // enqueue in also within y-range
                if (a.p.y() >= rect.ymin() && a.p.y() <= rect.ymax()) {
                    aq.enqueue(a.p);
                }

                // continue if there are more nodes to explore
                if (a.left != null) {
                    range(a.left, rect, aq, false);
                }
                if (a.right != null) {
                    range(a.right, rect, aq, false);
                }
            }
        }

        // horizontal split version of previous
        else {

            if (a.p.y() > rect.ymin() && a.p.y() > rect.ymax()) {
                a = a.left;
                range(a, rect, aq, true);
            }

            else if (a.p.y() < rect.ymin() && a.p.y() < rect.ymax()) {
                a = a.right;
                range(a, rect, aq, true);
            }

            else if (a.p.y() >= rect.ymin() && a.p.y() <= rect.ymax()) {

                if (a.p.x() >= rect.xmin() && a.p.x() <= rect.xmax()) {
                    aq.enqueue(a.p);
                }

                if (a.left != null) {
                    range(a.left, rect, aq, true);
                }

                if (a.right != null) {
                    range(a.right, rect, aq, true);
                }
            }
        }
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {

        exceptions(p);

        // start with champion at root where the min is very very far away
        champion = root;
        min = Double.POSITIVE_INFINITY;

        if (isEmpty()) {
            return null;
        }

        // run recursive method
        return Objects.requireNonNull(nearest(
                p, root, true, true, Double.POSITIVE_INFINITY)).p;
    }

    // recursive method of nearest that takes query point, the node, and
    // isVertical to keep track of which split we're on
    // isLeftChild to check which child the node in question is so we can
    // construct the bounding rectangle for it
    // parentCor passes the parent's x or y cor (depends on split) to the
    // child so we can construct the rectangle
    private Node nearest(Point2D query,
                         Node a,
                         boolean isVertical,
                         boolean isLeftChild,
                         double parentCor) {

        counter += 1;
        // base case if you've reached the end of tree
        if (a == null) {
            return null;
        }

        // vertical split
        if (isVertical) {

            // if query is to the left, first recursive on left
            if (query.x() < a.p.x()) {
                nearest(query, a.left, false, true, a.p.x());

                // create a bounding rectangle
                RectHV bound;

                // bounding rect is right half of A
                if (a.equals(root)) {
                    bound = new RectHV(a.p.x(),
                                       Double.NEGATIVE_INFINITY,
                                       Double.POSITIVE_INFINITY,
                                       Double.POSITIVE_INFINITY);
                }

                // xcor is min, parentCor depends on if leftchild or not
                else if (isLeftChild) {
                    bound = new RectHV(a.p.x(),
                                       Double.NEGATIVE_INFINITY,
                                       Double.POSITIVE_INFINITY,
                                       parentCor);
                }
                else {
                    bound = new RectHV(a.p.x(),
                                       parentCor,
                                       Double.POSITIVE_INFINITY,
                                       Double.POSITIVE_INFINITY);
                }

                // if dist between query & bounding rect is smaller than
                // min, check the right subtree
                if (bound.distanceSquaredTo(query) < min) {
                    nearest(query, a.right, false, false, a.p.x());
                }
            }

            // same thing as prev but you start on the right tree first
            else {
                nearest(query, a.right, false, false, a.p.x());

                RectHV bound;
                if (a.equals(root)) {
                    bound = new RectHV(Double.NEGATIVE_INFINITY,
                                       Double.NEGATIVE_INFINITY,
                                       a.p.x(),
                                       Double.POSITIVE_INFINITY);
                }
                else if (isLeftChild) {
                    bound = new RectHV(Double.NEGATIVE_INFINITY,
                                       Double.NEGATIVE_INFINITY,
                                       a.p.x(),
                                       parentCor);
                }

                else {
                    bound = new RectHV(Double.NEGATIVE_INFINITY,
                                       parentCor,
                                       a.p.x(),
                                       Double.POSITIVE_INFINITY);
                }

                if (bound.distanceSquaredTo(query) < min) {
                    nearest(query, a.left, false, true, a.p.x());
                }
            }
        }

        // horizontal split
        else {

            // if query is below
            if (query.y() < a.p.y()) {
                // same as prev methods but for horizontal splits going left first
                nearest(query, a.left, true, true, a.p.y());
                updateChampion(query, a);

                RectHV bound;
                if (isLeftChild) {
                    bound = new RectHV(Double.NEGATIVE_INFINITY,
                                       a.p.y(),
                                       parentCor,
                                       Double.POSITIVE_INFINITY);
                }

                else {
                    bound = new RectHV(parentCor,
                                       a.p.y(),
                                       Double.POSITIVE_INFINITY,
                                       Double.POSITIVE_INFINITY);
                }

                if (bound.distanceSquaredTo(query) < min) {
                    nearest(query, a.right, true, false, a.p.y());
                }
            }

            else {
                // going right first
                nearest(query, a.right, true, false, a.p.y());

                RectHV bound;
                if (isLeftChild) {
                    bound = new RectHV(Double.NEGATIVE_INFINITY,
                                       Double.NEGATIVE_INFINITY,
                                       parentCor,
                                       a.p.y());
                }

                else {
                    bound = new RectHV(parentCor,
                                       Double.NEGATIVE_INFINITY,
                                       Double.POSITIVE_INFINITY,
                                       a.p.y());
                }

                if (bound.distanceSquaredTo(query) < min) {
                    nearest(query, a.left, true, true, a.p.y());
                }
            }
        }

        return champion;
    }

    // updates the champion value
    private void updateChampion(Point2D query, Node a) {
        // calculate distance between points & update min & champ if closer
        double dist = a.p.distanceSquaredTo(query);
        if (dist < min) {
            min = dist;
            champion = a;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        // client testing
        String filename = args[0];
        In in = new In(filename);

        // initialize the two data structures with point from standard input
        // from Nearest neighbor visualizer
        KdTreeST<Integer> kdtree = new KdTreeST<Integer>();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.put(p, i);
        }

        int count2 = 0;
        double ti2 = 0;

        for (int i = 0; i < 1000000; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            Point2D rand = new Point2D(x, y);
            Stopwatch tim2 = new Stopwatch();
            kdtree.nearest(rand);
            double time2 = tim2.elapsedTime();
            count2 += kdtree.counter;
            ti2 += time2;
        }

        StdOut.println(count2);
        StdOut.println(ti2);

        KdTreeST<Integer> test = new KdTreeST<>();
        Point2D a = new Point2D(0.7, 0.2);
        Point2D b = new Point2D(0.5, 0.4);
        Point2D c = new Point2D(0.2, 0.3);
        Point2D d = new Point2D(0.4, 0.7);
        Point2D e = new Point2D(0.9, 0.6);
        // Point2D f = new Point2D(0.7, 0.6);

        Point2D g = new Point2D(0.7, 0.2);
        Point2D h = new Point2D(1, 1);

        int x = 0;
        int y = 1;

        StdOut.println(test.isEmpty()); // true
        StdOut.println(test.size());    // 0

        test.put(a, x);
        test.put(b, x);
        test.put(c, x);
        test.put(d, x);
        test.put(e, x);
        // test.put(f, x);

        StdOut.println(test.get(a));   // 0

        for (Point2D p : test.points()) {
            StdOut.println(p);
        }

        test.put(g, y);

        StdOut.println(test.isEmpty()); // false
        StdOut.println(test.size());    // 6

        StdOut.println(test.get(a));    // changed to 1

        StdOut.println(test.contains(a));  // all true except last
        StdOut.println(test.contains(b));
        StdOut.println(test.contains(c));
        StdOut.println(test.contains(d));
        StdOut.println(test.contains(e));
        // StdOut.println(test.contains(f));
        StdOut.println(test.contains(g));
        StdOut.println(test.contains(h));

        // no points
        RectHV rect = new RectHV(0, 0, 0, 0);
        for (Point2D p : test.range(rect)) {
            StdOut.println(p);
        }

        // all xcor less than equal to 5
        RectHV rect2 = new RectHV(0, 0, 0.5, 1);
        for (Point2D p : test.range(rect2)) {
            StdOut.println(p);
        }

        // expect every point
        RectHV rect3 = new RectHV(0, 0, 1, 1);
        for (Point2D p : test.range(rect3)) {
            StdOut.println(p);
        }

        // expect 0.5, 0.4
        Point2D query = new Point2D(0.5, 0.5);
        StdOut.println(test.nearest(query));

        // expect 0.2, 0.3
        Point2D query2 = new Point2D(0, 0);
        StdOut.println(test.nearest(query2));

        // expect 0.9, 0.6
        Point2D query3 = new Point2D(0.041, 445);
        StdOut.println(test.nearest(query3));

    }

}
