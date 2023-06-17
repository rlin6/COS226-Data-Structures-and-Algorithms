/* *****************************************************************************
 *  Name: Ricky Lin
 *  NetID: rickyl
 *  Precept: P09
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Operating system: Mac OS X
 *  Compiler: Java Programming Language Compiler (javac)
 *  Text editor / IDE: IntelliJ
 *
 *  Have you taken (part of) this course before: No
 *  Have you taken (part of) the Coursera course Algorithms, Part I or II: No
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 1: Percolation



/* *****************************************************************************
 *  Describe how you implemented Percolation.java. How did you check
 *  whether the system percolates?
 **************************************************************************** */

I used the idea of the virtual top node and bottom node to check percolation.
Using a map function that converted the 2D grid's coordinates into 1D array's
indices, I used a boolean array to keep track of which node was open. I start
with the virtual ones open, and my open function would change the boolean value
at the index to true, and check for other open sites near it. To work around
backwashing, I also created another WeightedQuickUnionUF that only had a
virtual top. A node is full it has the same canonical number as the top
virtual node in both UF structures to prevent backwashing. The system
percolates when the top virtual node has the same canonical number as the bottom
virtual node, meaning they are connected.

/* *****************************************************************************
 *  Perform computational experiments to estimate the running time of
 *  PercolationStats.java for various values of n and T when implementing
 *  Percolation.java with QuickFindUF.java (not QuickUnionUF.java). Use a
 *  "doubling" hypothesis, where you successively increase either n or T by
 *  a constant multiplicative factor (not necessarily 2).
 *
 *  To do so, fill in the two tables below. Each table must have 5-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one. Do not include
 *  data points that take less than 0.25 seconds.
 **************************************************************************** */

(keep T constant)
 T = 100
 multiplicative factor (for n) = 1.2

 n          time (seconds)       ratio         log ratio
--------------------------------------------------------
55         0.287                 -----         -----
66         0.561                 1.954         3.674
79         1.097                 1.955         3.677
95         2.218                 2.022         3.862
114        4.638                 2.091         4.046
136        9.572                 2.064         3.975


(keep n constant)
 n = 100
 multiplicative factor (for T) = 1.5

 T          time (seconds)       ratio         log ratio
--------------------------------------------------------
10          0.286                -----         -----
15          0.424                1.483         0.972
23          0.633                1.493         0.988
34          0.954                1.507         1.011
51          1.441                1.510         1.016
76          2.032                1.410         0.847
114         3.157                1.553         1.086


/* *****************************************************************************
 *  Using the empirical data from the above two tables, give a formula
 *  (using tilde notation) for the running time (in seconds) of
 *  PercolationStats.java as function of both n and T, such as
 *
 *       ~ 5.3*10^-8 * n^5.1 * T^1.5
 *
 *  Briefly explain how you determined the formula for the running time.
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Round each coefficient and exponent to two significant digits.
 **************************************************************************** */

I found two entries for n and T where the times are similar.
Then, I set the time of that entry equal to constant a times
the actual value of n raised to the average of the log
ratio and solved for a. a is the constant and I raised n and
T to their respective average log ratio.

QuickFindUF running time (in seconds) as a function of n and T:

    ~ 5.8*10^-8 * n^3.8 * T^.99
       _______________________________________



/* *****************************************************************************
 *  Repeat the previous two questions, but using WeightedQuickUnionUF
 *  (instead of QuickFindUF).
 **************************************************************************** */

(keep T constant)
 T = 100
 Factor = 1.5

 n          time (seconds)       ratio         log ratio
--------------------------------------------------------
200         0.381                -----         -----
300         0.836                2.194         1.938
450         1.983                2.372         2.130
675         5.809                2.929         2.650
1013        18.303               3.151         2.830


(keep n constant)
 n = 100
 Factor = 1.5

 T          time (seconds)       ratio         log ratio
--------------------------------------------------------
250         0.288                 -----        -----
375         0.352                 1.222        0.494
563         0.482                 1.369        0.775
844         0.677                 1.405        0.839
1266        0.958                 1.415        0.856


WeightedQuickUnionUF running time (in seconds) as a function of n and T:

    ~ 1.2*10^-6 * n^2.4 * T^.74
       _______________________________________



/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

While my code does fix backwash, I had to use two UF data structures in order to
do so. Doing this takes up a lot of memory, most of which is wasted on the same
union operations.

/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */

I discussed how union find worked with Darin Avila to understand the algorithm
better. I also went to Professor Tarjan for assistance in order to fix backwash.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

Initially, I didn't know how to use the unionfind data structure to organize a
grid. My initial impression was that I had to use a 2D array to map out the grid
and use some sort of connectivity to find it. However, I recognized the
usefulness of using a method to convert those 2D coordinates into 1D array indices
to make the data structure work with my code.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
