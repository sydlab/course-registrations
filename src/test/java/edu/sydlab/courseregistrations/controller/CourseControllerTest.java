package edu.sydlab.courseregistrations.controller;

import edu.sydlab.courseregistrations.model.Course;
import edu.sydlab.courseregistrations.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CourseControllerTest {

    private CourseService courseService;
    private CourseController controller;

    @BeforeEach
    void setUp() {
        courseService = mock(CourseService.class);
        controller = new CourseController(courseService);
    }

    @Test
    void getAllCoursesReturnsOkWhenCatalogHasRows() {
        Course course = new Course(1L, "CS101", "Introduction to Programming", 4, 50, 1L, 1L);
        when(courseService.getAllCourses("demo")).thenReturn(List.of(course));

        ResponseEntity<List<Course>> response = controller.getAllCourses("demo");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("CS101", response.getBody().get(0).getCode());
    }

    @Test
    void getAllCoursesReturnsNotFoundWhenCatalogIsEmpty() {
        when(courseService.getAllCourses("demo")).thenReturn(List.of());

        ResponseEntity<List<Course>> response = controller.getAllCourses("demo");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
