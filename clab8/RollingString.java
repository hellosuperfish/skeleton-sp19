import java.util.ArrayDeque;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString{

    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128; // ASCII

    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;


    private ArrayDeque<Character> ad;
    private int len;

    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    public RollingString(String s, int length) {
        assert(s.length() == length);
        /* FIX ME */
        this.len = length;
        ad = new ArrayDeque<>(length);
        for (int i = 0; i < length; i++) {
            ad.add(s.charAt(i));
        }

    }

    /**
     * Adds a character to the back of the stored "string" and 
     * removes the first character of the "string". 
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        /* FIX ME */
        ad.addLast(c);
        ad.remove();

    }


    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        /* FIX ME */
        for(char c : ad) {
            strb.append(c);
        }
        return strb.toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        /* FIX ME */
        return len;
    }


    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        /* FIX ME */
        if (this == o) {
            return true;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        if (this.length() != ((RollingString)o).length()) {
            return false;
        }
        if (this.hashCode() != ((RollingString)o).hashCode()) {
            return false;
        }
        ArrayDeque<Character> adThis = ad.clone();
        ArrayDeque<Character> adThat = ((RollingString)o).ad.clone();
        while (adThis.size() != 0) {
            if (adThis.poll() != adThat.poll()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        /* FIX ME */
        int code = 0;
        for (char c : ad) {
            code = code * UNIQUECHARS + c;
        }
        return Math.floorMod(code, PRIMEBASE);
    }
}
