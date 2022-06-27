import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
	private LineSegment[] segments;
	private Point[] p;
	
	public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
		if (points == null) throw new IllegalArgumentException();
		checkNull(points);
		p = points.clone();
		Arrays.sort(p); // p is sorted based on coordinate
		checkRepeat(p);
		ArrayList<LineSegment> segList = new ArrayList<LineSegment>(); // final list, waiting to be edited
		
		for (int i = 0; i < points.length; i++) { // for-loop
			Point origin = p[i]; // original point at each for loop
			Point[] bySlope = p.clone();
			Arrays.sort(bySlope, origin.slopeOrder()); // comparator			
			
			int count = 1;
			double slopeRefer = 0.0;
			while (count < points.length) {
				slopeRefer = origin.slopeTo(bySlope[count]);
				LinkedList<Point> candidates = new LinkedList<Point>();
				do {
					candidates.add(bySlope[count++]);
				} while (count < points.length && origin.slopeTo(bySlope[count]) == slopeRefer);
				
				if (candidates.size() >= 3 && origin.compareTo(candidates.peek()) < 0) {
					segList.add(new LineSegment(origin, candidates.removeLast()));
				}
			}
		}
		segments = segList.toArray(new LineSegment[segList.size()]);
	}
   
	public int numberOfSegments() {       // the number of line segments
		return segments.length;
	}
   
	public LineSegment[] segments() {               // the line segments
		return segments;
	}
	
	private void checkNull(Point[] points) {
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) throw new IllegalArgumentException();
		}
	}
	
	private void checkRepeat(Point[] points) {
		for (int i = 0; i < points.length - 1; i++) {
			if (points[i].compareTo(points[i+1]) == 0) throw new IllegalArgumentException();
		}
	}
}