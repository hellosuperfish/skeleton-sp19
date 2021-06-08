import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {

    Hashtable<Integer, Integer> timePassengers;
    Hashtable<Integer, Integer> timeTotalPassengers;
    PriorityQueue<Integer> pqTimes;
    PriorityQueue<Integer> pqPassengers;

    //Comparator function using lambda, can also write a class as
    //public static class passengerComparator implements Comparator<Flight> {
    //  public int compare(Flight f1, Flight f2) {
    //      return f1.passenger - f2.passengers;
    //  }
    // }
    Comparator<Integer> passengerComparator = (Integer p1, Integer p2) -> (p2 - p1);

    public FlightSolver(ArrayList<Flight> flights) {
        /* FIX ME */
        timePassengers = new Hashtable<>();
        pqTimes = new PriorityQueue<>(flights.size() * 2);
        for (Flight f: flights) {
            timePassengers.put(f.startTime, f.passengers);
            timePassengers.put(f.endTime, (0 - f.passengers));
            pqTimes.add(f.startTime);
            pqTimes.add(f.endTime);
        }

    }

    public int solve() {
        /* FIX ME */
        int total = 0;
        timeTotalPassengers = new Hashtable<>();
        while (pqTimes.size() > 0) {
            int time = pqTimes.poll();
            total += timePassengers.get(time);
            timeTotalPassengers.put(time, total);

        }

        pqPassengers = new PriorityQueue<>(11, passengerComparator);
        for (int t : timeTotalPassengers.keySet()) {
            pqPassengers.add(timeTotalPassengers.get(t));
        }

        return pqPassengers.peek();
    }

}
