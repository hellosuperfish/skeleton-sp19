package hw2;

public class PercolationStatsTest {

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(20, 30, pf);

        double mean = ps.mean();
        double stddev = ps.stddev();
        double threshold = mean / (20*20);

        System.out.println(mean + ", " +stddev + ", " + threshold);
    }
}
