import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private LineSegment[] segments;
	private Point[] p;
	
	public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
		if (points == null) throw new IllegalArgumentException(); // throw exception if input is null
		checkNull(points); // check if any point is null
		p = points.clone(); // TODO
		Arrays.sort(p); // 
		checkRepeat(p);
		ArrayList<LineSegment> segList = new ArrayList<LineSegment>();
		for (int a = 0; a < p.length - 3; a++) {
			for (int b = a + 1; b < p.length - 2; b++) {
				for (int c = b + 1; c < p.length - 1; c++) {
					if (p[a].slopeTo(p[b]) == p[a].slopeTo(p[c])) {
						for (int d = c + 1; d < p.length; d++) {
							if (p[a].slopeTo(p[b]) == p[a].slopeTo(p[d])) {
								LineSegment seg = new LineSegment(p[a], p[d]);
								if (!segList.contains(seg)) segList.add(seg);
							}
						}
					}
				}
			}
		}
		segments = segList.toArray(new LineSegment[segList.size()]);
	}
	public int numberOfSegments() {    // the number of line segments	
		return segments.length;
	}
	public LineSegment[] segments() {             // the line segments
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
