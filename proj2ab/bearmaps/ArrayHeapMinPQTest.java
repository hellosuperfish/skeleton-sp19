package bearmaps;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;


public class ArrayHeapMinPQTest {

    @Test
    public void addTestSimple() {
        ArrayHeapMinPQ<Integer> ahmp = new ArrayHeapMinPQ<>();

        ahmp.add(0, 0);
        ahmp.add(1, 1);
        ahmp.add(2, 2);
        ahmp.add(3, 3);
        ahmp.add(4, 4);
        ahmp.add(5, 5);

        ahmp.toArray();
        PrintHeapDemo.printFancyHeapDrawing(ahmp.toArray());
        assertEquals(6, ahmp.size());
        assertEquals(0, (int)ahmp.removeSmallest());
        assertEquals(1, (int)ahmp.removeSmallest());
        PrintHeapDemo.printSimpleHeapDrawing(ahmp.toArray());
    }

    @Test
    public void addTestReverse() {
        ArrayHeapMinPQ<Integer> ahmp = new ArrayHeapMinPQ<>();
        ahmp.add(5, 5);
        ahmp.add(4, 4);
        ahmp.add(3, 3);
        ahmp.add(2, 2);
        ahmp.add(1, 1);

        PrintHeapDemo.printSimpleHeapDrawing(ahmp.toArray());
        assertEquals(5, ahmp.size());
        assertEquals(0, (int)ahmp.removeSmallest());
        assertEquals(1, (int)ahmp.removeSmallest());
        PrintHeapDemo.printSimpleHeapDrawing(ahmp.toArray());
    }

    @Test
    public void changePriorityTest() {
        ArrayHeapMinPQ<Integer> ahmp = new ArrayHeapMinPQ<>();

        ahmp.add(5, 5);
        ahmp.add(4, 4);
        ahmp.add(3, 3);
        ahmp.add(2, 2);
        ahmp.add(1, 1);
        ahmp.add(0, 0);

        PrintHeapDemo.printSimpleHeapDrawing(ahmp.toArray());
        ahmp.changePriority(5, 0);
        ahmp.changePriority(0,5);
        PrintHeapDemo.printSimpleHeapDrawing(ahmp.toArray());
        assertEquals(5, (int)ahmp.removeSmallest());
        PrintHeapDemo.printSimpleHeapDrawing(ahmp.toArray());
        assertEquals(1, (int)ahmp.removeSmallest());
        PrintHeapDemo.printSimpleHeapDrawing(ahmp.toArray());
        //ahmp.removeSmallest();
        assertEquals(2, (int)ahmp.removeSmallest());
        //ahmp.toArray();
        PrintHeapDemo.printSimpleHeapDrawing(ahmp.toArray());
    }

    public static void ArrayHeapTime(){
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<Integer> ahmp = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10000000; i++) {
            ahmp.add(i, (double)i);
        }
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            int randItem = rand.nextInt(10000000);
            double randPriority = (double)rand.nextInt(100000000);
            ahmp.changePriority(randItem, randPriority);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed for ArrayMinPQ: " + (end - start) / 1000.0 + " seconds.");
    }
    public static void NaiveHeapTime(){
        long start = System.currentTimeMillis();
        NaiveMinPQ<Integer> nmp = new NaiveMinPQ<>();
        for (int i = 0; i < 10000000; i++) {
            nmp.add(i, (double)i);
        }
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            int randItem = rand.nextInt(10000000);
            double randPriority = (double)rand.nextInt(100000000);
            nmp.changePriority(randItem, randPriority);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed for Naive Min PQ: " + (end - start) / 1000.0 + " seconds.");
    }


    public static void main(String[] args) {
        ArrayHeapTime();
        NaiveHeapTime();

    }

}
