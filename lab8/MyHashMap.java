import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V>{

    /**
     * Removes all of the mappings from this map.
     */

    private int initialSize = 16;
    private double loadFactor = 0.75;
    private int mapSize = 0;
    private Entry<K, V>[] bins;



    private class Entry<K, V> {

        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public Entry<K, V> find(K key, Entry<K, V> en) {
            if(en == null) {
                return null;
            } else if (en.key.equals(key)) {
                return en;
            } else if (en.next == null) {
              return null;
            } else {
                return find(key, en.next);
            }
        }

    }

    public MyHashMap() {
        bins = (Entry<K, V>[]) new Entry[this.initialSize];
    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        bins = (Entry<K, V>[]) new Entry[this.initialSize];
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        bins = (Entry<K, V>[]) new Entry[this.initialSize];
    }


    @Override
    public void clear() {
        initialSize = 16;
        MyHashMap<K, V> newMap = new MyHashMap<>();
        this.bins = newMap.bins;
        this.mapSize = 0;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key
     */

    public boolean containsKey(K key) {
        int index = hashToIndex(key);

        if (bins[index]!= null && bins[index].find(key, bins[index]) != null) {
            return true;
        } else {
            return false;
        }
    }



    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    public V get(K key) {
        int index = hashToIndex(key);
        if (bins[index] == null) {
            return null;
        }
        Entry<K, V> found = bins[index].find(key, bins[index]);
        if (found != null) {
            return found.value;
        } else {
            return null;
        }
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {

        return this.mapSize;
    }

    private int hashToIndex(K key) {
        int code = key.hashCode();
        int index = Math.floorMod(code, initialSize); // 0x7fffffff turns negative value to positive
        return index;
    }

    private void resize() {

        MyHashMap<K,V> newMap = new MyHashMap<>(2 * initialSize); // create a new MyHashMap with the new size

        Set<K> keys = keySet();
        int count = keys.size();
        for (K key : keys) { // Go through each entry and insert into new bin
            newMap.put(key, get(key));
        }

        this.bins = newMap.bins;
        this.initialSize = newMap.initialSize;
        this.mapSize = newMap.mapSize;


    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     *
     * @param key
     * @param value
     */


    public void put(K key, V value) {
        if (! containsKey(key)) {
            double curLoadFactor = size()/(double) this.initialSize;
            if (curLoadFactor >= this.loadFactor) {
                this.resize();
            }

            int index = hashToIndex(key);
            if (bins[index] == null) {
                bins[index] = new Entry<K, V>(key, value);
            } else {
                Entry<K, V> current = bins[index];
                while (current.next != null) {
                    current = current.next;
                }
                current.next = new Entry<K, V>(key, value);
            }
            mapSize += 1;
        } else {
            if (!this.get(key).equals(value)) {
                int index = hashToIndex(key);
                Entry<K,V> update = bins[index].find(key, bins[index]);
                update.value = value;
            }
        }

    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set keySet() {
        Set<K> keys = new HashSet<>();
        for(int i=0; i < initialSize; i++) {
            if (bins[i] != null) {
                Entry en = bins[i];
                while (en != null) {
                    keys.add((K) en.key);
                    en = en.next;
                }
            }
        }
        return keys;
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator iterator() {
        Set keys = keySet();
        return keys.iterator();
    }



    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException("MyHashMap does not support remove");
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     *
     * @param key
     * @param value
     */
    @Override
    public Object remove(Object key, Object value) {
        throw new UnsupportedOperationException("MyHashMap does not support remove");
    }

}
