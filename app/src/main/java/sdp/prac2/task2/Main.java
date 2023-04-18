package sdp.prac2.task2;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("src/main/resources/Data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("record");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                String postalZip = element.getElementsByTagName("postalZip").item(0).getTextContent();
                String region = element.getElementsByTagName("region").item(0).getTextContent();
                String country = element.getElementsByTagName("country").item(0).getTextContent();
                String address = element.getElementsByTagName("address").item(0).getTextContent();
                String list = element.getElementsByTagName("list").item(0).getTextContent();

                System.out.println("Record " + (i+1) + ":");
                System.out.println("Name: " + name);
                System.out.println("Postal Zip: " + postalZip);
                System.out.println("Region: " + region);
                System.out.println("Country: " + country);
                System.out.println("Address: " + address);
                System.out.println("List: " + list);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

