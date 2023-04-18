package sdp.prac2.task2;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            File inputFile = new File("data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("record");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    String name = "";
                    String country = "";
                    // check if name and country fields are selected
                    if (args.length >= 1 && args[0].equals("name")) {
                        name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    }
                    if (args.length >= 2 && args[1].equals("country")) {
                        country = eElement.getElementsByTagName("country").item(0).getTextContent();
                    }
                    // print selected fields
                    System.out.println("Record " + (i+1) + ":");
                    if (!name.isEmpty()) {
                        System.out.println("Name: " + name);
                    }
                    if (!country.isEmpty()) {
                        System.out.println("Country: " + country);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


