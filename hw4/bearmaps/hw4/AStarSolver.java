package bearmaps.hw4;
import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private int stateExplored;
    private double timeSpent;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;



    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        stateExplored = 0;
        DoubleMapPQ<Vertex> fringe = new DoubleMapPQ<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        distTo.put(start, 0.0);
        fringe.add(start, input.estimatedDistanceToGoal(start, end));
        while (fringe.size() != 0 && sw.elapsedTime() <= timeout && !(distTo.containsKey(end))) {
            Vertex p = fringe.removeSmallest();
            stateExplored += 1;
            List<WeightedEdge<Vertex>> neighbors = input.neighbors(p);
            for (WeightedEdge<Vertex> w : neighbors) {
                Vertex from = w.from();
                Vertex to = w.to();
                double priority = distTo.get(from) + w.weight() + input.estimatedDistanceToGoal(to, end);
                double dist = distTo.get(from) + w.weight();
                if (!distTo.containsKey(to)) {
                    edgeTo.put(to, from);
                    distTo.put(to, dist);
                    fringe.add(to, priority);
                } else if (distTo.get(to) > priority) {
                    edgeTo.put(to, from);
                    distTo.put(to, priority);
                    fringe.changePriority(to, priority);
                }
            }
        }
        if (distTo.containsKey(end)) {
            timeSpent = sw.elapsedTime();
            outcome = SolverOutcome.SOLVED;
            solution = new ArrayList<>();
            solution.add(end);
            Vertex v = end;
            while (!v.equals(start)) {
                v = edgeTo.get(v);
                solution.add(v);
            }
            Collections.reverse(solution);
            solutionWeight = distTo.get(end);
        } else if (sw.elapsedTime() > timeout) {
            outcome = SolverOutcome.TIMEOUT;
        } else {
            outcome = SolverOutcome.UNSOLVABLE;
        }
    }

    public SolverOutcome outcome() {
        return outcome;
    }

    public List<Vertex> solution() {
        return solution;
    }

    public double solutionWeight() {
        return solutionWeight;
    }

    public int numStatesExplored() {
        return stateExplored;
    }

    public double explorationTime() {
        return timeSpent;
    }
}
