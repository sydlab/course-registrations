package edu.kalam.registrations.dao;

import edu.kalam.registrations.models.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDao {

    private JdbcTemplate jdbcTemplate;

    public CourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Course> getAllCourses() {

        return null; // Placeholder return
    }

}
