package edu.sydlab.courseregistrations.controller;

import edu.sydlab.courseregistrations.model.Student;
import edu.sydlab.courseregistrations.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentControllerTest {

    private StudentService studentService;
    private StudentController controller;

    @BeforeEach
    void setUp() {
        studentService = mock(StudentService.class);
        controller = new StudentController(studentService);
    }

    @Test
    void addStudentReturnsOkWhenCreateSucceeds() {
        Student student = new Student(null, null, "Ada", "Lovelace", "ada@student.edu", 2026);
        when(studentService.addStudent(student, "req-1")).thenReturn(true);

        ResponseEntity<String> response = controller.addStudent("req-1", student);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Student added successfully", response.getBody());
        verify(studentService, times(1)).addStudent(student, "req-1");
    }

    @Test
    void addStudentReturnsServerErrorWhenCreateFails() {
        Student student = new Student(null, null, "Ada", "Lovelace", "ada@student.edu", 2026);
        when(studentService.addStudent(student, "req-1")).thenReturn(false);

        ResponseEntity<String> response = controller.addStudent("req-1", student);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to add student", response.getBody());
        verify(studentService, times(1)).addStudent(student, "req-1");
    }
}
