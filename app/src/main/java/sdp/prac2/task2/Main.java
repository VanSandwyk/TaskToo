package sdp.prac2.task2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            RecordHandler handler = new RecordHandler(fields);

            saxParser.parse(file, handler);

        } catch (ParserConfigurationException | SAXException | IOException e) {
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

class RecordHandler extends DefaultHandler {
    private String[] fields;
    private Gson gson;
    private JsonObject jsonObject;
    private StringBuilder fieldValue;
    private boolean isRecord;

    public RecordHandler(String[] fields) {
        this.fields = fields;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.jsonObject = new JsonObject();
        this.fieldValue = new StringBuilder();
        this.isRecord = false;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("record")) {
            this.isRecord = true;
        } else if (isRecord && isValidField(qName)) {
            this.fieldValue.setLength(0);
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isRecord && this.fieldValue != null) {
            this.fieldValue.append(ch, start, length);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("record")) {
            String json = gson.toJson(jsonObject);
            System.out.println(json);
            this.jsonObject = new JsonObject();
            this.isRecord = false;
        } else if (isRecord && isValidField(qName)) {
            String value = this.fieldValue.toString().trim();
            this.jsonObject.addProperty(qName, value);
        }
    }

    private boolean isValidField(String field) {
        for (String validField : fields) {
            if (validField.equalsIgnoreCase(field)) {
                return true;
            }
        }

        return false;
    }
}



