package question1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Question 1: Student Class (5 Marks - CO4)
 * 
 * Attributes:
 * - Student ID
 * - Student Name
 * - Email Address
 * - Course Name
 * - Programming Score
 * - Mathematics Score
 * - List of Technical Skills
 * 
 * Demonstrates:
 * - Encapsulation (private fields, public getters/setters)
 * - Constructors (default & parameterized)
 * - Java Collections Framework (List<String>)
 * - Object Methods (Average, Performance Category, Stronger Subject, toString, equals)
 */
public class Student {

    // Private Attributes (Encapsulation)
    private Long studentId;
    private String studentName;
    private String email;
    private String course;
    private Double programmingScore;
    private Double mathematicsScore;
    private List<String> technicalSkills;

    // 1. Default No-Argument Constructor
    public Student() {
        this.technicalSkills = new ArrayList<>();
    }

    // 2. Parameterized Constructor
    public Student(Long studentId, String studentName, String email, String course,
                   Double programmingScore, Double mathematicsScore, List<String> technicalSkills) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.course = course;
        setProgrammingScore(programmingScore); // Validated setter
        setMathematicsScore(mathematicsScore); // Validated setter
        this.technicalSkills = technicalSkills != null ? new ArrayList<>(technicalSkills) : new ArrayList<>();
    }

    // ==========================================
    // GETTERS & SETTERS (Encapsulation with Validation)
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
        if (programmingScore != null && (programmingScore < 0 || programmingScore > 100)) {
            throw new IllegalArgumentException("Programming score must be between 0 and 100. Provided: " + programmingScore);
        }
        this.programmingScore = programmingScore;
    }

    public Double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(Double mathematicsScore) {
        if (mathematicsScore != null && (mathematicsScore < 0 || mathematicsScore > 100)) {
            throw new IllegalArgumentException("Mathematics score must be between 0 and 100. Provided: " + mathematicsScore);
        }
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills != null ? new ArrayList<>(technicalSkills) : new ArrayList<>();
    }

    public void addSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty()) {
            this.technicalSkills.add(skill.trim());
        }
    }

    // ==========================================
    // OBJECT METHODS & BUSINESS RULES
    // ==========================================

    /**
     * Requirement: Calculate student's average score
     */
    public Double calculateAverageScore() {
        if (programmingScore == null || mathematicsScore == null) return 0.0;
        return (programmingScore + mathematicsScore) / 2.0;
    }

    /**
     * Requirement: Assign performance category based on average:
     * - 85 or above: Excellent
     * - 70 to 84.99: Good
     * - 50 to 69.99: Satisfactory
     * - Below 50: Needs Improvement
     */
    public String getPerformanceCategory() {
        double avg = calculateAverageScore();
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
     * Requirement: Identify the stronger subject
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
     * Requirement: Needs improvement in programming (< 50) but math score >= 75
     */
    public boolean needsProgrammingImprovementWithMathAtLeast75() {
        return programmingScore != null && mathematicsScore != null 
                && programmingScore < 50.0 && mathematicsScore >= 75.0;
    }

    // Standard Object Methods
    @Override
    public String toString() {
        return String.format("Student[ID=%d, Name='%s', Course='%s', Prog=%.1f, Math=%.1f, Avg=%.2f, Category='%s', Stronger='%s', Skills=%s]",
                studentId, studentName, course, programmingScore, mathematicsScore,
                calculateAverageScore(), getPerformanceCategory(), getStrongerSubject(), technicalSkills);
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
