package nuts.lib.manager.data_access_manager;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

public abstract class DataSourceManager {

    public static HikariDataSource createJdbcMySqlDataSource(String host, int port, String dbName, String userName, String passWord) {
        String url = "jdbc:mysql://%s:%s/%s".formatted(host, port, dbName);
        return createDataSource(userName, passWord, url).initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


    private static DataSourceProperties createDataSource(String userName, String passWord, String url) {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl(url);
        dataSourceProperties.setUsername(userName);
        dataSourceProperties.setPassword(passWord);
        return dataSourceProperties;
    }
}
