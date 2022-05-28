// Muye Li 5/26/22 "Percolation.java" Backwash Problem is solved
// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private int top, bottom, count;
	private int sideLen = 0;	
	private WeightedQuickUnionUF uf, ufForFull; // ufForFull is for solving backwash problem
	private boolean[] array;
		
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	if (n <= 0) throw new IllegalArgumentException("n should be greater than 0!");
    	this.sideLen = n; // side length of the grid
    	this.uf = new WeightedQuickUnionUF(n*n+1+1); // n*n grid with 1 top and 1 bottom
    	this.ufForFull = new WeightedQuickUnionUF(n*n+1+1);
    	this.top = n*n;	// top is n*n 
    	this.bottom = n*n+1; // bottom is n*n+1
    	this.array = new boolean[n*n]; 	// instantiate an array of bool of size n^2
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	if (row <= 0 || row > sideLen) throw new IllegalArgumentException("row index i out of bounds");
    	if (col <= 0 || col > sideLen) throw new IllegalArgumentException("col index i out of bounds"); 
    	int index = xyTo1D(row, col); // convert target coordinate to 1D
    	if (array[index]) return; // if the site is open, just return
    	array[index] = true; // change the bool array
    	this.count++; // num of open site++
    	if (row == 1) {
    		uf.union(index, top); // if the site is in the top row
    		ufForFull.union(index, top); 
    	}
    	if (row == sideLen) uf.union(index, bottom); // if the site is in the bottom row
    	
    	// check if the 4-direction neighbor is valid and open
    	if (isValid(row-1, col) && isOpen(row-1, col)) {
    		uf.union(index, xyTo1D(row-1, col));
    		ufForFull.union(index, xyTo1D(row-1, col));
    	}
    	if (isValid(row+1, col) && isOpen(row+1, col)) {
    		uf.union(index, xyTo1D(row+1, col));
    		ufForFull.union(index, xyTo1D(row+1, col));
    	}
    	if (isValid(row, col-1) && isOpen(row, col-1)) {
    		uf.union(index, xyTo1D(row, col-1));
    		ufForFull.union(index, xyTo1D(row, col-1));
    	}
    	if (isValid(row, col+1) && isOpen(row, col+1)) {
    		uf.union(index, xyTo1D(row, col+1));	
    		ufForFull.union(index, xyTo1D(row, col+1));
    	}
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
       	if (row <= 0 || row > sideLen) throw new IllegalArgumentException("row index i out of bounds");
    	if (col <= 0 || col > sideLen) throw new IllegalArgumentException("col index i out of bounds"); 
    	int index = xyTo1D(row, col);
    	return array[index];
    }    
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
       	if (row <= 0 || row > sideLen) throw new IllegalArgumentException("row index i out of bounds");
    	if (col <= 0 || col > sideLen) throw new IllegalArgumentException("col index i out of bounds"); 
    	int index = xyTo1D(row, col);
    	return (ufForFull.find(index) == ufForFull.find(top));  // to check if the index in UF can find the top
    }
    
    // returns the number of open sites
    public int numberOfOpenSites() {
    	return count;
    }
    
    // does the system percolate?
    public boolean percolates() {
    	return (uf.find(bottom) == uf.find(top)); // to check if find bottom equals to find top
    }
    
    // private method that converts 2D array index to 1D index in UF
	private int xyTo1D(int x, int y) {
		return (x-1)*this.sideLen+y-1;	
	}
	
	// private method to check if the coordinate is valid
	private boolean isValid(int row, int col) {
		return (row > 0 && col > 0 && row <= sideLen && col <= sideLen);
	}
	
    // test client (optional)
    public static void main(String[] args) {
    }
}