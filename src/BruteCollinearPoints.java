
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private int numberOfSegments;
    private ArrayList<LineSegment> results = new ArrayList<>();
    private ArrayList<Point> starts = new ArrayList<>();
    private ArrayList<Point> ends = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("array zero length");
        Arrays.sort(points);

        // checking for duplicate points
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException("array contains null element");
            if (i > 0 && points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException ("array contains duplicate points!");
            }
        }

        for (int p1 = 0; p1 < points.length - 3; p1++){
            for (int p2 = p1 + 1; p2 < points.length - 2; p2++){
//                if (points[p2] == points[p1]) continue;   // excessive check
                double slope12 = points[p1].slopeTo(points[p2]);

                for (int p3 = p2 + 1; p3 < points.length - 1; p3++){

//                    if (points[p3] == points[p2] || points[p3] == points[p1]) continue;
                    if (points[p2].slopeTo(points[p3]) != slope12) continue;

                    for (int p4 = p3 + 1; p4 < points.length; p4++) {

//                        if (points[p4] == points[p3] || points[p4] == points[p2] || points[p4] == points[p1]) continue;
                        if  (points[p3].slopeTo(points[p4]) != slope12) continue;
//                        Point[] candidate = new Point[4];
//                        candidate[0] = points[p1];
//                        candidate[1] = points[p2];
//                        candidate[2] = points[p3];
//                        candidate[3] = points[p4];
//                        Arrays.sort(candidate);
//                        if (isNonUnique(candidate[0], candidate[3])) continue;
//                        results.add(new LineSegment(candidate[0], candidate[3]));
                        numberOfSegments++;
//                        starts.add(candidate[0]);
//                        ends.add(candidate[3]);
                        results.add(new LineSegment(points[p1], points[p4]));
                    }
                }
            }
        }
    }

//    private boolean isNonUnique(Point start, Point end){
//        if (starts.contains(start) && ends.contains(end) && starts.indexOf(start) == ends.indexOf(end)) return true;
//        else if (starts.contains(end) && ends.contains(start) && starts.indexOf(end) == ends.indexOf(start)) return
//                true;
//        return false;
//    }

    public int numberOfSegments() {
        return numberOfSegments;
    }


    public LineSegment[] segments() {
        return results.toArray(new LineSegment[results.size()]);
    }

    public static void main(String[] args) {

        In in = new In("e:\\input6.txt");
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