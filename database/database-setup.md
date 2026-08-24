# Local database setup

Follow these notes to create the local registration database, apply the current schema, and load seed data.

## Locked local-dev decisions

| Item | Value |
|------|--------|
| Engine | MySQL 8.x |
| Database name | `course_registrations` |
| App user | `app_user` @ `localhost` |
| Schema script | [`course_registrations.ddl`](course_registrations.ddl) |
| Seed script | [`course_registrations.dml`](course_registrations.dml) |
| ER diagram | [`docs/data/course_registrations.mmd`](../docs/data/course_registrations.mmd) |

Set the app user password locally. Do not commit a password value.

## Prerequisites

- MySQL 8.x running on `localhost:3306`
- A server admin user that can `CREATE DATABASE` and `CREATE USER`

## 1. Create `course_registrations` and the local app user

Connect as a MySQL admin user, then run:

```sql
CREATE DATABASE course_registrations;
USE course_registrations;

-- Set COURSE_REG_DB_PASSWORD locally. Do not commit the value.
CREATE USER 'app_user'@'localhost' IDENTIFIED BY '${COURSE_REG_DB_PASSWORD}';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX
ON course_registrations.* TO 'app_user'@'localhost';

FLUSH PRIVILEGES;
```

Confirm the user exists:

```sql
SELECT Host, User
FROM mysql.user
WHERE User = 'app_user';
```

## 2. Apply the current DDL

From the repo root:

```bash
mysql -u app_user -p course_registrations < database/course_registrations.ddl
```

Current schema (`database/course_registrations.ddl`):

```sql
-- Table for tracking the app users connecting
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Department table
CREATE TABLE departments (
    dept_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(10) UNIQUE NOT NULL
);

-- Instructor table
CREATE TABLE instructors (
    instruct_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    department_id BIGINT,
    FOREIGN KEY (department_id) REFERENCES departments(dept_id)
);

-- Course table
CREATE TABLE courses (
    course_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    credits INTEGER NOT NULL,
    capacity INTEGER NOT NULL,
    department_id BIGINT,
    instructor_id BIGINT,
    FOREIGN KEY (department_id) REFERENCES departments(dept_id),
    FOREIGN KEY (instructor_id) REFERENCES instructors(instruct_id)
);

-- Student table
CREATE TABLE students (
    stu_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_number VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    enrollment_year INTEGER NOT NULL
);

-- Enrollment table (junction)
CREATE TABLE enrollments (
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
```

If the `.ddl` file and the block above ever differ, **the file is the source of truth**.

## 3. Apply the current DML

```bash
mysql -u app_user -p course_registrations < database/course_registrations.dml
```

Current seed data (`database/course_registrations.dml`):

```sql
INSERT INTO users (name, email)
VALUES ('System Admin', 'admin@kalaam-uni.edu'),
       ('Registrar Office', 'registrar@kalaam-uni.edu'),
       ('Academic Advisor', 'advisor_01@kalaam-uni.edu');

INSERT INTO departments (name, code)
VALUES ('Computer Science', 'CS'),
       ('Mechanical Engineering', 'MECH'),
       ('Department of Mathematics', 'MATH'),
       ('Business Administration', 'BUS');

INSERT INTO instructors (first_name, last_name, email, department_id)
VALUES ('Alan', 'Turing', 'a.turing@university.edu', 1),
       ('Ada', 'Lovelace', 'a.lovelace@university.edu', 1),
       ('Isaac', 'Newton', 'i.newton@university.edu', 3),
       ('Katherine', 'Johnson', 'k.johnson@university.edu', 3),
       ('Mary', 'Barra', 'm.barra@university.edu', 4);

INSERT INTO courses (code, name, credits, capacity, department_id, instructor_id)
VALUES ('CS101', 'Introduction to Programming', 4, 50, 1, 1),
       ('CS202', 'Data Structures & Algorithms', 4, 40, 1, 2),
       ('MATH150', 'Calculus I', 3, 60, 3, 3),
       ('MATH300', 'Linear Algebra', 3, 30, 3, 4),
       ('BUS110', 'Principles of Management', 3, 100, 4, 5);

INSERT INTO students (student_number, first_name, last_name, email, enrollment_year)
VALUES ('STU-2025-001', 'John', 'Doe', 'j.doe@student.edu', 2025),
       ('STU-2025-002', 'Jane', 'Smith', 'j.smith@student.edu', 2025),
       ('STU-2024-045', 'Robert', 'Brown', 'r.brown@student.edu', 2024),
       ('STU-2023-099', 'Emily', 'Davis', 'e.davis@student.edu', 2023);

INSERT INTO enrollments (student_id, course_id, enrollment_date, grade, status)
VALUES (1, 1, '2025-01-10 09:00:00', NULL, 'ENROLLED'),
       (1, 3, '2025-01-10 09:15:00', NULL, 'ENROLLED'),
       (2, 1, '2025-01-11 10:30:00', NULL, 'ENROLLED'),
       (2, 5, '2025-01-11 10:45:00', NULL, 'ENROLLED'),
       (3, 2, '2025-01-05 14:00:00', 'A', 'COMPLETED'),
       (4, 4, '2025-01-02 11:00:00', 'B+', 'COMPLETED');
```

If the `.dml` file and the block above ever differ, **the file is the source of truth**.

## 4. Optional checks

Course roster:

```sql
SELECT c.name, s.first_name, s.last_name
FROM enrollments e
         JOIN courses c ON e.course_id = c.course_id
         JOIN students s ON e.student_id = s.stu_id;
```

Instructors by department:

```sql
SELECT d.name AS Department, i.first_name, i.last_name
FROM instructors i
         JOIN departments d ON i.department_id = d.dept_id;
```

## 5. Local app connection

Set a local password when you create `app_user`, then export the same value before starting the app. Do not commit it.

```bash
export COURSE_REG_DB_PASSWORD='your-local-password'
./mvnw spring-boot:run
```

Committed config (`src/main/resources/application.properties`):

- URL: `jdbc:mysql://localhost:3306/course_registrations`
- Username: `app_user`
- Password: `${COURSE_REG_DB_PASSWORD}`
- Driver: `com.mysql.cj.jdbc.Driver`

`GET /health` returns `UP` when MySQL answers `SELECT 1`, and `DOWN` when it does not.

## Out of scope here

Docker and Flyway/Liquibase. Catalog and add-student APIs are later Phase 0 slices.
