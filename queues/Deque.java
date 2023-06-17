/* *****************************************************************************
 *  Name:    Ricky Lin
 *  NetID:   rickyl
 *  Precept: P09
 *
 *  Description:  Creates a two-ended queue using a double linked list with
 *                pointers to the first and last node. Each node carries a
 *                value and a reference to the previous and next node. The
 *                add methods add a node to the beginning or end and connects
 *                it to the one before or after. The remove methods removes
 *                the reference to the node and returns its values. The iterator
 *                traverses through the list and returns the value at the node.
 *
 *  Written:       9/23/2019
 *  Last updated:  9/23/2019
 *
 *  % javac-algs4 Deque.java
 *  % java-algs4 Deque.java
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // pointers to keep track of the first and last nodes
    private Node<Item> first, last = null;

    // keep track of size of array
    private int num;

    private class Node<Item> {

        // what each node is carrying
        private final Item cargo;

        // each node points to the one before and after
        private Node<Item> prevNode, nextNode;

        // construct a new Node given an item and previous and
        // next node
        public Node(Item val, Node<Item> prev, Node<Item> next) {
            cargo = val;
            prevNode = prev;
            nextNode = next;
        }
    }

    // construct an empty deque
    public Deque() {
        num = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return num == 0;
    }

    // return the number of items on the deque
    public int size() {
        return num;
    }

    // prevents null arguments
    private void noNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    // add the item to the front
    public void addFirst(Item item) {

        noNull(item);

        Node<Item> front = new Node<Item>(item, null, null);

        if (isEmpty()) {             // if initially empty, both points
            first = front;           // to same new node
            last = front;
        } else {                     // otherwise:
            first.prevNode = front;  // former first points to new first
            front.nextNode = first;  // new first points to former first
            first = first.prevNode;  // and set new first node
        }

        num++;
    }

    // add the item to the back
    public void addLast(Item item) {

        noNull(item);

        Node<Item> back = new Node<Item>(item, null, null);

        if (isEmpty()) {           // see addFirst
            first = back;
            last = back;
        } else {
            last.nextNode = back;  // same procedure as addFirst
            back.prevNode = last;  // but reversed for back
            last = last.nextNode;
        }

        num++;
    }

    // throw exception if you try to remove when list empty
    private void bounds() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {

        bounds();                    // check for exception

        Item removed = first.cargo;  // save value of removed to return

        first = first.nextNode;      // remove reference to 1st

        if (num == 1) {              // if only one node left,
            first = null;            // both pointers point to null
            last = null;
        }

        num--;
        return removed;

    }

    // remove and return the item from the back
    public Item removeLast() {

        bounds();                    // same as removeFirst but back

        Item removed = last.cargo;

        last = last.prevNode;

        if (num == 1) {
            first = null;
            last = null;
        }

        num--;
        return removed;
    }

    private class DequeIterator implements Iterator<Item> {

        // reference to beginning of list
        private Node<Item> curr = first;

        // check if end
        public boolean hasNext() {
            return curr != null;
        }

        // not supported
        public void remove() {
            throw new UnsupportedOperationException();
        }

        // return the next item
        public Item next() {

            if (!hasNext()) {                       // if empty:
                throw new NoSuchElementException(); // throw exception
            }

            Item item = curr.cargo;
            curr = curr.nextNode;
            return item;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();

        StdOut.println("----- Testing Empty & Count -----");
        StdOut.println(deque.isEmpty()); // expect true
        StdOut.println(deque.size());    // expect 0

        deque.addFirst("Hello");         // expect reversed order in iter

        StdOut.println(deque.removeFirst());
        deque.addFirst("Hello");

        deque.addFirst("My");
        deque.addFirst("Name");
        deque.addFirst("Is");
        deque.addFirst("Bob");
        deque.addFirst("Junior");

        deque.addLast("Hello");          // expect normal order in iter
        deque.addLast("My");
        deque.addLast("Name");
        deque.addLast("Is");
        deque.addLast("Bob");
        deque.addLast("Junior");

        StdOut.println("----- After adding -----");
        StdOut.println(deque.isEmpty()); // expect false
        StdOut.println(deque.size());    // expect 12

        Iterator<String> i = deque.iterator();
        // i.remove();                   // expect exception

        while (i.hasNext()) {            // iterate and print
            String s = i.next();
            StdOut.println(s);
        }

        StdOut.println("----- Removing now -----");

        // order: both reversed
        StdOut.println(deque.removeFirst());

        deque.addFirst("Hello");
        StdOut.println(deque.removeFirst());

        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());

        // StdOut.println(deque.removeFirst());  expect exceptions
        // StdOut.println(deque.removeLast());


        while (i.hasNext()) {                 // iterate & print
            String s = i.next();              // every element
            StdOut.println(s);
        }

        StdOut.println(deque.isEmpty());      // true and 0
        StdOut.println(deque.size());
        StdOut.println(deque.first);          // both points to null
        StdOut.println(deque.last);

    }
}
