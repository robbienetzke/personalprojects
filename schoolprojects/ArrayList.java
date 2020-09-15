
/**
 * @author Robbie Netzke
 * A two-sided deque implementation
 * @param <T> A Generic
 */
public class ArrayDeque<T> {
    /**
     * items: container for the items in the deque.
     */
    private T[] items;
    /**
     * size: the size of the deque.
     */
    private int size;
    /**
     * sInd: the next index representing the start of the deque.
     */
    private int sInd;
    /**
     * eInd: the next index representing the end of the deque.
     */
    private int eInd;

    /**
     * Create an empty deque.
     */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        sInd = 0;
        eInd = 1;
        size = 0;
    }

    /**
     * Add an item to the front of the deque.
     * @param item a user provided item.
     */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[sInd] = item;
        sInd = ((sInd + items.length - 1) % items.length);
        size++;

    }

    /**
     * Add an item to the back of the deque.
     * @param item a user provided item.
     */
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[eInd] = item;
        eInd = (eInd + 1) % items.length;
        size++;
    }

    /**
     * Resize the deque by a resizing factor of 2.
     * @param s the new size of items.
     */
    private void resize(int s) {
        T[] newItems = (T[]) new Object[s];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[(i + 1 + sInd) % items.length];
        }
        sInd = s - 1;
        eInd = size;
        items = newItems;

    }

    /**
     * Return if the deque is empty or not.
     * @return a boolean representing if the deque is empty or not.
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Return the size of the deque.
     * @return the integer size of the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Print the deque.
     */
    public void printDeque() {
        if (size == 0) {
            System.out.println("" + null);
            return;
        }
        for (int i = 1; i <= size; i++) {
            System.out.print(items[(i + sInd) % items.length] + " ");
        }
        System.out.println("");
    }

    /**
     * Remove the first item of the deque and return it.
     * @return the previous first item of the deque.
     */
    public T removeFirst() {
        if ((double) size / items.length < 0.25 && items.length > 8) {
            resize(items.length / 2);
        }
        T i = items[(sInd + 1) % items.length];
        items[(sInd + 1) % items.length] = null;
        sInd = (sInd + 1) % items.length;
        if (isEmpty()) {
            return null;
        }
        size--;
        return i;
    }

    /**
     * Remove and return the last item of the deque.
     * @return the previous last item of the deque.
     */
    public T removeLast() {
        if ((double) size / items.length < 0.25 && items.length > 8) {
            resize(items.length / 2);
        }
        T i = items[(eInd - 1 + items.length) % items.length];
        items[(eInd - 1 + items.length) % items.length] = null;
        eInd = (eInd - 1 + items.length) % items.length;
        if (isEmpty()) {
            return null;
        }
        size--;
        return i;

    }

    /**
     * Get the item at the requested index.
     * If no such item exists, return null.
     * @param index the index of the requested item.
     * @return the item at index.
     */
    public T get(int index) {
        return items[(sInd + 1 + index) % items.length];
    }

}
