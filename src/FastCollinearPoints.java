import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FastCollinearPoints {

    private int numberOfSegments;
    private ArrayList<LineSegment> array = new ArrayList<>();
    private ArrayList<Point> starts = new ArrayList<>();
    private ArrayList<Point> ends = new ArrayList<>();
    public FastCollinearPoints(Point[] points) {
        if (points.length == 0) throw new NullPointerException("array zero length");
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException("array contains null element");
            if (i > 0 && points[i].slopeTo(points[i - 1]) == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException("array contains duplicate points!");
            }
        }

        for (Point x : points){
            Arrays.sort(points, x.slopeOrder());
            Point[] subArray = new Point[3];
            for (int i = 0; i < points.length-2; i++){
                subArray[0] = points[i];
                subArray[1] = points[i+1];
                subArray[2] = points[i+2];
                if ((x.slopeTo(subArray[0]) == x.slopeTo(subArray[1])) &&
                        (x.slopeTo(subArray[1]) == x.slopeTo(subArray[2]))){
                    Point[] candidate = new Point[4];
                    System.arraycopy(subArray, 0, candidate, 0, subArray.length);
                    candidate[3] = x;
                    Arrays.sort(candidate);
                    if (isNonUnique(candidate[0],candidate[3])) continue;
                    array.add(new LineSegment(candidate[0], candidate[3]));
                    numberOfSegments++;
                    starts.add(candidate[0]);
                    ends.add(candidate[3]);
                }
            }
        }
    }



    private boolean isNonUnique(Point start, Point end){
        if (starts.contains(start) && ends.contains(end) && starts.indexOf(start) == ends.indexOf(end)) return true;
        else if (starts.contains(end) && ends.contains(start) && starts.indexOf(end) == ends.indexOf(start)) return
                true;
        else return false;
    }


    public int numberOfSegments(){
        return numberOfSegments;
    }

    public LineSegment[] segments(){
        return array.toArray(new LineSegment[array.size()]);
    }


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}