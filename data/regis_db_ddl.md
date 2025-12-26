### Table for tracking the app users connecting

```sql
CREATE TABLE users
(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100)        NOT NULL,
email VARCHAR(150) UNIQUE NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Table for University departments

```sql
CREATE TABLE departments
(
dept_id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100)       NOT NULL,
code VARCHAR(10) UNIQUE NOT NULL
);
```

### Table for University instructors

```sql
CREATE TABLE instructors
(
instruct_id BIGINT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(50)         NOT NULL,
last_name VARCHAR(50)         NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
department_id BIGINT,
FOREIGN KEY (department_id) REFERENCES departments (dept_id)
);
```

### Table for University courses

```sql
CREATE TABLE courses
(
course_id BIGINT PRIMARY KEY AUTO_INCREMENT,
code VARCHAR(20) UNIQUE NOT NULL,
name VARCHAR(200)       NOT NULL,
credits INTEGER NOT NULL,
capacity INTEGER NOT NULL,
department_id BIGINT,
instructor_id BIGINT,
FOREIGN KEY (department_id) REFERENCES departments (dept_id),
FOREIGN KEY (instructor_id) REFERENCES instructors (instruct_id)
);
```

### Table for University students

```sql
CREATE TABLE students
(
stu_id BIGINT PRIMARY KEY AUTO_INCREMENT,
student_number VARCHAR(20) UNIQUE NOT NULL,
first_name VARCHAR(50)         NOT NULL,
last_name VARCHAR(50)         NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
enrollment_year INTEGER NOT NULL
);
```

### Table for tracking course enrollments (from students to courses & from instructors to courses)

```sql
CREATE TABLE enrollments
(
enroll_id BIGINT PRIMARY KEY AUTO_INCREMENT,
student_id BIGINT NOT NULL,
course_id BIGINT NOT NULL,
enrollment_date TIMESTAMP NOT NULL,
grade VARCHAR(2),
status VARCHAR(20) NOT NULL,
FOREIGN KEY (student_id) REFERENCES students (stu_id),
FOREIGN KEY (course_id) REFERENCES courses (course_id),
UNIQUE (student_id, course_id)
);
```