package bearmaps.proj2ab;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T>{

    private ArrayHeapNode[] minHeap;
    private HashMap<T, Integer> itemIndex;
    private int INITIALSIZE = 20;
    private int size;
    private double FILLINGRATE = 0.75;


    public ArrayHeapMinPQ() {
        minHeap = new ArrayHeapMinPQ.ArrayHeapNode[INITIALSIZE];
        itemIndex = new HashMap<>();
        size = 0;
    }

    @Override
    public void add(T item, double priority) {
        if (this.contains(item)) {
            throw new IllegalArgumentException("Can't add same item twice");
        }
        if (size / (double)minHeap.length >= FILLINGRATE) {
            resizeUp();
        }

        size += 1;
        minHeap[size] = new ArrayHeapNode(item, priority);
        itemIndex.put(item, size);
        swimUp(minHeap[size], size);


    }

    private void resizeUp() {
        ArrayHeapNode[] newHeap = new ArrayHeapMinPQ.ArrayHeapNode[minHeap.length * 2];
        for (int i = 0; i < minHeap.length; i++) {
            newHeap[i] = minHeap[i];
        }
        minHeap = newHeap;
    }

    private void swimUp(ArrayHeapNode node, int currInd) {
        if (currInd != 1 && node.compareTo(parentOfIndex(currInd)) < 0) {
            swap(node, parentOfIndex(currInd));
            swimUp(parentOfIndex(currInd), currInd/2);
        }
    }

    private ArrayHeapNode parentOfIndex(int currInd) {
        return minHeap[currInd/2];
    }

    private void swap (ArrayHeapNode aNode, ArrayHeapNode bNode) {
        T tempItem = aNode.getItem();
        double tempPriority = aNode.getPriority();
        int tempIndex = itemIndex.get(tempItem);
        itemIndex.replace(tempItem, itemIndex.get(bNode.item));
        itemIndex.replace(bNode.item, tempIndex);
        aNode.item = bNode.getItem();
        aNode.priority = bNode.getPriority();
        bNode.item = tempItem;
        bNode.priority = tempPriority;

    }

    @Override
    public boolean contains(T item) {
        return itemIndex.containsKey(item);
    }


    @Override
    public T getSmallest() {
        if (size() == 0) {
            return null;
        }
        return minHeap[1].getItem();
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            return null;
        }
        T small = minHeap[1].getItem();
        minHeap[1] = minHeap[size];
        minHeap[size] = null;
        swimDown(minHeap[1], 1);
        size -= 1;
        itemIndex.remove(small);
        if (minHeap.length > INITIALSIZE && (size() / (double)minHeap.length) < 0.25) {
            resizeDown();
        }
        return small;
    }

    private void resizeDown() {
        ArrayHeapNode[] newHeap = new ArrayHeapMinPQ.ArrayHeapNode[(int)(minHeap.length / 2)];
        for (int i = 0; i < newHeap.length; i++) {
            newHeap[i] = minHeap[i];
        }
        minHeap = newHeap;
    }

    private void swimDown(ArrayHeapNode node, int currInd) {

        if (minHeap[currInd * 2] != null && node.compareTo(smallerChild(currInd)) > 0) {
            ArrayHeapNode smaller = smallerChild(currInd);
            int indSmaller = itemIndex.get(smaller.item);
            swap(node, smaller);
            swimDown(smaller, indSmaller);
        }
    }

    private ArrayHeapNode smallerChild(int currInd) {
        if (minHeap[currInd *2 + 1] == null) {
            return minHeap[currInd * 2];
        }
        if (minHeap[currInd * 2].compareTo(minHeap[currInd * 2 + 1]) < 0) {
            return minHeap[currInd * 2];
        }
        return minHeap[currInd * 2 + 1];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (contains(item) == false) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        int index = itemIndex.get(item);
        double oldPriority = minHeap[index].getPriority();
        if (priority == oldPriority) {
            return;
        }
        minHeap[index].priority = priority;
        if (priority < oldPriority) {
            swimUp(minHeap[index],index);
        } else {
            swimDown(minHeap[index], index);
        }

    }

    public T[] toArray() {
        T[] TArray = (T[]) new Object[size()+1];
        for (int i = 1; i <= size(); i++) {
            if (minHeap[i] != null) {
                TArray[i] = minHeap[i].item;
            }
        }
        return TArray;
    }

    private class ArrayHeapNode implements Comparable<ArrayHeapNode> {

        private T item;
        private double priority;

        ArrayHeapNode (T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        T getItem() {
            return this.item;
        }

        double getPriority() {
            return this.priority;
        }

        void setPriority(double newPriority) {
            this.priority = newPriority;
        }

        @Override
        public int compareTo(ArrayHeapNode o) {
            if (o == null) {
                return -1;
            } else {
                return Double.compare(this.getPriority(), o.getPriority());
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null) {
                return false;
            }
            if (this.getClass() != o.getClass()) {
                return false;
            }
        return this.getItem().equals(((ArrayHeapNode) o).getItem());
        }

        @Override
        public int hashCode() {
            return this.getItem().hashCode();
        }

    }
}
