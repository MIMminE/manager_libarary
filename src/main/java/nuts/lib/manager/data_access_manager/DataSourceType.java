package nuts.lib.manager.data_access_manager;

import lombok.Getter;

/**
 * An enumeration type that contains the URL information and class information needed to create the data source.
 *
 * @since 2024. 06. 21
 */
@Getter
public enum DataSourceType {
    //TODO 여러 데이터소스 정보 업데이트 필요

    mysql("jdbc:mysql://%s:%s/%s", "com.mysql.cj.jdbc.Driver");

    private final String urlFormat;
    private final String driverClassName;

    DataSourceType(String urlFormat, String driverClassName) {
        this.urlFormat = urlFormat;
        this.driverClassName = driverClassName;
    }
}
