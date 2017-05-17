import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    Node root;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node leftBottom;
        private Node rightTop;
        int size;
        boolean isVertical;

        public RectHV getRect() {
            return rect;
        }

        public void setRect(RectHV rect) {
            this.rect = rect;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public boolean isVertical() {
            return isVertical;
        }

        public void setVertical(boolean vertical) {
            isVertical = vertical;
        }
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
            node.setVertical(isVertical);
            node.size = 1;
            node.setRect(rect);
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

        node.size = 1 + (node.rightTop != null ? node.rightTop.size : 0) + (node.leftBottom != null ? node.leftBottom.size : 0);
        return node;
    }

    public boolean contains(Point2D p)

    public void draw() {
        recursiveDraw(root);
    }

    private void recursiveDraw (Node node) {
        node.p.draw();
        if (node.leftBottom != null) recursiveDraw(node.leftBottom);
        if (node.rightTop != null)recursiveDraw(node.rightTop);
    }


    public Iterable<Point2D> range(RectHV rect)

    public Point2D nearest(Point2D p)

    public static void main(String[] args) {
    }
}