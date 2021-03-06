package RPIS_61.Arisova.wdad.learn.rmi.client;

import RPIS_61.Arisova.wdad.data.managers.PreferencesManager;
import RPIS_61.Arisova.wdad.data.managers.DataManager;
import RPIS_61.Arisova.wdad.learn.xml.Officiant;
import RPIS_61.Arisova.wdad.utils.PreferencesManagerConstants;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.GregorianCalendar;

public class Client {
    private static Registry registry;

    public static void main(String[] Args) throws RemoteException, NotBoundException {
        PreferencesManager manager = PreferencesManager.getInstance();
        System.setProperty("java.rmi.server.hostname", manager.getProperty(PreferencesManagerConstants.REGISTRY_ADDRESS));
        System.setProperty("java.security.policy", manager.getProperty(PreferencesManagerConstants.POLICY_PATH));
        System.setProperty("java.rmi.server.usecodebaseonly", manager.getProperty(PreferencesManagerConstants.USE_CODE_BASE_ONLY));
        /*if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }*/
        try{
            System.out.println("Connected");
            registry = LocateRegistry.getRegistry(manager.getProperty(PreferencesManagerConstants.REGISTRY_ADDRESS),
                    Integer.parseInt(manager.getProperty(PreferencesManagerConstants.REGISTRY_PORT)));
            /*System.out.println(registry.toString());
            String[] list = registry.list();
            System.out.println(list.length);*/
            DataManager stub = (DataManager) registry.lookup("DataManager");
            stub.removeDay(new GregorianCalendar(2018, 11, 27));
            Officiant alex = new Officiant("alex", "petrov");
            System.out.println(stub.earningsTotal(alex, new GregorianCalendar(2018, 11, 28)));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
