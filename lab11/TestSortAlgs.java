import edu.princeton.cs.algs4.Queue;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Random;

public class TestSortAlgs {

    private Queue<Integer> randomIntQueue(int N, int bound) {
        Random rd = new Random();
        Queue<Integer> qi = new Queue<Integer>();
        for (int i = 0; i < N; i++) {
            qi.enqueue(rd.nextInt(bound));
        }
        return qi;
    }


    @Test
    public void testQuickSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        Queue<String> sortedTas = QuickSort.quickSort(tas);
        assertTrue(isSorted(sortedTas));

        Queue<Integer> qi = randomIntQueue(100, 500);
        Queue<Integer> sortedQi = QuickSort.quickSort(qi);
        assertTrue(isSorted(sortedQi));
    }

    @Test
    public void testMergeSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        Queue<String> sortedTas = MergeSort.mergeSort(tas);
        assertTrue(isSorted(sortedTas));

        Queue<Integer> qi = randomIntQueue(100, 500);
        Queue<Integer> sortedQi = MergeSort.mergeSort(qi);
        assertTrue(isSorted(sortedQi));


    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
