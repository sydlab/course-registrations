CREATE TABLE IF NOT EXISTS courses (
    course_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    credits INTEGER NOT NULL,
    capacity INTEGER NOT NULL,
    department_id BIGINT,
    instructor_id BIGINT
);

CREATE TABLE IF NOT EXISTS students (
    stu_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_number VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    enrollment_year INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS enrollments (
    enroll_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    enrollment_date TIMESTAMP NOT NULL,
    grade VARCHAR(2),
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(stu_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id),
    UNIQUE(student_id, course_id)
);
