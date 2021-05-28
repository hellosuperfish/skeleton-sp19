import java.util.Arrays;

public class UnionFind {

    // TODO - Add instance variables?
    public int[] ds;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        // TODO

        ds = new int[n];
        Arrays.fill(ds, -1);

    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        // TODO
        if (vertex >= ds.length || vertex < 0) {
            throw new RuntimeException(vertex + " is not a valid index.");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        // TODO
        int root = find(v1);
        return (0 - ds[root]);

    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        // TODO
        validate(v1);
        return ds[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO
        if (find(v1) > -1 && (find(v1) == find(v2))) {
            return true;
        }
        return false;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a
       vertex with itself or vertices that are already connected should not
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        // TODO
        int size1 = sizeOf(v1);
        int size2 = sizeOf(v2);
        int rootV1 = find(v1);
        int rootV2 = find(v2);
        if (connected(v1, v2)) {
            ;
        }
        else if (size1 <= size2) {
            ds[rootV1] = rootV2;
        } else {
            ds[rootV2] = rootV1;
        }
        int root = find(v2);
        ds[root] =  - (size1 + size2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {

        int pointer = vertex;
        while (parent(pointer) >= 0) {
            pointer = parent(pointer);
        }
        int root = pointer;
        pointer = vertex;
        while (parent(pointer) >= 0) {
            int parentPointer = parent(pointer);
            ds[pointer] = root;
            pointer = parentPointer;
        }
        return root;
    }


    /* find root using recursion
    public int find(int vertex) {
        // TODO
        if (parent(vertex) <= -1) {
            return vertex;
        } else {
            return find(parent(vertex));
        }
    }
    */
}
