package main.edu.utfpr.cs.graph;

import java.util.BitSet;

import main.edu.princeton.cs.algs4.Graph;
import main.edu.princeton.cs.algs4.In;
import main.edu.princeton.cs.algs4.Queue;
import main.edu.princeton.cs.algs4.StdOut;


public class Eccentricity {

    final private Graph g;

    final private int[] eccentricity;    // eccentricity[i] = eccentricity of vertex `i`

    // helper class to keep track of vertex depth in BFS
    private static class Vertex {
        private int v;
        private int depth;

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

    public Eccentricity(Graph G) {
        g = G;
        eccentricity = new int[G.V()];
        
        buildGraphEccentricity();
    }

    // calculate eccentricity for every vertex on graph g
    private void buildGraphEccentricity() {
        for (int i = 0; i < g.V(); i++) {
            eccentricity[i] = vertexEccentricity(i, g);
        }
    }

    /**
     * Returns max reachable distance given source vertex 'v'
     * on a unweighted undirected graph.
     * @param v the source vertex (must be greather than or equal to 0)
     * @param G a graph of type {@link main.edu.princeton.cs.algs4.Graph Graph}
     * @return the max reachable distance from source vertex 'v'
     */
    public static int vertexEccentricity(int v, Graph G) {
        BitSet visited = new BitSet(G.V());
        visited.set(v);
        Queue<Vertex> queue = new Queue<Vertex>();
        queue.enqueue(new Vertex(v, 0));
        int maxDepth = 0;
        
        while (!queue.isEmpty()) {
            Vertex head = queue.dequeue();
            if (head.getDepth() > maxDepth) {
                maxDepth = head.getDepth();
            }
            for (int adjv : G.adj(head.getV())) {
                if (!visited.get(adjv)) {
                    visited.set(adjv);
                    queue.enqueue(new Vertex(adjv, head.getDepth() + 1));
                }
            }
        }

        return maxDepth;
    }

    public int[] getEccentricity() {
        return this.eccentricity;
    }

    public static void main(String[] args) {
        In in = new In("tinyG.txt");

        Graph G = new Graph(in);
        Eccentricity e = new Eccentricity(G);

        StdOut.println(G);

        int[] tmp = e.getEccentricity();
        for (int i = 0; i < G.V(); i++) {
            System.out.println(i + ": " + tmp[i]);
        }
    }
}
