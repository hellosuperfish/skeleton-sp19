package bearmaps;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    private static Random r = new Random(500);

    public KDTree buildDemoTree() {
        Point p1 = new Point(2,3);
        Point p2 = new Point(4,2);
        Point p3 = new Point(4,5);
        Point p4 = new Point(3,3);
        Point p5 = new Point(1,5);
        Point p6 = new Point(4,4);
        Point p7 = new Point(4,2);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        return kd;

    }

    @Test
    public void testNearest(){
        KDTree kd = buildDemoTree();
        Point goal = new Point(0, 7);
        Point actual = kd.nearest(goal.getX(), goal.getY());
        Point expected = new Point(1, 5);
        assertEquals(expected, actual);
    }

    private Point randomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);
    }

    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList();
        for(int i = 0; i < N; i++) {
            points.add(randomPoint());
        }
        return points;
    }

    @Test
    public void testRandomTree(){
        List<Point> points1000 = randomPoints(1000);
        NaivePointSet nps = new NaivePointSet(points1000);
        KDTree kd = new KDTree(points1000);

        List<Point> points200 = randomPoints(200);
        for(Point p : points200) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    public void NaivePointTime(){
        long start = System.currentTimeMillis();
        List<Point> points1000 = randomPoints(1000000);
        NaivePointSet nps = new NaivePointSet(points1000);
        List<Point> points200 = randomPoints(20000);
        for(Point p : points200) {
            Point expected = nps.nearest(p.getX(), p.getY());
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed for NaivePointSet: " + (end - start) / 1000.0 + " seconds.");
    }

    public void KDTreeTime(){
        long start = System.currentTimeMillis();
        List<Point> points1000 = randomPoints(1000000);
        KDTree kd = new KDTree(points1000);
        List<Point> points200 = randomPoints(20000);
        for(Point p : points200) {
            Point actual = kd.nearest(p.getX(), p.getY());
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed for KDTree: " + (end - start) / 1000.0 + " seconds.");
    }

    public static void main(String[] args) {
      KDTreeTest kdtt = new KDTreeTest();
      kdtt.NaivePointTime();
      kdtt.KDTreeTime();
    }
}
