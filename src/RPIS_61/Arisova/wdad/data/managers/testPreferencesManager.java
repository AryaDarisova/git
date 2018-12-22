package RPIS_61.Arisova.wdad.data.managers;

public class testPreferencesManager {
    public static void main(String[] args) {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();

        System.out.println("Old registry address: " + preferencesManager.getRegistryAddress());
        preferencesManager.setRegistryAddress("106.24.37.240");
        System.out.println("New registry adress:" + preferencesManager.getRegistryAddress());
    }
}
