import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 8;
    
    private Item[] q;         // array of items
    private int N;           
    // construct an empty randomized queue
	public RandomizedQueue() {
    	q = (Item[]) new Object[INIT_CAPACITY];
    	N = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
    	return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
    	return N;
    }

    // add the item
    public void enqueue(Item item) {
    	if (item == null) throw new IllegalArgumentException();
    	if (N == q.length) resize(2 * q.length);
    	q[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
    	if (isEmpty()) throw new NoSuchElementException();
    	if (N > 0 && N == q.length / 4) resize(q.length / 2);
    	int randomIndex = StdRandom.uniform(N);
    	Item ret = q[randomIndex];
    	q[randomIndex] = q[N-1];
    	q[N-1] = null;
    	N--;
    	return ret;
    }

    // return a random item (but do not remove it)
    public Item sample() {
    	if (isEmpty()) throw new NoSuchElementException();
    	int randomIndex = StdRandom.uniform(N);
    	return q[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
    	return new RandomQIterator();
    }
    
    private class RandomQIterator implements Iterator<Item> {
    	private int index;
    	private Item[] copyQ;
    	
    	public RandomQIterator() {
    		copyQ = q.clone();
    		StdRandom.shuffle(copyQ, 0, N);
    		index = N - 1;
    	}
    	
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return index >= 0;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Item next() {
			// TODO Auto-generated method stub
            if (!hasNext()) throw new NoSuchElementException();
            return copyQ[index--];
		}
    	
    }
    
    private void resize(int capacity) {
    	assert capacity >= N;
		Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
    	int n = 5;
    	RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
    	for (int i = 0; i < n; i++)
    	    queue.enqueue(i);
    	for (int a : queue) {
    	    for (int b : queue)
    	        System.out.print(a + "-" + b + " ");
    	    System.out.println();
    	}
    }

}