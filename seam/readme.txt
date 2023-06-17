/* *****************************************************************************
 *  Name:    Karen Li
 *  NetID:   karenli
 *  Precept: P01
 *
 *  Partner Name:    Ricky Lin
 *  Partner NetID:   rickyl
 *  Partner Precept: P09
 *
 *  Hours to complete assignment (optional): 10
 *
 **************************************************************************** */

Programming Assignment 7: Seam Carving


/* *****************************************************************************
 *  Describe concisely your algorithm to compute the horizontal and
 *  vertical seam.
 **************************************************************************** */
    For vertical seams, we created arrays to store the seam, edgeTo, and distTo.
    We also created a matrix to story the calculated energies of all the pixels
    as well as a IndexedMinPQ to know which pixels to prioritize in our search.
    We initialized all the values of distTo to positive infinity except for the
    elements in the first row. These elements had distTo values equal to their
    energies. In addition, we added the energies of the first row to the PQ (to
    simulate "virtual top"). Then, we ran Dijkstra's algorithm by dequeuing the
    min energy from the PQ and relaxing all of its edges (left down, down, right
    down). Once we hit the bottom row, we stop, as we have found the shortest path
    to the bottom. For the horizontal seam, we simply transpose the picture,
    run the algo for vertical seam, and transpose the picture back.

/* *****************************************************************************
 *  Describe what makes an image ideal for the seam-carving algorithm.
 *  Describe an image that would not work well.
 **************************************************************************** */
    An ideal image would have smooth transitions in color/shade (i.e. lots of
    adjacent low energy pixels).

    Am image that would not work well, would have lots of sharp contrasting
    colors and shades (lots of high energy pixels).


/* *****************************************************************************
 *  Perform computational experiments to estimate the running time to reduce
 *  a W-by-H image by one column (i.e., one call to findVerticalSeam() followed
 *  by one call to removeVerticalSeam()). Use a "doubling" hypothesis, where
 *  you successively increase either W or H by a constant multiplicative
 *  factor (not necessarily 2).
 *
 *  To do so, fill in the two tables below. Each table must have 5-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one.
 **************************************************************************** */

(keep W constant)
 W = 1000
 multiplicative factor (for H) = 3


 H           Column removal time (seconds)
------------------------------------------
20           0.302
60           0.957
180          3.074
540          8.748
1620         26.618
* we used -Xint *


(keep H constant)
 H = 1000
 multiplicative factor (for W) = 3

 W           Column removal time (seconds)
------------------------------------------
20           0.273
60           0.856
180          2.781
540          8.775
1620         26.982
* we used -Xint *


/* *****************************************************************************
 *  Using the empirical data from the above two tables, give a formula
 *  (using tilde notation) for the running time (in seconds) as a function
 *  of both W and H, such as
 *
 *       ~ 5.3*10^-8 * W^5.1 * H^1.5
 *
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Round each coefficient and exponent to two significant digits.
 **************************************************************************** */


Running time (in seconds) to remove one column as a function of both W and H:


    ~ 1.0*10^-5 * W^1.0 * H^1.0
       _______________________________________




/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */
   Chris Sciavolino's office hours: helped us understand how underlying data
   structures and algorithms work together.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */
    Using height(), width(), row, and col variables correctly.


/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */
    We followed the protocol as described on the assignment page. We did all of
    the functions and testing together in person. We followed pair-programming
    protocol and took turns being the driver and the navigator and contributed
    to the code equally.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
    N/A.
