package edu.sydlab.courseregistrations.service;

import edu.sydlab.courseregistrations.data.StudentRepo;
import edu.sydlab.courseregistrations.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public boolean addStudent(Student student, String requestId) {
        LOGGER.info("Request ID: {} - Adding student", requestId);
        try {
            return studentRepo.addStudentAndGetId(student, requestId) > 0;
        } catch (DataAccessException ex) {
            LOGGER.warn("Request ID: {} - Failed to add student", requestId, ex);
            return false;
        }
    }
}
