package main.edu.utfpr.cs.graph;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

import main.edu.princeton.cs.algs4.Graph;

public class GrafoGEXF {

    private Graph g;

    public GrafoGEXF(String path) {
        parseGraph(path);
    }

    public void parseGraph(String path) {
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
    
            doc.getDocumentElement().normalize();
    
            NodeList nodeList = doc.getElementsByTagName("node");
            NodeList edgeList = doc.getElementsByTagName("edge");

            g = new Graph(nodeList.getLength());
    
            for (int i = 0; i < edgeList.getLength(); i++) {
                Node node = edgeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoEdge = (Element) node;
                    String sourceStr = elementoEdge.getAttribute("source");
                    String targetStr = elementoEdge.getAttribute("target");
    
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

    public void displayGraph() {
        String graph = g.toString();
        System.out.println(graph);
    }

    public Graph getGraph() {
        return g;
    }
}