package bearmaps.proj2c;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.List;
import java.util.HashMap;
import java.util.Stack;
import java.util.NoSuchElementException;
import java.util.Collections;

/**
 * A shortest paths solver using A*.
 * @param <Vertex> a vertex of a graph.
 * @author Robbie Netzke.
 * @source https://www.w3schools.com/java/java_try_catch.asp
 * @source https://www.geeksforgeeks.org/collections-reverse-java-examples/
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    /** Indicates the outcome of the solver. */
    private SolverOutcome outcome;
    /** The weight of the path to the solution. */
    private double solutionWeight;
    /** The path from start to end. */
    private List<Vertex> solution = new Stack<>();
    /** The time spent to find the solution. */
    private double timeSpent;
    /** Priority queue to search the best vertices first. */
    private DoubleMapPQ<Vertex> pq;
    /** The distance from start to a particular vertex. */
    private HashMap<Vertex, Double> dists;
    /** Keeps track of edges in one direction. */
    private HashMap<Vertex, Vertex> edges;
    /** Number of vertices visited. */
    private int numStates;

    /**
     * Solves the path finding problem using A*.
     * @param input the graph.
     * @param start the beginning vertex.
     * @param end the goal vertex to reach.
     * @param timeout maximum length of the algorithm.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        dists = new HashMap<>();
        edges = new HashMap<>();
        solutionWeight = 0;
        numStates = 0;
        dists.put(start, 0.0);
        edges.put(start, null);
        pq = new DoubleMapPQ<>();
        Stopwatch sw = new Stopwatch();
        pq.add(start, input.estimatedDistanceToGoal(start, end));
        try {
            while (!(pq.getSmallest().equals(end))) {
                Vertex p = pq.removeSmallest();
                if (sw.elapsedTime() > timeout) {
                    outcome = SolverOutcome.TIMEOUT;
                    break;
                }
                numStates++;
                for (WeightedEdge<Vertex> e : input.neighbors(p)) {
                    relax(e, input, end);
                }
            }
        } catch (NoSuchElementException e) {
            outcome = SolverOutcome.UNSOLVABLE;
        }
        if (outcome == SolverOutcome.TIMEOUT) {
            timeSpent = sw.elapsedTime();
        } else if (outcome == SolverOutcome.UNSOLVABLE) {
            timeSpent = sw.elapsedTime();
        } else {
            timeSpent = sw.elapsedTime();
            outcome = SolverOutcome.SOLVED;
            Stack stack = (Stack) solution;
            Vertex e = end;
            while (e != null) {
                stack.push(e);
                e = edges.get(e);
            }
            solution = stack;
            Collections.reverse(solution);
            solutionWeight = dists.get(end);
        }
    }

    /**
     * Relaxes an edge.
     * Asserts that the weight of the outgoing edge is
     * at least as good as connecting p to q.
     * @param e the edge to relax.
     * @param i the graph.
     * @param g the end goal.
     */
    private void relax(WeightedEdge<Vertex> e, AStarGraph<Vertex> i, Vertex g) {
        Vertex p = e.from();
        Vertex q = e.to();
        Double w = e.weight();
        Double h = i.estimatedDistanceToGoal(q, g);

        if (!(dists.containsKey(q))) {
            dists.put(q, dists.get(p) + w);
            edges.put(q, p);
            pq.add(q, dists.get(p) + w + h);
        }

        if (dists.get(p) + w < dists.get(q)) {
            dists.put(q, dists.get(p) + w);
            edges.put(q, p);
            if (pq.contains(q)) {
                pq.changePriority(q, dists.get(q) + h);
            } else {
                pq.add(q, dists.get(q) + h);
            }
        }
    }
    /**
     * Indicates the outcome of the solver.
     * @return outcome.
     * */
    public SolverOutcome outcome() {
        return outcome;
    }
    /**
     * @return solution.
     * The path from start to end. */
    public List<Vertex> solution() {
        return solution;
    }
    /**
     * @return weight.
     * The weight of the path to the solution. */
    public double solutionWeight() {
        return solutionWeight;
    }
    /**
     * @return number of visited states.
     * Number of vertices visited. */
    public int numStatesExplored() {
        return numStates;
    }
    /**
     * @return time of exploration.
     * The time spent to find the solution. */
    public double explorationTime() {
        return timeSpent;
    }
}
