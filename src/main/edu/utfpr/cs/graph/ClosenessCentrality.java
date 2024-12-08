package main.edu.utfpr.cs.graph;

import java.util.BitSet;

import main.edu.princeton.cs.algs4.Graph;
import main.edu.princeton.cs.algs4.Queue;

public class ClosenessCentrality {

    final private Graph g;

    final private float[] cc_score; // cc[i] = closeness centrality score of vertex 'i'
    
    final private int[][] dist_matrix; // dist_matrix[s][t] = distance (value of shortest path) 
                                       // from vertex 's' to vertex 't'

    // helper class to keep track of vertex depth in BFS
    private static class Vertex {
        final private int v;
        final private int depth;

        public Vertex(int v, int depth) {
            this.v = v;
            this.depth = depth;
        }

        public int getV() {
            return v;
        }
    
        public int getDepth() {
            return depth;
        }
    }
    
    public ClosenessCentrality(Graph G) {
        g = G;
        cc_score = new float[G.V()];
        dist_matrix = new int[G.V()][G.V()];

        generateDistMatrix();
        calculateNormalizedCC();
    }

    private void generateDistMatrix() {
        for (int v = 0; v < g.V(); v++) {
            int[] distances = vertexDistances(v, g); // distances[i] = distance of vertex 'v' to vertex 'i'

            dist_matrix[v] = distances;
        }
    }

    private void calculateNormalizedCC() {
        for (int v = 0; v < g.V(); v++) {
            float sum = 0;
            
            for (int i = 0; i < g.V(); i++) {
                sum += dist_matrix[v][i];
            }

            cc_score[v] = (g.V() - 1) / sum;
        }
    }

    /**
     * Computes the shortest distances from a given vertex to all other vertices in the graph.
     * @param vertex the source vertex (must be greather than or equal to 0)
     * @param G a graph of type {@link main.edu.princeton.cs.algs4.Graph Graph}
     * @return an array of integers where each element at index 'i' represents the 
     *         shortest distance from the given vertex to vertex 'i'
     */
    public static int[] vertexDistances(int vertex, Graph G) {
        int[] distances = new int[G.V()];

        BitSet visited = new BitSet(G.V());
        visited.set(vertex);

        Queue<Vertex> queue = new Queue<>();
        queue.enqueue(new Vertex(vertex, 0));

        while (!queue.isEmpty()) {
            Vertex head = queue.dequeue();
            distances[head.getV()] = head.getDepth();

            for (int adjv : G.adj(head.getV())) {
                if (!visited.get(adjv)) {
                    visited.set(adjv);
                    queue.enqueue(new Vertex(adjv, head.getDepth() + 1));
                }
            }
        }

        return distances;
    }

    /**
     * Calculates the Normalized Closeness Centrality for each vertex in a graph.
     * @param G a graph of type {@link main.edu.princeton.cs.algs4.Graph Graph}
     * @return an array of float where each element at index 'i' represents the 
     *         closeness centrality score value of vertex 'i'
     */
    public static float[] calculateNormalizedCC(Graph G) {
        int dist_matrix[][] = new int[G.V()][G.V()];

        for (int v = 0; v < G.V(); v++) {
            int[] distances = vertexDistances(v, G); // distances[i] = distance of vertex 'v' to vertex 'i'

            dist_matrix[v] = distances;
        }

        float[] cc_score = new float[G.V()];

        for (int v = 0; v < G.V(); v++) {
            int sum = 0;
            
            for (int i = 0; i < G.V(); i++) {
                sum += dist_matrix[v][i];
            }

            cc_score[v] = (G.V() - 1) / sum;
        }

        return cc_score;
    }

    public float[] getCCScore() {
        return this.cc_score;
    }

    public int[][] getDistanceMatrix() {
        return this.dist_matrix;
    }

}