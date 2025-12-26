package edu.kalam.registrations.service;

import edu.kalam.registrations.data.StudentRepo;
import edu.kalam.registrations.models.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing students.
 */
@Service
public class StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private StudentRepo studentRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    /**
     * Adds a new student to the system.
     *
     * @param requestId unique request ID for tracking
     * @param student   student object to be added
     * @return true if the student was added successfully, false otherwise
     */
    public boolean addStudent(Student student,
                              String requestId) {
        LOGGER.info("Request ID: {} - Adding student: {}", requestId,
            student);

        long studentId = studentRepo.addStudentAndGetId(student, requestId);

        LOGGER.info("Request ID: {} - Student added with ID: {}", requestId,
            studentId);

        return studentId > 0;
    }

}
