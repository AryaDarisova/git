package RPIS_61.Arisova.wdad.learn.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class xmlTask {
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document document;
    {
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(new File("src\\RPIS_61\\Arisova\\wdad\\learn\\xml\\firstRestaurant"));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /*
    возвращающий суммарную выручку заданного официанта в заданный день
     */

    public int earningsTotal(String officiantSecondName, Calendar calendar){
        int earningsTotal = 0;
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH));
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        try {
            Element restaurant = document.getDocumentElement();
            NodeList dates = restaurant.getElementsByTagName("date");
            for (int i = 0; i < dates.getLength(); i++) {
                Element date = (Element) dates.item(i);
                if (date.getAttribute("day").equals(day)
                & date.getAttribute("month").equals(month)
                    & date.getAttribute("year").equals(year)) {
                    NodeList orders = date.getElementsByTagName("order");
                    for (int j = 0; j < orders.getLength(); j++) {
                        Element order = (Element) orders.item(j);
                        NodeList officiants = order.getElementsByTagName("officiant");
                        for (int k = 0; k < officiants.getLength(); k++) {
                            Element officiant = (Element) officiants.item(k);
                            Element totalcost = (Element) order.getElementsByTagName("totalcost").item(0);
                            if (officiant.getAttribute("secondName").equals(officiantSecondName)) {
                                earningsTotal += new Integer(totalcost.getTextContent());
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return earningsTotal;
    }

    /*
    удаляет информацию по заданному дню
     */

    public void removeDay(Calendar calendar) {
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH));
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        try {
            Element restaurant = document.getDocumentElement();
            NodeList dates = restaurant.getElementsByTagName("date");
            for (int i = 0; i < dates.getLength(); i++) {
                Element date = (Element) dates.item(i);
                if (date.getAttribute("day").equals(day)
                        & date.getAttribute("month").equals(month)
                        & date.getAttribute("year").equals(year)) {
                    restaurant.removeChild(date);
                    writeXml();
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*
    изменяющий имя и фамилию официанта во всех днях и записывающий результат в этот же xml-файл
     */

    public void changeOfficiantName(String oldFirstName, String oldSecondName, String newFirstName, String newSecondName) {
        try {
            Element restaurant = document.getDocumentElement();
            NodeList dates = restaurant.getElementsByTagName("date");
            for (int i = 0; i < dates.getLength(); i++) {
                Element date = (Element) dates.item(i);
                    NodeList orders = date.getElementsByTagName("order");
                    for (int j = 0; j < orders.getLength(); j++) {
                        Element order = (Element) orders.item(j);
                        NodeList officiants = order.getElementsByTagName("officiant");
                        for (int k = 0; k < officiants.getLength(); k++) {
                            Element officiant = (Element) officiants.item(k);
                            if (officiant.getAttribute("firstName").equals(oldFirstName) &
                                    officiant.getAttribute("secondName").equals(oldSecondName)) {
                                officiant.setAttribute("firstName", newFirstName);
                                officiant.setAttribute("secondName", newSecondName);
                                officiant.setTextContent(newFirstName.substring(0, 1).toUpperCase() + newFirstName.substring(1)
                                        + " " + newSecondName.substring(0, 1).toUpperCase() + newSecondName.substring(1));
                                writeXml();
                            }
                        }
                    }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Если при парсинге документа, тег <totalcost>
    отсутствует или имеет не корректное значение –
    необходимо его добавить или исправить значение
    соответственно
     */

    public void checkTotalCost() {
        try {
            Element restaurant = document.getDocumentElement();
            NodeList dates = restaurant.getElementsByTagName("date");
            for (int i = 0; i < dates.getLength(); i++) {
                Element date = (Element) dates.item(i);
                NodeList orders = date.getElementsByTagName("order");
                for (int j = 0; j < orders.getLength(); j++) {
                    Element order = (Element) orders.item(j);
                    if (order.getElementsByTagName("totalcost").getLength() == 0) {
                        Element totalcost = document.createElement("totalcost");
                        order.appendChild(totalcost);
                        totalcost.setTextContent("0");
                        writeXml();
                    }
                    NodeList totalcosts = order.getElementsByTagName("totalcost");
                    for (int k = 0; k < totalcosts.getLength(); k++) {
                        Element totalcost = (Element) totalcosts.item(k);
                        if (!totalcost.getTextContent().matches("[-+]?\\d+")) {
                            totalcost.setTextContent("0");
                            writeXml();
                        }
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void writeXml() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("src\\RPIS_61\\Arisova\\wdad\\learn\\xml\\firstRestaurant"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
