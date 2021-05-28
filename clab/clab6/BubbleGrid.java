/*
Make a copy grid, first remove every dart, and create a disjointed set that tracks the end status
Then add the darts back, while using the above disjointed set to track the increasing of the darts
connecting to top.

 */


public class BubbleGrid {
    private int[][] grid;
    private int[][] copyGrid;
    private int R;
    private int C;
    private int TOP;
    private UnionFind wquf;


    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        this.R = grid.length;
        this.C = grid[0].length;
        copyGrid = new int[R][C];
        wquf = new UnionFind(R * C + 1);
        TOP = R * C;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                copyGrid[i][j] = grid[i][j];
            }
        }
        for (int i = 0; i < C; i++) {
            if(copyGrid[0][i] == 1) {
                wquf.union(i, TOP);
            }
        }
    }

    private int rcTo1D(int r, int c) {
        return r * C + c;
    }

    //Get the results of popBubble using a copy of the Grid
    private void popBubblesResults(int[][] copyGrid, int[][] darts) {
        for (int i = 0; i < darts.length; i++) {
            copyGrid[darts[i][0]][darts[i][1]] = 0;
        }
    }

    private void orthoConnect(int r, int c) {
        int[] orthoR = {-1, 1, 0, 0};
        int[] orthoC = {0, 0, -1, 1};
        int[][] orthoGrid = new int[4][2];
        for (int i = 0; i < 4; i++) {
            orthoGrid[i][0] = r + orthoR[i];
            orthoGrid[i][1] = c + orthoC[i];
        }
        int center = rcTo1D(r, c);
        for (int g[] : orthoGrid) {
            if (g[0] >= 0 && g[0] < R && g[1] >= 0 && g[1] < C) {
                if (isBubble(copyGrid, g[0], g[1])) {
                    // if the surrounding cell is open, connect with center cell
                    if(!wquf.connected(center, rcTo1D(g[0], g[1]))) {
                        wquf.union(center, rcTo1D(g[0], g[1]));
                    }
                }
            }
        }
    }

    private boolean isBubble(int[][] testGrid, int r, int c) {
        if (testGrid[r][c] == 1) {
            return true;
        }
        return false;
    }

    // scan the whole grid and union the bubbles
    private void scanUnion(int[][] copyGrid) {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (isBubble(copyGrid, i, j)){
                    orthoConnect(i, j);
                }

            }
        }
    }


    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {

        int[] bubbleFall = new int[darts.length];
        int ConnectToTopEnd;
        popBubblesResults(copyGrid, darts);
        scanUnion(copyGrid);
        ConnectToTopEnd = wquf.sizeOf(TOP);

        for (int i = darts.length-1; i >= 0; i--) {
            if (isBubble(grid, darts[i][0], darts[i][1])) {
                orthoConnect(darts[i][0], darts[i][1]);
                int ConnectToTop = wquf.sizeOf(TOP);
                bubbleFall[i] = ConnectToTop - ConnectToTopEnd - 1;
                ConnectToTopEnd = ConnectToTop;
            }
        }

        return bubbleFall;
    }
}
