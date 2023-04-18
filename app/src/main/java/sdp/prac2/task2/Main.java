package sdp.prac2.task2;

import java.io.File;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter comma-separated list of fields to output: ");
            String[] fields = scanner.nextLine().trim().split(",");

            for (String field : fields) {
                if (!isValidField(field.trim())) {
                    throw new IllegalArgumentException("Invalid field: " + field);
                }
            }

            File file = new File("data.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("record");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    JsonObject jsonObject = new JsonObject();

                    for (String field : fields) {
                        String value = element.getElementsByTagName(field).item(0).getTextContent().trim();
                        jsonObject.addProperty(field, value);
                    }

                    String json = gson.toJson(jsonObject);
                    System.out.println(json);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static boolean isValidField(String field) {
        String[] validFields = {"name", "postalZip", "region", "country", "address", "list"};

        for (String validField : validFields) {
            if (validField.equalsIgnoreCase(field)) {
                return true;
            }
        }

        return false;
    }
}




