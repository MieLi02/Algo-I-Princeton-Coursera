
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	private searchNode solution;
	private boolean isSolvable = true;
	private boolean isSolved = false;
	
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	if (initial == null) throw new IllegalArgumentException();
    	// the input board
    	MinPQ<searchNode> pq = new MinPQ<searchNode>();
    	searchNode first = new searchNode(initial, 0, null);
    	pq.insert(first);
    	// the input board's twin
    	Board twin = initial.twin();
    	MinPQ<searchNode> pqTwin = new MinPQ<searchNode>();
    	searchNode firstTwin = new searchNode(twin, 0, null);
    	pqTwin.insert(firstTwin);
    	// solving process

    	while (!isSolved && isSolvable) {
    		searchNode current = pq.delMin();
    		if (current.board.isGoal()) {
    			solution = current;
    			isSolved = true;
    		}
    		else {
    			for (Board b : current.board.neighbors()) {
    				if (current.moves == 0) pq.insert(new searchNode(b, current.moves + 1, current));
    				else if (!b.equals(current.prev.board)) pq.insert(new searchNode(b, current.moves + 1, current));
    			}
    		}
    		searchNode currentTwin = pqTwin.delMin();
    		if (currentTwin.board.isGoal()) {
    			isSolvable = false;
    		}
    		else {
    			for (Board bTwin : currentTwin.board.neighbors()) {
    				if (currentTwin.moves == 0) pqTwin.insert(new searchNode(bTwin, currentTwin.moves + 1, currentTwin));
    				else if (!bTwin.equals(currentTwin.prev.board)) pqTwin.insert(new searchNode(bTwin, currentTwin.moves + 1, currentTwin));
    			}
    		}
    	}
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
    	return this.isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
    	if (!isSolvable()) return -1;
    	return solution.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
    	if (!isSolvable()) return null;
    	Stack<Board> reverseSol = new Stack<Board>();
    	searchNode current = this.solution;
    	while (current.prev != null) {
    		reverseSol.push(current.board);
    		current = current.prev;
    	}
    	reverseSol.push(current.board);
    	return reverseSol;
    }
    
    private class searchNode implements Comparable<searchNode>{
    	Board board;
    	int moves, priority;
    	searchNode prev;
    	public searchNode(Board board, int moves, searchNode prev) {
    		this.board = board;
    		this.moves = moves;
    		this.prev = prev;
    		this.priority = board.manhattan() + moves;
    	}
    	
		@Override
		public int compareTo(Solver.searchNode o) {
			// TODO Auto-generated method stub
			if (this.priority > o.priority) return 1;
			if (this.priority < o.priority) return -1;
			return 0;
		} 
    }
    
    public static void main(String[] args) {
    	
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
