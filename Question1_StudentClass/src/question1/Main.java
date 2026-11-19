package question1;

import java.util.Arrays;
import java.util.List;

/**
 * Runner / Test Driver for Question 1: Student Class
 * Demonstrates creation of student objects, encapsulation, collections, and object methods.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println(" QUESTION 1: STUDENT PROFILE & OBJECT-ORIENTED PROGRAMMING DEMO");
        System.out.println("======================================================================\n");

        // 1. Create Student 1 (Excellent performance)
        Student s1 = new Student(
                101L,
                "Aabhas Katiyar",
                "aabhas@gmail.com",
                "CSE",
                92.0,
                88.0,
                Arrays.asList("Java", "Spring Boot", "MySQL")
        );

        // 2. Create Student 2 (Needs improvement in Programming, but Math >= 75)
        Student s2 = new Student(
                102L,
                "Priya Sharma",
                "priya@gmail.com",
                "CSEAI",
                45.0,
                82.0,
                Arrays.asList("Python", "MySQL")
        );

        // 3. Create Student 3 (Satisfactory performance)
        Student s3 = new Student(
                103L,
                "Rohan Verma",
                "rohan@gmail.com",
                "IT",
                74.0,
                78.0,
                Arrays.asList("Java", "HTML/CSS")
        );

        List<Student> students = Arrays.asList(s1, s2, s3);

        for (Student s : students) {
            System.out.println("Student ID:             " + s.getStudentId());
            System.out.println("Name:                   " + s.getStudentName());
            System.out.println("Course:                 " + s.getCourse());
            System.out.println("Email:                  " + s.getEmail());
            System.out.println("Programming Score:      " + s.getProgrammingScore());
            System.out.println("Mathematics Score:      " + s.getMathematicsScore());
            System.out.println("Average Score:          " + s.calculateAverageScore());
            System.out.println("Performance Category:   " + s.getPerformanceCategory());
            System.out.println("Stronger Subject:       " + s.getStrongerSubject());
            System.out.println("Technical Skills:       " + s.getTechnicalSkills());
            System.out.println("Needs Prog Improvement (Math >= 75): " + s.needsProgrammingImprovementWithMathAtLeast75());
            System.out.println("----------------------------------------------------------------------");
        }

        // Test Score Validation (0 to 100)
        System.out.println("\nTesting Score Validation (< 0 or > 100):");
        try {
            s1.setProgrammingScore(150.0);
        } catch (IllegalArgumentException e) {
            System.out.println("-> Expected Validation Caught: " + e.getMessage());
        }
    }
}
