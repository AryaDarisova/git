package RPIS_61.Arisova.wdad.data.managers;

import static RPIS_61.Arisova.wdad.utils.PreferencesManagerConstants.USE_CODE_BASE_ONLY;

public class testPreferencesManager {
    public static void main(String[] args) {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();

        System.out.println("Old registry address: " + preferencesManager.getRegistryAddress());
        preferencesManager.setRegistryAddress("106.24.37.240");
        System.out.println("New registry adress:" + preferencesManager.getRegistryAddress());

        preferencesManager.setProperty(USE_CODE_BASE_ONLY, "no");
        System.out.println(preferencesManager.getProperty(USE_CODE_BASE_ONLY));
    }
}
