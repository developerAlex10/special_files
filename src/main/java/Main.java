import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileCsv = "data.csv";
        String fileJson_1 = "data.json";
        String fileXml = "data.xml";
        String fileJson_2 = "data2.json";
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};

        createFileCsv(fileCsv);
        readFile(fileCsv);

        List<Employee> listCsv = parseCSV(columnMapping, fileCsv);
        String json_1 = listToJson(listCsv);
        writeString(json_1, fileJson_1);

        List<Employee> listXml = parseXML(fileXml);
        String json_2 = listToJson(listXml);
        writeString(json_2, fileJson_2);

        String json_data_1 = readString(fileJson_1);
        List<Employee> listJson_1 = jsonToList(json_data_1);
        printJson(listJson_1);

        String json_data_2 = readString(fileJson_1);
        List<Employee> listJson_2 = jsonToList(json_data_2);
        printJson(listJson_2);
    }

    private static void createFileCsv(String fileName) {
        String[] employee_1 = "1,John,Smith,USA,25".split(",");
        String[] employee_2 = "2,Inav,Petrov,RU,23".split(",");
        List<String[]> employees = new ArrayList<>();
        employees.add(employee_1);
        employees.add(employee_2);
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            employees.forEach(writer::writeNext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFile(String fileName) {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                System.out.println(Arrays.toString(nextLine));
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            return new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build()
                    .parse();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<Employee> parseXML(String fileName) {
        List<Employee> employees = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));

            Node root = document.getDocumentElement();
            NodeList nodeList = root.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element element = (Element) node;
                    long id = Long.parseLong(getElementValue(element, "id"));
                    String firstName = getElementValue(element, "firstName");
                    String lastName = getElementValue(element, "lastName");
                    String country = getElementValue(element, "country");
                    int age = Integer.parseInt(getElementValue(element, "age"));

                    employees.add(new Employee(id, firstName, lastName, country, age));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    private static String getElementValue(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private static String readString(String fileName) {
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    private static List<Employee> jsonToList(String json) {
        List<Employee> list = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(json);
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                Employee employee = gson.fromJson(jsonObject.toString(), Employee.class);
                list.add(employee);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String listToJson(List<Employee> list) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        return gson.toJson(list, listType);
    }

    private static void writeString(String json, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(json);
            System.out.println("JSON успешно записан в файл " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printJson(List<Employee> employees) {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
}
