import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    /**
     * Removes all of the mappings from this map.
     */

    int size = 0;
    private BST root;

    private class BST{
        K key;
        V value;
        BST left;
        BST right;


        public BST(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }



        public BST BSTFind(BST parent, K k) {
            if (parent != null && k.equals(parent.key)) {
                return parent;
            } else if (parent == null) {
                return null;
            } else if (k.compareTo(parent.key) < 0) {
                return BSTFind(parent.left, k);
            } else {
                return BSTFind(parent.right, k);
            }
        }

        public BST BSTInsert(BST parent, K k, V v) {
            if (parent == null) {
                return new BST(k, v);
            } else if (k.compareTo(parent.key) < 0) {
                parent.left = BSTInsert(parent.left, k, v);
            } else {
                parent.right = BSTInsert(parent.right, k, v);
            }
            return parent;
        }

    }


    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (root == null) {
            return false;
        }
        return root.BSTFind(root, key) != null;
    }

    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        } 
        BST lookup = root.BSTFind(root, key);
        return lookup.value;
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new BST(key, value);
            size += 1;
        } else {
            BST lookup = root.BSTFind(root, key);
            if (lookup == null) {
                root.BSTInsert(root, key, value);
                size += 1;
            } else {
                lookup.value = value;
            }
        }

    }

    public void printInOrder() {

    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("BSTMap does not support keySet() function");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("BSTMap does not support remove() function");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("BSTMap does not support remove() function");
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("BSTMap does not support iterator() function");
    }
}
