package RPIS_61.Arisova.wdad.data.managers;

import RPIS_61.Arisova.wdad.learn.xml.Officiant;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class testJDBCDataManager {
    public static void main(String[] args) throws RemoteException {
        JDBCDataManager jdbcDataManager = new JDBCDataManager();
        Officiant alexPetrov = new Officiant("alex", "petrov");
        System.out.println(jdbcDataManager.earningsTotal(alexPetrov,
                new GregorianCalendar(2018, 11, 28)));
        Officiant oldOfficiant = new Officiant("dima", "korobkov");
        Officiant newOfficiant = new Officiant("ivan", "chukov");
        jdbcDataManager.changeOfficiantName(oldOfficiant, newOfficiant);

        System.out.println(jdbcDataManager.lastOfficiantWorkDate(alexPetrov).get(Calendar.YEAR) + "-" +
                jdbcDataManager.lastOfficiantWorkDate(alexPetrov).get(Calendar.MONTH) + "-" +
                jdbcDataManager.lastOfficiantWorkDate(alexPetrov).get(Calendar.DAY_OF_MONTH));
    }
}
