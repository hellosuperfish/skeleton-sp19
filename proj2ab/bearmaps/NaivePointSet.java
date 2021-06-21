package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet{

    List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        double nearDist = Double.MAX_VALUE;
        Point nearP = null;
        for (Point p : points) {
            double dist = Point.distance(p.getX(), x, p.getY(), y);
            if (dist < nearDist) {
                nearDist = dist;
                nearP = p;
            }
        }
        return nearP;
    }
}
