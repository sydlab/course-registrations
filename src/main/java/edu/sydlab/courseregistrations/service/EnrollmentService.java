package edu.sydlab.courseregistrations.service;

import edu.sydlab.courseregistrations.constants.ApiConstants;
import edu.sydlab.courseregistrations.data.EnrollmentRepo;
import edu.sydlab.courseregistrations.model.Enrollment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class EnrollmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnrollmentService.class);

    private final EnrollmentRepo enrollmentRepo;

    public EnrollmentService(EnrollmentRepo enrollmentRepo) {
        this.enrollmentRepo = enrollmentRepo;
    }

    public boolean enroll(Enrollment enrollment, String requestId) {
        LOGGER.info("Request ID: {} - Enrolling student {} in course {}",
            requestId, enrollment.getStudentId(), enrollment.getCourseId());

        if (enrollment.getStudentId() == null || enrollment.getCourseId() == null) {
            LOGGER.warn("Request ID: {} - Missing studentId or courseId", requestId);
            return false;
        }

        Integer capacity = enrollmentRepo.findCapacity(enrollment.getCourseId());
        if (capacity == null) {
            LOGGER.warn("Request ID: {} - Course {} not found", requestId, enrollment.getCourseId());
            return false;
        }

        int active = enrollmentRepo.countActiveByCourseId(enrollment.getCourseId());
        if (active >= capacity) {
            LOGGER.warn("Request ID: {} - Course {} is at capacity {}",
                requestId, enrollment.getCourseId(), capacity);
            return false;
        }

        try {
            Timestamp enrollmentDate = Timestamp.from(Instant.now());
            return enrollmentRepo.insertEnrollment(
                enrollment.getStudentId(),
                enrollment.getCourseId(),
                enrollmentDate,
                ApiConstants.ENROLLMENT_STATUS_ENROLLED) > 0;
        } catch (DataAccessException ex) {
            LOGGER.warn("Request ID: {} - Failed to enroll student {} in course {}",
                requestId, enrollment.getStudentId(), enrollment.getCourseId(), ex);
            return false;
        }
    }
}
