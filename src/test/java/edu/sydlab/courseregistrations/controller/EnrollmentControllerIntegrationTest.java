package edu.sydlab.courseregistrations.controller;

import edu.sydlab.courseregistrations.constants.ApiConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EnrollmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void clearTables() {
        jdbcTemplate.update("DELETE FROM enrollments");
        jdbcTemplate.update("DELETE FROM students");
        jdbcTemplate.update("DELETE FROM courses");
    }

    @Test
    void enrollCreatesRowWhenUnderCapacity() throws Exception {
        long studentId = insertStudent("STU-2026-001", "ada@student.edu");
        long courseId = insertCourse("CS101", 2);

        mockMvc.perform(post(ApiConstants.ENROLLMENTS_ADD_PATH)
                .header(ApiConstants.REQUEST_ID_HEADER, "req-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"studentId":%d,"courseId":%d}
                    """.formatted(studentId, courseId)))
            .andExpect(status().isOk())
            .andExpect(content().string(ApiConstants.ENROLLED_SUCCESS));

        Integer rowCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM enrollments WHERE student_id = ? AND course_id = ?",
            Integer.class,
            studentId,
            courseId);
        String status = jdbcTemplate.queryForObject(
            "SELECT status FROM enrollments WHERE student_id = ? AND course_id = ?",
            String.class,
            studentId,
            courseId);
        Timestamp enrollmentDate = jdbcTemplate.queryForObject(
            "SELECT enrollment_date FROM enrollments WHERE student_id = ? AND course_id = ?",
            Timestamp.class,
            studentId,
            courseId);

        assertEquals(1, rowCount);
        assertEquals(ApiConstants.ENROLLMENT_STATUS_ENROLLED, status);
        assertNotNull(enrollmentDate);
    }

    @Test
    void enrollRejectsDuplicateStudentAndCourse() throws Exception {
        long studentId = insertStudent("STU-2026-001", "ada@student.edu");
        long courseId = insertCourse("CS101", 2);
        String body = """
            {"studentId":%d,"courseId":%d}
            """.formatted(studentId, courseId);

        mockMvc.perform(post(ApiConstants.ENROLLMENTS_ADD_PATH)
                .header(ApiConstants.REQUEST_ID_HEADER, "req-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk());

        mockMvc.perform(post(ApiConstants.ENROLLMENTS_ADD_PATH)
                .header(ApiConstants.REQUEST_ID_HEADER, "req-2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string(ApiConstants.ENROLL_FAILED));

        Integer rowCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM enrollments WHERE student_id = ? AND course_id = ?",
            Integer.class,
            studentId,
            courseId);
        assertEquals(1, rowCount);
    }

    @Test
    void enrollRejectsWhenCourseAtCapacity() throws Exception {
        long firstStudentId = insertStudent("STU-2026-001", "ada@student.edu");
        long secondStudentId = insertStudent("STU-2026-002", "alan@student.edu");
        long courseId = insertCourse("CS101", 1);

        mockMvc.perform(post(ApiConstants.ENROLLMENTS_ADD_PATH)
                .header(ApiConstants.REQUEST_ID_HEADER, "req-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"studentId":%d,"courseId":%d}
                    """.formatted(firstStudentId, courseId)))
            .andExpect(status().isOk())
            .andExpect(content().string(ApiConstants.ENROLLED_SUCCESS));

        mockMvc.perform(post(ApiConstants.ENROLLMENTS_ADD_PATH)
                .header(ApiConstants.REQUEST_ID_HEADER, "req-2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"studentId":%d,"courseId":%d}
                    """.formatted(secondStudentId, courseId)))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string(ApiConstants.ENROLL_FAILED));

        Integer rowCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM enrollments WHERE course_id = ?",
            Integer.class,
            courseId);
        assertEquals(1, rowCount);
    }

    @Test
    void enrollRequiresRequestIdHeader() throws Exception {
        mockMvc.perform(post(ApiConstants.ENROLLMENTS_ADD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"studentId":1,"courseId":1}
                    """))
            .andExpect(status().isBadRequest());
    }

    private long insertStudent(String studentNumber, String email) {
        jdbcTemplate.update(
            "INSERT INTO students (student_number, first_name, last_name, email, enrollment_year) "
                + "VALUES (?, ?, ?, ?, ?)",
            studentNumber, "Ada", "Lovelace", email, 2026);
        Long id = jdbcTemplate.queryForObject(
            "SELECT stu_id FROM students WHERE student_number = ?",
            Long.class,
            studentNumber);
        return id;
    }

    private long insertCourse(String code, int capacity) {
        jdbcTemplate.update(
            "INSERT INTO courses (code, name, credits, capacity, department_id, instructor_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)",
            code, "Introduction to Programming", 4, capacity, 1, 1);
        Long id = jdbcTemplate.queryForObject(
            "SELECT course_id FROM courses WHERE code = ?",
            Long.class,
            code);
        return id;
    }
}
