import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private int numberOfSegments = 0;
    private ArrayList<LineSegment> array = new ArrayList<>();
    private ArrayList<Double> slopesLib = new ArrayList<>();
    private ArrayList<Point> endpoints = new ArrayList<>();
//    private ArrayList<Point> starts = new ArrayList<>();
//    private ArrayList<Point> ends = new ArrayList<>();


    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("array zero length");

        Point[] copy = points.clone();
        Arrays.sort(copy);

        // checking for duplicate points
        for (int i = 0; i < copy.length; i++) {
            if (copy[i] == null) throw new NullPointerException("array contains null element");
            if (i > 0 && copy[i].compareTo(copy[i - 1]) == 0) {
                throw new IllegalArgumentException ("array contains duplicate points!");
            }
        }

        for (Point x : points){
            Arrays.sort(copy, x.slopeOrder());
            Point[] subArray = new Point[3];
            for (int i = 0; i < copy.length-2; i++){
                subArray[0] = copy[i];
                subArray[1] = copy[i+1];
                subArray[2] = copy[i+2];

                double slope12 = x.slopeTo(subArray[0]);
                double slope13 = x.slopeTo(subArray[1]);
                double slope14 = x.slopeTo(subArray[2]);

                if ((slope12 == slope13) && (slope13 == slope14)) {
                    Point[] candidate = new Point[4];
                    candidate[0] = x;
                    candidate[1] = subArray[0];
                    candidate[2] = subArray[1];
                    candidate[3] = subArray[2];
                    Arrays.sort(candidate);
                    if (!isNonUnique(candidate)){
                        array.add(new LineSegment(candidate[0], candidate[3]));
                        endpoints.add(candidate[0]);
                        endpoints.add(candidate[3]);
                        numberOfSegments++;
                    };




//                if ((x.slopeTo(subArray[0]) == x.slopeTo(subArray[1])) &&
//                        (x.slopeTo(subArray[1]) == x.slopeTo(subArray[2]))){
//                    Point[] candidate = new Point[4];
//                    System.arraycopy(subArray, 0, candidate, 0, subArray.length);
//                    candidate[3] = x;
//                    Arrays.sort(candidate);
//                    if (isNonUnique(candidate[0],candidate[3])) continue;
//                    array.add(new LineSegment(candidate[0], candidate[3]));
//                    numberOfSegments++;
//                    starts.add(candidate[0]);
//                    ends.add(candidate[3]);
                }
            }
        }
    }



    private boolean isNonUnique(Point[] arr){
        if (endpoints.contains(arr[0]) && endpoints.contains(arr[3])){
            if (Math.abs(endpoints.indexOf(arr[0]) - endpoints.indexOf(arr[3])) == 1){
                return true;
            }
        }
        return false;
    }

//    private boolean isNonUnique(Point start, Point end){
//        if (starts.contains(start) && ends.contains(end) && starts.indexOf(start) == ends.indexOf(end)) return true;
//        else if (starts.contains(end) && ends.contains(start) && starts.indexOf(end) == ends.indexOf(start)) return
//                true;
//        else return false;
//    }


    public int numberOfSegments(){
        return numberOfSegments;
    }

    public LineSegment[] segments(){
        return array.toArray(new LineSegment[array.size()]);
    }


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("e:\\input6.txt");
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