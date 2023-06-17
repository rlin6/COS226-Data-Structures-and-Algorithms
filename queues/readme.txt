/* *****************************************************************************
 *  Name: Ricky Lin
 *  NetID: rickyl
 *  Precept: P09
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 2: Deques and Randomized Queues


/* *****************************************************************************
 *  Explain briefly how you implemented the randomized queue and deque.
 *  Which data structure did you choose (array, linked list, etc.)
 *  and why?
 **************************************************************************** */

I implemented the randomized queue using an array. This is because arrays
allow for constant time index access, which makes shuffling the queue much
more easier because you do not have to iterate through the entire linked list
to get to a random element. I set a head and tail to keep track of where I am
enqueueing and dequeueing from. I expand the array by a factor of 2 when it
is full and I shrunk it by half when it's 1/4 full. My sample uses the shuffle
method from StdRandom and I shuffled whenever I expanded, minimizing the number
of times I need to shuffle for a constant amortized time.

I implemented the deque with a double linked list because having a pointer to
both the next and previous node lets me access both ends of the list easily. I
kept track of the first and last node, which I created as an inner class. This
way I can easily start from either end and add/remove nodes.

/* *****************************************************************************
 *  How much memory (in bytes) do your data types use to store n items
 *  in the worst case? Use the 64-bit memory cost model from Section
 *  1.4 of the textbook and use tilde notation to simplify your answer.
 *  Briefly justify your answers and show your work.
 *
 *  Do not include the memory for the items themselves (as this
 *  memory is allocated by the client and depends on the item type)
 *  or for any iterators, but do include the memory for the references
 *  to the items (in the underlying array or linked list).
 **************************************************************************** */

Randomized Queue:   ~  8n bytes

The underlying array structure uses 24 bytes plus 8 bytes per Object reference,
making it use 8n + 24 bytes of memory. The three int instance variables uses
4 bytes each, adding an additional 12 bytes. There is the object overhead of 16
bytes, brining it up to 8n + 52. There is an additional padding of 4 bytes to
bring it to a multiple of 8, leaving the total usage to be 8n + 56.


Deque:              ~  48n bytes

The underlying Node uses 8 bytes for the reference to the private class and 16
bytes for the Node object overhead. It also references 2 Nodes and an Object,
each 8 bytes, bringing the total memory usage of a node to 48. Since we are
creating multiple nodes in a Deque, this value is 48n. Each Deque itself uses
16 bytes for the overhead, 16 bytes for the pointers to the beginning and
ending Node (8 bytes each for Object), 4 bytes for an int, and 4 bytes for
the padding to make it a multiple of 8.This totals to a memory usage of
48n + 40.

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

My permutation doesn't take just k items.

/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */

I looked at my old code that I wrote myself in high school for queues and
stacks. I also discussed with my classmate Darin Avila about different data
structures and went to office hours with Professor Wayne on Monday 23rd.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

I tried making RandomizedQueue with a linked list and had a huge problem trying
to sample because I had to go through the entire linked list to get to a random
index.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
