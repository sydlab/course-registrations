package edu.sydlab.courseregistrations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    private Long enrollId;
    private Long studentId;
    private Long courseId;
    private Timestamp enrollmentDate;
    private String status;
}
