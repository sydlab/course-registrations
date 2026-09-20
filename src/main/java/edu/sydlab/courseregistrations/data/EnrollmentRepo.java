package edu.sydlab.courseregistrations.data;

import java.sql.Timestamp;

public interface EnrollmentRepo {

    Integer findCapacity(long courseId);

    int countActiveByCourseId(long courseId);

    long insertEnrollment(long studentId, long courseId, Timestamp enrollmentDate, String status);
}
