import edu.princeton.cs.algs4.StdIn;

public class Permutation {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomizedQueue<String> RQ = new RandomizedQueue<String>();
		int n = Integer.parseInt(args[0]);
		while (!StdIn.isEmpty()) {
			String str = StdIn.readString();
			RQ.enqueue(str);
		}
        for (int i = 0; i < n; i++) {
            System.out.println(RQ.dequeue());
        }
	}
}
