package edu.sydlab.courseregistrations.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void clearCourses() {
        jdbcTemplate.update("DELETE FROM courses");
    }

    @Test
    void getAllCoursesReturnsNotFoundWhenCatalogIsEmpty() throws Exception {
        mockMvc.perform(get("/courses/all").header("user-id", "demo"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getAllCoursesReturnsCatalogWhenRowsExist() throws Exception {
        jdbcTemplate.update(
            "INSERT INTO courses (code, name, credits, capacity, department_id, instructor_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)",
            "CS101", "Introduction to Programming", 4, 50, 1, 1);

        mockMvc.perform(get("/courses/all").header("user-id", "demo"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].code").value("CS101"))
            .andExpect(jsonPath("$[0].name").value("Introduction to Programming"))
            .andExpect(jsonPath("$[0].credits").value(4))
            .andExpect(jsonPath("$[0].capacity").value(50))
            .andExpect(jsonPath("$[0].departmentId").value(1))
            .andExpect(jsonPath("$[0].instructorId").value(1));
    }

    @Test
    void getAllCoursesRequiresUserIdHeader() throws Exception {
        mockMvc.perform(get("/courses/all"))
            .andExpect(status().isBadRequest());
    }
}
