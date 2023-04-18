package sdp.prac2.task2;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Main {
    public static void main(String[] args) {
        try {
            File inputFile = new File("data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("record");
            Gson gson = new Gson();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    JsonObject recordObj = new JsonObject();
                    // add selected fields to JSON object
                    if (args.length >= 1 && args[0].equals("name")) {
                        recordObj.addProperty("name", eElement.getElementsByTagName("name").item(0).getTextContent());
                    }
                    if (args.length >= 2 && args[1].equals("country")) {
                        recordObj.addProperty("country", eElement.getElementsByTagName("country").item(0).getTextContent());
                    }
                    // serialize JSON object to string and print
                    String jsonStr = gson.toJson(recordObj);
                    System.out.println(jsonStr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



