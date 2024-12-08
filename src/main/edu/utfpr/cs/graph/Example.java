package main.edu.utfpr.cs.graph;

public class Example {
    public static void main(String[] args) {
        // Dados de entrada no formato *.gexf
        String filePath = "data/LesMiserables.gexf";
        GrafoGEXF grafoGEXF = new GrafoGEXF(filePath);
        grafoGEXF.exibirGrafo();

        // Eccentricity
        System.out.println("\n\nEccentricity");
        Eccentricity eccentricity = new Eccentricity(grafoGEXF.getGraph());
        int[] eccentricities = eccentricity.getEccentricity();
        for (int i = 0; i < grafoGEXF.getGraph().V(); i++) {
            System.out.println(i + ": " + eccentricities[i]);
        }

        // Normalized Closeness Centrality
        System.out.println("\n\nNormalized Closeness Centrality");
        ClosenessCentrality cc = new ClosenessCentrality(grafoGEXF.getGraph());
        float[] tmp = cc.getCCScore();
        for (int i = 0; i < grafoGEXF.getGraph().V(); i++) {
            System.out.println(i + ": " + tmp[i]);
        }

    }
}