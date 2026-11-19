package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.dto.StudentResponseDTO;
import com.example.studentmanagement.dto.StudentScoreSkillUpdateDTO;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of StudentService incorporating business rules
 * and extensive use of Java Collections Framework (List, ArrayList, Map, HashMap, Comparator).
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Q3.1: Add a Student
     * Validates:
     * - Duplicate student ID prevention (Q2.8)
     * - Duplicate email prevention
     * - Score validation between 0 and 100 (Q2.8)
     */
    @Override
    public StudentResponseDTO addStudent(Student student) {
        // Prevent duplicate student IDs
        if (studentRepository.existsByStudentId(student.getStudentId())) {
            throw new IllegalArgumentException("Duplicate Error: Student with ID " + student.getStudentId() + " already exists!");
        }

        // Prevent duplicate emails
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("Duplicate Error: Email " + student.getEmail() + " is already registered!");
        }

        // Validate score boundaries (0 to 100)
        validateScore(student.getProgrammingScore(), "Programming");
        validateScore(student.getMathematicsScore(), "Mathematics");

        Student saved = studentRepository.save(student);
        return new StudentResponseDTO(saved);
    }

    /**
     * Q3.2: Retrieve All Students
     * Returns a List<StudentResponseDTO> collection
     */
    @Override
    public List<StudentResponseDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        // Use of Java Collections & Streams to transform into DTOs
        return students.stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Q3.3: Search for a student by ID
     * Uses Optional<Student>
     */
    @Override
    public StudentResponseDTO getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student with ID " + studentId + " not found."));
        return new StudentResponseDTO(student);
    }

    /**
     * Q3.4: Update student scores and skills
     */
    @Override
    public StudentResponseDTO updateScoresAndSkills(Long studentId, StudentScoreSkillUpdateDTO dto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update. Student with ID " + studentId + " not found."));

        validateScore(dto.getProgrammingScore(), "Programming");
        validateScore(dto.getMathematicsScore(), "Mathematics");

        student.setProgrammingScore(dto.getProgrammingScore());
        student.setMathematicsScore(dto.getMathematicsScore());
        if (dto.getTechnicalSkills() != null) {
            student.setTechnicalSkills(new ArrayList<>(dto.getTechnicalSkills()));
        }

        Student updated = studentRepository.save(student);
        return new StudentResponseDTO(updated);
    }

    /**
     * Q3.5: Delete a student record
     */
    @Override
    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Cannot delete. Student with ID " + studentId + " not found.");
        }
        studentRepository.deleteById(studentId);
    }

    /**
     * Q3.6 & Q2.5: Generate and Display the Merit List
     * Sorted by Average Score in Descending Order using Java Collections (Comparator)
     */
    @Override
    public List<StudentResponseDTO> getMeritList() {
        List<Student> allStudents = studentRepository.findAll();

        // Convert to DTOs
        List<StudentResponseDTO> meritList = allStudents.stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());

        // Sort using Collections & Comparator
        meritList.sort(Comparator.comparingDouble(StudentResponseDTO::getAverageScore).reversed());

        return meritList;
    }

    /**
     * Q2.4: Search by Email or Course
     */
    @Override
    public List<StudentResponseDTO> searchStudents(String email, String course) {
        if (email != null && !email.trim().isEmpty()) {
            return studentRepository.findByEmailIgnoreCase(email)
                    .map(s -> Collections.singletonList(new StudentResponseDTO(s)))
                    .orElse(Collections.emptyList());
        }

        if (course != null && !course.trim().isEmpty()) {
            return studentRepository.findByCourseIgnoreCase(course).stream()
                    .map(StudentResponseDTO::new)
                    .collect(Collectors.toList());
        }

        return getAllStudents();
    }

    /**
     * Q2.6: Identify students who have the same technical skills
     * Returns a Map<String, List<StudentResponseDTO>> grouping students by skill
     */
    @Override
    public Map<String, List<StudentResponseDTO>> getStudentsGroupedBySkills() {
        List<Student> allStudents = studentRepository.findAll();
        Map<String, List<StudentResponseDTO>> skillMap = new HashMap<>();

        for (Student student : allStudents) {
            StudentResponseDTO dto = new StudentResponseDTO(student);
            for (String skill : student.getTechnicalSkills()) {
                String normalizedSkill = skill.trim();
                skillMap.computeIfAbsent(normalizedSkill, k -> new ArrayList<>()).add(dto);
            }
        }

        return skillMap;
    }

    /**
     * Q2.7: Display students needing programming improvement (< 50) but math score >= 75
     */
    @Override
    public List<StudentResponseDTO> getStudentsNeedingProgrammingImprovement() {
        return studentRepository.findByProgrammingScoreLessThanAndMathematicsScoreGreaterThanEqual(50.0, 75.0)
                .stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
    }

    private void validateScore(Double score, String subject) {
        if (score == null || score < 0.0 || score > 100.0) {
            throw new IllegalArgumentException(subject + " score must be between 0 and 100. Provided: " + score);
        }
    }
}
