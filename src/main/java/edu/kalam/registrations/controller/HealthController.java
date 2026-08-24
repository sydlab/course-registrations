package edu.kalam.registrations.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthController.class);

    private final JdbcTemplate jdbcTemplate;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/health")
    public String healthCheck() {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            boolean up = result != null && result == 1;
            LOGGER.debug("DB health check result = {}", result);
            return up ? "UP" : "DOWN";
        } catch (DataAccessException ex) {
            LOGGER.warn("DB health check failed", ex);
            return "DOWN";
        }
    }
}
