import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class MyTrieSet implements TrieSet61B{

    private Node root;

    private class Node {
        private boolean isKey = false;
        private HashMap<Character, Node> map;

        public Node(boolean isKey) {
            this.isKey = isKey;
            this.map = new HashMap<>(4);
        }

        private boolean containsKey(char c) {
            for (char i : map.keySet()) {
                if (i == c) {
                    return true;
                }
            }
            return false;
        }

    }

    public MyTrieSet() {
        root = new Node(false);
    }

    /**
     * Clears all items out of Trie
     */
    @Override
    public void clear() {
        root = new Node(false);
    }

    /**
     * Returns true if the Trie contains KEY, false otherwise
     *
     * @param key
     */
    @Override
    public boolean contains(String key) {
        if (key == null) {
            return false;
        }
        Node curr = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!curr.containsKey(c)) {
                return false;
            }
            curr = curr.map.get(c);
        }
        if (curr.isKey) {
            return true;
        }
        return false;
    }

    /**
     * Inserts string KEY into Trie
     *
     * @param key
     */
    @Override
    public void add(String key) {
        if (key == null) {
            return;
        }
        Node curr = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!curr.containsKey(c)) {
                curr.map.put(c, new Node(false));
            }
                curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    /**
     * Returns a list of all words that start with PREFIX
     *
     * @param prefix
     */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> keys = new ArrayList<>();
        Node curr = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (!curr.containsKey(c)) {
                return null;
            }
            curr = curr.map.get(c);
        }
        colHelp(prefix, keys, curr);
        return keys;
    }

    public List<String> collect() {
        List<String> strings = new ArrayList<>();
        Node curr = root;
        for (char c: root.map.keySet()) {
            colHelp(String.valueOf(c), strings, root.map.get(c));
        }
        return strings;
    }

    private void colHelp(String s, List<String> x, Node n) {
        if (n.isKey) {
            x.add(s);
        }
        if (n.map.size() > 0) {
            for (char c : n.map.keySet()) {
                colHelp(s + c, x, n.map.get(c));
            }
        }
    }

    /**
     * Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
