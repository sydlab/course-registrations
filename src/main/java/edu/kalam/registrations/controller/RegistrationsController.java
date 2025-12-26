package edu.kalam.registrations.controller;

import edu.kalam.registrations.models.Course;
import edu.kalam.registrations.models.Student;
import edu.kalam.registrations.service.CourseService;
import edu.kalam.registrations.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling registration-related endpoints.
 */
@RestController
public class RegistrationsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationsController.class);

    private CourseService courseService;

    private JdbcTemplate jdbcTemplate;

    private StudentService studentService;

    @Autowired
    public RegistrationsController(CourseService courseService,
                                   StudentService studentService,
                                   JdbcTemplate jdbcTemplate) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Health check endpoint.
     *
     * @return String indicating service status
     */
    @GetMapping("/health")
    public String healthCheck() {
        try {
            Integer result = jdbcTemplate
                .queryForObject("SELECT 1", Integer.class);
            boolean up = result != null && result == 1;
            LOGGER.debug("DB health check result = {}", result);
            return up ? "UP" : "DOWN";
        } catch (DataAccessException ex) {
            LOGGER.warn("DB health check failed", ex);
            return "DOWN";
        }
    }

    /**
     * Endpoint to get all courses.
     *
     * @param userId user identifier from request header
     * @return ResponseEntity containing the list of courses
     */
    @GetMapping("/courses/all")
    public ResponseEntity<List<Course>> getAllCourses(@RequestHeader("user-id") String userId) {
        List<Course> courseList = courseService.getAllCourses(userId);

        if (courseList.isEmpty()) {
            LOGGER.warn("No courses found for user: {}", userId);
            return ResponseEntity.notFound()
                .build();
        } else {
            LOGGER.info("Retrieved {} courses for user: {}", courseList.size(), userId);
            return ResponseEntity
                .ok(courseList);
        }
    }

    /**
     * Endpoint to add a new student.
     *
     * @return ResponseEntity indicating the result of the operation
     */
    public ResponseEntity<String> addStudent(@RequestHeader String requestId,
                                             @RequestHeader Student student) {
        LOGGER.info("Received request to add a new student, with request id: {}", requestId);

        // validating student details
        LOGGER.info("Student details validated for request id: {}", requestId);

        studentService.addStudent(student, requestId);

        return studentService.addStudent(student, requestId)
            ? ResponseEntity
            .ok()
            .body("Student added successfully")
            : ResponseEntity
            .status(500)
            .body("Failed to add student");
    }

}
