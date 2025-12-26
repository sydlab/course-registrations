package edu.kalam.registrations.data;

import edu.kalam.registrations.models.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.Year;

/**
 * Implementation of the StudentRepo interface.
 */
@Service
public class StudentRepoImpl implements StudentRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentRepoImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long addStudentAndGetId(Student student, String requestId) {
        String addStudentQuery =
            "INSERT INTO students (first_name, last_name, email, enrollment_year) "
                + "VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        LOGGER.info("Request ID: {} - Executing query: {} to add student: {}",
            requestId, addStudentQuery, student);

        /* getting current year to insert the enrollment year */
        int year = Year.now().getValue();

        /* TODO add logic to generate student number must be in STU-YYYY-XX XX is the month of enrollment */

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(addStudentQuery,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getEmail());
            ps.setString(4, String.valueOf(year));

            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

}
