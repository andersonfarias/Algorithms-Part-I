import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int n;

    private int trials;

    private double stddev;

    private double mean;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        this.n = n;
        this.trials = trials;

        init();
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }

    private void init() {
        Percolation p;
        int row, column;

        double[] thresholds = new double[trials];
        double[] stddevs = new double[trials];

        for (int i = 0; i < this.trials; i++) {
            p = new Percolation(this.n);

            while (!p.percolates()) {
                do {
                    row = StdRandom.uniform(1, n + 1);
                    column = StdRandom.uniform(1, n + 1);
                } while (p.isOpen(row, column));

                p.open(row, column);
            }

            thresholds[i] = Integer.valueOf(p.numberOfOpenSites()).doubleValue() / (n * n);
        }

        this.mean = StdStats.mean(thresholds);

        for (int i = 0; i < this.trials; i++) {
            stddevs[i] = thresholds[i] - mean();
        }

        this.stddev = StdStats.stddev(stddevs);
    }

    public double mean() {
        return this.mean;
    }

    public double stddev() {
        return this.stddev;
    }

    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(this.trials));
    }

    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(this.trials));
    }

}