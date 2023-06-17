/* *****************************************************************************
 *  Name: Ricky Lin
 *  NetID: rickyl
 *  Precept: P09
 *
 *  Partner Name:     N/A
 *  Partner NetID:    N/A
 *  Partner Precept:  N/A
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 8: Burrows-Wheeler



/* *****************************************************************************
 *  List in table format which input files you used to test your program.
 *  Fill in columns for how long your program takes to compress and
 *  decompress these instances (by applying BurrowsWheeler, MoveToFront,
 *  and Huffman in succession). Also, fill in the third column for
 *  the compression ratio (number of bytes in compressed message
 *  divided by the number of bytes in the message).
 *
 *  Use three significant digits for the compression ratio.
 **************************************************************************** */

File     Encoding Time    Decoding time      Compression ratio
------------------------------------------------------------------------
mobydick.txt   1.546s         0.353s                0.347
amendments.txt 0.356s         0.217s                0.302
movetofront.class  0.232s     0.486s                0.475

/* *****************************************************************************
 *  Compare the compression ratio and running time of your program on
 *  mobydick.txt to that of gzip and Huffman alone. Use three digits of
 *  precision for each entry.
 **************************************************************************** */

Algorithm             Time (seconds)          Compression ratio
----------------------------------------------------------------------
My program            1.546                   0.346 (413692 / 1191463)
Huffman alone         0.855                   0.560 (667651 / 1191463)
gzip                  0.110                   0.408 (485952 / 1191463)

/* *****************************************************************************
 *  Give the order of growth of the running time of each of the 7
 *  methods as a function of the input size n and the alphabet size R
 *  both in practice (on typical English text inputs) and in theory
 *  (in the worst case), e.g., n, n + R, n log n, n^2, or R n.
 *
 *  Include the time for sorting circular suffixes in Burrows-Wheeler
 *  transform().
 **************************************************************************** */

                                      typical            worst
---------------------------------------------------------------------
CircularSuffixArray constructor       n log(baseR)n      n^2
BurrowsWheeler transform()            n log(baseR)n      n^2
BurrowsWheeler inverseTransform()     n + R              n + R
MoveToFront encode()                  n + R              nR
MoveToFront decode()                  n + R              nR
Huffman compress()                    n + R log R        n + R log R
Huffman expand()                      n                  n





/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

Slow?

/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */

I discussed concepts behind the sorting aspects with Darin Avila.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

I confused myself with Inverse Transform before realizing it was much simpler
than I thought it was.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it. Additionally, you may include any suggestions
 *  for what to change or what to keep (assignments or otherwise) in future
 *  semesters.
 **************************************************************************** */
