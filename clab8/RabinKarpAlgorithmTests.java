import org.junit.Test;
import static org.junit.Assert.*;

public class RabinKarpAlgorithmTests {
    @Test
    public void basic() {
        String input = "hello";
        String pattern = "ell";
        RabinKarpAlgorithm.rabinKarp(input, pattern);
        assertEquals(1, RabinKarpAlgorithm.rabinKarp(input, pattern));
    }

    @Test
    public void basic2() {
        String input = "hello world";
        String pattern = "orl";
        RabinKarpAlgorithm.rabinKarp(input, pattern);
        assertEquals(7, RabinKarpAlgorithm.rabinKarp(input, pattern));
    }

    @Test
    public void testLong() {
        String input = "Resizable-array implementation of the Deque interface. Array deques have no " +
                "capacity restrictions; they grow as necessary to support usage. They are not thread-safe;" +
                " in the absence of external synchronization, they do not support concurrent access by " +
                "multiple threads. Null elements are prohibited. This class is likely to be faster than " +
                "Stack when used as a stack, and faster than LinkedList when used as a queue.";
        String pattern = "in the absence of";
        int ans = input.indexOf("in the absence of");
        assertEquals(ans, RabinKarpAlgorithm.rabinKarp(input, pattern));
    }


}
