package RPIS_61.Arisova.wdad.data.managers;

import RPIS_61.Arisova.wdad.learn.xml.Officiant;
import RPIS_61.Arisova.wdad.learn.xml.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

public interface DataManager extends Remote {

    public int earningsTotal (Officiant officiant, Calendar calendar) throws RemoteException;
    public void removeDay (Calendar calendar) throws RemoteException;
    public void changeOfficiantName (Officiant oldOfficiant, Officiant newOfficiant) throws RemoteException;
    public List<Order> getOrders (Calendar calendar) throws RemoteException;
    public Calendar lastOfficiantWorkDate (Officiant officiant) throws RemoteException;
}
