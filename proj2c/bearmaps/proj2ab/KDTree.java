package bearmaps.proj2ab;

import java.util.List;

public class KDTree implements PointSet{
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private KDNode root;



    public KDTree(List<Point> points) {
        if (points.size() > 0) {
            root = new KDNode(points.get(0));
            root.orientation = HORIZONTAL;
            for (int i = 1; i < points.size(); i++) {
                root.KDInsert(root, points.get(i), root.orientation);
            }
        }

    }


    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x,y);
        KDNode nearest = root.KDFindNearest(root, goal, root);
        return nearest.point;
    }

    private class KDNode {
        private Point point;
        private boolean orientation;
        private KDNode left;
        private KDNode right;

        public KDNode(Point p) {
            this.point = p;
            left = null;
            right = null;
        }

        public KDNode(Point p, boolean dir) {
            this.point = p;
            this.orientation = dir;
            this.left = null;
            this.right = null;
        }

        private int pointsCompare(Point p1, Point p2, boolean dir) {
            if (dir == HORIZONTAL) {
                return Double.compare(p1.getX(), p2.getX());
            } else {
                return Double.compare(p1.getY(), p2.getY());
            }
        }

        private int compareBadSide(KDNode node, Point p, KDNode best) {
            if (node.orientation == HORIZONTAL) {
                return Double.compare((p.getX()-node.point.getX()) * (p.getX()-node.point.getX()),
                        Point.distance(p, best.point));
            } else {
                return Double.compare((p.getY()-node.point.getY()) * (p.getY()-node.point.getY()),
                        Point.distance(p, best.point));
            }
        }

        private KDNode KDFindNearest(KDNode node, Point p, KDNode best) {
            KDNode goodSide;
            KDNode badSide;
            if (node == null) {
                return best;
            }
            if (Point.distance(p, node.point) < Point.distance(p, best.point)) {
                best = node;
            }
            if (pointsCompare(p, node.point, node.orientation) < 0) {
               goodSide = node.left;
               badSide = node.right;
            } else {
                goodSide = node.right;
                badSide = node.left;
            }
            best = KDFindNearest(goodSide, p, best);
            if(compareBadSide(node, p, best) < 0) {
                best = KDFindNearest(badSide, p, best);
            }
            return best;
        }

        // Insert a new node of p as the point under the parent pNode
        private KDNode KDInsert(KDNode pNode, Point p, boolean dir) {
            if (pNode == null) {
                return new KDNode(p, dir);
            } else if (pNode.point.equals(p)) {
                return pNode;
            } else if (pointsCompare(p, pNode.point, pNode.orientation) < 0) {
                pNode.left = KDInsert(pNode.left, p, (!pNode.orientation));

            } else {
                pNode.right = KDInsert(pNode.right, p, (!pNode.orientation));
            }
            return pNode;
        }

    }

}
