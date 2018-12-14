package RPIS_61.Arisova.wdad.learn.xml;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class testXmlTask {

    public static void main(String[] args) {
        xmlTask xmlTask = new xmlTask();
        System.out.println("Total Cost: " + xmlTask.earningsTotal("petrov",
                new GregorianCalendar(2018, 11, 24)));

        xmlTask.removeDay(new GregorianCalendar(2018, 11, 27));

        xmlTask.changeOfficiantName("pavel", "sidorov", "roman", "krasnov");

        xmlTask.checkTotalCost();
    }
}
