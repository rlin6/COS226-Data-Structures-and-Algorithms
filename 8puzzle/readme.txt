/* *****************************************************************************
 *  Name: Darin Avila
 *  NetID: dgavila
 *  Precept: P09
 *
 *  Partner Name: Ricky Lin
 *  Partner NetID: rickyl
 *  Partner Precept: P09
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 4: Slider Puzzle


/* *****************************************************************************
 *  Explain briefly how you represented the Board data type.
 **************************************************************************** */
We represented the Board as an 1-dimensional array of integers. Our instance variables
included the size of the array, the row and column containing the zero, and the
two heuristics. We were able to implement the API as written using these variables,
as well as a private method createNeighbor() which made a neighboring board. We
switched 2D coordinates into 1D to save memory for each Board.



/* *****************************************************************************
 *  Explain briefly how you represented a search node
 *  (board + number of moves + previous search node).
 **************************************************************************** */
Our search node was contained inside Solver.java as a private class whose constructor
included parameters for board, number of moves, and previous search node. We
calculated the results of the priority funtion for each node outside in the
compareTo.




/* *****************************************************************************
 *  Explain briefly how you detected unsolvable puzzles.
 *
 *  What is the order of growth of the running time of your isSolvable()
 *  method in the worst case as function of the board size n? Recall that with
 *  order-of-growth notation, you should discard leading coefficients and
 *  lower-order terms, e.g., n log n or n^3.
 **************************************************************************** */

Description:
We compared each position in the board to every other position in the board (not
including the zero space) and kept track of the row-major position, and incremented
a local variable that counts inversions each time a tile was greater than a tile which
was in a greater row-major position. We then simply included if-statements which
correlated to the rules for solvable puzzles.



Order of growth of running time: N^4



/* *****************************************************************************
 *  For each of the following instances, give the minimum number of moves to
 *  solve the instance (as reported by your program). Also, give the amount
 *  of time your program takes with both the Hamming and Manhattan priority
 *  functions. If your program can't solve the instance in a reasonable
 *  amount of time (say, 5 minutes) or memory, indicate that instead. Note
 *  that your program may be able to solve puzzle[xx].txt even if it can't
 *  solve puzzle[yy].txt and xx > yy.
 **************************************************************************** */


                 min number          seconds
     instance     of moves     Hamming     Manhattan
   ------------  ----------   ----------   ----------
   puzzle28.txt     28          1.249        0.049
   puzzle30.txt     30          2.129        0.058
   puzzle32.txt     32       Memory error    1.305
   puzzle34.txt     34            |          0.395
   puzzle36.txt     36            |          3.761
   puzzle38.txt     38            |          2.635
   puzzle40.txt     40            |          0.8
   puzzle42.txt     42            v          2.349



/* *****************************************************************************
 *  If you wanted to solve random 4-by-4 or 5-by-5 puzzles, which
 *  would you prefer: a faster computer (say, 2x as fast), more memory
 *  (say 2x as much), a better priority queue (say, 2x as fast),
 *  or a better priority function (say, one on the order of improvement
 *  from Hamming to Manhattan)? Why?
 **************************************************************************** */
Judging by how much better Manhattan performs than Hamming (Manhattan
was able to solve many puzzles in under one minute that Hamming couldn't
do), a better heuristic is clearly more important to a puzzle solving
algorithm such as this than an increase in computing power.




/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
It is slow with the Hamming heuristic. We are also using a lot of memory for
Solver and we are not sure how to fix it.


/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */
N/A




/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */
We had major bugs in getting the puzzle to actually complete, as our hamming distance
was off by one (due to an error in when we calculated it relative to switching the
position of the zero) so the puzzle would always be solved up until the second to last
move.


/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */
We followed the protocol as indicated on the assignment page.

I, Darin Avila, worked on computing the Manhattan and Hamming heuristics, composing
the searchnode private class, worked on various public methods and debugging.

I, Ricky Lin, helped to build the Iterators and to handle the usage of the
MinPQ class as well as the loop which performs the A* algorithm.

We both made fair contributions to the project.




/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
