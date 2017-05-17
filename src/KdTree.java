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

        public Point2D getP() {
            return p;
        }

        public void setP(Point2D p) {
            this.p = p;
        }

        public RectHV getRect() {
            return rect;
        }

        public void setRect(RectHV rect) {
            this.rect = rect;
        }

        public Node getLeftBottom() {
            return leftBottom;
        }

        public void setLeftBottom(Node leftBottom) {
            this.leftBottom = leftBottom;
        }

        public Node getRightTop() {
            return rightTop;
        }

        public void setRightTop(Node rightTop) {
            this.rightTop = rightTop;
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
//        if (root == null) {
//            root = new Node();
//            root.setP(p);
//            root.setSize(1);
//            root.setRect(new RectHV(0.0, 0.0, 1.0, 1.0));
//        }
        root = recursivePut(root, p, true);
    }

    private Node recursivePut(Node node, Point2D p, boolean isVertical) {
        if (node == null) {
            node = new Node();
            node.setP(p);
            node.setVertical(isVertical);
            node.size = 1;
            return node;
        }
        else {
            if (node.isVertical) {          // if vertical, comparing .x coords
                if (p.x() >= node.getP().x()) {
                    node.setRightTop(recursivePut(node.getRightTop(), p, false));
                }
                else  {
                    node.setLeftBottom(recursivePut(node.getLeftBottom(), p, false));
                }
            }
            else {
                if (p.y() >= node.getP().y()) {
                    node.setRightTop(recursivePut(node.getRightTop(), p, true));
                }
                else {
                    node.setLeftBottom(recursivePut(node.getLeftBottom(), p, true));
                }
            }
        }

        node.size = 1 + (node.getRightTop() != null ? node.getRightTop().size : 0) + (node.getLeftBottom() != null ? node.getLeftBottom().size : 0);
        return node;
    }

//    public boolean contains(Point2D p)
//
//    public void draw()
//
//    public Iterable<Point2D> range(RectHV rect)
//
//    public Point2D nearest(Point2D p)

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.39, 0.41));
        tree.insert(new Point2D(0.72, 0.12));
        tree.insert(new Point2D(0.7, 0.98));

        tree.insert(new Point2D(0.35, 0.17));
        tree.insert(new Point2D(0.60, 0.35));
        tree.insert(new Point2D(0.46, 0.25));

        tree.insert(new Point2D(0.24, 0.18));

        System.out.println("!");
    }
}