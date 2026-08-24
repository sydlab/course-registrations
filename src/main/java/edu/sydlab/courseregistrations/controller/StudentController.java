package edu.sydlab.courseregistrations.controller;

import edu.sydlab.courseregistrations.model.Student;
import edu.sydlab.courseregistrations.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "/students/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addStudent(@RequestHeader("requestId") String requestId,
                                             @RequestBody Student student) {
        LOGGER.info("Received request to add a student, request id: {}", requestId);
        boolean added = studentService.addStudent(student, requestId);
        if (added) {
            return ResponseEntity.ok("Student added successfully");
        }
        return ResponseEntity.internalServerError().body("Failed to add student");
    }
}
