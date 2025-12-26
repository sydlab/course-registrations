package edu.kalam.registrations.data;

import edu.kalam.registrations.models.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CoursesRepoImpl implements CoursesRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoursesRepoImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CoursesRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Fetches all courses from the database.
     *
     * @return List of all courses.
     */
    public List<Course> getAllCourses() {
        String getAllCoursesQuery = "SELECT * FROM courses";

        LOGGER.info("Executing query: {} to fetch all courses", getAllCoursesQuery);

        return jdbcTemplate.query(getAllCoursesQuery,
            new BeanPropertyRowMapper<>(Course.class));
    }

}
