import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> set;

    public PointSET() {
        set = new SET<>();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        set.add(p);
    }

    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    public void draw() {
        for (Point2D x : set) {
            x.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        ArrayList<Point2D> arr = new ArrayList<>();
        for (Point2D p : set) if (rect.contains(p)) arr.add(p);
        return arr;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        Point2D cand = null;
        for (Point2D x : set) {
            if (cand == null) {
                cand = x;
            }
            else {
                if (x.distanceSquaredTo(p) < cand.distanceSquaredTo(p)) cand = x;
            }
        }
        return cand;
    }

    public static void main(String[] args) {}

}