package nuts.lib.manager.data_access_manager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

public abstract class DataSourceManager {


    public static HikariDataSource createJdbcMySqlDataSource(String host, int port, String dbName, String userName, String passWord) {
        String jdbcUrl = "jdbc:mysql://%s:%s/%s".formatted(host, port, dbName);

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(userName);
        config.setPassword(passWord);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return new HikariDataSource(config);
    }


    private static DataSourceProperties createDataSource(String userName, String passWord, String url) {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl(url);
        dataSourceProperties.setUsername(userName);
        dataSourceProperties.setPassword(passWord);
        return dataSourceProperties;
    }
}
