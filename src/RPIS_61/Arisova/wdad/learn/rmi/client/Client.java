package RPIS_61.Arisova.wdad.learn.rmi.client;

import RPIS_61.Arisova.wdad.data.managers.PreferencesManager;
import RPIS_61.Arisova.wdad.learn.rmi.Remote.XmlDataManager;
import RPIS_61.Arisova.wdad.utils.PreferencesManagerConstants;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.GregorianCalendar;

public class Client {
    private static Registry registry;

    public static void main(String[] Args) throws RemoteException {
        PreferencesManager manager = PreferencesManager.getInstance();
        System.setProperty("java.rmi.server.hostname", manager.getProperty(PreferencesManagerConstants.REGISTRY_ADDRESS));
        System.setProperty("java.security.policy", manager.getProperty(PreferencesManagerConstants.POLICY_PATH));
        System.setProperty("java.rmi.server.usecodebaseonly", manager.getProperty(PreferencesManagerConstants.USE_CODE_BASE_ONLY));
        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try{
            registry = LocateRegistry.getRegistry(manager.getProperty(PreferencesManagerConstants.REGISTRY_ADDRESS),
                    Integer.parseInt(manager.getProperty(PreferencesManagerConstants.REGISTRY_PORT)));
            System.out.println(registry.toString());
            String[] list = registry.list();
            System.out.println(list.length);
            XmlDataManager stub = (XmlDataManager) registry.lookup("XmlDataManager");
            stub.removeDay(new GregorianCalendar(2018, 11, 27));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
