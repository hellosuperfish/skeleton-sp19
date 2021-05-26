package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationNoBackWash1 {

    private int[][] grid;
    private int openSize = 0;
    WeightedQuickUnionUF wquf;

    // create N-by-N grid, with all sites initially blocked
    public PercolationNoBackWash1(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("Grid size has to be a positive number.");
        }
        this.grid = new int[N][N];
        this.wquf = new WeightedQuickUnionUF(N*N);

        for (int i = 0; i < N; i++) {
            grid[0][i] = 10;
            grid[N-1][i] = 1;
        }
    }

    // helper method to transfer coordinates to a number
    private int xyTo1D(int row, int col) {
        return row * (grid.length) + col;
    }

    //check if a cell is connected to TOP through the stored value
    private boolean connectToTop(int row, int col) {
        int value = (Math.floorDiv(grid[row][col], 10)) % 10;
        return value == 1;
    }

    //check if a cell is connected to BOTTOM through the stored value
    private boolean connectToBottom(int row, int col) {
        int value = grid[row][col] % 10;
        return value == 1;
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
                    if (connectToTop(g[0], g[1]) && (!connectToTop(row, col))){
                        grid[row][col] += 10;
                    }
                    if (connectToBottom(g[0], g[1]) && (!connectToBottom(row, col))){
                        grid[row][col] += 1;
                    }

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
            grid[row][col] += 100;
            orthoConnect(row, col);
            openSize += 1;
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row >= grid.length || col >= grid.length) {
            throw new java.lang.IndexOutOfBoundsException("coordinate out of range");
        }
        return ((Math.floorDiv(grid[row][col], 100)) % 10 == 1);
    }



    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row >= grid.length || col >= grid.length) {
            throw new java.lang.IndexOutOfBoundsException("coordinate out of range");
        }
        /*
        if (isOpen(row,col) && connectToTop(row, col)) { // check if this site is connected to TOP
            return true;
        }
        return false;*/
        return (isOpen(row, col) && (Math.floorDiv(grid[row][col], 10)) % 10 == 1);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSize;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < grid.length; i++){
            if (grid[grid.length-1][i] % 100 == 11) {
                return true;
            }
        }

        return false;
    }
    //public static void main(String[] args)   // use for unit testing (not required, but keep this here for the autograder)
}
