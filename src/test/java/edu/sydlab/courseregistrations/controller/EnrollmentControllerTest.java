package edu.sydlab.courseregistrations.controller;

import edu.sydlab.courseregistrations.constants.ApiConstants;
import edu.sydlab.courseregistrations.model.Enrollment;
import edu.sydlab.courseregistrations.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EnrollmentControllerTest {

    private EnrollmentService enrollmentService;
    private EnrollmentController controller;

    @BeforeEach
    void setUp() {
        enrollmentService = mock(EnrollmentService.class);
        controller = new EnrollmentController(enrollmentService);
    }

    @Test
    void enrollReturnsOkWhenEnrollSucceeds() {
        Enrollment enrollment = new Enrollment(null, 1L, 2L, null, null);
        when(enrollmentService.enroll(enrollment, "req-1")).thenReturn(true);

        ResponseEntity<String> response = controller.enroll("req-1", enrollment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiConstants.ENROLLED_SUCCESS, response.getBody());
        verify(enrollmentService, times(1)).enroll(enrollment, "req-1");
    }

    @Test
    void enrollReturnsServerErrorWhenEnrollFails() {
        Enrollment enrollment = new Enrollment(null, 1L, 2L, null, null);
        when(enrollmentService.enroll(enrollment, "req-1")).thenReturn(false);

        ResponseEntity<String> response = controller.enroll("req-1", enrollment);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ApiConstants.ENROLL_FAILED, response.getBody());
        verify(enrollmentService, times(1)).enroll(enrollment, "req-1");
    }
}
