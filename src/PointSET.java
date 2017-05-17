import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    SET <Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (!contains(p)) {
            set.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D x : set) {
            x.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect){
        if (rect == null) throw new NullPointerException();
        ArrayList<Point2D> arr = new ArrayList<>();
        for (Point2D p : set) {
            if ((p.x() >= rect.xmin() && p.x() <= rect.xmax()) && (p.y() >= rect.ymin() && p.y() <= rect.xmax())) {
                arr.add(p);
            }
        }
        return arr;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        boolean firsttime = true;
        Point2D cand = null;
        for (Point2D x : set) {
            if (!x.equals(p)) {
                if (firsttime) {
                    cand = x;
                    firsttime = false;
                }
                if (x.distanceTo(p) < cand.distanceTo(p)) cand = x;
            }
        }
        return cand;
    }

//    public static void main(String[] args)                  // unit testing of the methods (optional)
}