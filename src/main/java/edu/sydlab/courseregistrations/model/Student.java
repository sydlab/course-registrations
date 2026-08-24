package edu.sydlab.courseregistrations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Long studentId;
    private String studentNumber;
    private String firstName;
    private String lastName;
    private String email;
    private Integer enrollmentYear;
}
