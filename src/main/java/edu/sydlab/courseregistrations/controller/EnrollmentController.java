package edu.sydlab.courseregistrations.controller;

import edu.sydlab.courseregistrations.constants.ApiConstants;
import edu.sydlab.courseregistrations.model.Enrollment;
import edu.sydlab.courseregistrations.service.EnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnrollmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnrollmentController.class);

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping(value = ApiConstants.ENROLLMENTS_ADD_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enroll(@RequestHeader(ApiConstants.REQUEST_ID_HEADER) String requestId,
                                         @RequestBody Enrollment enrollment) {
        LOGGER.info("Received request to enroll, request id: {}", requestId);
        boolean enrolled = enrollmentService.enroll(enrollment, requestId);
        if (enrolled) {
            return ResponseEntity.ok(ApiConstants.ENROLLED_SUCCESS);
        }
        return ResponseEntity.internalServerError().body(ApiConstants.ENROLL_FAILED);
    }

    @PostMapping(value = ApiConstants.ENROLLMENTS_DROP_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> drop(@RequestHeader(ApiConstants.REQUEST_ID_HEADER) String requestId,
                                       @RequestBody Enrollment enrollment) {
        LOGGER.info("Received request to drop, request id: {}", requestId);
        boolean dropped = enrollmentService.drop(enrollment, requestId);
        if (dropped) {
            return ResponseEntity.ok(ApiConstants.DROPPED_SUCCESS);
        }
        return ResponseEntity.internalServerError().body(ApiConstants.DROP_FAILED);
    }
}
