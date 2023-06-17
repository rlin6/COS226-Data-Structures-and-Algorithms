/* *****************************************************************************
 *  Name:    Ricky Lin
 *  NetID:   rickyl
 *  Precept: P09
 *
 *  Partner Name:    Darin Avila
 *  Partner NetID:   dgavila
 *  Partner Precept: P09
 *
 *  Description:  Creates a Solver object with a MinPQ that stores all the
 *                SearchNodes, representations of one possible state of the
 *                board, and a Stack that stores the shortest path to the goal
 *                board. The SearchNode class stores the board, priority (sum
 *                of moves and board's heuristic distance), moves, and a
 *                reference to the previous SearchNode. When we construct a
 *                Solver object, it solves the puzzle and returns the goal
 *                board.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Solver {

    // MinPQ holding boards to find solution
    private final MinPQ<SearchNode> solver;

    // Stack storing all boards needed to reach solution
    private final Stack<Board> path;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        // exceptions
        if (initial == null) {
            throw new IllegalArgumentException("Null board");
        }

        if (!(initial.isSolvable())) {
            throw new IllegalArgumentException("Not solvable");
        }

        // create structs & add first SearchNode
        solver = new MinPQ<SearchNode>();
        path = new Stack<Board>();
        SearchNode first = new SearchNode(initial, 0, null);
        solver.insert(first);
        boolean searching = true;

        if (solver.min().isGoal()) {
            searching = false;
        }

        // run while still looking for final board
        while (searching) {

            // dequeue min & add it to solutions
            SearchNode dequeued = solver.delMin();

            // find every neighbor & create a SearchNode
            for (Board neighbor : dequeued.board.neighbors()) {
                // if first neighbor, insert
                if (dequeued.moves == 0) {
                    SearchNode n = new SearchNode(
                            neighbor, dequeued.moves + 1, dequeued);
                    solver.insert(n);

                    // check if you found goal board
                    if (solver.min().isGoal()) {
                        searching = false;
                    }
                }

                // if SearchNode has previous SearchNode
                else if (dequeued.prev != null) {

                    // only add if not the same as previous board
                    if (!(neighbor.equals(dequeued.prev.board))) {
                        SearchNode n = new SearchNode(
                                neighbor, dequeued.moves + 1, dequeued);
                        solver.insert(n);
                        if (solver.min().isGoal()) {
                            searching = false;
                        }
                    }
                }
            }
        }

        // traverse backwards through the right tree
        SearchNode curr = solver.min();
        while (curr.prev != null) {
            path.push(curr.board);
            curr = curr.prev;
        }
        path.push(curr.board);
    }

    private class SearchNode implements Comparable<SearchNode> {

        // board of current SearchNode
        private final Board board;

        // number of moves so far
        private final int moves;

        // reference to previous SearchNode
        private final SearchNode prev;

        // constructor for all nodes
        public SearchNode(Board tiles, int move, SearchNode previous) {
            board = tiles;

            prev = previous;
            moves = move;
        }

        // compares priority of SearchNodes
        public int compareTo(SearchNode s) {
            if (this.board.manhattan() + this.moves >
                    s.board.manhattan() + s.moves) {
                return 1;
            }
            else if (this.board.manhattan() + this.moves <
                    s.board.manhattan() + s.moves) {
                return -1;
            }
            return 0;
        }

        // return if SearchNode is goal state
        public boolean isGoal() {
            return board.isGoal();
        }
    }

    // min number of moves to solve initial board
    public int moves() {
        return solver.min().moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return path;
    }

    // test client (see below)
    public static void main(String[] args) {

        // parse file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                tiles[row][col] = in.readInt();
            }
        }

        Board puzzle = new Board(tiles);

        // if solvable, time and print the path to solution
        if (puzzle.isSolvable()) {
            Stopwatch watch = new Stopwatch();
            Solver answer = new Solver(puzzle);
            double time = watch.elapsedTime();
            for (Board step : answer.solution()) {
                StdOut.println(step);
            }
            StdOut.println(time);
            StdOut.println("Minimum number of moves = " + answer.moves());
        }

        // else print not solvable
        else {
            StdOut.println(puzzle);
            StdOut.println("Unsolvable puzzle");
        }
    }
}
