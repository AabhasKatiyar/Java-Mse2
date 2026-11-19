package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Student Entity.
 * Provides built-in CRUD operations and custom query methods.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Prevent duplicate student IDs
    boolean existsByStudentId(Long studentId);

    // Prevent duplicate emails
    boolean existsByEmail(String email);

    // Search by email
    Optional<Student> findByEmailIgnoreCase(String email);

    // Search by course
    List<Student> findByCourseIgnoreCase(String course);

    // Q2.7: Students needing programming improvement (< 50) with math score >= 75
    List<Student> findByProgrammingScoreLessThanAndMathematicsScoreGreaterThanEqual(Double prog, Double math);

    // Q2.6: Search students by technical skill
    @Query("SELECT s FROM Student s JOIN s.technicalSkills skill WHERE LOWER(skill) = LOWER(:skillName)")
    List<Student> findBySkill(@Param("skillName") String skillName);
}
