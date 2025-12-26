package edu.kalam.registrations.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {DatabaseConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")
class DatabaseConfigTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads_andBeansAreCreated() {
        assertNotNull(dataSource, "DataSource should be created");
        assertNotNull(jdbcTemplate, "JdbcTemplate should be created");
    }

    @Test
    void jdbcTemplate_canExecuteSimpleQuery() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertNotNull(result, "Query result should not be null");
        assertEquals(1, result.intValue(), "SELECT 1 should return 1");
    }
}
