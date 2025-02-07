package main.edu.utfpr.cs.graph;

import java.util.*;

import main.edu.princeton.cs.algs4.Graph;

public class BetweennessCentrality {

    private final Graph g;
    private final double[] bc_score; // bc[i] = betweenness centrality score of vertex 'i'

    public BetweennessCentrality(Graph G) {
        g = G;
        bc_score = new double[G.V()];
        calculateBetweennessCentrality();
    }

    /*
     Betweenness centrality is a measure of a node’s importance in a graph based on 
     the number of shortest paths that pass through it. It quantifies how often
     a node acts as a bridge in the shortest path between other nodes.
    */
    private void calculateBetweennessCentrality() {
        for (int s = 0; s < g.V(); s++) {
            Stack<Integer> stack = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();
            List<Integer>[] predecessors = new List[g.V()];
            double[] sigma = new double[g.V()];
            int[] distance = new int[g.V()];
            double[] dependency = new double[g.V()];

            Arrays.fill(distance, -1);
            for (int v = 0; v < g.V(); v++) {
                predecessors[v] = new ArrayList<>();
            }

            sigma[s] = 1.0;
            distance[s] = 0;
            queue.add(s);

            while (!queue.isEmpty()) {
                int v = queue.poll();
                stack.push(v);
                for (int w : g.adj(v)) {
                    if (distance[w] < 0) {
                        queue.add(w);
                        distance[w] = distance[v] + 1;
                    }
                    if (distance[w] == distance[v] + 1) {
                        sigma[w] += sigma[v];
                        predecessors[w].add(v);
                    }
                }
            }

            while (!stack.isEmpty()) {
                int w = stack.pop();
                for (int v : predecessors[w]) {
                    dependency[v] += (sigma[v] / sigma[w]) * (1 + dependency[w]);
                }
                if (w != s) {
                    bc_score[w] += dependency[w];
                }
            }
        }

        for (int v = 0; v < g.V(); v++) {
            bc_score[v] /= 2.0;
        }
    }

    public double[] getBCScore() {
        return this.bc_score;
    }

    public static double[] calculateBetweennessCentrality(Graph G) {
        return new BetweennessCentrality(G).getBCScore();
    }
}
