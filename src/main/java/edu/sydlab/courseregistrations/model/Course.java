package edu.sydlab.courseregistrations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    private Long courseId;
    private String code;
    private String name;
    private Integer credits;
    private Integer capacity;
    private Long departmentId;
    private Long instructorId;
}
