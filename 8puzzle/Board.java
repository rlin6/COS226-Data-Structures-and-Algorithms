/* *****************************************************************************
 *  Name:    Ricky Lin
 *  NetID:   rickyl
 *  Precept: P09
 *
 *  Partner Name:    Darin Avila
 *  Partner NetID:   dgavila
 *  Partner Precept: P09
 *
 *  Description:  Creates a Board object containing the tiles of the puzzle.
 *                It stores the 2D array of tiles, the size, location of the
 *                blank tile, hamming distance, and manhattan distance. Our
 *                methods allow us to return a String representation, tile at
 *                at a location, size, the distances, two methods telling
 *                us if we've reached a goal board or the board is solvable,
 *                a way to compare two boards, and an iterable of neighbors.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    // creates board containing tiles
    private int[] board;

    // size
    private final int n;

    // stores row of blank tile
    private int blankRow;

    // stores col of blank tile
    private int blankCol;

    // stores hamming distance
    private int hamming;

    // stores manhattan distance
    private int manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        // set board to right size
        n = tiles.length;
        board = new int[n * n];


        for (int i = 0; i < n * n; i++) {

            int row = i / n;
            int col = i % n;

            // copy over tile
            board[i] = tiles[row][col];

            int tile = tileAt(row, col);

            // save location of blank tile
            if (tile == 0) {
                blankRow = row;
                blankCol = col;
            }

            // calculate manhattan and hamming distances
            else {
                int col2 = (tile - 1) % n;
                int row2 = (tile - 1) / n;

                manhattan += Math.abs(row - row2) + Math.abs(col - col2);

                if (tile != (row * n + col + 1)) {
                    hamming += 1;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {

        // size
        StringBuilder retStr = new StringBuilder("" + n);

        // adds each tile
        for (int i = 0; i < n * n; i++) {
            int row = i / n;
            int col = i % n;

            if (col == 0) {
                retStr.append("\n");
            }

            retStr.append(String.format("%2d ", tileAt(row, col)));
        }

        return retStr.toString();
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {

        // throw exception when out of range
        if (row > n - 1 || row < 0 || col > n - 1 || col < 0) {
            throw new IllegalArgumentException("Row/Col out of range");
        }

        return board[row * n + col];
    }

    // board size n
    public int size() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }


    // does this board equal y?
    public boolean equals(Object y) {

        // easy corner case when equal
        if (this == y) {
            return true;
        }

        // corner case when y null or not right class
        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }

        Board other = (Board) y;

        // compare each value if same size
        if (this.size() == other.size()) {
            for (int i = 0; i < n * n; i++) {
                if (this.board[i] != other.board[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // method that converts 1D array to 2D
    private int[][] shift(Board b) {
        int[][] twoD = new int[n][n];
        for (int i = 0; i < n * n; i++) {
            twoD[i / n][i % n] = b.board[i];
        }
        return twoD;
    }

    // create neighbor board based on given row and col
    private Board createNeighbor(int row, int col) {

        // swap blank tile with left/up/right/down neighbor and
        // set new blank row and col
        Board copy = new Board(shift(this));
        copy.board[blankRow * n + blankCol] = copy.tileAt(row, col);
        copy.blankRow = row;
        copy.blankCol = col;
        copy.board[row * n + col] = 0;

        // create new board to get new hamming/manhanttan distances
        Board neighbor = new Board(shift(copy));
        return neighbor;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        // iterable to store neighbors
        Queue<Board> neighbors = new Queue<Board>();

        if (blankCol > 0) {                   // add left neighbor
            neighbors.enqueue(
                    createNeighbor(blankRow, blankCol - 1)
            );
        }

        if (blankCol < size() - 1) {          // add right neighbor
            neighbors.enqueue(
                    createNeighbor(blankRow, blankCol + 1)
            );
        }

        if (blankRow > 0) {                   // add up neighbor
            neighbors.enqueue(
                    createNeighbor(blankRow - 1, blankCol)
            );
        }

        if (blankRow < size() - 1) {          // add down neighbor
            neighbors.enqueue(
                    createNeighbor(blankRow + 1, blankCol)
            );
        }

        return neighbors;
    }


    // is this board solvable?
    public boolean isSolvable() {
        int inversions = 0;

        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < n * n; j++) {

                int row = i / n;
                int col = i % n;
                int row2 = j / n;
                int col2 = j % n;

                int small = i + 1;
                int big = j + 1;

                // check for inversions
                if (tileAt(row2, col2) != 0 && tileAt(row, col) != 0) {
                    if (small > big && tileAt(row2, col2) > tileAt(row, col)) {
                        inversions++;
                    }
                }
            }
        }

        // odd case
        if (n % 2 != 0 && inversions % 2 != 0) {
            return false;
        }

        // even case
        if (n % 2 == 0 && (inversions + blankRow) % 2 == 0) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {

        int[][] tiles = {
                { 3, 1, 6, 4 },
                { 5, 0, 9, 7 },
                { 10, 2, 11, 8 },
                { 13, 15, 14, 12 }
        };

        Board test = new Board(tiles);

        StdOut.println(test);
        StdOut.println("Size: " + test.size());
        StdOut.println("Hamming: " + test.hamming());
        StdOut.println("Manhattan: " + test.manhattan());
        StdOut.println("Tiles at 1,1: " + test.tileAt(1, 1));
        StdOut.println("isGoal: " + test.isGoal());

        StdOut.println("isSolvable: " + test.isSolvable());

        StdOut.println("---- Testing neighbors -----");
        for (Board n : test.neighbors()) {
            StdOut.println(n);
        }

        int[][] tiles2 = {
                { 3, 1, 6, 4 },
                { 5, 0, 9, 7 },
                { 10, 2, 11, 8 },
                { 13, 15, 14, 12 }
        };

        int[][] tiles3 = {
                { 3, 2, 4, 8 },
                { 1, 6, 0, 12 },
                { 5, 10, 7, 11 },
                { 9, 13, 14, 15 }
        };

        int[][] goal = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 0 }
        };

        Board test2 = new Board(tiles2);
        Board test3 = new Board(tiles3);
        Board goals = new Board(goal);

        StdOut.println(test.equals(test2));
        StdOut.println(test.equals(goals));

        StdOut.println(goals.hamming());
        StdOut.println(goals.manhattan());

        StdOut.println(goals.isGoal());

        StdOut.println("isSolvableTest for unsolvable: " + test3.isSolvable());
    }
}
