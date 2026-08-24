package edu.sydlab.courseregistrations.service;

import edu.sydlab.courseregistrations.data.CoursesRepo;
import edu.sydlab.courseregistrations.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

    private final CoursesRepo coursesRepo;

    public CourseService(CoursesRepo coursesRepo) {
        this.coursesRepo = coursesRepo;
    }

    public List<Course> getAllCourses(String userId) {
        LOGGER.info("Fetching all courses for user: {}", userId);
        return coursesRepo.getAllCourses();
    }
}
