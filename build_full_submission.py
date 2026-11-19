import os
import docx
from docx.shared import Inches, Pt, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_TABLE_ALIGNMENT, WD_ALIGN_VERTICAL
from docx.oxml import OxmlElement, parse_xml
from docx.oxml.ns import nsdecls, qn

base_dir = r"c:\Users\abhas\OneDrive\Desktop\Java Mse2"
screenshots_dir = os.path.join(base_dir, "screenshots")

def set_cell_border(cell, **kwargs):
    """
    kwargs: top, bottom, left, right
    values: dict(sz=12, val='single', color='003366', space='0')
    """
    tcPr = cell._tc.get_or_add_tcPr()
    tcBorders = parse_xml(r'<w:tcBorders xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">'
                          r'<w:top w:val="single" w:sz="6" w:space="0" w:color="CCCCCC"/>'
                          r'<w:left w:val="single" w:sz="6" w:space="0" w:color="CCCCCC"/>'
                          r'<w:bottom w:val="single" w:sz="6" w:space="0" w:color="CCCCCC"/>'
                          r'<w:right w:val="single" w:sz="6" w:space="0" w:color="CCCCCC"/>'
                          r'</w:tcBorders>')
    tcPr.append(tcBorders)

def set_cell_background(cell, fill_hex):
    tcPr = cell._tc.get_or_add_tcPr()
    shd = parse_xml(f'<w:shd xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" w:fill="{fill_hex}"/>')
    tcPr.append(shd)

def create_full_document():
    doc = docx.Document()

    # Set 1-inch margins
    for s in doc.sections:
        s.top_margin = Inches(0.8)
        s.bottom_margin = Inches(0.8)
        s.left_margin = Inches(0.8)
        s.right_margin = Inches(0.8)

    # 1. Main Title
    p_title = doc.add_paragraph()
    p_title.alignment = WD_ALIGN_PARAGRAPH.CENTER
    p_title.paragraph_format.space_before = Pt(0)
    p_title.paragraph_format.space_after = Pt(2)
    r1 = p_title.add_run("OOPJ (CS336B) - Object Oriented Programming in Java\n")
    r1.font.name = "Calibri"
    r1.font.size = Pt(16)
    r1.font.bold = True
    r1.font.color.rgb = RGBColor(0x1F, 0x4E, 0x79)

    r2 = p_title.add_run("Moodle MSE2 Official Examination Submission Format\n")
    r2.font.name = "Calibri"
    r2.font.size = Pt(13)
    r2.font.bold = True
    r2.font.color.rgb = RGBColor(0x33, 0x33, 0x33)

    r3 = p_title.add_run("Examination Shift: Morning Shift (10:00 AM - 12:30 PM)")
    r3.font.name = "Calibri"
    r3.font.size = Pt(10.5)
    r3.font.italic = True
    r3.font.color.rgb = RGBColor(0x55, 0x55, 0x55)

    # 2. Student Info Table
    t_info = doc.add_table(rows=6, cols=2)
    t_info.alignment = WD_TABLE_ALIGNMENT.CENTER
    info_data = [
        ("Student Name", "Aabhas Katiyar"),
        ("Branch", "Information Technology"),
        ("Registration Number", "202501100600001"),
        ("Section", "A"),
        ("Shift", "Morning Shift (10:00 AM - 12:30 PM)"),
        ("Case Study Name", "Student Profile and Skill Development Management System")
    ]
    for idx, (label, val) in enumerate(info_data):
        row = t_info.rows[idx]
        c0, c1 = row.cells[0], row.cells[1]
        c0.width = Inches(2.2)
        c1.width = Inches(4.7)
        c0.text = label
        c1.text = val
        c0.paragraphs[0].runs[0].font.bold = True
        c0.paragraphs[0].runs[0].font.size = Pt(10)
        c1.paragraphs[0].runs[0].font.size = Pt(10)
        set_cell_background(c0, "F2F4F7")
        set_cell_border(c0)
        set_cell_border(c1)

    doc.add_paragraph() # Spacing

    # Helpers
    def add_section_header(q_num, q_title, marks):
        p = doc.add_paragraph()
        p.paragraph_format.space_before = Pt(14)
        p.paragraph_format.space_after = Pt(4)
        r = p.add_run(f"{q_num}. {q_title} ({marks})")
        r.font.name = "Calibri"
        r.font.size = Pt(14)
        r.font.bold = True
        r.font.color.rgb = RGBColor(0x1F, 0x4E, 0x79)

    def add_subheading(sub_title):
        p = doc.add_paragraph()
        p.paragraph_format.space_before = Pt(8)
        p.paragraph_format.space_after = Pt(2)
        r = p.add_run(sub_title)
        r.font.name = "Calibri"
        r.font.size = Pt(11.5)
        r.font.bold = True
        r.font.color.rgb = RGBColor(0x22, 0x22, 0x22)

    def add_github_table():
        t = doc.add_table(rows=1, cols=1)
        t.alignment = WD_TABLE_ALIGNMENT.CENTER
        c = t.rows[0].cells[0]
        c.width = Inches(6.9)
        c.text = "GitHub Repository: https://github.com/AabhasKatiyar/Java-Mse2"
        c.paragraphs[0].runs[0].font.name = "Calibri"
        c.paragraphs[0].runs[0].font.size = Pt(10)
        c.paragraphs[0].runs[0].font.bold = True
        set_cell_background(c, "EBF1F5")
        set_cell_border(c)
        doc.add_paragraph()

    def add_code_block(title, code_str):
        p_hdr = doc.add_paragraph()
        p_hdr.paragraph_format.space_before = Pt(6)
        p_hdr.paragraph_format.space_after = Pt(1)
        r_hdr = p_hdr.add_run(title)
        r_hdr.font.name = "Consolas"
        r_hdr.font.size = Pt(9.5)
        r_hdr.font.bold = True
        r_hdr.font.color.rgb = RGBColor(0x00, 0x33, 0x66)

        p = doc.add_paragraph()
        p.paragraph_format.space_before = Pt(1)
        p.paragraph_format.space_after = Pt(6)
        p.paragraph_format.line_spacing = 1.05
        r = p.add_run(code_str)
        r.font.name = "Consolas"
        r.font.size = Pt(8.5)
        r.font.color.rgb = RGBColor(0x11, 0x11, 0x11)

    def add_screenshot(caption, img_name, width=5.6):
        p = doc.add_paragraph()
        p.paragraph_format.space_before = Pt(6)
        p.paragraph_format.space_after = Pt(6)
        r_cap = p.add_run(f"{caption}\n")
        r_cap.font.name = "Calibri"
        r_cap.font.size = Pt(10)
        r_cap.font.bold = True
        r_cap.font.color.rgb = RGBColor(0x1F, 0x4E, 0x79)
        
        img_path = os.path.join(screenshots_dir, img_name)
        r_img = p.add_run()
        r_img.add_picture(img_path, width=Inches(width))

    def add_prompts_table(prompts_data):
        t = doc.add_table(rows=len(prompts_data), cols=2)
        t.alignment = WD_TABLE_ALIGNMENT.CENTER
        for idx, (p_title, p_content) in enumerate(prompts_data):
            row = t.rows[idx]
            c0, c1 = row.cells[0], row.cells[1]
            c0.width = Inches(2.2)
            c1.width = Inches(4.7)
            c0.text = p_title
            c1.text = p_content
            c0.paragraphs[0].runs[0].font.bold = True
            c0.paragraphs[0].runs[0].font.size = Pt(9.5)
            c1.paragraphs[0].runs[0].font.size = Pt(9.5)
            set_cell_background(c0, "F5F7FA")
            set_cell_border(c0)
            set_cell_border(c1)
        doc.add_paragraph()

    # Read Source Code Files
    def read_code(rel_path):
        with open(os.path.join(base_dir, rel_path), "r", encoding="utf-8") as f:
            return f.read()

    # =========================================================================
    # SECTION Q1: QUESTION 1 (5 MARKS)
    # =========================================================================
    add_section_header("Q1", "Question 1: Standalone Student OOP Model Class", "5 Marks - CO4")
    
    add_subheading("1. Moodle Upload/GitHub Repository Link")
    add_github_table()

    add_subheading("2. Project code")
    q1_student_code = read_code(r"Question1_StudentClass\src\question1\Student.java")
    q1_main_code = read_code(r"Question1_StudentClass\src\question1\Main.java")
    add_code_block("=== Question1_StudentClass/src/question1/Student.java ===", q1_student_code)
    add_code_block("=== Question1_StudentClass/src/question1/Main.java ===", q1_main_code)

    add_subheading("3. Screenshots of Model, Controller, and Execution")
    add_screenshot("Figure 1.1: Question 1 Execution - Student Profile, Scores, Category & Stronger Subject Console Output",
                   "Screenshot 2026-11-19 105016.png")
    add_screenshot("Figure 1.2: Question 1 Execution - Score Validation (0-100) Exception Handling in Console",
                   "Screenshot 2026-11-19 105100.png")

    add_subheading("4. Screenshot of Eclipse/Intellij Project Structure")
    add_screenshot("Figure 1.3: IntelliJ IDEA Project Structure Window - Question 1 Module Structure",
                   "Screenshot 2026-11-19 104758.png")

    add_subheading("5. Write three AI Prompts used for creating the Project")
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
    add_prompts_table(q1_prompts)

    # =========================================================================
    # SECTION Q2: QUESTION 2 (5 MARKS)
    # =========================================================================
    add_section_header("Q2", "Question 2: MySQL Database & Interactive Java Logic (JDBC)", "5 Marks - CO3")

    add_subheading("1. Moodle Upload/GitHub Repository Link")
    add_github_table()

    add_subheading("2. Project code")
    q2_schema_code = read_code(r"Question2_MySQL_Database\schema.sql")
    q2_jdbc_code = read_code(r"Question2_MySQL_Database\src\question2\StudentDatabaseApp.java")
    add_code_block("=== Question2_MySQL_Database/schema.sql (Database Schema & 8 SQL Queries) ===", q2_schema_code)
    add_code_block("=== Question2_MySQL_Database/src/question2/StudentDatabaseApp.java (Interactive JDBC Console App) ===", q2_jdbc_code)

    add_subheading("3. Screenshots of Database, Logic, and Console Execution")
    add_screenshot("Figure 2.1: MySQL Workbench - student_db Schema Creation and Students Table Records Grid",
                   "Screenshot 2026-11-19 102253.png")
    add_screenshot("Figure 2.2: Question 2 JDBC Application - Interactive Console Menu (Options 1-10 covering all 8 requirements)",
                   "Screenshot 2026-11-19 105237.png")
    add_screenshot("Figure 2.3: Question 2 JDBC Application - Option 1 & 2 Execution: Average Score & Performance Category Table",
                   "Screenshot 2026-11-19 105247.png")
    add_screenshot("Figure 2.4: Question 2 JDBC Application - Option 6 Execution: Academic Merit List (Sorted by Average Score DESC)",
                   "Screenshot 2026-11-19 105302.png")
    add_screenshot("Figure 2.5: Question 2 JDBC Application - Option 9 Execution: Duplicate Student ID & Email Violation Prevention",
                   "Screenshot 2026-11-19 105848.png")

    add_subheading("4. Screenshot of Eclipse/Intellij Project Structure")
    add_screenshot("Figure 2.6: IntelliJ IDEA Project Structure Window - Question 2 MySQL JDBC Module Structure",
                   "Screenshot 2026-11-19 104758.png")

    add_subheading("5. Write three AI Prompts used for creating the Project")
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
    add_prompts_table(q2_prompts)

    # =========================================================================
    # SECTION Q3: QUESTION 3 (10 MARKS)
    # =========================================================================
    add_section_header("Q3", "Question 3: Spring Boot REST API with Spring Data JPA & Java Collections", "10 Marks - CO5")

    add_subheading("1. Moodle Upload/GitHub Repository Link")
    add_github_table()

    add_subheading("2. Project code")
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

    add_code_block("=== 1. Spring Data JPA Entity Model: Student.java ===", q3_entity)
    add_code_block("=== 2. Spring Data JPA Repository: StudentRepository.java ===", q3_repo)
    add_code_block("=== 3. Data Transfer Objects: StudentResponseDTO.java & StudentScoreSkillUpdateDTO.java ===", q3_dto_resp + "\n\n" + q3_dto_upd)
    add_code_block("=== 4. Service Layer: StudentService.java & StudentServiceImpl.java (Java Collections Framework) ===", q3_service_intf + "\n\n" + q3_service_impl)
    add_code_block("=== 5. REST Controller: StudentController.java (RESTful Endpoints) ===", q3_controller)
    add_code_block("=== 6. Exception Handling: GlobalExceptionHandler.java & ResourceNotFoundException.java ===", q3_ex_res + "\n\n" + q3_ex_handler)
    add_code_block("=== 7. Spring Boot Main Application & Database Seeder: StudentManagementApplication.java ===", q3_app)

    add_subheading("3. Screenshots of Model, Controller, and Postman Execution")
    add_screenshot("Figure 3.1: Spring Boot Application Layering and Code View in IntelliJ IDEA",
                   "Screenshot 2026-11-19 102644.png")
    add_screenshot("Figure 3.2: Postman API Test - POST /api/students Creating New Student Profile (Status: 201 Created)",
                   "Screenshot 2026-11-19 110347.png")
    add_screenshot("Figure 3.3: Postman API Test - GET /api/students/merit-list Academic Merit List Sorted DESC (Status: 200 OK)",
                   "Screenshot 2026-11-19 110443.png")

    add_subheading("4. Screenshot of Eclipse/Intellij Project Structure")
    add_screenshot("Figure 3.4: IntelliJ IDEA Project Structure Window - Spring Boot REST API Package Hierarchy (controller, dto, model, repository, service)",
                   "Screenshot 2026-11-19 102448.png")

    add_subheading("5. Write three AI Prompts used for creating the Project")
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
    add_prompts_table(q3_prompts)

    # Save
    out_docx = os.path.join(base_dir, "OOPJ_MSE2_Complete_Paper_Submission_Aabhas_Katiyar.docx")
    doc.save(out_docx)
    print("Created successfully:", out_docx)

if __name__ == "__main__":
    create_full_document()
