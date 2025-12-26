### SAMPLE DATA

### Populate sample users data

```sql
INSERT INTO users (name, email)
VALUES ('System Admin', 'admin@kalaam-uni.edu'),
('Registrar Office', 'registrar@kalaam-uni.edu'),
('Academic Advisor', 'advisor_01@kalaam-uni.edu');
```

### Populate sample departments data

```sql
INSERT INTO departments (name, code)
VALUES ('Computer Science', 'CS'),
('Mechanical Engineering', 'MECH'),
('Department of Mathematics', 'MATH'),
('Business Administration', 'BUS');
```

### Populate sample instructors data

```sql
INSERT INTO instructors (first_name, last_name, email, department_id)
VALUES ('Alan', 'Turing', 'a.turing@university.edu', 1),
('Ada', 'Lovelace', 'a.lovelace@university.edu', 1),
('Isaac', 'Newton', 'i.newton@university.edu', 3),
('Katherine', 'Johnson', 'k.johnson@university.edu', 3),
('Mary', 'Barra', 'm.barra@university.edu', 4);
```

### Populate sample courses data

```sql
INSERT INTO courses (code, name, credits, capacity, department_id, instructor_id)
VALUES ('CS101', 'Introduction to Programming', 4, 50, 1, 1),
('CS202', 'Data Structures & Algorithms', 4, 40, 1, 2),
('MATH150', 'Calculus I', 3, 60, 3, 3),
('MATH300', 'Linear Algebra', 3, 30, 3, 4),
('BUS110', 'Principles of Management', 3, 100, 4, 5);
```

### Populate sample students data

```sql
INSERT INTO students (student_number, first_name, last_name, email, enrollment_year)
VALUES ('STU-2025-001', 'John', 'Doe', 'j.doe@student.edu', 2025),
('STU-2025-002', 'Jane', 'Smith', 'j.smith@student.edu', 2025),
('STU-2024-045', 'Robert', 'Brown', 'r.brown@student.edu', 2024),
('STU-2023-099', 'Emily', 'Davis', 'e.davis@student.edu', 2023);
```

### Populate sample enrollments data

```sql
INSERT INTO enrollments (student_id, course_id, enrollment_date, grade, status)
VALUES (1, 1, '2025-01-10 09:00:00', NULL, 'ENROLLED'),
(1, 3, '2025-01-10 09:15:00', NULL, 'ENROLLED'),
(2, 1, '2025-01-11 10:30:00', NULL, 'ENROLLED'),
(2, 5, '2025-01-11 10:45:00', NULL, 'ENROLLED'),
(3, 2, '2025-01-05 14:00:00', 'A', 'COMPLETED'),
(4, 4, '2025-01-02 11:00:00', 'B+', 'COMPLETED');
```