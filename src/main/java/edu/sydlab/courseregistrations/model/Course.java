package edu.sydlab.courseregistrations.model;

public class Course {

    private Long courseId;
    private String code;
    private String name;
    private Integer credits;
    private Integer capacity;
    private Long departmentId;
    private Long instructorId;

    public Course() {
    }

    public Course(Long courseId, String code, String name, Integer credits, Integer capacity,
                  Long departmentId, Long instructorId) {
        this.courseId = courseId;
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
        this.departmentId = departmentId;
        this.instructorId = instructorId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
}
