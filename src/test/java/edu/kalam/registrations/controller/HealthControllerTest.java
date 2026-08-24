package edu.kalam.registrations.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HealthControllerTest {

    private JdbcTemplate jdbcTemplate;
    private HealthController controller;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        controller = new HealthController(jdbcTemplate);
    }

    @Test
    void healthCheckReturnsUpWhenSelectOneSucceeds() {
        when(jdbcTemplate.queryForObject(eq("SELECT 1"), eq(Integer.class))).thenReturn(1);

        assertEquals("UP", controller.healthCheck());
    }

    @Test
    void healthCheckReturnsDownWhenDatabaseFails() {
        when(jdbcTemplate.queryForObject(eq("SELECT 1"), eq(Integer.class)))
            .thenThrow(new DataAccessResourceFailureException("db down"));

        assertEquals("DOWN", controller.healthCheck());
    }
}
