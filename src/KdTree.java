import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {
    private Node root;
    private ArrayList<Point2D> pts;
    private Point2D ololo = null;
    private Point2D localCandidate;
    private double localCandidateSqDist;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node leftBottom;
        private Node rightTop;
        private int size;
        private boolean isVertical;
    }

    public KdTree() {
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        if (root == null) return 0;
        return root.size;
    }

    public void insert(Point2D p) {
        root = recursivePut(root, p, true, 0, 0, 1, 1);
    }

    private Node recursivePut(Node node, Point2D p, boolean isVertical, double x0, double y0, double x1, double y1) {
        if (node == null) {
            node = new Node();
            node.p = p;
            node.isVertical = isVertical;
            node.size = 1;
//            node.rect = rect;
            node.rect = new RectHV(x0, y0, x1, y1);
            return node;
        }

        else if (node.p.equals(p)) {        // test for degeneracy
            return node;
        }

        else {
            if (node.isVertical) {          // if vertical, comparing .x coords
                if (p.x() >= node.p.x()) {
                    node.rightTop = recursivePut(node.rightTop, p, false, node.p.x(), y0, x1, y1);
                }
                else  {
                    node.leftBottom = recursivePut(node.leftBottom, p, false, x0, y0, node.p.x(), y1);
                }
            }
            else {
                if (p.y() >= node.p.y()) {
                    node.rightTop = recursivePut(node.rightTop, p, true, x0, node.p.y(), x1, y1);
                }
                else {
                    node.leftBottom = recursivePut(node.leftBottom, p, true, x0, y0, x1, node.p.y());
                }
            }
        }

        node.size = 1 + (node.rightTop != null ? node.rightTop.size : 0) +
                (node.leftBottom != null ? node.leftBottom.size : 0);
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return recursiveContains(root, p);
    }

    private boolean recursiveContains(Node node, Point2D p) {
        if (node == null) return false;
        else if (node.p.equals(p)) return true;

        else {
            if  (node.isVertical) {
                if (p.x() >= node.p.x()) {
                    return recursiveContains(node.rightTop, p);
                }
                else return recursiveContains(node.leftBottom, p);
            }
            else {
                if (p.y() >= node.p.y()) {
                    return recursiveContains(node.rightTop, p);
                }
                else return recursiveContains(node.leftBottom, p);
            }
        }
    }

    public void draw() {
        recursiveDraw(root);
    }

    private void recursiveDraw(Node node) {
        node.p.draw();
        if (node.leftBottom != null) recursiveDraw(node.leftBottom);
        if (node.rightTop != null)recursiveDraw(node.rightTop);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw  new NullPointerException();
        pts = new ArrayList<>();
        recursiveRange(root, rect);
        return pts;
    }

    private Point2D recursiveRange(Node node, RectHV rect) {
        if ((node.p.x() >= rect.xmin() && node.p.x() <= rect.xmax()) &&
                (node.p.y() >= rect.ymin() && node.p.y() <= rect.ymax())) {
            ololo = node.p;
            if (ololo != null) pts.add(ololo);
        }
        if  (node.rightTop != null && node.rightTop.rect.intersects(rect)) {
            ololo = recursiveRange(node.rightTop, rect);
        }
        if  (node.leftBottom != null && node.leftBottom.rect.intersects(rect)) {
            ololo = recursiveRange(node.leftBottom, rect);
        }
        return null;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw  new NullPointerException();
        if (isEmpty()) return null;
        Point2D cand;
        cand = recursiveNearest(root, p);
        return cand;
    }

    private Point2D recursiveNearest(Node node, Point2D p) {
        if (node == root) {             // first-timers only
            localCandidate = root.p;
            localCandidateSqDist = root.p.distanceSquaredTo(p);
        }

        else if (node == null) return localCandidate;       // exit node

        else if (node.p.distanceSquaredTo(p) < localCandidateSqDist) {
            localCandidate = node.p;
            localCandidateSqDist = node.p.distanceSquaredTo(p);
        }
        if  (node.isVertical) {
            if (p.x() >= node.p.x()) {
                localCandidate = recursiveNearest(node.rightTop, p);
                if (node.leftBottom != null && node.leftBottom.rect.distanceSquaredTo(p) < localCandidateSqDist) {
                    localCandidate = recursiveNearest(node.leftBottom, p);
                }
            } else if (p.x() < node.p.x()) {
                localCandidate = recursiveNearest(node.leftBottom, p);
                if (node.rightTop != null && node.rightTop.rect.distanceSquaredTo(p) < localCandidateSqDist) {
                    localCandidate = recursiveNearest(node.rightTop, p);
                }
            }
        }
        else  {
            if (p.y() >= node.p.y()) {
                localCandidate = recursiveNearest(node.rightTop, p);
                if (node.leftBottom != null && node.leftBottom.rect.distanceSquaredTo(p) < localCandidateSqDist) {
                    localCandidate = recursiveNearest(node.leftBottom, p);
                }
            }
            else if (p.y() < node.p.y()) {
                localCandidate = recursiveNearest(node.leftBottom, p);
                if (node.rightTop != null && node.rightTop.rect.distanceSquaredTo(p) < localCandidateSqDist) {
                    localCandidate = recursiveNearest(node.rightTop, p);
                }
            }
        }
        return localCandidate;
    }

    public static void main(String[] args) {
    }
}