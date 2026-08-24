package edu.sydlab.courseregistrations.data;

import edu.sydlab.courseregistrations.model.Student;

public interface StudentRepo {

    long addStudentAndGetId(Student student, String requestId);
}
