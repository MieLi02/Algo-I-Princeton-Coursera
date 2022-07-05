
import java.util.Arrays;
import edu.princeton.cs.algs4.Stack;

public class Board {
	private final int[][] tiles; // local tiles
	private final int n; // dimension
	private int zeroX, zeroY; // coordinate of zero
	
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tilesInput) {
    	this.n = tilesInput.length;
    	this.tiles = new int[n][n];
    	for (int i = 0; i < n; i++) { // figure out where the 0 is
    		for (int j = 0; j < n; j++) {
    			this.tiles[i][j] = tilesInput[i][j];
    			if (tilesInput[i][j] == 0) {
    				zeroX = j;
    				zeroY = i;
    			} 
    		}
    	}
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
    	return n;
    }

    // number of tiles out of place
    public int hamming() {
    	int hamming = 0;
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			if (tiles[i][j] != 0 && tiles[i][j] != i * n + j + 1) hamming++;
    		}
    	}
    	return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	int counter = 1, totalManh = 0;
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			if (tiles[i][j] == 0 || tiles[i][j] == counter) {
    				counter++;
    			}
    			else {
    				int rowDiff = Math.abs(row(tiles[i][j]) - row(counter));
    				int colDiff = Math.abs(col(tiles[i][j]) - col(counter));
    				int manh = rowDiff + colDiff;
    				totalManh += manh;
    				counter++;
    			}
    		}
    	}    	
    	return totalManh;
    }

    // is this board the goal board?
    public boolean isGoal() {
    	return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
    	if (y == this) return true;
    	if (y == null) return false;
    	if (y.getClass() != this.getClass()) return false;
    	Board that = (Board) y;
    	return Arrays.deepEquals(this.tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
    	Stack<Board> stack = new Stack<Board>();
    	if (zeroX != 0) {
    		int[][] neighbor = cloneNestedArray(tiles);
    		swapLeft(neighbor, zeroX, zeroY);
    		stack.push(new Board(neighbor));
    	}
    	if (zeroX != (n - 1)) {
    		int[][] neighbor = cloneNestedArray(tiles);
    		swapRight(neighbor, zeroX, zeroY);
    		stack.push(new Board(neighbor));
    	}
    	if (zeroY != 0) {
    		int[][] neighbor = cloneNestedArray(tiles);
    		swapUp(neighbor, zeroX, zeroY);
    		stack.push(new Board(neighbor));
    	}
    	if (zeroY != (n - 1)) {
    		int[][] neighbor = cloneNestedArray(tiles);
    		swapDown(neighbor, zeroX, zeroY);
    		stack.push(new Board(neighbor));
    	}
    	return stack;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    	int[][] twinBoard = cloneNestedArray(tiles);
        if (this.tiles[0][0] != 0)
            if (this.tiles[0][1] != 0) {
              twinBoard[0][0] = this.tiles[0][1];
              twinBoard[0][1] = this.tiles[0][0];
            }
            else {
              twinBoard[0][0] = this.tiles[1][0];
              twinBoard[1][0] = this.tiles[0][0];
            }
        else {
            twinBoard[0][1] = this.tiles[1][1];
            twinBoard[1][1] = this.tiles[0][1];
        }
        return new Board(twinBoard);
    }

    private void swapRight(int[][] tiles, int zeroX, int zeroY) {
    	tiles[zeroY][zeroX] = tiles[zeroY][zeroX + 1];
    	tiles[zeroY][zeroX + 1] = 0;
    }
    
    private void swapLeft(int[][] tiles, int zeroX, int zeroY) {
    	tiles[zeroY][zeroX] = tiles[zeroY][zeroX - 1];
    	tiles[zeroY][zeroX - 1] = 0;
    }
    
    private void swapUp(int[][] tiles, int zeroX, int zeroY) {
    	tiles[zeroY][zeroX] = tiles[zeroY - 1][zeroX];
    	tiles[zeroY - 1][zeroX] = 0;
    }
    
    private void swapDown(int[][] tiles, int zeroX, int zeroY) {
    	tiles[zeroY][zeroX] = tiles[zeroY + 1][zeroX];
    	tiles[zeroY + 1][zeroX] = 0;
    }
    
    private int[][] cloneNestedArray(int[][] array) { // need to use this implementation to clone nested array
    	int[][] clone = array.clone();
    	for (int i = 0; i < array.length; i++) {
    		clone[i] = array[i].clone();
    	}
    	return clone;
    }
    
    private int row(int num) {
    	return (int)Math.ceil((double)num / (double)n);
    }
    
    private int col(int num) {
    	if (num % n == 0) return n;
    	return num % n;
    }
    
    // unit testing (not graded)
    public static void main(String[] args) {
    }
}
