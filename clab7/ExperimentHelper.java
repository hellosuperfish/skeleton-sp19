import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N cannot be 0 or negative");
        }
        if (N == 1) {
            return 0;
        } else {
            int num = (int)optimalAverageDepth(N);
            return optimalIPL(N-1) + num;
        }
    }


    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N cannot be 0 or negative");
        }
        double a = Math.log(N);
        double b = Math.log(2);
        double c = a / b;

        return c;
    }

    public static void randomInsert(BST tree, int max, int addSize) {
        if (max < addSize) {
            throw new IllegalArgumentException("max needs to be larger than treeSize to ensure unique insertion");
        }
        for (int i = 1; i <= addSize; i++) {
            int rand = StdRandom.uniform(max);
            while(tree.contains(rand)) { // make sure each random number is unique
                rand = StdRandom.uniform(max);
            }
            tree.add(rand);
        }
    }

    public static void DeleteTakingSuccessor(BST t) {
        t.deleteTakingSuccessor(t.getRandomKey());
    }

    public static void DeleteTakingRandom(BST t) {
        t.deleteTakingRandom(t.getRandomKey());
    }


    public static void main(String[] args){
        for (int i = 1; i < 11; i++) {
            System.out.println("N: " + i + ", OIPL: " + optimalIPL(i));

        }
    }
}
