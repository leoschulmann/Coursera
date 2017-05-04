//Write a program BruteCollinearPoints.java that examines 4 points at a time
// and checks whether they all lie on the same line segment, returning all such
// line segments. To check whether the 4 points p, q, r, and s are collinear,
// check whether the three slopes between p and q, between p and r, and
// between p and s are all equal.


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private int numberOfSegments;
    private ArrayList<LineSegment> results = new ArrayList<>();
    private ArrayList<Point> heads = new ArrayList<>();
    private ArrayList<Point> tails = new ArrayList<>();

    public BruteCollinearPoints(Point[] points){
        if (points.length == 0) throw new NullPointerException("array zero length");
        Arrays.sort(points);
            for (int i = 0; i < points.length; i++){
                if (points[i] == null) throw new NullPointerException("array contains null element");
                if (i > 0 && points[i].slopeTo(points[i-1])==Double.NEGATIVE_INFINITY) throw new IllegalArgumentException("array contains duplicate points!");
        }

        for (int p1 = 0; p1< points.length; p1++){
            for (int p2 = 1; p2< points.length; p2++){
                if (points[p2] == points[p1]) continue;
                double refSlope = points[p1].slopeTo(points[p2]);

                for (int p3 = 2; p3< points.length; p3++){
                    if (points[p3] == points[p2] || points[p3] == points[p1]) continue;
                    if (points[p2].slopeTo(points[p3]) != refSlope) continue; //if 2-3 slope differs from 1-2 - iterate p3

                    for (int p4 = 3; p4< points.length; p4++){
                        if (points[p4] == points[p3] || points[p4] == points[p2] || points[p4] == points[p1]) continue;
                        if  (points[p3].slopeTo(points[p4]) != refSlope) continue;
                        Point[] candidate = new Point[4];
                        candidate[0] = points[p1];
                        candidate[1] = points[p2];
                        candidate[2] = points[p3];
                        candidate[3] = points[p4];
                        Arrays.sort(candidate);
                        if ((heads.contains(candidate[0]) && tails.get(heads.indexOf(candidate[0])) == candidate[3]) ||
                                (heads.contains(candidate[3]) && tails.get(heads.indexOf(candidate[0])) == candidate[0])) continue;
                        results.add(new LineSegment(candidate[0], candidate[3]));
                        numberOfSegments++;
                        heads.add(candidate[0]);
                        tails.add(candidate[3]);
                    }
                }
            }
        }
    }



    // the number of line segments
    public int numberOfSegments(){
        return numberOfSegments;
    }


    // the line segments
    //The method segments() should include each line segment containing 4 points exactly once.
    // If 4 points appear on a line segment in the order p→q→r→s, then you should include
    // either the line segment p→s or s→p (but not both) and you should not include subsegments
    // such as p→r or q→r. For simplicity, we will not supply any input to BruteCollinearPoints
    // that has 5 or more collinear points.
    public LineSegment[] segments(){
        return results.toArray(new LineSegment[results.size()]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}