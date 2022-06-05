import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	
	private Node first, last;
	private int N;
	
	private class Node {
		Item val;
		Node previous, next;
	}
	
    // construct an empty deque
    public Deque() {
    	N = 0;
    	first = last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
    	return N == 0;
    }

    // return the number of items on the deque
    public int size() {
    	return N;
    }

    // add the item to the front
    public void addFirst(Item item) {
    	if (item == null) throw new IllegalArgumentException();
    	Node oldFirst = first;
    	first = new Node();
    	first.val = item;
    	first.next = oldFirst;
    	if (oldFirst != null) 
    		oldFirst.previous = first;
    	else 
    		last = first;
    	N++;
    }

    // add the item to the back
    public void addLast(Item item) {
    	if (item == null) throw new IllegalArgumentException();
    	Node oldLast = last;
    	last = new Node();
    	last.val = item;
    	last.previous = oldLast;
    	if (oldLast != null) 
    		oldLast.next = last;
    	else 
    		first = last;
    	N++; 	
    }
 
    // remove and return the item from the front
    public Item removeFirst() {
    	if (isEmpty()) throw new NoSuchElementException();
    	Item item = first.val;
    	first = first.next;
    	if (first != null)
    		first.previous = null;
    	else 
    		last = first;
    	N--;
    	return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
    	if (isEmpty()) throw new NoSuchElementException();
    	Item item = last.val;
    	last = last.previous;
    	if (last != null)
    		last.next = null;
    	else
    		first = last;
    	N--;
    	return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
    	return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
    	private Node current = first;
		@Override
		public boolean hasNext() {
			return current != null;
		}
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		@Override
		public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.val;
            current = current.next; 
            return item;
		}
    }

    // unit testing (required)
    public static void main(String[] args) {
    }
}
