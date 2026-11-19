-- =====================================================================
-- QUESTION 2: MySQL Database Schema, Constraints & SQL Queries
-- Course: CS336B - Object Oriented Prog. Using Java (5 Marks - CO4)
-- Case Study: Student Profile and Skill Development Management System
-- =====================================================================

-- Step 1: Create Database
CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

-- Step 2: Create Students Table with constraints:
-- - Prevent duplicate student IDs (PRIMARY KEY)
-- - Validate scores between 0 and 100 (CHECK constraints)
CREATE TABLE IF NOT EXISTS students (
    student_id BIGINT PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    course VARCHAR(50) NOT NULL,
    programming_score DOUBLE NOT NULL CHECK (programming_score BETWEEN 0 AND 100),
    mathematics_score DOUBLE NOT NULL CHECK (mathematics_score BETWEEN 0 AND 100)
);

-- Step 3: Create Technical Skills Table (1-to-many relationship using collections)
CREATE TABLE IF NOT EXISTS student_skills (
    student_id BIGINT NOT NULL,
    skill VARCHAR(50) NOT NULL,
    PRIMARY KEY (student_id, skill),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

-- Step 4: Insert Sample Records for Testing
INSERT INTO students (student_id, student_name, email, course, programming_score, mathematics_score) VALUES
(101, 'Aabhas Katiyar', 'aabhas@gmail.com', 'CSE', 92.0, 88.0),
(102, 'Priya Sharma', 'priya@gmail.com', 'CSEAI', 45.0, 82.0),
(103, 'Rohan Verma', 'rohan@gmail.com', 'IT', 74.0, 78.0),
(104, 'Ananya Patel', 'ananya@gmail.com', 'CSE', 40.0, 48.0)
ON DUPLICATE KEY UPDATE student_name = VALUES(student_name);

INSERT INTO student_skills (student_id, skill) VALUES
(101, 'Java'),
(101, 'Spring Boot'),
(101, 'MySQL'),
(102, 'Python'),
(102, 'MySQL'),
(103, 'Java'),
(103, 'HTML/CSS'),
(104, 'C++')
ON DUPLICATE KEY UPDATE skill = VALUES(skill);

-- =====================================================================
-- THE 8 MANDATORY SQL QUERIES IMPLEMENTED:
-- =====================================================================

-- 1. Calculate each student's average score
SELECT student_id, student_name, 
       (programming_score + mathematics_score) / 2.0 AS average_score
FROM students;

-- 2. Assign a performance category based on the average:
--    - 85 or above: Excellent
--    - 70–84.99: Good
--    - 50–69.99: Satisfactory
--    - Below 50: Needs Improvement
SELECT student_id, student_name, 
       (programming_score + mathematics_score) / 2.0 AS average_score,
       CASE 
           WHEN (programming_score + mathematics_score) / 2.0 >= 85 THEN 'Excellent'
           WHEN (programming_score + mathematics_score) / 2.0 >= 70 THEN 'Good'
           WHEN (programming_score + mathematics_score) / 2.0 >= 50 THEN 'Satisfactory'
           ELSE 'Needs Improvement'
       END AS performance_category
FROM students;

-- 3. Identify the stronger subject for each student
SELECT student_id, student_name, programming_score, mathematics_score,
       CASE
           WHEN programming_score > mathematics_score THEN 'Programming'
           WHEN mathematics_score > programming_score THEN 'Mathematics'
           ELSE 'Equal'
       END AS stronger_subject
FROM students;

-- 4. Search for students by ID, Email or Course
-- Example 4a: Search by ID
SELECT * FROM students WHERE student_id = 101;

-- Example 4b: Search by Email
SELECT * FROM students WHERE email = 'priya@gmail.com';

-- Example 4c: Search by Course
SELECT * FROM students WHERE course = 'CSE';

-- 5. Generate a merit list sorted by average score in descending order
SELECT student_id, student_name, course,
       (programming_score + mathematics_score) / 2.0 AS average_score
FROM students
ORDER BY average_score DESC;

-- 6. Identify students who have the same technical skills
SELECT s.skill, 
       GROUP_CONCAT(st.student_name SEPARATOR ', ') AS students_with_skill, 
       COUNT(st.student_id) AS student_count
FROM student_skills s
JOIN students st ON s.student_id = st.student_id
GROUP BY s.skill
HAVING COUNT(st.student_id) > 1;

-- 7. Display students who need improvement in programming (< 50) but math score >= 75
SELECT student_id, student_name, course, programming_score, mathematics_score
FROM students
WHERE programming_score < 50 AND mathematics_score >= 75;

-- 8. Prevent duplicate student IDs and validate scores between 0 and 100
-- Enforced via PRIMARY KEY constraint on student_id and CHECK constraints on scores.
