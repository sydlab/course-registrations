package edu.kalam.registrations.data;

import edu.kalam.registrations.models.Course;

import java.util.List;

public interface CoursesRepo {

    /**
     * Fetches all courses from the database.
     *
     * @return List of all courses.
     */
    public List<Course> getAllCourses();

}
