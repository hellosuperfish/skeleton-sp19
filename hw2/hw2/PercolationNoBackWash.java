package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationNoBackWash {

    private int TOP;
    private int BOTTOM;
    private int[][] grid;
    private int openSize = 0;
    WeightedQuickUnionUF wquf;
    WeightedQuickUnionUF wqufTop;

    // create N-by-N grid, with all sites initially blocked
    public PercolationNoBackWash(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("Grid size has to be a positive number.");
        }
        this.grid = new int[N][N];
        this.wquf = new WeightedQuickUnionUF(N*N+2);
        this.wqufTop = new WeightedQuickUnionUF(N*N+1);
        TOP = N*N;
        BOTTOM = N*N+1;
        for (int i = 0; i < N; i++) {
            wquf.union(TOP, xyTo1D(0, i));
            wqufTop.union(TOP,xyTo1D(0, i));
            wquf.union(BOTTOM, xyTo1D((N-1), i));
        }
    }

    // helper method to transfer coordinates to a number
    private int xyTo1D(int row, int col) {

        return row * (grid.length) + col;
    }


    // helper method to connect a cell to its surrounding cell if any is open
    private void orthoConnect(int row, int col) {
        // orthoR and orthoC are to calculate cells that are above, below, left, and right to the center cell
        int[] orthoR = {-1, 1, 0, 0};
        int[] orthoC = {0, 0, -1, 1};
        int[][] orthoGrid = new int[4][2];
        for (int i = 0; i < 4; i++) {
            orthoGrid[i][0] = row + orthoR[i];
            orthoGrid[i][1] = col + orthoC[i];
        }
        int center = xyTo1D(row, col);
        for (int g[] : orthoGrid) {
            if (g[0] >= 0 && g[0] < grid.length && g[1] >= 0 && g[1] < grid.length) {
                if (isOpen(g[0], g[1])) {
                    // if the surrounding cell is open, connect with center cell
                    wquf.union(center, xyTo1D(g[0], g[1]));
                    wqufTop.union(center,xyTo1D(g[0], g[1]));

                }
            }
        }
    }

    // open the site (row, col) if it is not open already
    // 0 is a closed site; 100 is an open site; 110 is a open site connected to top;
    // 101 is for a site connected to the bottom
    public void open(int row, int col){
        if (row >= grid.length || col >= grid.length) {
            throw new java.lang.IndexOutOfBoundsException("coordinate out of range");
        }
        if(!isOpen(row, col)) {
            grid[row][col] = 1;
            orthoConnect(row, col);
            openSize += 1;
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row >= grid.length || col >= grid.length) {
            throw new java.lang.IndexOutOfBoundsException("coordinate out of range");
        }
        return grid[row][col] == 1;
    }



    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row >= grid.length || col >= grid.length) {
            throw new java.lang.IndexOutOfBoundsException("coordinate out of range");
        }
        int cell1D = xyTo1D(row, col);

        if (isOpen(row,col) && wqufTop.connected(cell1D, TOP)) { // check if this site is connected to TOP
            return true;
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSize;
    }

    // does the system percolate?
    public boolean percolates() {
        if (wquf.connected(TOP, BOTTOM)) {
            return true;
        }
        return false;
    }
    //public static void main(String[] args)   // use for unit testing (not required, but keep this here for the autograder)
}
