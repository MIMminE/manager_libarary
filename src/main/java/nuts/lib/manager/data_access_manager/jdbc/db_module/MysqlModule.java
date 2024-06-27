package nuts.lib.manager.data_access_manager.jdbc.db_module;

import java.sql.Timestamp;
import java.util.Map;
import java.util.StringJoiner;

public class MysqlModule implements DatabaseQueryModule {

    @Override
    public String upsertQuery(String tableName, String key, Map<String, Object> object) {
        if (tableName == null || key == null || object == null || object.isEmpty()) {
            throw new IllegalArgumentException("Table name, key, and object map must be provided and not empty");
        }

        StringJoiner columns = new StringJoiner(", ");
        StringJoiner values = new StringJoiner(", ", "(", ")");
        StringJoiner updates = new StringJoiner(", ");
        String tableAlias = "t";  // 테이블에 사용할 별칭

        for (Map.Entry<String, Object> entry : object.entrySet()) {
            String column = entry.getKey();
            Object value = entry.getValue();

            columns.add(column);
            values.add(formatValue(value));

            updates.add(column + " = " + tableAlias + "." + column);
        }

        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES " + values
                + " AS " + tableAlias + " ON DUPLICATE KEY UPDATE " + updates;

        return sql;
    }

    private static String formatValue(Object value) {
        if (value instanceof String || value instanceof Timestamp) {
            return "'" + value.toString().replace("'", "''") + "'";
        } else if (value == null) {
            return "NULL";
        } else {
            return value.toString();
        }
    }
}
