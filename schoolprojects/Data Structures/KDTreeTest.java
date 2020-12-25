package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    private static Random r = new Random(500);

    @Test
    public void lectureSlides() {
        Point p1 = new Point(2.0, 3.0);
        Point p2 = new Point(4.0, 2.0);
        Point p3 = new Point(4.0, 2.0);
        Point p4 = new Point(4.0, 5.0);
        Point p5 = new Point(3.0, 3.0);
        Point p6 = new Point(1.0, 5.0);
        Point p7 = new Point(4.0, 4.0);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        Point expected = new Point(1,5);
        Point actual =  kd.nearest(0,7);
        assertEquals(actual.getX(), expected.getX(), 0.0000001);
        assertEquals(actual.getY(), expected.getY(), 0.0000001);


    }

    @Test
    public void randomTests() {
        List<Point> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(randomPoint());
        }

        List<Point> query = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            query.add(randomPoint());
        }

        NaivePointSet np = new NaivePointSet(list);
        KDTree kd = new KDTree(list);

        for (Point p : query) {
            Point expected = np.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(actual.getX(), expected.getX(), 0.0000001);
            assertEquals(actual.getY(), expected.getY(), 0.0000001);
        }

    }

    private static Point randomPoint() {
        double X = r.nextDouble() * Math.pow(-1, r.nextInt(2 )) * 1000;
        double Y = r.nextDouble() * Math.pow(-1, r.nextInt(2 )) * 1000;
        return new Point(X, Y);
    }

    public static void main(String[] args) {
        constructorTime();
        naiveTime();
        kdTime();
    }

    private static void kdTime() {
        List<Point> queries = generateList(1000000);

        System.out.println("Time testing for 1000000 queries of nearest on a KDTRee of size N: 125000, 250000, 500000, 1000000, 2000000");
        List<Point> cList1 = generateList(125000);
        KDTree kd1 = new KDTree(cList1);
        Stopwatch sw1 = new Stopwatch();
        for(Point q: queries) {
            kd1.nearest(q.getX(), q.getY());
        }
        System.out.println("Total time elapsed: " + sw1.elapsedTime() +  " seconds.");

        List<Point> cList2 = generateList(250000);
        KDTree kd2 = new KDTree(cList2);
        Stopwatch sw2 = new Stopwatch();
        for(Point q: queries) {
            kd2.nearest(q.getX(), q.getY());
        }
        System.out.println("Total time elapsed: " + sw2.elapsedTime() +  " seconds.");

        List<Point> cList3 = generateList(500000);
        KDTree kd3 = new KDTree(cList3);
        Stopwatch sw3 = new Stopwatch();
        for(Point q: queries) {
            kd3.nearest(q.getX(), q.getY());
        }
        System.out.println("Total time elapsed: " + sw3.elapsedTime() +  " seconds.");

        List<Point> cList4 = generateList(1000000);
        KDTree kd4 = new KDTree(cList4);
        Stopwatch sw4 = new Stopwatch();
        for(Point q: queries) {
            kd4.nearest(q.getX(), q.getY());
        }
        System.out.println("Total time elapsed: " + sw4.elapsedTime() +  " seconds.");

        List<Point> cList5 = generateList(2000000);
        KDTree kd5 = new KDTree(cList5);
        Stopwatch sw5 = new Stopwatch();
        for(Point q: queries) {
            kd5.nearest(q.getX(), q.getY());
        }
        System.out.println("Total time elapsed: " + sw5.elapsedTime() +  " seconds.");
        System.out.println("______________________________________________________________________________________________________________________________");

    }

    private static void naiveTime() {
        List<Point> queries = generateList(1000000);

        System.out.println("Time testing for 1000000 queries of nearest on a NaivePointSet of size N: 125, 250, 500, 1000");
        List<Point> cList1 = generateList(125);
        NaivePointSet nps1 = new NaivePointSet(cList1);
        Stopwatch sw1 = new Stopwatch();
        for(Point q: queries) {
            nps1.nearest(q.getX(), q.getY());
        }
        System.out.println("Total time elapsed: " + sw1.elapsedTime() +  " seconds.");

        List<Point> cList2 = generateList(250);
        NaivePointSet nps2 = new NaivePointSet(cList2);
        Stopwatch sw2 = new Stopwatch();
        for(Point q: queries) {
            nps2.nearest(q.getX(), q.getY());
        }
        System.out.println("Total time elapsed: " + sw2.elapsedTime() +  " seconds.");

        List<Point> cList3 = generateList(500);
        NaivePointSet nps3 = new NaivePointSet(cList3);
        Stopwatch sw3 = new Stopwatch();
        for(Point q: queries) {
            nps3.nearest(q.getX(), q.getY());
        }
        System.out.println("Total time elapsed: " + sw3.elapsedTime() +  " seconds.");

        List<Point> cList4 = generateList(500);
        NaivePointSet nps4 = new NaivePointSet(cList4);
        Stopwatch sw4 = new Stopwatch();
        for(Point q: queries) {
            nps4.nearest(q.getX(), q.getY());
        }
        System.out.println("Total time elapsed: " + sw4.elapsedTime() +  " seconds.");
        System.out.println("______________________________________________________________________________________________________________________________");
    }

    private static void constructorTime() {

        System.out.println("Time elasped for a KDTree Constructor of Size 31250, 62500, 125000, 250000, 500000, 1000000, 2000000");
        List<Point> cList1 = generateList(31250);
        Stopwatch sw1 = new Stopwatch();
        KDTree kd1 = new KDTree(cList1);
        System.out.println("Total time elapsed: " + sw1.elapsedTime() +  " seconds.");

        List<Point> cList2 = generateList(2*31250);
        Stopwatch sw2 = new Stopwatch();
        KDTree kd2 = new KDTree(cList2);
        System.out.println("Total time elapsed: " + sw2.elapsedTime() +  " seconds.");

        List<Point> cList3 = generateList(2*2*31250);
        Stopwatch sw3 = new Stopwatch();
        KDTree kd3 = new KDTree(cList3);
        System.out.println("Total time elapsed: " + sw3.elapsedTime() +  " seconds.");

        List<Point> cList4 = generateList(250000);
        Stopwatch sw4 = new Stopwatch();
        KDTree kd4 = new KDTree(cList4);
        System.out.println("Total time elapsed: " + sw4.elapsedTime() +  " seconds.");

        List<Point> cList5 = generateList(2*250000);
        Stopwatch sw5 = new Stopwatch();
        KDTree kd5 = new KDTree(cList5);
        System.out.println("Total time elapsed: " + sw5.elapsedTime() +  " seconds.");

        List<Point> cList6 = generateList(4*250000);
        Stopwatch sw6 = new Stopwatch();
        KDTree kd6 = new KDTree(cList6);
        System.out.println("Total time elapsed: " + sw6.elapsedTime() +  " seconds.");

        List<Point> cList7 = generateList(8*250000);
        Stopwatch sw7 = new Stopwatch();
        KDTree kd7 = new KDTree(cList7);
        System.out.println("Total time elapsed: " + sw7.elapsedTime() +  " seconds.");
        System.out.println("______________________________________________________________________________________________________________________________");


    }

    private static List<Point> generateList(int N) {
        List<Point> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.add(randomPoint());
        }
        return list;
    }


}
