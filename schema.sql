-- =====================================================================
-- Question 2: MySQL Database Schema & SQL Queries
-- Student Profile and Skill Development Management System (5 Marks - CO4)
-- =====================================================================

-- Step 1: Create Database
CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

-- Step 2: Create Students Table with constraints (Prevent duplicate student ID, scores between 0 and 100)
CREATE TABLE IF NOT EXISTS students (
    student_id BIGINT PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    course VARCHAR(50) NOT NULL,
    programming_score DOUBLE NOT NULL CHECK (programming_score BETWEEN 0 AND 100),
    mathematics_score DOUBLE NOT NULL CHECK (mathematics_score BETWEEN 0 AND 100)
);

-- Step 3: Create Student Technical Skills Table (One-to-Many relationship using Collections)
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
-- SQL QUERIES IMPLEMENTING THE 8 REQUIREMENTS:
-- =====================================================================

-- 1. Calculate each student's average score
SELECT student_id, student_name, 
       (programming_score + mathematics_score) / 2.0 AS average_score
FROM students;

-- 2. Assign performance category based on the average
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

-- 4. Search for students by ID, Email, or Course (e.g. course = 'CSE')
SELECT * FROM students WHERE student_id = 101 OR email = 'priya@gmail.com' OR course = 'CSE';

-- 5. Generate Merit List sorted by average score in descending order
SELECT student_id, student_name, course,
       (programming_score + mathematics_score) / 2.0 AS average_score
FROM students
ORDER BY average_score DESC;

-- 6. Identify students who have the same technical skills (e.g. 'Java' or 'MySQL')
SELECT s.skill, GROUP_CONCAT(st.student_name SEPARATOR ', ') AS students_with_skill, COUNT(st.student_id) AS student_count
FROM student_skills s
JOIN students st ON s.student_id = st.student_id
GROUP BY s.skill
HAVING COUNT(st.student_id) > 1;

-- 7. Display students who need improvement in programming (< 50) but math score >= 75
SELECT student_id, student_name, programming_score, mathematics_score
FROM students
WHERE programming_score < 50 AND mathematics_score >= 75;

-- 8. Duplicate Student ID Prevention & Score Validation (0 to 100)
-- Guaranteed by PRIMARY KEY on student_id and CHECK (score BETWEEN 0 AND 100).
