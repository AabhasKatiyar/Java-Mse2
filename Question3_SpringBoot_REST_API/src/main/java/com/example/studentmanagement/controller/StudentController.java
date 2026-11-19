package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.StudentResponseDTO;
import com.example.studentmanagement.dto.StudentScoreSkillUpdateDTO;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Question 3: Spring Boot REST Controller (10 Marks - CO5)
 * Base Path: /api/students
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Q3.1: Add a student
     * HTTP Method: POST
     * Endpoint: /api/students
     * Status: 201 Created
     */
    @PostMapping
    public ResponseEntity<StudentResponseDTO> addStudent(@Valid @RequestBody Student student) {
        StudentResponseDTO savedStudent = studentService.addStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    /**
     * Q3.2: Retrieve all students
     * HTTP Method: GET
     * Endpoint: /api/students
     * Status: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    /**
     * Q3.6: Display the merit list (sorted by average score descending)
     * HTTP Method: GET
     * Endpoint: /api/students/merit-list
     * Status: 200 OK
     */
    @GetMapping("/merit-list")
    public ResponseEntity<List<StudentResponseDTO>> getMeritList() {
        List<StudentResponseDTO> meritList = studentService.getMeritList();
        return ResponseEntity.ok(meritList);
    }

    /**
     * Q3.3: Search for a student by ID
     * HTTP Method: GET
     * Endpoint: /api/students/{id}
     * Status: 200 OK (or 404 Not Found if missing)
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        StudentResponseDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    /**
     * Q3.4: Update student scores and skills
     * HTTP Method: PUT
     * Endpoint: /api/students/{id}
     * Status: 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateScoresAndSkills(
            @PathVariable Long id,
            @Valid @RequestBody StudentScoreSkillUpdateDTO updateDTO) {
        StudentResponseDTO updated = studentService.updateScoresAndSkills(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Q3.5: Delete a student record
     * HTTP Method: DELETE
     * Endpoint: /api/students/{id}
     * Status: 200 OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(Map.of("message", "Student with ID " + id + " deleted successfully."));
    }

    // =========================================================================
    // ADDITIONAL ENDPOINTS SATISFYING ALL QUESTION 2 REQUIREMENTS:
    // =========================================================================

    /**
     * Q2.4: Search for students by email or course
     * Endpoint: /api/students/search?email=... OR /api/students/search?course=...
     */
    @GetMapping("/search")
    public ResponseEntity<List<StudentResponseDTO>> searchStudents(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String course) {
        List<StudentResponseDTO> results = studentService.searchStudents(email, course);
        return ResponseEntity.ok(results);
    }

    /**
     * Q2.6: Identify students who have the same technical skills
     * Endpoint: /api/students/skills-grouped
     */
    @GetMapping("/skills-grouped")
    public ResponseEntity<Map<String, List<StudentResponseDTO>>> getStudentsGroupedBySkills() {
        Map<String, List<StudentResponseDTO>> grouped = studentService.getStudentsGroupedBySkills();
        return ResponseEntity.ok(grouped);
    }

    /**
     * Q2.7: Display students who need improvement in programming (< 50) but math score >= 75
     * Endpoint: /api/students/programming-improvement-math-strong
     */
    @GetMapping("/programming-improvement-math-strong")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsNeedingImprovement() {
        List<StudentResponseDTO> students = studentService.getStudentsNeedingProgrammingImprovement();
        return ResponseEntity.ok(students);
    }
}
