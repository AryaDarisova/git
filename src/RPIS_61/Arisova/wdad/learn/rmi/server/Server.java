package RPIS_61.Arisova.wdad.learn.rmi.server;

import RPIS_61.Arisova.wdad.data.managers.PreferencesManager;
import RPIS_61.Arisova.wdad.learn.rmi.Remote.XmlDataManagerImpl;
import RPIS_61.Arisova.wdad.utils.PreferencesManagerConstants;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    private static Registry registry;

    public static void main(String[] Args) throws RemoteException {
        PreferencesManager manager = PreferencesManager.getInstance();
        System.setProperty("java.rmi.server.hostname", manager.getProperty(PreferencesManagerConstants.REGISTRY_ADDRESS));
        System.setProperty("java.security.policy", manager.getProperty(PreferencesManagerConstants.POLICY_PATH));
        System.setProperty("java.rmi.server.usecodebaseonly", manager.getProperty(PreferencesManagerConstants.USE_CODE_BASE_ONLY));

        /*if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }*/
        try {
            if(manager.getProperty(PreferencesManagerConstants.CREATE_REGISTRY).equals("yes")){
                registry = LocateRegistry.createRegistry(Integer.parseInt(manager.getProperty(PreferencesManagerConstants.REGISTRY_PORT)));
            } else {
                registry = LocateRegistry.getRegistry(Integer.parseInt(manager.getProperty(PreferencesManagerConstants.REGISTRY_PORT)));
            }
            XmlDataManagerImpl xmlDataManager = new XmlDataManagerImpl();
            //DataManager stub = (DataManager) UnicastRemoteObject.exportObject(xmlDataManager,0);
            registry.bind("DataManager", xmlDataManager);
            System.out.println("Server start");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
