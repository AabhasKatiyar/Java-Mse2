package com.example.studentmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Question 1: Student Class (5 Marks - CO4)
 * Represents student personal information, academic scores, and technical skill interests.
 * Features:
 * - Encapsulation (private fields, public getters/setters)
 * - Constructors (no-arg, parameterized)
 * - Java Collections Framework (List<String> for technical skills)
 * - Object Methods (Average calculation, Performance Category, Stronger Subject, toString, equals)
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @NotNull(message = "Student ID cannot be null")
    @Column(name = "student_id", unique = true, nullable = false)
    private Long studentId;

    @NotBlank(message = "Student name is required")
    @Column(name = "student_name", nullable = false, length = 100)
    private String studentName;

    @NotBlank(message = "Email address is required")
    @Email(message = "Invalid email format")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Course name is required")
    @Column(name = "course", nullable = false, length = 50)
    private String course;

    @NotNull(message = "Programming score is required")
    @Min(value = 0, message = "Programming score cannot be less than 0")
    @Max(value = 100, message = "Programming score cannot be greater than 100")
    @Column(name = "programming_score", nullable = false)
    private Double programmingScore;

    @NotNull(message = "Mathematics score is required")
    @Min(value = 0, message = "Mathematics score cannot be less than 0")
    @Max(value = 100, message = "Mathematics score cannot be greater than 100")
    @Column(name = "mathematics_score", nullable = false)
    private Double mathematicsScore;

    // Use of Java Collections Framework (List<String>)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_skills", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "skill")
    private List<String> technicalSkills = new ArrayList<>();

    // 1. Default No-Argument Constructor (Required by JPA / Hibernate)
    public Student() {
    }

    // 2. Parameterized Constructor
    public Student(Long studentId, String studentName, String email, String course, 
                   Double programmingScore, Double mathematicsScore, List<String> technicalSkills) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.course = course;
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        this.technicalSkills = technicalSkills != null ? technicalSkills : new ArrayList<>();
    }

    // ==========================================
    // GETTERS & SETTERS (Encapsulation)
    // ==========================================
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(Double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public Double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(Double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills != null ? technicalSkills : new ArrayList<>();
    }

    // ==========================================
    // OBJECT METHODS & BUSINESS RULES (Q2)
    // ==========================================

    /**
     * Requirement 1: Calculate student's average score
     */
    public Double getAverageScore() {
        if (programmingScore == null || mathematicsScore == null) return 0.0;
        return (programmingScore + mathematicsScore) / 2.0;
    }

    /**
     * Requirement 2: Assign performance category based on average:
     * - 85 or above: Excellent
     * - 70 to 84.99: Good
     * - 50 to 69.99: Satisfactory
     * - Below 50: Needs Improvement
     */
    public String getPerformanceCategory() {
        double avg = getAverageScore();
        if (avg >= 85.0) {
            return "Excellent";
        } else if (avg >= 70.0) {
            return "Good";
        } else if (avg >= 50.0) {
            return "Satisfactory";
        } else {
            return "Needs Improvement";
        }
    }

    /**
     * Requirement 3: Identify the stronger subject
     */
    public String getStrongerSubject() {
        if (programmingScore == null || mathematicsScore == null) return "Unknown";
        if (programmingScore > mathematicsScore) {
            return "Programming";
        } else if (mathematicsScore > programmingScore) {
            return "Mathematics";
        } else {
            return "Equal";
        }
    }

    /**
     * Requirement 7: Check if student needs improvement in programming (< 50) but has math score >= 75
     */
    public boolean needsProgrammingImprovementWithMathAtLeast75() {
        return programmingScore != null && mathematicsScore != null 
                && programmingScore < 50.0 && mathematicsScore >= 75.0;
    }

    // ==========================================
    // STANDARD OBJECT METHODS
    // ==========================================
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", email='" + email + '\'' +
                ", course='" + course + '\'' +
                ", programmingScore=" + programmingScore +
                ", mathematicsScore=" + mathematicsScore +
                ", averageScore=" + getAverageScore() +
                ", performanceCategory='" + getPerformanceCategory() + '\'' +
                ", strongerSubject='" + getStrongerSubject() + '\'' +
                ", technicalSkills=" + technicalSkills +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}
