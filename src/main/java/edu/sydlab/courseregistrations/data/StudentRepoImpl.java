package edu.sydlab.courseregistrations.data;

import edu.sydlab.courseregistrations.constants.ApiConstants;
import edu.sydlab.courseregistrations.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.Year;
import java.util.List;

@Repository
public class StudentRepoImpl implements StudentRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentRepoImpl.class);

    private static final String INSERT_STUDENT =
        "INSERT INTO students (student_number, first_name, last_name, email, enrollment_year) "
            + "VALUES (?, ?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public StudentRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long addStudentAndGetId(Student student, String requestId) {
        int enrollmentYear = student.getEnrollmentYear() != null
            ? student.getEnrollmentYear()
            : Year.now(ApiConstants.ZONE_MST).getValue();
        String studentNumber = nextStudentNumber(enrollmentYear);

        LOGGER.info("Request ID: {} - Adding student {} ({})", requestId, studentNumber, student.getEmail());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_STUDENT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, studentNumber);
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.setString(4, student.getEmail());
            ps.setInt(5, enrollmentYear);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key == null ? 0 : key.longValue();
    }

    private String nextStudentNumber(int year) {
        String prefix = ApiConstants.STUDENT_NUMBER_PREFIX + year + "-";
        List<String> existing = jdbcTemplate.queryForList(
            "SELECT student_number FROM students WHERE student_number LIKE ?",
            String.class,
            prefix + "%");
        int next = existing.stream()
            .map(number -> number.substring(prefix.length()))
            .mapToInt(Integer::parseInt)
            .max()
            .orElse(0) + 1;
        return prefix + String.format("%03d", next);
    }
}
