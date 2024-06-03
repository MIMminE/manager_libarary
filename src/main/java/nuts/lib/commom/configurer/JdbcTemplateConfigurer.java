package nuts.lib.commom.configurer;

import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Getter
public class JdbcTemplateConfigurer {

    JdbcTemplate jdbcTemplate;

    public JdbcTemplateConfigurer dataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        return this;
    }

    public JdbcTemplateConfigurer setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        return this;
    }
}
