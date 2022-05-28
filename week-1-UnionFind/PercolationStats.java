
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
// import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
	private double[] results;
	
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	if (n <= 0 || trials <= 0) throw new IllegalArgumentException("n or trial is smaller or equal to 0!");
    	results = new double[trials];
    	for (int i = 0; i < trials; i++) {
    		results[i] = calculate(n);
    	}
    }
    
    // sample mean of percolation threshold
    public double mean() {
    	return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	return mean()-1.96*stddev()/Math.sqrt(results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return mean()+1.96*stddev()/Math.sqrt(results.length);
    }
    
    // private helper method to calculate a single percentage
    private double calculate(int n) {
    	Percolation p = new Percolation(n);
    	int numOfOpen = 0;
    	while (!p.percolates()) {
    		int row = StdRandom.uniform(1, n+1);
    		int col = StdRandom.uniform(1, n+1);
    		if (!p.isOpen(row, col)) {
    			p.open(row, col);
    			numOfOpen++;
    		}
    	}
    	return (double) numOfOpen / (n*n);
    }
    
   // test client (see below)
   public static void main(String[] args) {
	   int n = Integer.parseInt(args[0]);
	   int trials = Integer.parseInt(args[1]);
	   PercolationStats p = new PercolationStats(n, trials);
	   System.out.println("mean                    = "+p.mean());
	   System.out.println("stddev                  = "+p.stddev());
	   System.out.println("95% confidence interval = ["+p.confidenceLo()+", "+p.confidenceHi()+"]");
   }
}