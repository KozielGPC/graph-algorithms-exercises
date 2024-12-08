package main.edu.utfpr.cs.graph;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

import main.edu.princeton.cs.algs4.Graph;

public class GrafoGEXF {

    // Estrutura para armazenar o grafo (não dirigido)
    private Map<Integer, List<Integer>> grafo;

    public GrafoGEXF() {
        grafo = new HashMap<>();
    }

    // Método para ler e parsear o arquivo GEXF
    public void lerGEXF(String arquivo) {
    try {
        File fXmlFile = new File(arquivo);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        // Normalizar o documento XML
        doc.getDocumentElement().normalize();

        // Obter os nós
        NodeList nodeList = doc.getElementsByTagName("node");

        // Adicionar os nós ao grafo
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoNode = (Element) node;
                String idStr = elementoNode.getAttribute("id");

                // Tratar o ID como double
                try {
                    double id = Double.parseDouble(idStr);
                    int idInt = (int) Math.floor(id); // Truncar a parte decimal
                    grafo.putIfAbsent(idInt, new ArrayList<>());
                } catch (NumberFormatException e) {
                    System.err.println("ID inválido: " + idStr);
                }
            }
        }

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

                    // Truncar a parte decimal e adicionar as arestas
                    grafo.get((int) Math.floor(source)).add((int) Math.floor(target));
                    grafo.get((int) Math.floor(target)).add((int) Math.floor(source));
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
        for (Map.Entry<Integer, List<Integer>> entry : grafo.entrySet()) {
            System.out.println("Nó " + entry.getKey() + ": " + entry.getValue());
        }
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