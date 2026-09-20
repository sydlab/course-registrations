package edu.sydlab.courseregistrations.data;

import edu.sydlab.courseregistrations.constants.ApiConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class EnrollmentRepoImpl implements EnrollmentRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnrollmentRepoImpl.class);

    private static final String SELECT_CAPACITY = "SELECT capacity FROM courses WHERE course_id = ?";
    private static final String COUNT_ACTIVE =
        "SELECT COUNT(*) FROM enrollments WHERE course_id = ? AND status = ?";
    private static final String INSERT_ENROLLMENT =
        "INSERT INTO enrollments (student_id, course_id, enrollment_date, status) VALUES (?, ?, ?, ?)";
    private static final String DELETE_ENROLLMENT =
        "DELETE FROM enrollments WHERE student_id = ? AND course_id = ?";

    private final JdbcTemplate jdbcTemplate;

    public EnrollmentRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer findCapacity(long courseId) {
        List<Integer> rows = jdbcTemplate.queryForList(SELECT_CAPACITY, Integer.class, courseId);
        return rows.isEmpty() ? null : rows.get(0);
    }

    @Override
    public int countActiveByCourseId(long courseId) {
        Integer count = jdbcTemplate.queryForObject(
            COUNT_ACTIVE,
            Integer.class,
            courseId,
            ApiConstants.ENROLLMENT_STATUS_ENROLLED);
        return count == null ? 0 : count;
    }

    @Override
    public long insertEnrollment(long studentId, long courseId, Timestamp enrollmentDate, String status) {
        LOGGER.info("Inserting enrollment for student {} in course {}", studentId, courseId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_ENROLLMENT, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, studentId);
            ps.setLong(2, courseId);
            ps.setTimestamp(3, enrollmentDate);
            ps.setString(4, status);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key == null ? 0 : key.longValue();
    }

    @Override
    public int deleteEnrollment(long studentId, long courseId) {
        LOGGER.info("Deleting enrollment for student {} in course {}", studentId, courseId);
        return jdbcTemplate.update(DELETE_ENROLLMENT, studentId, courseId);
    }
}
