package RPIS_61.Arisova.wdad.data.storage;

import RPIS_61.Arisova.wdad.data.managers.PreferencesManager;
import RPIS_61.Arisova.wdad.utils.PreferencesManagerConstants;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {

    public static DataSource createDataSource() {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        return createDataSource(preferencesManager.getProperty(PreferencesManagerConstants.CLASS_NAME),
                preferencesManager.getProperty(PreferencesManagerConstants.DRIVER_TYPE),
                preferencesManager.getProperty(PreferencesManagerConstants.HOST_NAME),
                Integer.parseInt(preferencesManager.getProperty(PreferencesManagerConstants.PORT)),
                preferencesManager.getProperty(PreferencesManagerConstants.DB_NAME),
                preferencesManager.getProperty(PreferencesManagerConstants.USER),
                preferencesManager.getProperty(PreferencesManagerConstants.PASSWORD));
    }

    public static DataSource createDataSource(String className, String
            driverType, String host, int port, String dbName, String user, String password) {
        MysqlDataSource dataSource = null;
        try {
            dataSource = new MysqlDataSource();
            dataSource.setUser(user);
            dataSource.setPassword(password);
            dataSource.setUrl("jdbc:" + driverType + "://" + host + ":" + port + "/" + dbName);
            dataSource.setDatabaseName(dbName);
            dataSource.setUseSSL(false);
            dataSource.setAutoReconnect(true);
            Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}
