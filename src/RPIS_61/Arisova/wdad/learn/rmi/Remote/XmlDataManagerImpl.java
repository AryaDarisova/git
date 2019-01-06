package RPIS_61.Arisova.wdad.learn.rmi.Remote;

import RPIS_61.Arisova.wdad.learn.xml.Officiant;
import RPIS_61.Arisova.wdad.learn.xml.Order;
import RPIS_61.Arisova.wdad.learn.xml.XmlTask;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.List;

public class XmlDataManagerImpl extends UnicastRemoteObject implements XmlDataManager, Serializable {
    private XmlTask xmlTask;

    public XmlDataManagerImpl() throws RemoteException {
        this.xmlTask = new XmlTask();
    }

    @Override
    public int earningsTotal (Officiant officiant, Calendar calendar) throws RemoteException {
        return xmlTask.earningsTotal(officiant.getSecondName(), calendar);
    }

    @Override
    public void removeDay (Calendar calendar) throws RemoteException {
        xmlTask.removeDay(calendar);
    }

    @Override
    public void changeOfficiantName (Officiant oldOfficiant, Officiant newOfficiant) throws RemoteException {
        xmlTask.changeOfficiantName(oldOfficiant.getFirstName(), oldOfficiant.getSecondName(),
                newOfficiant.getFirstName(), newOfficiant.getSecondName());
    }

    @Override
    public List<Order> getOrders (Calendar calendar) throws RemoteException {
        return xmlTask.getOrders(calendar);
    }

    @Override
    public Calendar lastOfficiantWorkDate (Officiant officiant) throws RemoteException {
        return xmlTask.lastOfficiantWorkDate(officiant.getSecondName());
    }
}
