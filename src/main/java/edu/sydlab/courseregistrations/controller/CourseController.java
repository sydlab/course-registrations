package edu.sydlab.courseregistrations.controller;

import edu.sydlab.courseregistrations.model.Course;
import edu.sydlab.courseregistrations.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses/all")
    public ResponseEntity<List<Course>> getAllCourses(@RequestHeader("user-id") String userId) {
        List<Course> courseList = courseService.getAllCourses(userId);

        if (courseList.isEmpty()) {
            LOGGER.warn("No courses found for user: {}", userId);
            return ResponseEntity.notFound().build();
        }

        LOGGER.info("Retrieved {} courses for user: {}", courseList.size(), userId);
        return ResponseEntity.ok(courseList);
    }
}
