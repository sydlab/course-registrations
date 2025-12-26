package edu.kalam.registrations.models;

/**
 * Model class representing a Student entity.
 */
public class Student {

    private Long studentId;
    private String studentNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String enrollmentYear;

    public Student() {
    }

    public Student(Long studentId,
                   String studentNumber,
                   String firstName,
                   String lastName,
                   String email,
                   String enrollmentYear) {
        this.studentId = studentId;
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enrollmentYear = enrollmentYear;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(String enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }
}
