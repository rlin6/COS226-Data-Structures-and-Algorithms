/* *****************************************************************************
 *  Name:    Ricky Lin
 *  NetID:   rickyl
 *  Precept: P09
 *
 *  Description:  Finds statistical measures of Percolation
 *
 *  Written:       9/16/2019
 *  Last updated:  9/16/2019
 *
 *  % javac PercolationStats.java
 *  % java PercolationStats
 *
 **************************************************************************** */


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private double[] data;  // stores percentages in array
    private double time;    // save the time

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        // exceptions for n and trials out of bounds
        if (n <= 0) {
            throw new IllegalArgumentException(
                    "Dimensions of grid cannot be 0 or less"
            );
        }

        if (trials <= 0) {
            throw new IllegalArgumentException(
                    "Number of trials cannot be 0 or less"
            );
        }

        data = new double[trials];

        // starts recording time
        Stopwatch record = new Stopwatch();

        // iterate through trial number of times
        for (int i = 0; i < trials; i++) {
            Percolation test = new Percolation(n);

            // keep opening new sites until percolates
            while (!(test.percolates())) {
                test.open(StdRandom.uniform(n), StdRandom.uniform(n));
            }

            // calculate the percentage and put it in the data
            data[i] = (double) test.numberOfOpenSites() / (n * n);
        }

        // save the time elapsed
        time = record.elapsedTime();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(data);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(data);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(data.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(data.length));
    }

    // test client (see below)
    public static void main(String[] args) {

        PercolationStats simulation = new PercolationStats(
                Integer.parseInt(args[0]), Integer.parseInt(args[1])
        );

        StdOut.println("mean() = " + simulation.mean());
        StdOut.println("stddev() = " + simulation.stddev());
        StdOut.println("confidenceLow() = " + simulation.confidenceLow());
        StdOut.println("confidenceHigh() = " + simulation.confidenceHigh());
        StdOut.println("elapsed time = " + simulation.time);
    }

}
