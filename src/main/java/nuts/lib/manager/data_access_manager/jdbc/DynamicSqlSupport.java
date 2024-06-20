package nuts.lib.manager.data_access_manager.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.TestClassOrder;

import java.util.Map;

public abstract class DynamicSqlSupport {

    public static QuerySet insertData(String tableName, Map<String, Object> data) {
        // Construct the SQL INSERT statement dynamically
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");

        // Add column names
        for (String column : data.keySet()) {
            sql.append(column).append(", ");
        }
        // Remove trailing comma and space
        sql.setLength(sql.length() - 2);
        sql.append(") VALUES (");

        // Add placeholders for values
        for (int i = 0; i < data.size(); i++) {
            sql.append("?, ");
        }
        // Remove trailing comma and space
        sql.setLength(sql.length() - 2);
        sql.append(")");

        // Prepare the values array
        Object[] values = data.values().toArray();

        // Execute the INSERT statement
        return new QuerySet(sql.toString(), values);
    }

    @ToString
    @RequiredArgsConstructor
    static public class QuerySet {
        final String sql;
        final Object[] preparedValues;
    }
}
