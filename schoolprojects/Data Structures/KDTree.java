package bearmaps;
import java.util.List;

/**
 * A 2D Tree.
 * @author Robbie Netzke.
 */
public class KDTree implements PointSet {
    /** Orientation of the Node. */
    private static final boolean HORIZONTAL = true;
    /** Start of the tree. */
    private Node root;
    /**Stores ancestors and orientation for each point. */
    private class Node {
        /** The point in the Node. */
        private Point point;
        /** Left ancestor to the this Node. */
        private Node ld;
        /** Right ancestor to the this Node. */
        private Node ru;
        /** Orientation of the ancestor Nodes. */
        private boolean orien;
        /**
         * Constructs the Node.
         * @param p a point.
         * @param o an orientation.
         */
        Node(Point p, boolean o) {
            point = p;
            orien = o;
        }
    }
    /**
     * KDTree construtor.
     * @param points a list of points to add.
     */
    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root, HORIZONTAL);
        }
    }
    /**
     * Adds a point to the tree.
     * @return the root Node.
     * @param p a point.
     * @param n a node.
     * @param orientation an orientation.
     */
    private Node add(Point p, Node n, boolean orientation) {
        if (n == null) {
            return new Node(p, orientation);
        }
        int cmp = comparePoints(p, n.point, n.orien);
        if (cmp < 0) {
            n.ld = add(p, n.ld, !(orientation));
        } else if (cmp > 0) {
            n.ru = add(p, n.ru, !(orientation));
        } else {
            if (comparePoints(p, n.point, !(orientation)) == 0) {
                return n;
            }
            n.ru = add(p, n.ru, !(orientation));
        }
        return n;


    }
    /**
     * Compares two points based on orientation.
     * @return -1, 0, or 1.
     * @param a first point.
     * @param b second point.
     * @param orientation the orientation of the points.
     * */
    private int comparePoints(Point a, Point b, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return Double.compare(a.getX(), b.getX());
        }
        return Double.compare(a.getY(), b.getY());
    }
    /** Finds the nearest point to x,y.*/
    @Override
    public Point nearest(double x, double y) {
        return nearest(root, new Point(x, y), root).point;
    }
    /** Helper method for nearest.
     * @return nearest point.
     * @param n the Node we are on.
     * @param goal target Node.
     * @param best best Node so far.
     * */
    private Node nearest(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.point, goal) < Point.distance(best.point, goal)) {
            best = n;
        }
        Node goodNode = n.ld;
        Node badNode = n.ru;
        if (n.orien == HORIZONTAL) {
            if (goal.getX() > n.point.getX()) {
                goodNode = n.ru;
                badNode = n.ld;
            }
        } else {
            if (goal.getY() > n.point.getY()) {
                goodNode = n.ru;
                badNode = n.ld;
            }
        }
        best = nearest(goodNode, goal, best);
        if (n.orien == HORIZONTAL) {
            Point compare = new Point(n.point.getX(), goal.getY());
            double d1 = Point.distance(compare, goal);
            double d2 = Point.distance(best.point, goal);
            if (d1 < d2) {
                best = nearest(badNode, goal, best);
            }
        } else {
            Point compare = new Point(goal.getX(), n.point.getY());
            double d1 = Point.distance(compare, goal);
            double d2 = Point.distance(best.point, goal);
            if (d1 < d2) {
                best = nearest(badNode, goal, best);
            }
        }
        return best;
    }
}
