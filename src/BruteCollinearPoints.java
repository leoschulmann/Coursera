//Write a program BruteCollinearPoints.java that examines 4 points at a time
// and checks whether they all lie on the same line segment, returning all such
// line segments. To check whether the 4 points p, q, r, and s are collinear,
// check whether the three slopes between p and q, between p and r, and
// between p and s are all equal.


public class BruteCollinearPoints {

    private Point[] pts;
    private int numberOfSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points){
        pts = points;
        for (int p1 = 0; p1< pts.length; p1++){
            for (int p2 = 1; p2< pts.length; p2++){
                double refSlope = pts[p1].slopeTo(pts[p2]);

                for (int p3 = 2; p3< pts.length; p3++){
                    if (pts[p2].slopeTo(pts[p3]) != refSlope) continue; //if 2-3 slope differs from 1-2 - iterate p3

                    for (int p4 = 3; p4< pts.length; p4++){



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
    public LineSegment[] segments()
}