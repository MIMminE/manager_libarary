package nuts.lib.manager.data_access_manager.jdbc.db_module;

import java.util.Map;

public interface DatabaseQueryModule {

    String upsertQuery(String tableName, String key, Map<String, Object> object);
}
