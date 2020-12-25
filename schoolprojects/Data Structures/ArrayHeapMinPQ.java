package bearmaps;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.ArrayList;

/**
 * A Minimum Priority-Queue using an ArrayList and TreeMap.
 * @author Robbie Netzke.
 * @source https://algs4.cs.princeton.edu/24pq/MinPQ.java.html
 * A MinPQ implementation
 * by Robert Sedgewick and Kevin Wayne
 * @param <T> A generic type.
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /** A list of nodes that contain an item and its priority. */
    private ArrayList<Node> nodes;
    /**
     * A list of items and their index in the queue.
     * Allows for Log(N) access time.
     */
    private TreeMap<T, Integer> fastItems;
    /** A Node class to store an item and its priority. */
    private class Node {
        /** A generic type. */
        private T item;
        /** Priority of the item in the queue. */
        private double priority;
        /**
         * Node constructor.
         * @param i an item.
         * @param p a priority.
         */
        Node(T i, double p) {
            item = i;
            priority = p;
        }
    }
    /** Priority Queue constructor. */
    public ArrayHeapMinPQ() {
        nodes = new ArrayList<>();
        nodes.add(null);
        fastItems = new TreeMap<>();
    }

    /**
     * Adds an item to the priority queue.
     * @param item an item.
     * @param priority the item's priority.
     */
    @Override
    public void add(T item, double priority) {
        if (size() > 0 && contains(item)) {
            throw new IllegalArgumentException("Elem. already in Queue.");
        }
        nodes.add(new Node(item, priority));
        fastItems.put(item, size());
        swim(size());
    }

    /**
     * Helper method: items with lower priorities than their parents swim up.
     * @param index current index of the node.
     */
    private void swim(int index) {
        while (index > 1 && checkPriority(index / 2, index)) {
            exchange(index, index / 2);
            index = index / 2;
        }
    }

    /**
     * Checks if the priority of the parent node is greater than its child.
     * @param p index of parent Node.
     * @param c index of child Node.
     * @return True if p's priority > c's priority. False otherwise.
     */
    private boolean checkPriority(int p, int c) {
        return nodes.get(p).priority > nodes.get(c).priority;
    }

    /**
     * Switch the positioning of the two Nodes.
     * @param p parent Node.
     * @param c child Node.
     */
    private void exchange(int p, int c) {
        Node parent = nodes.get(p);
        nodes.set(p, nodes.get(c));
        nodes.set(c, parent);
        fastItems.put(nodes.get(p).item, p);
        fastItems.put(nodes.get(c).item, c);
    }

    @Override
    public boolean contains(T item) {
        return fastItems.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("Priority Queue Underflow.");
        }
        return nodes.get(1).item;
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("Priority Queue Underflow.");
        }
        T returnItem = getSmallest();
        exchange(1, size());
        nodes.remove(size());
        fastItems.remove(returnItem);
        sink(1);
        return returnItem;
    }

    /**
     * If an item has a bigger priority than one of the child's priorities,
     * sink.
     * @param index index of the current Node.
     */
    private void sink(int index) {
        while (2 * index <= size()) {
            int target = 2 * index;
            if (target < size() && checkPriority(target, target + 1)) {
                target++;
            }
            if (!(checkPriority(index, target))) {
                break;
            }
            exchange(index, target);
            index = target;
        }
    }

    @Override
    public int size() {
        return nodes.size() - 1;
    }

    /**
     * True if the parent priority is larger than the child's,
     * and if the parent does not have position zero.
     * @param p parent Node index.
     * @param c child Node index.
     * @return
     */
    private boolean sinkOrSwim(int p, int c) {
        if (p == 0) {
            return false;
        }
        return nodes.get(p).priority > nodes.get(c).priority;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (contains(item)) {
            int indexOf = fastItems.get(item);
            nodes.get(indexOf).priority = priority;
            if (size() > 0 && sinkOrSwim(indexOf / 2, indexOf)) {
                swim(indexOf);
            } else {
                sink(indexOf);
            }
        } else {
            throw new NoSuchElementException("Elem. not in Queue.");
        }
    }
}

