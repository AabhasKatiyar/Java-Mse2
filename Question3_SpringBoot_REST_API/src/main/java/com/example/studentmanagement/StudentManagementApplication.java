package com.example.studentmanagement;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class StudentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
        System.out.println("\n=================================================================");
        System.out.println(" Student Profile & Skill Development Management System Started!");
        System.out.println(" Server Port: 8080");
        System.out.println(" Base URL:    http://localhost:8080/api/students");
        System.out.println(" Merit List:  http://localhost:8080/api/students/merit-list");
        System.out.println(" Connected Database: MySQL 'student_db'");
        System.out.println("=================================================================\n");
    }

    /**
     * Seeds initial records into MySQL if database is empty
     */
    @Bean
    public CommandLineRunner initDatabase(StudentRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                // Student 1: Excellent (Avg: 90.0)
                Student s1 = new Student(
                        101L,
                        "Aabhas Katiyar",
                        "aabhas@gmail.com",
                        "CSE",
                        92.0,
                        88.0,
                        Arrays.asList("Java", "Spring Boot", "MySQL")
                );

                // Student 2: Needs improvement in Programming (<50) but Math >= 75 (Avg: 63.5)
                Student s2 = new Student(
                        102L,
                        "Priya Sharma",
                        "priya@gmail.com",
                        "CSEAI",
                        45.0,
                        82.0,
                        Arrays.asList("Python", "MySQL")
                );

                // Student 3: Good (Avg: 76.0)
                Student s3 = new Student(
                        103L,
                        "Rohan Verma",
                        "rohan@gmail.com",
                        "IT",
                        74.0,
                        78.0,
                        Arrays.asList("Java", "HTML/CSS")
                );

                // Student 4: Needs Improvement (Avg: 44.0)
                Student s4 = new Student(
                        104L,
                        "Ananya Patel",
                        "ananya@gmail.com",
                        "CSE",
                        40.0,
                        48.0,
                        Arrays.asList("C++")
                );

                repository.saveAll(Arrays.asList(s1, s2, s3, s4));
                System.out.println("-> [DATABASE SEED]: 4 sample student records seeded into MySQL successfully!");
            }
        };
    }
}
