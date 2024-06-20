package nuts.lib.manager.data_access_manager.jdbc;

import org.junit.jupiter.api.Test;

import java.util.Map;


class DynamicSqlSupportTest {

    @Test
    void test() {

        Map<String, Object> maps = Map.of("id", 1, "value", "hello", "intValue", 3);
        DynamicSqlSupport.QuerySet set = DynamicSqlSupport.insertData("test", maps);


        System.out.println(set);
    }
}