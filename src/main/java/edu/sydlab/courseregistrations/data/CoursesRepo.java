package edu.sydlab.courseregistrations.data;

import edu.sydlab.courseregistrations.model.Course;

import java.util.List;

public interface CoursesRepo {

    List<Course> getAllCourses();
}
