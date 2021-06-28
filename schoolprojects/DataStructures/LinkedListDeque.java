
/**
 * @author Robbie Netzke
 * A two-sided deque implementation
 * @param <T> A Generic
 */

public class LinkedListDeque<T> {
    /**
     * A nested class Node, to store items and directional pointers.
     */

    private class Node {
        /**
         * Instance variables for a Node.
         * item: user provided item.
         */
        private T item;
        /**
         * next: a reference to the next Node in the list.
         */
        private Node next;
        /**
         * prev: a reference to the previous Node in the list.
         */
        private Node prev;

        /**
         * Make an empty Node.
         */
        private Node() {
            this.item = null;
            this.next = null;
            this.prev = null;
        }

        /**
         * Make a populated Node.
         * @param i item from the user
         * @param n next Node in the list
         * @param p previous node in the list
         */
        private Node(T i, Node n, Node p) {
            this.item = i;
            this.next = n;
            this.prev = p;
        }
    }

    /**
     * size: the size of the deque.
     */
    private int size;
    /**
     * sentinel: an empty node to represent the start and end of the list.
     */
    private Node sentinel;

    /**
     * Creates an empty linked list deque.
     */
    public LinkedListDeque() {
        size = 0;
        sentinel = new Node();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /**
     * Add an item to the front of the deque.
     * @param item a user provided item.
     */
    public void addFirst(T item) {
        if (size == 0) {
            sentinel.next = new Node(item, sentinel, sentinel);
            sentinel.prev = sentinel.next;
            size++;
            return;
        }
        Node newNode = new Node(item, sentinel.next, sentinel);
        newNode.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    /**
     * Add an item to the back of the deque.
     * @param item a user provided item.
     */
    public void addLast(T item) {
        if (size == 0) {
            sentinel.prev = new Node(item, sentinel, sentinel);
            sentinel.next = sentinel.prev;
            size++;
            return;
        }
        Node newNode = new Node(item, sentinel, sentinel.prev);
        newNode.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    /**
     * Return if the deque is empty.
     * @return a boolean representing if a deque is empty or not.
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Return the size of the deque.
     * @return integer number of the deque size.
     */
    public int size() {
        return size;
    }

    /**
     * Print the deque.
     */
    public void printDeque() {
        Node current = this.sentinel.next;
        if (current.next == this.sentinel) {
            System.out.println(current.item);
            return;
        }
        while (current.next != this.sentinel) {
            System.out.print(current.item + " ");
            current = current.next;
        }
        System.out.print(current.item);
        System.out.println();

    }

    /**
     * Remove the first item from the deque.
     * @return previous first item.
     */
    public T removeFirst() {

        if (size == 0) {
            return null;
        }
        T t = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return t;
    }

    /**
     * Remove the last item from the deque.
     * @return the previous last item.
     */
    public T removeLast() {

        if (size == 0) {
            return null;
        }
        T t = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return t;
    }

    /**
     * Get the item from a specified index.
     * If no such item exists at that index, return null.
     * @param index of requested item.
     * @return the item at index.
     */
    public T get(int index) {

        Node current = this.sentinel;
        if (size == 0) {
            return null;
        }
        if (index >= size) {
            return null;
        } else {
            for (int i = 0; i <= index; i++) {
                current = current.next;
            }
            return current.item;
        }

    }

    /**
     * Get the item from a specified index recursively.
     * If no such item exists at that index, return null.
     * @param index of requested item.
     * @return the item at index.
     */
    public T getRecursive(int index) {

        if (size == 0) {
            return null;
        } else if (index >= size) {
            return null;
        }
        return recursiveHelper(sentinel.next, index);

    }

    /**
     * Helper function for get.
     * @param current the current Node.
     * @param index the number of indexes until arriving at the specified item.
     * @return the requested item.
     */
    private T recursiveHelper(Node current, int index) {
        if (index == 0) {
            return current.item;
        } else {
            return recursiveHelper(current.next, index - 1);
        }

    }


}
