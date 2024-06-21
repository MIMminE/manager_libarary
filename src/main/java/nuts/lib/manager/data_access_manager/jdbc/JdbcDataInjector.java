package nuts.lib.manager.data_access_manager.jdbc;

import com.navercorp.fixturemonkey.FixtureMonkey;
import nuts.lib.manager.fixture_manager.FixtureManager;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * It provides a function that makes it easy to enter test data into the database.
 * You need to create a member variable with the same name as the table field,
 * pass the class meta information as a parameter, and implement a setter method.
 * <p>
 * 2024. 06. 21
 */
public class JdbcDataInjector {
    private final JdbcTemplate jdbcTemplate;
    private final FixtureMonkey fixtureMonkey = FixtureManager.supplierSetterObjectIntrospect.get();

    public JdbcDataInjector(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void dataSampleInject(String table, Class<?> schemaStructClass) throws IllegalAccessException, InvocationTargetException {
        Object instance = fixtureMonkey.giveMeOne(schemaStructClass);

        Field[] declaredFields = instance.getClass().getDeclaredFields();

        Map<String, Object> objectMap = Arrays.stream(declaredFields).collect(Collectors.toMap(Field::getName, field -> {
            field.setAccessible(true);
            try {
                return field.get(instance);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }));

        insert(table, objectMap);
    }

    public void dataSampleInject(String table, Class<?> schemaStructClass, int size) throws IllegalAccessException, InvocationTargetException {
        List<?> objects = fixtureMonkey.giveMe(schemaStructClass, size);

        List<Map<String, Object>> objectMapList = new ArrayList<>();

        for (Object instance : objects) {
            Field[] declaredFields = instance.getClass().getDeclaredFields();

            objectMapList.add(Arrays.stream(declaredFields).collect(Collectors.toMap(Field::getName, field -> {
                field.setAccessible(true);
                try {
                    return field.get(instance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            })));
        }


        insert(table, objectMapList);
    }

    private void insert(String tableName, Map<String, Object> data) {
        StringBuilder sql = createDynamicInsert(tableName, data);

        jdbcTemplate.update(sql.toString(), data.values().toArray());
    }

    private void insert(String tableName, List<Map<String, Object>> datas) {
        for (Map<String, Object> data : datas) {
            StringBuilder sql = createDynamicInsert(tableName, data);
            jdbcTemplate.update(sql.toString(), data.values().toArray());
        }
    }

    private StringBuilder createDynamicInsert(String tableName, Map<String, Object> data) {
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");

        for (String column : data.keySet()) {
            sql.append(column).append(", ");
        }
        sql.setLength(sql.length() - 2);
        sql.append(") VALUES (");

        for (int i = 0; i < data.size(); i++) {
            sql.append("?, ");
        }
        sql.setLength(sql.length() - 2);
        sql.append(")");
        return sql;
    }
}