package edu.kalam.registrations.models;

/**
 * Model class representing a Course entity.
 */
public class Course {

    private Long courseId;
    private String name;
    private String credits;
    private String capacity;

    public Course() {
    }

    public Course(Long courseId,
                  String name,
                  String credits,
                  String capacity) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

}
