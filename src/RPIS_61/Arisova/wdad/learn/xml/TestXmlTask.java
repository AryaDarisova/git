package RPIS_61.Arisova.wdad.learn.xml;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TestXmlTask {

    public static void main(String[] args) {
        XmlTask xmlTask = new XmlTask();
        System.out.println("Total Cost: " + xmlTask.earningsTotal("petrov",
                new GregorianCalendar(2018, 11, 24)));

        xmlTask.removeDay(new GregorianCalendar(2018, 11, 27));

        xmlTask.changeOfficiantName("pavel", "sidorov", "roman", "krasnov");

        xmlTask.checkTotalCost();

        System.out.println(xmlTask.lastOfficiantWorkDate("petrov").get(Calendar.DAY_OF_MONTH) + "." +
                xmlTask.lastOfficiantWorkDate("petrov").get(Calendar.MONTH) + "." +
                xmlTask.lastOfficiantWorkDate("petrov").get(Calendar.YEAR));
    }
}
