package edu.kalam.registrations.service;

import edu.kalam.registrations.data.CoursesRepo;
import edu.kalam.registrations.models.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing courses.
 */
@Service
public class CourseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

    private CoursesRepo coursesRepo;

    @Autowired
    public CourseService(CoursesRepo coursesRepo) {
        this.coursesRepo = coursesRepo;
    }

    /**
     * Retrieves all courses.
     *
     * @param userId the user identifier
     * @return list of all courses
     */
    public List<Course> getAllCourses(String userId) {
        LOGGER.info("Fetching all courses for user: {}", userId);

        return coursesRepo.getAllCourses();
    }
}
