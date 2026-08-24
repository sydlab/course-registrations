package edu.sydlab.courseregistrations.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void clearStudents() {
        jdbcTemplate.update("DELETE FROM students");
    }

    @Test
    void addStudentCreatesRowWithGeneratedStudentNumber() throws Exception {
        mockMvc.perform(post("/students/add")
                .header("requestId", "req-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"firstName":"Ada","lastName":"Lovelace","email":"ada@student.edu","enrollmentYear":2026}
                    """))
            .andExpect(status().isOk())
            .andExpect(content().string("Student added successfully"));

        String studentNumber = jdbcTemplate.queryForObject(
            "SELECT student_number FROM students WHERE email = ?",
            String.class,
            "ada@student.edu");
        Integer enrollmentYear = jdbcTemplate.queryForObject(
            "SELECT enrollment_year FROM students WHERE email = ?",
            Integer.class,
            "ada@student.edu");

        assertEquals("STU-2026-001", studentNumber);
        assertEquals(2026, enrollmentYear);
    }

    @Test
    void addStudentIncrementsStudentNumberForTheSameYear() throws Exception {
        mockMvc.perform(post("/students/add")
                .header("requestId", "req-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"firstName":"Ada","lastName":"Lovelace","email":"ada@student.edu","enrollmentYear":2026}
                    """))
            .andExpect(status().isOk());

        mockMvc.perform(post("/students/add")
                .header("requestId", "req-2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"firstName":"Alan","lastName":"Turing","email":"alan@student.edu","enrollmentYear":2026}
                    """))
            .andExpect(status().isOk());

        String secondNumber = jdbcTemplate.queryForObject(
            "SELECT student_number FROM students WHERE email = ?",
            String.class,
            "alan@student.edu");
        assertEquals("STU-2026-002", secondNumber);
    }

    @Test
    void addStudentReturnsServerErrorWhenEmailAlreadyExists() throws Exception {
        mockMvc.perform(post("/students/add")
                .header("requestId", "req-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"firstName":"Ada","lastName":"Lovelace","email":"ada@student.edu","enrollmentYear":2026}
                    """))
            .andExpect(status().isOk());

        mockMvc.perform(post("/students/add")
                .header("requestId", "req-2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"firstName":"Other","lastName":"Student","email":"ada@student.edu","enrollmentYear":2026}
                    """))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string("Failed to add student"));
    }

    @Test
    void addStudentRequiresRequestIdHeader() throws Exception {
        mockMvc.perform(post("/students/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"firstName":"Ada","lastName":"Lovelace","email":"ada@student.edu","enrollmentYear":2026}
                    """))
            .andExpect(status().isBadRequest());
    }
}
