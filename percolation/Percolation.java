/* *****************************************************************************
 *  Name:    Ricky Lin
 *  NetID:   rickyl
 *  Precept: P09
 *
 *  Description:  Creates a n by n grid using union find
 *                where the user can open sections of the
 *                grid. Open sections connected to the top
 *                row will be considered full. When there
 *                is a full site on the bottom row, the
 *                system is considered to have percolated.
 *
 *  Written:       9/16/2019
 *  Last updated:  9/16/2019
 *
 *  % javac Percolation.java
 *  % java Percolation
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // UF storing the grid with virtual top and bottom
    private WeightedQuickUnionUF grid;

    // same grid but without virtual bottom
    private WeightedQuickUnionUF backwash;

    // keeps track of which sites are open
    private boolean[] opened;

    // remembers the dimensions of grid
    private int size;

    // tracks number of open sites
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    // takes n as dimensions and throws exception if n <= 0
    // initializes the two grids, opened, and set size to n
    // opens the virtual top and bottom sites
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException(
                    "Dimensions of grid cannot be 0 or less"
            );
        }

        // initializes the grids n by n with 1-2 extra
        // slots for virtual top and/or bottom
        grid = new WeightedQuickUnionUF(n * n + 2);
        backwash = new WeightedQuickUnionUF(n * n + 1);

        opened = new boolean[n * n + 2];
        size = n;

        // set virtual sites open
        opened[0] = true;
        opened[n * n + 1] = true;
    }

    // takes the row, column, and dimensions of the grid
    // calculate the 1D array index from 2D coordinates
    private int map(int row, int col, int dimension) {
        return row * dimension + col + 1;  // the +1 accounts for virtual top
    }

    // wrapper function for the find method in unionfind library
    private int find(int p) {
        return grid.find(p);
    }

    // overloaded method where we take the 2D coordinates instead of index
    private int find(int row, int col) {
        return grid.find(map(row, col, size));
    }

    // method that sets the limits of row and col inputs
    private void bounds(int row, int col) {

        if (row < 0 || row >= size) {
            throw new IllegalArgumentException("Rows are out of bounds");
        }

        if (col < 0 || col >= size) {
            throw new IllegalArgumentException("Columns are out of bounds");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        bounds(row, col);                // check input is valid

        int site = map(row, col, size);  // convert to site's index in array
        if (!opened[site]) {
            openSites++;
        }
        opened[site] = true;             // open the site

        if (col > 0) {                   // look at sites with left neighbor
            if (opened[site - 1]) {      // check if left open and union if true
                grid.union(site, site - 1);
                backwash.union(site, site - 1);
            }
        }

        if (col < size - 1) {            // look at sites with right neighbor
            if (opened[site + 1]) {      // check if right open and union if true
                grid.union(site, site + 1);
                backwash.union(site, site + 1);
            }
        }

        if (row > 0) {                   // look at sites with up neighbor
            if (row == size - 1) {       // if bottom row, union with virtual bot
                grid.union(size * size + 1, site);
            }
            if (opened[site - size]) {   // check if up open and union if true
                grid.union(site, site - size);
                backwash.union(site, site - size);
            }
        }

        if (row < size - 1) {            // look at sites with down neighbor
            if (row == 0) {              // if top row, union with virtual top
                grid.union(0, site);
                backwash.union(0, site);
            }
            if (opened[site + size]) {   // check if down open and union if true
                grid.union(site, site + size);
                backwash.union(site, site + size);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        bounds(row, col);                   // check inputs are valid

        return opened[map(row, col, size)]; // check if site is open in boolean
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        bounds(row, col);                   // check if inputs valid

        // if a site is full, it must have the same canonical element as the
        // virtual top in both backwash and grid data structures
        return backwash.find(map(row, col, size)) == backwash.find(0) &&
                grid.find(map(row, col, size)) == grid.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {

        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {

        // percolates if just one
        if (size == 1 && numberOfOpenSites() == 1) {
            return true;
        }
        // system percolates when virtual top and bottom has
        // same canonical element == connected
        return find(0) == find(size * size + 1);
    }

    // unit testing (required)
    public static void main(String[] args) {

        // Percolation test = new Percolation(-1); returns exception
        Percolation test = new Percolation(5);    // creates 5 x 5 grid

        for (int i = 0; i < 27; i++) {
            StdOut.println(test.find(i));     // prints all elements in 5 x 5 grid
        }

        // test.open(-1, 0);                          returns exception

        StdOut.println(test.numberOfOpenSites());   // expect 0, no open sites

        StdOut.println(test.isOpen(0, 0));          // expect false
        test.open(2, 2);
        StdOut.println(test.find(2, 2));            // expect 13
        StdOut.println(test.isOpen(2, 2));          // expect true, is open
        StdOut.println(test.isOpen(3, 3));          // expect false, not open

        test.open(3, 3);
        StdOut.println(test.find(3, 3));            // expect 19


        test.open(2, 3);
        StdOut.println(test.isFull(2, 2));             // expect false, no path to top

        test.open(1, 2);
        StdOut.println(test.isFull(1, 2));             // expect false, no path to top

        StdOut.println(test.find(1, 2));               // expect 14 for next 4
        StdOut.println(test.find(2, 2));
        StdOut.println(test.find(2, 3));
        StdOut.println(test.find(3, 3));
        StdOut.println(test.find(0, 2));               // expect 3, haven't been open

        test.open(0, 2);

        StdOut.println(test.percolates());             // expect false, no path
        StdOut.println(test.isFull(0, 2));             // expect all trues, linked top
        StdOut.println(test.isFull(1, 2));
        StdOut.println(test.isFull(2, 2));
        StdOut.println(test.isFull(2, 3));
        StdOut.println(test.isFull(3, 3));

        test.open(4, 3);
        StdOut.println(test.percolates());             // connects to bottom row, true
    }

}
