import java.util.*;

public class MyHashMap<K, V> implements Map61B<K,V> {

    private double lFactor;
    private int iSize;
    private int size;
    private int M;
    private HashSet<K> hs;
    private List<K>[] keys;
    private List<V>[] values;

    public MyHashMap() {
        this(16, 0.75);
    }
    public MyHashMap(int initialSize){
        this(initialSize, 0.75);
    }
    public MyHashMap(int initialSize, double loadFactor){
        if(initialSize < 1 || loadFactor <= 0.0) {
            throw new IllegalArgumentException();
        }
        this.lFactor = loadFactor;
        this.iSize = initialSize;
        this.size = 0;
        this.M = initialSize;

        this.keys = (List<K>[]) new ArrayList[iSize];
        this.values = (List<V>[]) new ArrayList[iSize];
        this.hs = new HashSet<>();

        for(int i = 0; i < initialSize; i++) {
            keys[i] = new ArrayList<>();
            values[i] = new ArrayList<>();
        }
    }

    private int computeIndex(K key, int m) {
        int hash = key.hashCode();
        return Math.floorMod(hash, m);
    }

    @Override
    public void clear() {
        this.size = 0;
        for(int i = 0; i < M; i++) {
            keys[i].clear();
            values[i].clear();
        }
        this.hs.clear();
    }

    @Override
    public boolean containsKey(K key) {
        int ind = computeIndex(key, M);
        if(keys[ind].contains(key)) {
            return true;
        }
        return false;
    }

    @Override
    public V get(K key) {
        int keyIndex = computeIndex(key, M);
        int index = keys[keyIndex].indexOf(key);
        if (index == -1) {
            return null;
        }
        return values[keyIndex].get(index);

    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        int keyIndex = computeIndex(key, M);
        int index = keys[keyIndex].indexOf(key);
        if (index > -1) {
            values[keyIndex].set(index, value);
            return;
        }
        if ((double) size / M >= lFactor) {
            resize(M*2);
        }
        int kI = computeIndex(key, M);
        hs.add(key);
        keys[kI].add(key);
        values[kI].add(value);
        size++;

    }

    private void resize(int newSize) {
        List<K>[] newKeys = (List<K>[]) new ArrayList[newSize];
        List<V>[] newVals = (List<V>[]) new ArrayList[newSize];
        for(int i = 0; i < newSize; i++) {
            newKeys[i] = new ArrayList<>();
            newVals[i] = new ArrayList<>();
        }
        for (int i = 0; i < keys.length; i++) {
            for(K key : keys[i]) {
                int keyIndex = computeIndex(key, newSize);
                newKeys[keyIndex].add(key);
                newVals[keyIndex].add(get(key));
            }
        }
        this.M = newSize;
        this.keys = newKeys;
        this.values = newVals;
    }

    @Override
    public Set<K> keySet() {
        return hs;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return hs.iterator();

    }
}
