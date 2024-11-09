package main.edu.utfpr.cs.graph;

import main.edu.princeton.cs.algs4.Graph;
import main.edu.princeton.cs.algs4.Queue;
import main.edu.princeton.cs.algs4.In;
import main.edu.princeton.cs.algs4.StdOut;

import java.util.BitSet;

public class Eccentricity {

    private int[] eccentricity;    // eccentricity[i] = eccentricity of vertex `i`

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
        this.eccentricity = new int[G.V()];
        calculateEccentricity(G);
    }

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

    private void calculateEccentricity(Graph G) {
        for (int i = 0; i < G.V(); i++) {
            eccentricity[i] = vertexEccentricity(i, G);
        }
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
