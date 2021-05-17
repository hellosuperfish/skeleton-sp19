package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(5);


        assertEquals(5, arb.fillCount());

        int deq1 = (int)arb.dequeue();
        assertEquals(1, deq1);
        //assertEquals(2, arb.peek());

        arb.dequeue();
        arb.dequeue();
        arb.dequeue();
        arb.dequeue();

        assertEquals(0, arb.fillCount());

        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(5);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(5);

        ArrayRingBuffer<Integer> brb = new ArrayRingBuffer<>(10);
        brb.enqueue(1);
        brb.enqueue(2);
        brb.enqueue(3);
        brb.enqueue(4);
        brb.enqueue(5);
        brb.enqueue(1);
        brb.enqueue(2);
        brb.enqueue(3);
        brb.enqueue(4);
        brb.enqueue(5);

        int[] arrayArb = new int[10];
        int[] expectedArray = {1, 2, 3, 4, 5, 1, 2, 3, 4, 5};
        int j = 0;
        for(int i : arb) {
            assertEquals(i,expectedArray[j]);
            j+=1;
        }

        assertEquals(10, arb.fillCount());
        assertTrue(arb.equals(arb));
        assertTrue(arb.equals(brb));


    }


}
