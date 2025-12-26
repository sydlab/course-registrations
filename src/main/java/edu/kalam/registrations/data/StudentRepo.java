package edu.kalam.registrations.data;

import edu.kalam.registrations.models.Student;

/**
 * Repository interface for Student data operations.
 */
public interface StudentRepo {

    /**
     * Adds a new student to the repository.
     *
     * @param student   student object to be added
     * @param requestId unique request ID for tracking
     * @return the ID of the newly added student
     */
    long addStudentAndGetId(Student student,
                            String requestId);

}
