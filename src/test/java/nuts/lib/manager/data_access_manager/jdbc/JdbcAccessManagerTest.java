package nuts.lib.manager.data_access_manager.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.validation.constraints.Size;
import lombok.Data;
import nuts.lib.manager.data_access_manager.DataSourceGenerator;
import nuts.lib.manager.data_access_manager.DataSourceType;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;


class JdbcAccessManagerTest {


    @Test
    void test() throws IllegalAccessException, InvocationTargetException {
        HikariDataSource hikariDataSource = DataSourceGenerator.createHikariDataSource(DataSourceType.mysql, "localhost", 9000, "test_db", "tester", "tester");


        JdbcAccessManager jdbcAccessManager = new JdbcAccessManager(hikariDataSource);
        JdbcDataInjector jdbcDataInjector = new JdbcDataInjector(new JdbcTemplate(hikariDataSource));
        jdbcDataInjector.dataSampleInject("db1_tmp_table1", TestClass.class, 20000);
    }

    @Data
    static public class TestClass{
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
}