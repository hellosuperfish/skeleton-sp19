import java.util.*;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    List<Bear> solvedBears = new ArrayList<>();
    List<Bed> solvedBeds = new ArrayList<>();
    List<Pair<Bear, Bed>> pairs;


    // O(N2)
    /*
    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        // TODO: Fix me.
        for (Bear bear : bears) {
            for (Bed bed : beds) {
                if (bear.getSize() == bed.getSize()) {
                    solvedBears.add(bear);
                    solvedBeds.add(bed);
                }
            }
        }
    }*/

    //Didn't work because priorityQueue requires comparison of bear to bear and bed to bed, Nice try!
    /*
    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        PriorityQueue<Bear> pqBear = new PriorityQueue<>();
        PriorityQueue<Bed> pqBed = new PriorityQueue<>();

        for (Bear bear : bears) {
            pqBear.add(bear);
        }

        for (Bed bed : beds) {
            pqBed.add(bed);
        }

        while((!pqBear.isEmpty()) && (!pqBed.isEmpty())) {
            if (pqBear.peek().getSize() == pqBear.peek().getSize()) {
                pqBear.add(pqBear.poll());
                pqBed.add(pqBed.poll());
            } else if (pqBear.peek().getSize() < pqBear.peek().getSize()) {
                pqBear.poll();
            } else {
                pqBed.poll();
            }
        }

    }*/

    /* A smart? solution
    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        this.solvedBears = bears;

        HashMap<Integer, Bed> bedMap = new HashMap<>();
        for (Bed b : beds) {
            bedMap.put(b.getSize(), b);
        }

        for (Bear bear : bears) {
            solvedBeds.add(bedMap.get(bear.getSize()));
        }

    }*/

    /** Official Solution: Our solution will modify quicksort. Let’s begin by choosing a pivot from the Bears
     list. To avoid quicksort’s worst case behavior on a sorted array, we will choose a
     random Bear as the pivot. Next we will partition the Beds into three groups —
     those less than, equal to, and greater than the pivot Bear. Next, we will select
     a pivot from the Bears list. This is very important — our pivot Bed will be the
     Bed that is equal to the pivot Bear. Given that the Beds and Bears have unique
     sizes, we know that exactly one Bed will be equal to the pivot Bear. Next we will
     partition the Beds into three groups — those less than, equal to, and greater than
     the pivot Bed.
     Next, we will ”match” the pivot Bear with the pivot Bed by adding them to the
     Bears and Beds lists at the same index, which is as easy as just adding to the end.
     Finally, in the same fashion as quicksort, we will have two recursive calls. The first
     recursive call will contain the Beds and Bears that are less than their respective
     pivots. The second recursive call will contain the Beds and Bears that are greater
     than their respective pivots.*/

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        pairs = new ArrayList<Pair<Bear, Bed>>();
        getPairs(bears, beds, pairs);
        for (Pair p : pairs) {
            solvedBears.add((Bear)p.first());
            solvedBeds.add((Bed)p.second());
        }

    }

    private void bedPartition(List<Bed> beds, Bear pivot, List<Bed> small, List<Bed> right, List<Bed> big) {
        for (Bed bed : beds) {
            if (bed.getSize() < pivot.getSize()) {
                small.add(bed);
            } else if (bed.getSize() == pivot.getSize()) {
                right.add(bed);
            } else {
                big.add(bed);
            }
        }
    }

    private void bearPartition(List<Bear> bears, Bed pivot, List<Bear> small, List<Bear> right, List<Bear> big) {
        for (Bear bear : bears) {
            if (bear.getSize() < pivot.getSize()) {
                small.add(bear);
            } else if (bear.getSize() == pivot.getSize()) {
                right.add(bear);
            } else {
                big.add(bear);
            }
        }
    }

    private void getPairs (List<Bear> bears, List<Bed> beds, List<Pair<Bear, Bed>> pair) {


        if (bears.size() < 1 || beds.size() < 1) {
            return;
        }else if (bears.size() == 1 && beds.size() == 1) {
            pair.add(new Pair(bears.get(0), beds.get(0)));
        } else {
            List<Bed> smallBed = new ArrayList<>();
            List<Bed> rightBed = new ArrayList<>();
            List<Bed> bigBed = new ArrayList<>();
            List<Bear> smallBear = new ArrayList<>();
            List<Bear> rightBear = new ArrayList<>();
            List<Bear> bigBear = new ArrayList<>();
            Bear pivot = getRandomBear(bears);
            bedPartition(beds, pivot, smallBed, rightBed, bigBed);
            pair.add(new Pair(pivot, rightBed.get(0)));
            bearPartition(bears, rightBed.get(0), smallBear, rightBear, bigBear);
            getPairs(smallBear, smallBed, pair);
            getPairs(bigBear, bigBed, pair);
        }


    }

    private Bear getRandomBear(List<Bear> bears) {
        int pivotIndex = (int) (Math.random() * bears.size());
        Bear pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Bear bear : bears) {
            if (pivotIndex == 0) {
                pivot = bear;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }



    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        // TODO: Fix me.

        return solvedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        // TODO: Fix me.
        return solvedBeds;
    }
}
