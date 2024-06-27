package nuts.lib.manager.data_access_manager.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.validation.constraints.Size;
import lombok.Data;
import nuts.lib.manager.data_access_manager.DataSourceGenerator;
import nuts.lib.manager.data_access_manager.DataSourceType;
import nuts.lib.manager.data_access_manager.jdbc.db_module.SupportQueryModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


class JdbcAccessManagerTest {


    @Test
    void test() throws IllegalAccessException, InvocationTargetException {
        HikariDataSource hikariDataSource = DataSourceGenerator.createHikariDataSource(DataSourceType.mysql, "localhost", 9000, "test_db", "tester", "tester");


        JdbcAccessManager jdbcAccessManager = new JdbcAccessManager(hikariDataSource, SupportQueryModule.MYSQL);
        JdbcDataInjector jdbcDataInjector = new JdbcDataInjector(new JdbcTemplate(hikariDataSource));
        jdbcDataInjector.dataSampleInject("db1_tmp_table1", TestClass.class, 30000);
    }

    @Data
    static public class TestClass {
        @Size(min = 3, max = 50)
        String value;

        @Size(min = 20, max = 50)
        String string_value1;

        @Size(min = 40, max = 50)
        String string_value2;

        @Size(min = 5, max = 50)
        String string_value3;

        @Size(min = 15, max = 50)
        String string_value4;

        int int_value1;

        int int_value2;

    }


    @Test
    void test1() {

        HikariDataSource hikariDataSource = DataSourceGenerator.createHikariDataSource(DataSourceType.mysql, "localhost", 9000, "test_db", "tester", "tester");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(hikariDataSource);

        jdbcTemplate.setFetchSize(1);

        long start = System.nanoTime();

        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from db1_tmp_table1");

        System.out.println(maps);
        long endTime = System.nanoTime();
        System.out.println(endTime - start);

    }


}