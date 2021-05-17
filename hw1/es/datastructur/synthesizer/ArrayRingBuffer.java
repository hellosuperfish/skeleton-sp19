package es.datastructur.synthesizer;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return this.fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */

    // helper function for index to move to next, especially helpful when needing to wrap around
    private int plusOne(int index) {
        int newIndex;
        if (index < capacity() - 1) {
            newIndex = index + 1;
        } else {
            newIndex = 0;
        }
        return newIndex;
    }



    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if (fillCount < capacity()) {

            rb[last] = x;
            fillCount += 1;
            last = plusOne(last);

        } else {
            throw new RuntimeException("Ring Buffer overflow");
        }

    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        T deleted;
        if (fillCount > 0) {
            deleted = rb[first];
            rb[first] = null;
            fillCount -= 1;
            first = plusOne(first);
        } else {
            throw new RuntimeException("Can't dequeue from empty queue");
        }

        return deleted;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.


        return rb[first];
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayRingIterator();
    }

    private class ArrayRingIterator implements Iterator<T> {
        private int position;

        public ArrayRingIterator() {
            position = 0;
        }

        public boolean hasNext() {
            return position < fillCount();
        }

        public T next() {
            T returnItem = rb[position];
            position += 1;
            return returnItem;
        }

    }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Unless otherwise specified by the implementing class,
     * actions are performed in the order of iteration (if an iteration order
     * is specified).  Exceptions thrown by the action are relayed to the
     * caller.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        BoundedQueue.super.forEach(action);
    }

    /**
     * Creates a {@link Spliterator} over the elements described by this
     * {@code Iterable}.
     *
     * @return a {@code Spliterator} over the elements described by this
     * {@code Iterable}.
     * @implSpec The default implementation creates an
     * <em><a href="Spliterator.html#binding">early-binding</a></em>
     * spliterator from the iterable's {@code Iterator}.  The spliterator
     * inherits the <em>fail-fast</em> properties of the iterable's iterator.
     * @implNote The default implementation should usually be overridden.  The
     * spliterator returned by the default implementation has poor splitting
     * capabilities, is unsized, and does not report any spliterator
     * characteristics. Implementing classes can nearly always provide a
     * better implementation.
     * @since 1.8
     */
    @Override
    public Spliterator<T> spliterator() {
        return BoundedQueue.super.spliterator();
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.

    private ArrayRingBuffer<T> copy(ArrayRingBuffer<T> original) {
        ArrayRingBuffer<T> newCopy = new ArrayRingBuffer<>(original.capacity());
        for (T item: original) {
            newCopy.enqueue(item);
        }
        return newCopy;
    }

    @Override
    public boolean equals(Object o) {
        ArrayRingBuffer<T> otherObject= copy((ArrayRingBuffer<T>)o);
        ArrayRingBuffer<T> thisObject = copy(this);

        if (thisObject == o) {
            return true;
        } else if (thisObject.fillCount() != otherObject.fillCount()) {
            return false;
        } else if (thisObject.capacity() != otherObject.capacity()) {
            return false;
        }
        for (T item: thisObject ) {
            if (thisObject.dequeue() != otherObject.dequeue()) {
                return false;
            }
        }
        return true;
    }
}
    // TODO: Remove all comments that say TODO when you're done.
