package hw2;
import edu.princeton.cs.introcs.*;

public class PercolationStats {

    int[] threshold;
    int percT;
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Grid size has to be a positive number.");
        }
        threshold = new int[T];
        percT = T;
        for (int i = 0; i < T; i++) {
            Percolation perc = pf.make(N);
            // get random row and column and open the site until it percolates
            while(!perc.percolates()){
                int r = StdRandom.uniform(N);
                int c = StdRandom.uniform(N);
                perc.open(r,c);
            }
            threshold[i] = perc.numberOfOpenSites();
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double u = mean();
        double sd = stddev();
        return u - (1.96 * sd / Math.sqrt(percT));
    }
    public double confidenceHigh() {
        double u = mean();
        double sd = stddev();
        return u + (1.96 * sd / Math.sqrt(percT));
    }
}
