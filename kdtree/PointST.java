/* *****************************************************************************
 *  Name:    Darin Avila
 *  NetID:   dgavila
 *  Precept: P09
 *
 *  Partner Name:    Ricky Lin
 *  Partner NetID:   rickyl
 *  Partner Precept: P09
 *
 *  Description:  Creates a symbol table using a red-black binary search
 *                tree to hold information. The symbol table takes a
 *                two-dimensional point as it's key, and can take any value.
 *                The symbol table supports methods to put and get data, check
 *                if it contains a certain key, return its size/emptiness, and
 *                iterators which can access all keys, or only those keys
 *                within a specified rectangle.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class PointST<Value> {

    // underlying container holding Point2D as keys, value as value
    private final RedBlackBST<Point2D, Value> rb;

    //
    private int counter = 0;

    // construct an empty symbol table of points
    public PointST() {
        rb = new RedBlackBST<Point2D, Value>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return rb.isEmpty();
    }

    // number of points
    public int size() {
        return rb.size();
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
        exceptions(val);
        rb.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        exceptions(p);
        return rb.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        return rb.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return rb.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

        exceptions(rect);

        Queue<Point2D> r = new Queue<Point2D>();
        for (Point2D p : rb.keys()) {
            if (rect.contains(p)) {
                r.enqueue(p);
            }
        }
        return r;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {

        counter += 1;
        exceptions(p);

        if (rb.isEmpty()) {
            return null;
        }

        Point2D min = rb.max();
        double mindist = rb.max().distanceSquaredTo(p);
        for (Point2D key : rb.keys()) {
            if (p.distanceSquaredTo(key) < mindist) {
                min = key;
                mindist = p.distanceSquaredTo(key);
            }
        }
        return min;
    }

    // unit testing (required)
    public static void main(String[] args) {

        String filename = args[0];
        In in = new In(filename);

        // initialize the two data structures with point from standard input
        PointST<Integer> brute = new PointST<Integer>();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.put(p, i);
        }

        int count1 = 0;
        double ti1 = 0;

        for (int i = 0; i < 100; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            Point2D rand = new Point2D(x, y);
            Stopwatch tim = new Stopwatch();
            brute.nearest(rand);
            double time = tim.elapsedTime();
            count1 += brute.counter;
            ti1 += time;
        }

        StdOut.println(count1);
        StdOut.println(ti1);

        PointST<Integer> st = new PointST<Integer>();

        // prints if the symbol table is empty
        System.out.println("Is empty?: " + st.isEmpty());


        // creates points
        Point2D a = new Point2D(1, 5);
        Point2D b = new Point2D(2, 7);
        Point2D c = new Point2D(7, 2);
        Point2D d = new Point2D(-30, 11);
        Point2D e = new Point2D(0.0325, 6.25);
        Point2D f = new Point2D(0, 0);
        Point2D g = new Point2D(1, -1);

        int x = 0;
        int y = 5;


        // prints if the symbol table contains point a
        System.out.println("Contains a: " + st.contains(a));

        // puts the created points in the symbol table with values
        st.put(a, x);
        st.put(b, x);
        st.put(c, x);
        st.put(d, y);
        st.put(e, x);
        st.put(f, x);
        st.put(g, x);


        // prints if the symbol table is empty
        System.out.println("Is empty?: " + st.isEmpty());

        // prints if the symbol table contains point a
        System.out.println("Contains a: " + st.contains(a));

        // prints the value associated with point d (shuld be 5)
        System.out.println("Get d: " + st.get(d));

        // prints the value associated with point a (shuld be 0)
        System.out.println("Get a: " + st.get(a));

        // returns the size of the symbol table (Should be 7)
        System.out.println("Size: " + st.size());

        // returns each point in the symbol table
        StdOut.println("Iterator for entire table:");
        for (Point2D p : st.points()) {
            StdOut.println(p);
        }


        // returns each point in the symbol table within the given rectangle
        // should be a, b, c, e, and f
        StdOut.println("Iterator for rectangle (0,0) to (10, 10):");
        RectHV rectangle = new RectHV(0, 0, 10, 10);
        for (Point2D v : st.range(rectangle)) {
            StdOut.println(v);
        }

        // prints the closest point to f (should be G)
        StdOut.println("Nearest point to (0,0): " + st.nearest(f));


        Point2D same = new Point2D(-30, 11);
        int z = 42;
        st.put(same, z);

        // should still return 7, as value was mutated for a point which
        // already existed in the table
        StdOut.println("Size after inserting same point: " + st.size());
    }


}
