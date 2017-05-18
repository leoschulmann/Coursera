import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {
    private Node root;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node leftBottom;
        private Node rightTop;
        int size;
        boolean isVertical;
    }

    public KdTree() {
    }

    public boolean isEmpty() {
        return root.size == 0;
    }

    public int size() {
        return root.size;
    }

    public void insert(Point2D p) {
        root = recursivePut(root, p, true, new RectHV(0.0, 0.0, 1.0, 1.0));
    }

    private Node recursivePut(Node node, Point2D p, boolean isVertical, RectHV rect) {
        if (node == null) {
            node = new Node();
            node.p = p;
            node.isVertical = isVertical;
            node.size = 1;
            node.rect = rect;
            return node;
        }
        else {
            if (node.isVertical) {          // if vertical, comparing .x coords
                if (p.x() >= node.p.x()) {
                    RectHV newRect = new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
                    node.rightTop = recursivePut(node.rightTop, p, false, newRect);
                }
                else  {
                    RectHV newRect = new RectHV(rect.xmin(), rect.ymin(), node.p.x(), rect.ymax());
                    node.leftBottom = recursivePut(node.leftBottom, p, false, newRect);
                }
            }
            else {
                if (p.y() >= node.p.y()) {
                    RectHV newRect = new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax());
                    node.rightTop = recursivePut(node.rightTop, p, true, newRect);
                }
                else {
                    RectHV newRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.p.y());
                    node.leftBottom = recursivePut(node.leftBottom, p, true, newRect);
                }
            }
        }

        node.size = 1 + (node.rightTop != null ? node.rightTop.size : 0) +
                (node.leftBottom != null ? node.leftBottom.size : 0);
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return recursiveContains (root, p, true);
    }

    private boolean recursiveContains (Node node, Point2D p, boolean isVertical) {
        if (node == null) return false;
        else if (node.p.equals(p)) return true;

        else {
            if  (node.isVertical) {
                if (p.x() >= node.p.x()) {
                    return recursiveContains(node.rightTop, p, false);
                }
                else return recursiveContains(node.leftBottom, p, false);
            }
            else {
                if (p.y() >= node.p.y()) {
                    return recursiveContains(node.rightTop, p, true);
                }
                else return recursiveContains(node.leftBottom, p, true);
            }
        }
    }

    public void draw() {
        recursiveDraw(root);
    }

    private void recursiveDraw (Node node) {
        node.p.draw();
        if (node.leftBottom != null) recursiveDraw(node.leftBottom);
        if (node.rightTop != null)recursiveDraw(node.rightTop);
    }

/*Range search. To find all points contained in a given query rectangle,
start at the root and recursively search for points in both subtrees
using the following pruning rule: if the query rectangle does not
intersect the rectangle corresponding to a node, there is no need
to explore that node (or its subtrees). A subtree is searched only
if it might contain a point contained in the query rectangle.
 */

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw  new NullPointerException();
        pts = new ArrayList<>();
        recursiveRange(root, rect);
        return pts;
    }

    private ArrayList<Point2D> pts;
    private Point2D ololo = null;

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

    /* To find a closest point to a given query point, start at the root
    and recursively search in both subtrees using the following pruning rule:
    if the closest point discovered so far is closer than the distance between the query point and the rectangle corresponding to a node, there is no need
    to explore that node (or its subtrees). That is, a node is searched only if
    it might contain a point that is closer than the best one found so far. The
    effectiveness of the pruning rule depends on quickly finding a nearby point.
    To do this, organize your recursive method so that when there are two possible
    subtrees to go down, you always choose the subtree that is on the same side
    of the splitting line as the query point as the first subtree to explore—
    the closest point found while exploring the first subtree may enable pruning
    of the second subtree.
     */

    public Point2D nearest(Point2D p) {
        if (p == null) throw  new NullPointerException();
        Point2D cand;
        cand = recursiveNearest(root, p, true);
        return cand;
    }

    private Point2D localCandidate;
    private double localCandidateSqDist;

    private Point2D recursiveNearest (Node node, Point2D p, boolean isVertical) {
        if (node == root) {             //first-timers only
            localCandidate = root.p;
            localCandidateSqDist = root.p.distanceSquaredTo(p);
        }

        else if (node == null) return localCandidate;

        else if (node.p.distanceSquaredTo(p) < localCandidateSqDist) {
            localCandidate = node.p;
            localCandidateSqDist = node.p.distanceSquaredTo(p);
        }

        if (isVertical) {
            if (p.x() >= node.p.x() && localCandidateSqDist > node.rect.distanceSquaredTo(p)) {
                localCandidate = recursiveNearest(node.rightTop, p, false);
            }
            else if (p.x() <= node.p.x() && localCandidateSqDist > node.rect.distanceSquaredTo(p)) {
                localCandidate = recursiveNearest(node.leftBottom, p, false);
            }
        }
        else {
            if (p.y() >= node.p.y() && localCandidateSqDist > node.rect.distanceSquaredTo(p)) {
                localCandidate = recursiveNearest(node.rightTop, p, true);
            }
            else if (p.y() <= node.p.y() && localCandidateSqDist > node.rect.distanceSquaredTo(p)) {
                localCandidate = recursiveNearest(node.leftBottom, p, true);
            }
        }
        return localCandidate;
    }




    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.88, 0.88));
        tree.insert(new Point2D(0.90, 0.90));
        tree.insert(new Point2D(0.85, 0.90));
        tree.insert(new Point2D(0.82, 0.88));
        tree.insert(new Point2D(0.45, 0.95));

        System.out.println(tree.nearest(new Point2D(0.88, 0.88)));
    }
}