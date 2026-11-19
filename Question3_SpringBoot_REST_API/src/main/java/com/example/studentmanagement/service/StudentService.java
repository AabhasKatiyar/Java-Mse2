package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.StudentResponseDTO;
import com.example.studentmanagement.dto.StudentScoreSkillUpdateDTO;
import com.example.studentmanagement.model.Student;

import java.util.List;
import java.util.Map;

/**
 * Service Interface defining business logic operations for Student Management.
 */
public interface StudentService {

    // Q3.1: Add a student (Checks duplicates, validates 0-100)
    StudentResponseDTO addStudent(Student student);

    // Q3.2: Retrieve all students
    List<StudentResponseDTO> getAllStudents();

    // Q3.3: Search for a student by ID
    StudentResponseDTO getStudentById(Long studentId);

    // Q3.4: Update student scores and skills
    StudentResponseDTO updateScoresAndSkills(Long studentId, StudentScoreSkillUpdateDTO dto);

    // Q3.5: Delete a student record
    void deleteStudent(Long studentId);

    // Q3.6: Display the merit list (sorted by average score descending using Collections)
    List<StudentResponseDTO> getMeritList();

    // Q2.4: Search by Email or Course
    List<StudentResponseDTO> searchStudents(String email, String course);

    // Q2.6: Identify students with the same technical skills (Uses Map<String, List<StudentResponseDTO>>)
    Map<String, List<StudentResponseDTO>> getStudentsGroupedBySkills();

    // Q2.7: Display students needing programming improvement (< 50) but math >= 75
    List<StudentResponseDTO> getStudentsNeedingProgrammingImprovement();
}
