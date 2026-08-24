CREATE TABLE IF NOT EXISTS courses (
    course_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    credits INTEGER NOT NULL,
    capacity INTEGER NOT NULL,
    department_id BIGINT,
    instructor_id BIGINT
);
