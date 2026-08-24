package edu.sydlab.courseregistrations.data;

import edu.sydlab.courseregistrations.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CoursesRepoImpl implements CoursesRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoursesRepoImpl.class);

    private static final String GET_ALL_COURSES = "SELECT * FROM courses";

    private final JdbcTemplate jdbcTemplate;

    public CoursesRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> getAllCourses() {
        LOGGER.debug("Executing query to fetch all courses");
        return jdbcTemplate.query(GET_ALL_COURSES, new BeanPropertyRowMapper<>(Course.class));
    }
}
