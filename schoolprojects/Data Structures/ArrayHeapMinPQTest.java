package bearmaps;

import org.junit.Test;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    private static Random r = new Random(500);
    @Test
    public void addTest() {
        NaiveMinPQ<String> npq = new NaiveMinPQ<>();
        ArrayHeapMinPQ<String> apq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 5; i++) {
            npq.add("happy"+i, i);
            apq.add("happy"+i, i);
        }
        for (int i = 0; i < 10; i++) {
            npq.add("happy"+i+10, 7 - i);
            apq.add("happy"+i+10, 7 - i);
        }
        for(int i = 0; i < apq.size(); i++) {
            assertEquals(npq.getSmallest(), apq.getSmallest());

        }

    }

    @Test
    public void containsTest() {
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000; i++) {
            apq.add(i, i);
        }
        for (int i = 0; i < 1000; i++) {
            assertTrue(apq.contains(i));
        }
        assertFalse(apq.contains(1001));
        assertFalse(apq.contains(1000));
        assertFalse(apq.contains(-1));
    }

    @Test
    public void removeTest() {
        NaiveMinPQ<String> npq = new NaiveMinPQ<>();
        ArrayHeapMinPQ<String> apq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 5; i++) {
            npq.add("happy"+i, i);
            apq.add("happy"+i, i);
        }
        for (int i = 0; i < 5; i++) {
            npq.add("happy"+i+5, 7 - i);
            apq.add("happy"+i+5, 7 - i);
        }
        for(int i = 0; i < apq.size(); i++) {
            assertEquals(npq.getSmallest(), apq.getSmallest());
            apq.removeSmallest();
            npq.removeSmallest();
        }
    }

    /**
     * This test fails due to arbitrary tie-breaking in sink.
     */
    @Test
    public void priorityTest() {
        NaiveMinPQ<String> npq = new NaiveMinPQ<>();
        ArrayHeapMinPQ<String> apq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 5; i++) {
            npq.add("happy"+i, i);
            apq.add("happy"+i, i);
        }
        for (int i = 0; i < 5; i++) {
            npq.add("happy"+i+5, 7 - i);
            apq.add("happy"+i+5, 7 - i);
        }

        for(int i = 0; i < apq.size(); i++) {
            double newPriority = r.nextDouble() * 10;
            assertEquals(npq.getSmallest(), apq.getSmallest());
            apq.changePriority(apq.getSmallest(), newPriority);
            npq.changePriority(npq.getSmallest(), newPriority);
            apq.removeSmallest();
            npq.removeSmallest();
        }
    }
    @Test
    public void swimTest() {
        ArrayHeapMinPQ<String> apq = new ArrayHeapMinPQ<>();
        apq.add("one", 4.0);
        apq.add("two", 4.0);
        apq.add("three", 3.0);
    }

    @Test
    public void lectureTest() {
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        apq.add(1,1);
        apq.add(2,5);
        apq.add(3,1);
        apq.add(4,6);
        apq.add(5,5);
        apq.add(6,6);
        apq.add(7,3);
        apq.add(8,7);
        apq.add(9,7);
        apq.add(10,8);
        apq.add(11,3);
        apq.add(12, 5);
        apq.removeSmallest();
        apq.removeSmallest();
    }

    @Test
    public void priorityTest2() {
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        apq.add(1,1);
        apq.add(2,5);
        apq.add(3,1);
        apq.add(4,6);
        apq.add(5,5);
        apq.add(6,6);
        apq.add(7,3);
        apq.add(8,7);
        apq.add(9,7);
        apq.add(10,8);
        apq.add(11,3);
        apq.add(12, 5);
        apq.removeSmallest();
        apq.removeSmallest();
        apq.changePriority(12, 0);
        apq.changePriority(2, -1);
        assertEquals((int) apq.removeSmallest(), 2);
        assertEquals((int) apq.removeSmallest(), 12);
        assertEquals((int) apq.removeSmallest(), 11);
        assertEquals((int) apq.removeSmallest(), 7);
        assertEquals((int) apq.removeSmallest(), 5);
        assertEquals((int) apq.removeSmallest(), 4);
        apq.changePriority(10, -1);
        assertEquals((int) apq.removeSmallest(), 10);

    }

    @Test
    public void sizeTest() {
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        assertEquals(apq.size(), 0);
        for (int i = 0; i < 100000; i++) {
            apq.add(i,i);
        }
        assertEquals(apq.size(), 100000);
        for (int i = 0; i < 100000; i++) {
            apq.removeSmallest();
        }
        assertEquals(apq.size(), 0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void throwAdd() {
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        apq.add(1,1);
        apq.add(1,1);
    }

    @Test (expected = NoSuchElementException.class)
    public void throwSmallest() {
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        apq.removeSmallest();
    }

    @Test (expected = NoSuchElementException.class)
    public void throwGetSmallest() {
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        apq.getSmallest();
    }

    @Test (expected = NoSuchElementException.class)
    public void throwChangePriority() {
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        apq.add(1,1);
        apq.changePriority(2,0);
    }

    @Test
    public void randomAddRemove() {
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> npq = new NaiveMinPQ<>();
        for (int i = 0; i < 100000; i++) {
            double newPriority = r.nextDouble() * 10;
            apq.add(i, newPriority);
            npq.add(i, newPriority);
            assertEquals(apq.getSmallest(), npq.getSmallest());
        }
        for (int i = 0; i < 99999; i++) {
            apq.removeSmallest();
            npq.removeSmallest();
            assertEquals(apq.getSmallest(), npq.getSmallest());
        }
    }

    @Test
    public void randomChangePriority() {

        for (int i = 0; i < 10000; i++) {
            ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
            NaiveMinPQ<Integer> npq = new NaiveMinPQ<>();
            for (int j = 0; j < 10000; j++) {
                apq.add(j, j);
                npq.add(j, j);
            }
            int nextInt = r.nextInt(10000);
            apq.changePriority(nextInt, -1);
            npq.changePriority(nextInt, -1);
            assertEquals(apq.getSmallest(), npq.getSmallest());
            assertEquals((int) apq.getSmallest(), nextInt);
        }

        for (int i = 0; i < 10000; i++) {
            ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
            NaiveMinPQ<Integer> npq = new NaiveMinPQ<>();
            for (int j = 0; j < 10000; j++) {
                apq.add(j, j);
                npq.add(j, j);
            }
            int nextInt = r.nextInt(10000);
            int nextPrior = r.nextInt(10);
            apq.changePriority(nextInt, nextPrior);
            npq.changePriority(nextInt, nextPrior);
            assertEquals(apq.getSmallest(), npq.getSmallest());
        }
    }

    @Test
    public void addTime() {

        System.out.println("Time elasped for a ArrayMinPQ Constructor of Size 31250, 62500, 125000, 250000, 500000, 1000000, 2000000");
        Stopwatch sw1 = new Stopwatch();
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 31250; i++) {
            apq.add(i, i);
        }
        System.out.println("Total time elapsed: " + sw1.elapsedTime() +  " seconds.");
        ArrayHeapMinPQ<Integer> apq2 = new ArrayHeapMinPQ<>();
        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < 62500; i++) {
            apq2.add(i, i);
        }
        System.out.println("Total time elapsed: " + sw2.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> apq3 = new ArrayHeapMinPQ<>();
        Stopwatch sw3 = new Stopwatch();
        for (int i = 0; i < 125000; i++) {
            apq3.add(i, i);
        }
        System.out.println("Total time elapsed: " + sw3.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> apq4 = new ArrayHeapMinPQ<>();
        Stopwatch sw4 = new Stopwatch();
        for (int i = 0; i < 250000; i++) {
            apq4.add(i, i);
        }
        System.out.println("Total time elapsed: " + sw4.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> apq5 = new ArrayHeapMinPQ<>();
        Stopwatch sw5 = new Stopwatch();
        for (int i = 0; i < 500000; i++) {
            apq5.add(i, i);
        }
        System.out.println("Total time elapsed: " + sw5.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> apq6 = new ArrayHeapMinPQ<>();
        Stopwatch sw6 = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            apq6.add(i, i);
        }
        System.out.println("Total time elapsed: " + sw6.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> apq7 = new ArrayHeapMinPQ<>();
        Stopwatch sw7 = new Stopwatch();
        for (int i = 0; i < 2000000; i++) {
            apq7.add(i, i);
        }
        System.out.println("Total time elapsed: " + sw7.elapsedTime() +  " seconds.");

        System.out.println("______________________________________________________________________________________________________________________________");
    }

    @Test
    public void containsTime() {

        System.out.println("Time elasped for a ArrayMinPQ Constructor of Size 31250, 62500, 125000, 250000, 500000");
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 31250; i++) {
            apq.add(i, i);
        }
        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < 31250; i++) {
            apq.contains(i);
        }
        System.out.println("Total time elapsed: " + sw1.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> apq2 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 62500; i++) {
            apq2.add(i, i);
        }
        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < 62500; i++) {
            apq2.contains(i);
        }
        System.out.println("Total time elapsed: " + sw2.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> apq3 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 125000; i++) {
            apq3.add(i, i);
        }
        Stopwatch sw3 = new Stopwatch();
        for (int i = 0; i < 125000; i++) {
            apq3.contains(i);
        }
        System.out.println("Total time elapsed: " + sw3.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> apq4 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 250000; i++) {
            apq4.add(i, i);
        }
        Stopwatch sw4 = new Stopwatch();
        for (int i = 0; i < 250000; i++) {
            apq4.contains(i);
        }
        System.out.println("Total time elapsed: " + sw4.elapsedTime() +  " seconds.");

        ArrayHeapMinPQ<Integer> apq5 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 500000; i++) {
            apq5.add(i, i);
        }
        Stopwatch sw5 = new Stopwatch();
        for (int i = 0; i < 500000; i++) {
            apq5.contains(i);
        }
        System.out.println("Total time elapsed: " + sw5.elapsedTime() +  " seconds.");

        System.out.println("______________________________________________________________________________________________________________________________");
    }

    @Test
    public void changePriorityTime() {

        System.out.println("Time elasped for a ArrayMinPQ Constructor of Size 31250, 62500, 125000, 250000, 500000, 1000000, 2000000");
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 31250; i++) {
            apq.add(i, i);
        }
        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < 31250; i++) {
            apq.changePriority(i, 0);
        }
        System.out.println("Total time elapsed: " + sw1.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq2 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 62500; i++) {
            apq2.add(i, i);
        }
        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < 62500; i++) {
            apq2.changePriority(i, 0);
        }
        System.out.println("Total time elapsed: " + sw2.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq3 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 125000; i++) {
            apq3.add(i, i);
        }
        Stopwatch sw3 = new Stopwatch();
        for (int i = 0; i < 125000; i++) {
            apq3.changePriority(i, 0);
        }
        System.out.println("Total time elapsed: " + sw3.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq4 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 250000; i++) {
            apq4.add(i, i);
        }
        Stopwatch sw4 = new Stopwatch();
        for (int i = 0; i < 250000; i++) {
            apq4.changePriority(i, 0);
        }
        System.out.println("Total time elapsed: " + sw4.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq5 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 500000; i++) {
            apq5.add(i, i);
        }
        Stopwatch sw5 = new Stopwatch();
        for (int i = 0; i < 500000; i++) {
            apq5.changePriority(i, 0);
        }
        System.out.println("Total time elapsed: " + sw5.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq6 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000000; i++) {
            apq6.add(i, i);
        }
        Stopwatch sw6 = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            apq6.changePriority(i, 0);
        }
        System.out.println("Total time elapsed: " + sw6.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq7 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 2000000; i++) {
            apq7.add(i, i);
        }
        Stopwatch sw7 = new Stopwatch();
        for (int i = 0; i < 2000000; i++) {
            apq7.changePriority(i, 0);
        }
        System.out.println("Total time elapsed: " + sw7.elapsedTime() + " seconds.");

        System.out.println("______________________________________________________________________________________________________________________________");
    }

    @Test
    public void getSmallestTime() {

        System.out.println("Time elasped for a ArrayMinPQ Constructor of Size 31250, 62500, 125000, 250000, 500000, 1000000, 2000000");
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 31250; i++) {
            apq.add(i, i);
        }
        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < 31250; i++) {
            apq.getSmallest();
        }
        System.out.println("Total time elapsed: " + sw1.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq2 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 62500; i++) {
            apq2.add(i, i);
        }
        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < 62500; i++) {
            apq2.getSmallest();
        }
        System.out.println("Total time elapsed: " + sw2.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq3 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 125000; i++) {
            apq3.add(i, i);
        }
        Stopwatch sw3 = new Stopwatch();
        for (int i = 0; i < 125000; i++) {
            apq3.getSmallest();
        }
        System.out.println("Total time elapsed: " + sw3.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq4 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 250000; i++) {
            apq4.add(i, i);
        }
        Stopwatch sw4 = new Stopwatch();
        for (int i = 0; i < 250000; i++) {
            apq4.getSmallest();
        }
        System.out.println("Total time elapsed: " + sw4.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq5 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 500000; i++) {
            apq5.add(i, i);
        }
        Stopwatch sw5 = new Stopwatch();
        for (int i = 0; i < 500000; i++) {
            apq5.getSmallest();
        }
        System.out.println("Total time elapsed: " + sw5.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq6 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000000; i++) {
            apq6.add(i, i);
        }
        Stopwatch sw6 = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            apq6.getSmallest();
        }
        System.out.println("Total time elapsed: " + sw6.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq7 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 2000000; i++) {
            apq7.add(i, i);
        }
        Stopwatch sw7 = new Stopwatch();
        for (int i = 0; i < 2000000; i++) {
            apq7.getSmallest();
        }
        System.out.println("Total time elapsed: " + sw7.elapsedTime() + " seconds.");

        System.out.println("______________________________________________________________________________________________________________________________");
    }

    @Test
    public void removeTime() {

        System.out.println("Time elasped for a ArrayMinPQ Constructor of Size 31250, 62500, 125000, 250000, 500000, 1000000, 2000000");
        ArrayHeapMinPQ<Integer> apq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 31250; i++) {
            apq.add(i, i);
        }
        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < 31250; i++) {
            apq.removeSmallest();
        }
        System.out.println("Total time elapsed: " + sw1.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq2 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 62500; i++) {
            apq2.add(i, i);
        }
        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < 62500; i++) {
            apq2.removeSmallest();
        }
        System.out.println("Total time elapsed: " + sw2.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq3 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 125000; i++) {
            apq3.add(i, i);
        }
        Stopwatch sw3 = new Stopwatch();
        for (int i = 0; i < 125000; i++) {
            apq3.removeSmallest();
        }
        System.out.println("Total time elapsed: " + sw3.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq4 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 250000; i++) {
            apq4.add(i, i);
        }
        Stopwatch sw4 = new Stopwatch();
        for (int i = 0; i < 250000; i++) {
            apq4.removeSmallest();
        }
        System.out.println("Total time elapsed: " + sw4.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq5 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 500000; i++) {
            apq5.add(i, i);
        }
        Stopwatch sw5 = new Stopwatch();
        for (int i = 0; i < 500000; i++) {
            apq5.removeSmallest();
        }
        System.out.println("Total time elapsed: " + sw5.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq6 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000000; i++) {
            apq6.add(i, i);
        }
        Stopwatch sw6 = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            apq6.removeSmallest();
        }
        System.out.println("Total time elapsed: " + sw6.elapsedTime() + " seconds.");

        ArrayHeapMinPQ<Integer> apq7 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 2000000; i++) {
            apq7.add(i, i);
        }
        Stopwatch sw7 = new Stopwatch();
        for (int i = 0; i < 2000000; i++) {
            apq7.removeSmallest();
        }
        System.out.println("Total time elapsed: " + sw7.elapsedTime() + " seconds.");

        System.out.println("______________________________________________________________________________________________________________________________");
    }

    /**
     * Long test.
     */
    @Test
    public void comparisonTest() {
        NaiveMinPQ<Integer> npq = new NaiveMinPQ<>();
        for (int i = 0; i < 500000; i++) {
            npq.add(i, i);
        }
        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < 500000; i++) {
            npq.removeSmallest();
        }
        System.out.println("Total time elapsed: " + sw1.elapsedTime() + " seconds.");
    }
}

