package main.edu.utfpr.cs.graph;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

import main.edu.princeton.cs.algs4.Graph;

public class GrafoGEXF {

    // Estrutura para armazenar o grafo (não dirigido)
    private Graph g;

    public GrafoGEXF(String path) {
        parseGraph(path);
    }

    // Método para ler e parsear o arquivo GEXF
    public void parseGraph(String path) {
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
    
            // Normalizar o documento XML
            doc.getDocumentElement().normalize();
    
            // Obter os nós
            NodeList nodeList = doc.getElementsByTagName("node");

            g = new Graph(nodeList.getLength());
    
            // Obter as arestas
            NodeList edgeList = doc.getElementsByTagName("edge");
    
            // Adicionar as arestas ao grafo
            for (int i = 0; i < edgeList.getLength(); i++) {
                Node node = edgeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoEdge = (Element) node;
                    String sourceStr = elementoEdge.getAttribute("source");
                    String targetStr = elementoEdge.getAttribute("target");
    
                    // Tratar os source e target como double
                    try {
                        double source = Double.parseDouble(sourceStr);
                        double target = Double.parseDouble(targetStr);

                        g.addEdge((int) source, (int)target);
                    } catch (NumberFormatException e) {
                        System.err.println("Aresta inválida: " + sourceStr + " -> " + targetStr);
                    }
                }
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Método para exibir o grafo
    public void exibirGrafo() {
        String graph = g.toString();
        System.out.println(graph);
    }

    public static void main(String[] args) {
        GrafoGEXF grafoGEXF = new GrafoGEXF();
       
        // Caminho para o arquivo GEXF
        String caminhoArquivo = "caminho aqui";
       
        // Ler o arquivo GEXF e construir o grafo
        grafoGEXF.lerGEXF(caminhoArquivo);
       
        // Exibir o grafo
        grafoGEXF.exibirGrafo();
    }
}