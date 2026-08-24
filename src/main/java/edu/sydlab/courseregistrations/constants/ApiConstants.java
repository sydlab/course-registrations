package edu.sydlab.courseregistrations.constants;

import java.time.ZoneId;

public final class ApiConstants {

    public static final String STUDENTS_ADD_PATH = "/students/add";
    public static final String REQUEST_ID_HEADER = "requestId";
    public static final String STUDENT_ADDED_SUCCESS = "Student added successfully";
    public static final String STUDENT_ADD_FAILED = "Failed to add student";
    public static final String STUDENT_NUMBER_PREFIX = "STU-";
    public static final ZoneId ZONE_MST = ZoneId.of("America/Phoenix");

    private ApiConstants() {
    }
}
