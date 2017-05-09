
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private int numberOfSegments;
    private ArrayList<LineSegment> results = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("array zero length");
        Point[] newPoints = points.clone();
        Arrays.sort(newPoints);

        for (int i = 0; i < newPoints.length; i++) {
            if (newPoints[i] == null) throw new NullPointerException("array contains null element");
            if (i > 0 && newPoints[i].compareTo(newPoints[i - 1]) == 0) {
                throw new IllegalArgumentException ("array contains duplicate points!");
            }
        }

        for (int p1 = 0; p1 < newPoints.length - 3; p1++) {
            for (int p2 = p1 + 1; p2 < newPoints.length - 2; p2++) {
                double slope12 = newPoints[p1].slopeTo(newPoints[p2]);
                for (int p3 = p2 + 1; p3 < newPoints.length - 1; p3++) {
                    if (newPoints[p2].slopeTo(newPoints[p3]) != slope12) continue;
                    for (int p4 = p3 + 1; p4 < newPoints.length; p4++) {
                        if  (newPoints[p3].slopeTo(newPoints[p4]) != slope12) continue;
                        numberOfSegments++;
                        results.add(new LineSegment(newPoints[p1], newPoints[p4]));
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }


    public LineSegment[] segments() {
        return results.toArray(new LineSegment[results.size()]);
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}