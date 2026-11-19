import os
import docx
from docx.shared import Inches, Pt, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH

base_dir = r"c:\Users\abhas\OneDrive\Desktop\Java Mse2"
template_path = os.path.join(base_dir, "OOPJ Students  Moodle Submission Format.docx")
screenshots_dir = os.path.join(base_dir, "screenshots")

def read_code(rel_path):
    with open(os.path.join(base_dir, rel_path), "r", encoding="utf-8") as f:
        return f.read()

def populate_exact_template():
    doc = docx.Document(template_path)

    # ---------------------------------------------------------
    # 1. TABLE 0: Student Information
    # ---------------------------------------------------------
    t0 = doc.tables[0]
    t0.rows[0].cells[1].text = "Aabhas Katiyar"
    t0.rows[1].cells[1].text = "Information Technology"
    t0.rows[2].cells[0].text = "Registration Number"
    t0.rows[2].cells[1].text = "202501100600001"
    t0.rows[3].cells[1].text = "A"
    t0.rows[4].cells[1].text = "Morning Shift (10:00 AM - 12:30 PM)"
    t0.rows[5].cells[1].text = "Student Profile and Skill Development Management System"

    for r in t0.rows:
        for c in r.cells:
            for p in c.paragraphs:
                for run in p.runs:
                    run.font.name = "Calibri"
                    run.font.size = Pt(11)

    # ---------------------------------------------------------
    # 2. SECTION Q1: Question 1 (Standalone Student OOP Model)
    # ---------------------------------------------------------
    # Table 1: GitHub Link
    t1 = doc.tables[1]
    t1.rows[0].cells[0].text = "GitHub Repository: https://github.com/AabhasKatiyar/Java-Mse2"
    t1.rows[1].cells[0].text = "https://github.com/AabhasKatiyar/Java-Mse2"
    for r in t1.rows:
        for p in r.cells[0].paragraphs:
            for run in p.runs:
                run.font.name = "Calibri"
                run.font.size = Pt(11)

    # Paragraph 10: Project code for Q1
    q1_student = read_code(r"Question1_StudentClass\src\question1\Student.java")
    q1_main = read_code(r"Question1_StudentClass\src\question1\Main.java")
    p10 = doc.paragraphs[10]
    p10.text = (
        "=== QUESTION 1: STUDENT MODEL CLASS (Question1_StudentClass/src/question1/Student.java) ===\n" +
        q1_student + "\n\n" +
        "=== QUESTION 1: TEST RUNNER (Question1_StudentClass/src/question1/Main.java) ===\n" +
        q1_main
    )
    for run in p10.runs:
        run.font.name = "Courier New"
        run.font.size = Pt(9)

    # Paragraphs 25, 26: Screenshots for Q1
    p25 = doc.paragraphs[25]
    p25.text = "Figure 1.1: Question 1 Execution - Student Profile, Scores, Category & Stronger Subject Console Output\n"
    r25_img = p25.add_run()
    r25_img.add_picture(os.path.join(screenshots_dir, "Screenshot 2026-11-19 105016.png"), width=Inches(5.8))

    p26 = doc.paragraphs[26]
    p26.text = "Figure 1.2: Question 1 Execution - Score Validation (0-100) Exception Handling in Console\n"
    r26_img = p26.add_run()
    r26_img.add_picture(os.path.join(screenshots_dir, "Screenshot 2026-11-19 105100.png"), width=Inches(5.8))

    # Paragraph 30: Project Structure for Q1
    p30 = doc.paragraphs[30]
    p30.text = "Figure 1.3: IntelliJ IDEA Project Structure Window - Question 1 Module Structure\n"
    r30_img = p30.add_run()
    r30_img.add_picture(os.path.join(screenshots_dir, "Screenshot 2026-11-19 104758.png"), width=Inches(5.8))

    # Table 2: AI Prompts for Q1
    t2 = doc.tables[2]
    q1_prompts = [
        ("Prompt 1: Student OOP Model & Encapsulation",
         '"Design an encapsulated Java class Student in Question1_StudentClass with private attributes studentId, studentName, email, course, programmingScore, mathematicsScore, and technicalSkills (List<String>), complete with constructor, getters, and setters."\n\n'
         '-> Reflection: The AI established clean encapsulation with private fields and standard accessors. I verified getter and setter functionality using unit test scenarios in Main.java.'),
        ("Prompt 2: Academic Calculation & Categorization",
         '"Implement business logic methods in Student.java: calculateAverageScore(), getPerformanceCategory() using grade thresholds (85+ Excellent, 70-84.99 Good, 50-69.99 Satisfactory, Below 50 Needs Improvement), getStrongerSubject(), and toString()."\n\n'
         '-> Reflection: The business methods accurately compute arithmetic mean and map categories. I tested boundary values (85.0, 70.0, 50.0) in Main.java and verified output strings.'),
        ("Prompt 3: Score Validation & Test Runner",
         '"Add validation in Student.java ensuring scores are between 0 and 100, throwing IllegalArgumentException otherwise. Implement Main.java to instantiate sample students, display formatted profile cards, and test exception handling."\n\n'
         '-> Reflection: Executed Main.java in IntelliJ IDEA console, confirming expected profile outputs and verifying that invalid score inputs are caught with descriptive exception messages.')
    ]
    for idx, (p_title, p_body) in enumerate(q1_prompts):
        t2.rows[idx].cells[0].text = p_title
        t2.rows[idx].cells[1].text = p_body
        for c in t2.rows[idx].cells:
            for p in c.paragraphs:
                for run in p.runs:
                    run.font.name = "Calibri"
                    run.font.size = Pt(10)

    # ---------------------------------------------------------
    # 3. SECTION Q2: Question 2 (MySQL Database & JDBC Application)
    # ---------------------------------------------------------
    # Table 3: GitHub Link
    t3 = doc.tables[3]
    t3.rows[0].cells[0].text = "GitHub Repository: https://github.com/AabhasKatiyar/Java-Mse2"
    t3.rows[1].cells[0].text = "https://github.com/AabhasKatiyar/Java-Mse2"
    for r in t3.rows:
        for p in r.cells[0].paragraphs:
            for run in p.runs:
                run.font.name = "Calibri"
                run.font.size = Pt(11)

    # Paragraph 48: Project code for Q2
    q2_schema = read_code(r"Question2_MySQL_Database\schema.sql")
    q2_jdbc = read_code(r"Question2_MySQL_Database\src\question2\StudentDatabaseApp.java")
    p48 = doc.paragraphs[48]
    p48.text = (
        "=== QUESTION 2: MYSQL SCHEMA & 8 SQL QUERIES (Question2_MySQL_Database/schema.sql) ===\n" +
        q2_schema + "\n\n" +
        "=== QUESTION 2: INTERACTIVE JDBC CONSOLE APPLICATION (StudentDatabaseApp.java) ===\n" +
        q2_jdbc
    )
    for run in p48.runs:
        run.font.name = "Courier New"
        run.font.size = Pt(9)

    # Paragraphs 63, 64, 65, 66: Screenshots for Q2
    p63 = doc.paragraphs[63]
    p63.text = "Figure 2.1: MySQL Workbench - student_db Schema Creation and Students Table Records Grid\n"
    r63_img = p63.add_run()
    r63_img.add_picture(os.path.join(screenshots_dir, "Screenshot 2026-11-19 102253.png"), width=Inches(5.8))

    p64 = doc.paragraphs[64]
    p64.text = "Figure 2.2: Question 2 JDBC Application - Option 1 & 2 Execution: Average Score & Performance Category Table\n"
    r64_img = p64.add_run()
    r64_img.add_picture(os.path.join(screenshots_dir, "Screenshot 2026-11-19 105247.png"), width=Inches(5.8))

    p65 = doc.paragraphs[65]
    p65.text = "Figure 2.3: Question 2 JDBC Application - Option 6 Execution: Academic Merit List (Sorted by Average Score DESC)\n"
    r65_img = p65.add_run()
    r65_img.add_picture(os.path.join(screenshots_dir, "Screenshot 2026-11-19 105302.png"), width=Inches(5.8))

    p66 = doc.paragraphs[66]
    p66.text = "Figure 2.4: Question 2 JDBC Application - Option 9 Execution: Duplicate Student ID & Email Violation Prevention Caught\n"
    r66_img = p66.add_run()
    r66_img.add_picture(os.path.join(screenshots_dir, "Screenshot 2026-11-19 105848.png"), width=Inches(5.8))

    # Paragraph 68: Project Structure for Q2
    p68 = doc.paragraphs[68]
    p68.text = "Figure 2.5: IntelliJ IDEA Project Structure Window - Question 2 MySQL JDBC Module Structure\n"
    r68_img = p68.add_run()
    r68_img.add_picture(os.path.join(screenshots_dir, "Screenshot 2026-11-19 104758.png"), width=Inches(5.8))

    # Table 4: AI Prompts for Q2
    t4 = doc.tables[4]
    q2_prompts = [
        ("Prompt 1: Relational Schema & Constraints",
         '"Create MySQL schema schema.sql with students and student_skills tables. Enforce PRIMARY KEY (student_id) to prevent duplicate IDs and CHECK (score BETWEEN 0 AND 100)."\n\n'
         '-> Reflection: The relational schema with foreign keys and cascading deletes maintained data integrity. Executed schema.sql in MySQL Workbench and verified table structures and constraints.'),
        ("Prompt 2: SQL Analytics & Performance Queries",
         '"Write SQL queries covering all 8 requirements: calculating averages, performance category CASE statement, stronger subject, search by ID/email/course, merit list DESC, shared skills, and programming improvement filtering."\n\n'
         '-> Reflection: Tested all 8 queries directly in MySQL Workbench. Aggregate queries and conditional expressions produced accurate merit list rankings and skill-group associations.'),
        ("Prompt 3: Menu-Driven JDBC Application",
         '"Develop StudentDatabaseApp.java in Question2_MySQL_Database connecting via JDBC, providing interactive console choices 1-10 with PreparedStatement queries and active duplicate ID verification."\n\n'
         '-> Reflection: Built PreparedStatement queries to prevent SQL injection. Ran the interactive console menu in IntelliJ IDEA, testing average calculations, merit list display, and duplicate key violation blocking.')
    ]
    for idx, (p_title, p_body) in enumerate(q2_prompts):
        t4.rows[idx].cells[0].text = p_title
        t4.rows[idx].cells[1].text = p_body
        for c in t4.rows[idx].cells:
            for p in c.paragraphs:
                for run in p.runs:
                    run.font.name = "Calibri"
                    run.font.size = Pt(10)

    # ---------------------------------------------------------
    # 4. SECTION Q3: Question 3 (Spring Boot REST API)
    # ---------------------------------------------------------
    # Add identical template elements for Q3
    p_q3 = doc.add_paragraph("Q3.")
    p_q3.style = "Normal"
    for r in p_q3.runs:
        r.font.name = "Calibri"
        r.font.size = Pt(14)
        r.font.bold = True

    p_q3_link = doc.add_paragraph("Moodle upload / GitHub Repository Link ")
    p_q3_link.style = "List Paragraph"

    t5 = doc.add_table(rows=2, cols=1)
    t5.style = "Table Grid"
    t5.rows[0].cells[0].text = "GitHub Repository: https://github.com/AabhasKatiyar/Java-Mse2"
    t5.rows[1].cells[0].text = "https://github.com/AabhasKatiyar/Java-Mse2"
    for r in t5.rows:
        for p in r.cells[0].paragraphs:
            for run in p.runs:
                run.font.name = "Calibri"
                run.font.size = Pt(11)

    p_q3_code_title = doc.add_paragraph("Project code ")
    p_q3_code_title.style = "List Paragraph"

    q3_entity = read_code(r"Question3_SpringBoot_REST_API\src\main\java\com\example\studentmanagement\model\Student.java")
    q3_repo = read_code(r"Question3_SpringBoot_REST_API\src\main\java\com\example\studentmanagement\repository\StudentRepository.java")
    q3_dto_resp = read_code(r"Question3_SpringBoot_REST_API\src\main\java\com\example\studentmanagement\dto\StudentResponseDTO.java")
    q3_dto_upd = read_code(r"Question3_SpringBoot_REST_API\src\main\java\com\example\studentmanagement\dto\StudentScoreSkillUpdateDTO.java")
    q3_service_intf = read_code(r"Question3_SpringBoot_REST_API\src\main\java\com\example\studentmanagement\service\StudentService.java")
    q3_service_impl = read_code(r"Question3_SpringBoot_REST_API\src\main\java\com\example\studentmanagement\service\impl\StudentServiceImpl.java")
    q3_controller = read_code(r"Question3_SpringBoot_REST_API\src\main\java\com\example\studentmanagement\controller\StudentController.java")
    q3_ex_handler = read_code(r"Question3_SpringBoot_REST_API\src\main\java\com\example\studentmanagement\exception\GlobalExceptionHandler.java")
    q3_ex_res = read_code(r"Question3_SpringBoot_REST_API\src\main\java\com\example\studentmanagement\exception\ResourceNotFoundException.java")
    q3_app = read_code(r"Question3_SpringBoot_REST_API\src\main\java\com\example\studentmanagement\StudentManagementApplication.java")

    p_q3_code = doc.add_paragraph(
        "=== 1. Spring Data JPA Entity Model: Student.java ===\n" + q3_entity + "\n\n" +
        "=== 2. Spring Data JPA Repository: StudentRepository.java ===\n" + q3_repo + "\n\n" +
        "=== 3. Data Transfer Objects: StudentResponseDTO.java & StudentScoreSkillUpdateDTO.java ===\n" + q3_dto_resp + "\n\n" + q3_dto_upd + "\n\n" +
        "=== 4. Service Layer: StudentService.java & StudentServiceImpl.java (Java Collections Framework) ===\n" + q3_service_intf + "\n\n" + q3_service_impl + "\n\n" +
        "=== 5. REST Controller: StudentController.java (RESTful Endpoints) ===\n" + q3_controller + "\n\n" +
        "=== 6. Exception Handling: GlobalExceptionHandler.java & ResourceNotFoundException.java ===\n" + q3_ex_res + "\n\n" + q3_ex_handler + "\n\n" +
        "=== 7. Spring Boot Main Application & Database Seeder: StudentManagementApplication.java ===\n" + q3_app
    )
    p_q3_code.style = "List Paragraph"
    for run in p_q3_code.runs:
        run.font.name = "Courier New"
        run.font.size = Pt(9)

    p_q3_ss_title = doc.add_paragraph("Screenshots of Model, Controller, and Postman")
    p_q3_ss_title.style = "List Paragraph"

    p_q3_ss1 = doc.add_paragraph("Figure 3.1: Spring Boot Application Layering and Code View in IntelliJ IDEA\n")
    p_q3_ss1.style = "List Paragraph"
    p_q3_ss1.add_run().add_picture(os.path.join(screenshots_dir, "Screenshot 2026-11-19 102644.png"), width=Inches(5.8))

    p_q3_ss2 = doc.add_paragraph("Figure 3.2: Postman API Test - POST /api/students Creating New Student Profile (Status: 201 Created)\n")
    p_q3_ss2.style = "List Paragraph"
    p_q3_ss2.add_run().add_picture(os.path.join(screenshots_dir, "Screenshot 2026-11-19 110347.png"), width=Inches(5.8))

    p_q3_ss3 = doc.add_paragraph("Figure 3.3: Postman API Test - GET /api/students/merit-list Academic Merit List Sorted DESC (Status: 200 OK)\n")
    p_q3_ss3.style = "List Paragraph"
    p_q3_ss3.add_run().add_picture(os.path.join(screenshots_dir, "Screenshot 2026-11-19 110443.png"), width=Inches(5.8))

    p_q3_struct_title = doc.add_paragraph("Screenshot of Eclipse/Intellij Project Structure.")
    p_q3_struct_title.style = "List Paragraph"

    p_q3_struct_img = doc.add_paragraph("Figure 3.4: IntelliJ IDEA Project Structure Window - Spring Boot REST API Package Hierarchy (controller, dto, model, repository, service)\n")
    p_q3_struct_img.style = "List Paragraph"
    p_q3_struct_img.add_run().add_picture(os.path.join(screenshots_dir, "Screenshot 2026-11-19 102448.png"), width=Inches(5.8))

    p_q3_prompts_title = doc.add_paragraph("Write three AI Prompts used for creating the Project.")
    p_q3_prompts_title.style = "List Paragraph"

    t6 = doc.add_table(rows=3, cols=2)
    t6.style = "Table Grid"
    q3_prompts = [
        ("Prompt 1: Spring Data JPA Entity & Repository",
         '"In Question3_SpringBoot_REST_API, create Student model with JPA annotations (@Entity, @Table, @Id, @ElementCollection) and StudentRepository extending JpaRepository with derived queries."\n\n'
         '-> Reflection: Configured JPA entity relationships and database persistence. MySQL auto-generated tables and populated sample records on startup via CommandLineRunner in StudentManagementApplication.java.'),
        ("Prompt 2: Service Layer with Java Collections",
         '"Implement StudentService and StudentServiceImpl using Java Collections (List, Map, HashMap, Comparator, Collections.sort()) to compute merit list descending order and group students by technical skills."\n\n'
         '-> Reflection: Utilized Java Collections Framework effectively. Implemented custom Comparator for average score sorting and Map<String, List<StudentResponseDTO>> for skill grouping in service implementation.'),
        ("Prompt 3: REST Controller & Exception Handler",
         '"Implement StudentController with endpoints POST /api/students (201 Created), GET /api/students, GET /api/students/{id}, PUT /api/students/{id}, DELETE /api/students/{id}, GET /api/students/merit-list, and GlobalExceptionHandler."\n\n'
         '-> Reflection: Tested REST API in Postman within IntelliJ IDEA. Verified POST created student 105 (Vikram) returning 201 Created, and GET /api/students/merit-list returned 200 OK with sorted rankings.')
    ]
    for idx, (p_title, p_body) in enumerate(q3_prompts):
        t6.rows[idx].cells[0].text = p_title
        t6.rows[idx].cells[1].text = p_body
        for c in t6.rows[idx].cells:
            for p in c.paragraphs:
                for run in p.runs:
                    run.font.name = "Calibri"
                    run.font.size = Pt(10)

    # Save to the exact submission docx
    out_path = os.path.join(base_dir, "OOPJ_MSE2_Moodle_Submission_Format_Aabhas_Katiyar.docx")
    doc.save(out_path)
    print("SUCCESS: Generated exact template format docx at:", out_path)

if __name__ == "__main__":
    populate_exact_template()
