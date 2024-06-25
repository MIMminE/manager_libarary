package nuts.lib.manager.data_access_manager.jdbc.db_module;

import lombok.Getter;

@Getter
public enum SupportQueryModule {

    MYSQL(new MysqlModule());

    private final DatabaseQueryModule databaseQueryModule;

    SupportQueryModule(DatabaseQueryModule databaseQueryModule) {
        this.databaseQueryModule = databaseQueryModule;
    }
}
