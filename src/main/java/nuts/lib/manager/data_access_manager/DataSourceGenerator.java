package nuts.lib.manager.data_access_manager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * It is used to configure the Hikari data source directly in a way that is not automatic configuration of springs.
 *
 * @since 2024. 06. 21
 */
public abstract class DataSourceGenerator {

    public static HikariDataSource createHikariDataSource(DataSourceType dataSourceType, String host, int port, String dbName, String userName, String password) {
        String dateSourceUrl = dataSourceType.getUrlFormat().formatted(host, port, dbName);

        HikariConfig config = createHikariConfig(dataSourceType, userName, password, dateSourceUrl);
//        +"?useCursorFetch=true"

        return new HikariDataSource(config);
    }

    private static HikariConfig createHikariConfig(DataSourceType dataSourceType, String userName, String password, String dateSourceUrl) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dateSourceUrl);
        config.setUsername(userName);
        config.setPassword(password);
        config.setDriverClassName(dataSourceType.getDriverClassName());
        return config;
    }
}
