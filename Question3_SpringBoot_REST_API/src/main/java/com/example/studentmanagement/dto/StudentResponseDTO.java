package com.example.studentmanagement.dto;

import com.example.studentmanagement.model.Student;
import java.util.List;

/**
 * Data Transfer Object representing complete Student Profile
 * including all calculated business attributes (Average, Category, Stronger Subject).
 */
public class StudentResponseDTO {

    private Long studentId;
    private String studentName;
    private String email;
    private String course;
    private Double programmingScore;
    private Double mathematicsScore;
    private Double averageScore;
    private String performanceCategory;
    private String strongerSubject;
    private List<String> technicalSkills;

    public StudentResponseDTO() {
    }

    public StudentResponseDTO(Student s) {
        this.studentId = s.getStudentId();
        this.studentName = s.getStudentName();
        this.email = s.getEmail();
        this.course = s.getCourse();
        this.programmingScore = s.getProgrammingScore();
        this.mathematicsScore = s.getMathematicsScore();
        this.averageScore = s.getAverageScore();
        this.performanceCategory = s.getPerformanceCategory();
        this.strongerSubject = s.getStrongerSubject();
        this.technicalSkills = s.getTechnicalSkills();
    }

    // Getters and Setters
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

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public String getPerformanceCategory() {
        return performanceCategory;
    }

    public void setPerformanceCategory(String performanceCategory) {
        this.performanceCategory = performanceCategory;
    }

    public String getStrongerSubject() {
        return strongerSubject;
    }

    public void setStrongerSubject(String strongerSubject) {
        this.strongerSubject = strongerSubject;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills;
    }
}
