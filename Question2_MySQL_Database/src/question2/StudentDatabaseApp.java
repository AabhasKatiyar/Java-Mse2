package question2;

import java.sql.*;
import java.util.Scanner;

/**
 * Question 2: Student Database Operations Application (5 Marks - CO4)
 * Connects to MySQL and demonstrates all 8 requirements with interactive inputs and outputs in IntelliJ console.
 */
public class StudentDatabaseApp {

    // Database Configuration
    private static final String SERVER_URL = "jdbc:mysql://localhost:3306/?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Aabhas83"; // Your MySQL password

    private static Connection connection = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Setup DB and connect
            setupDatabaseAndTables();
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            System.out.println("==========================================================================");
            System.out.println(" [SUCCESS] Connected to MySQL Database 'student_db' successfully!");
            System.out.println("==========================================================================");

            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("Enter your choice (1-10): ");
                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number between 1 and 10.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        calculateAverageAndCategory();
                        break;
                    case 2:
                        identifyStrongerSubject();
                        break;
                    case 3:
                        searchStudentById();
                        break;
                    case 4:
                        searchStudentByEmail();
                        break;
                    case 5:
                        searchStudentByCourse();
                        break;
                    case 6:
                        displayMeritList();
                        break;
                    case 7:
                        identifyStudentsWithSameSkills();
                        break;
                    case 8:
                        displayProgImprovementWithMathAtLeast75();
                        break;
                    case 9:
                        insertStudentWithValidation();
                        break;
                    case 10:
                        running = false;
                        System.out.println("Exiting Student Database Application. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice! Choose between 1 and 10.");
                }
            }

        } catch (Exception e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private static void printMenu() {
        System.out.println("\n---------------- QUESTION 2: MYSQL DATABASE OPERATIONS MENU ----------------");
        System.out.println("1.  (Req 1 & 2) Calculate Each Student's Average Score & Performance Category");
        System.out.println("2.  (Req 3)     Identify Stronger Subject for Each Student");
        System.out.println("3.  (Req 4a)    Search Student by Student ID");
        System.out.println("4.  (Req 4b)    Search Student by Email Address");
        System.out.println("5.  (Req 4c)    Search Students by Course Name");
        System.out.println("6.  (Req 5)     Generate & Display Merit List (Sorted by Average DESC)");
        System.out.println("7.  (Req 6)     Identify Students Who Have the Same Technical Skills");
        System.out.println("8.  (Req 7)     Display Students Needing Prog. Improvement (<50) with Math >= 75");
        System.out.println("9.  (Req 8)     Insert Student (Test Duplicate ID & Score Validation 0-100)");
        System.out.println("10. Exit");
        System.out.println("----------------------------------------------------------------------------");
    }

    /**
     * Requirement 1 & 2: Calculate average score and assign performance category
     */
    private static void calculateAverageAndCategory() {
        System.out.println("\n--- [Requirements 1 & 2] Average Score & Performance Category ---");
        String sql = "SELECT student_id, student_name, course, programming_score, mathematics_score, " +
                     "(programming_score + mathematics_score) / 2.0 AS avg_score, " +
                     "CASE " +
                     "    WHEN (programming_score + mathematics_score) / 2.0 >= 85 THEN 'Excellent' " +
                     "    WHEN (programming_score + mathematics_score) / 2.0 >= 70 THEN 'Good' " +
                     "    WHEN (programming_score + mathematics_score) / 2.0 >= 50 THEN 'Satisfactory' " +
                     "    ELSE 'Needs Improvement' " +
                     "END AS performance_category " +
                     "FROM students";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-10s %-20s %-10s %-12s %-12s %-12s %-18s\n",
                    "ID", "Name", "Course", "Prog Score", "Math Score", "Average", "Category");
            System.out.println("---------------------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %-20s %-10s %-12.1f %-12.1f %-12.2f %-18s\n",
                        rs.getLong("student_id"),
                        rs.getString("student_name"),
                        rs.getString("course"),
                        rs.getDouble("programming_score"),
                        rs.getDouble("mathematics_score"),
                        rs.getDouble("avg_score"),
                        rs.getString("performance_category"));
            }
        } catch (SQLException e) {
            System.err.println("Query Error: " + e.getMessage());
        }
    }

    /**
     * Requirement 3: Identify stronger subject for each student
     */
    private static void identifyStrongerSubject() {
        System.out.println("\n--- [Requirement 3] Stronger Subject Analysis ---");
        String sql = "SELECT student_id, student_name, programming_score, mathematics_score, " +
                     "CASE " +
                     "    WHEN programming_score > mathematics_score THEN 'Programming' " +
                     "    WHEN mathematics_score > programming_score THEN 'Mathematics' " +
                     "    ELSE 'Equal' " +
                     "END AS stronger_subject " +
                     "FROM students";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-10s %-20s %-15s %-15s %-18s\n",
                    "ID", "Name", "Programming", "Mathematics", "Stronger Subject");
            System.out.println("---------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %-20s %-15.1f %-15.1f %-18s\n",
                        rs.getLong("student_id"),
                        rs.getString("student_name"),
                        rs.getDouble("programming_score"),
                        rs.getDouble("mathematics_score"),
                        rs.getString("stronger_subject"));
            }
        } catch (SQLException e) {
            System.err.println("Query Error: " + e.getMessage());
        }
    }

    /**
     * Requirement 4a: Search by Student ID
     */
    private static void searchStudentById() {
        System.out.println("\n--- [Requirement 4a] Search Student by ID ---");
        System.out.print("Enter Student ID to search (e.g., 101): ");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric ID!");
            return;
        }

        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    printStudentDetails(rs);
                } else {
                    System.out.println("-> [NOT FOUND]: No student found with ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("Query Error: " + e.getMessage());
        }
    }

    /**
     * Requirement 4b: Search by Email
     */
    private static void searchStudentByEmail() {
        System.out.println("\n--- [Requirement 4b] Search Student by Email ---");
        System.out.print("Enter Email to search (e.g., priya@gmail.com): ");
        String email = scanner.nextLine().trim();

        String sql = "SELECT * FROM students WHERE LOWER(email) = LOWER(?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    printStudentDetails(rs);
                } else {
                    System.out.println("-> [NOT FOUND]: No student found with Email: " + email);
                }
            }
        } catch (SQLException e) {
            System.err.println("Query Error: " + e.getMessage());
        }
    }

    /**
     * Requirement 4c: Search by Course
     */
    private static void searchStudentByCourse() {
        System.out.println("\n--- [Requirement 4c] Search Students by Course ---");
        System.out.print("Enter Course Name to search (e.g., CSE, IT, CSEAI): ");
        String course = scanner.nextLine().trim();

        String sql = "SELECT * FROM students WHERE LOWER(course) = LOWER(?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, course);
            try (ResultSet rs = pstmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    printStudentDetails(rs);
                }
                if (!found) {
                    System.out.println("-> [NOT FOUND]: No students found in Course: " + course);
                }
            }
        } catch (SQLException e) {
            System.err.println("Query Error: " + e.getMessage());
        }
    }

    /**
     * Requirement 5: Generate Merit List sorted by average score descending
     */
    private static void displayMeritList() {
        System.out.println("\n--- [Requirement 5] Academic Merit List (Sorted by Average Score DESC) ---");
        String sql = "SELECT student_id, student_name, course, programming_score, mathematics_score, " +
                     "(programming_score + mathematics_score) / 2.0 AS average_score " +
                     "FROM students " +
                     "ORDER BY average_score DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-6s %-10s %-20s %-10s %-12s %-12s %-12s\n",
                    "Rank", "ID", "Name", "Course", "Prog Score", "Math Score", "Average Score");
            System.out.println("---------------------------------------------------------------------------------------");

            int rank = 1;
            while (rs.next()) {
                System.out.printf("%-6d %-10d %-20s %-10s %-12.1f %-12.1f %-12.2f\n",
                        rank++,
                        rs.getLong("student_id"),
                        rs.getString("student_name"),
                        rs.getString("course"),
                        rs.getDouble("programming_score"),
                        rs.getDouble("mathematics_score"),
                        rs.getDouble("average_score"));
            }
        } catch (SQLException e) {
            System.err.println("Query Error: " + e.getMessage());
        }
    }

    /**
     * Requirement 6: Identify students who have the same technical skills
     */
    private static void identifyStudentsWithSameSkills() {
        System.out.println("\n--- [Requirement 6] Students Grouped by Shared Technical Skills ---");
        String sql = "SELECT s.skill, GROUP_CONCAT(st.student_name SEPARATOR ', ') AS students_with_skill, COUNT(st.student_id) AS student_count " +
                     "FROM student_skills s " +
                     "JOIN students st ON s.student_id = st.student_id " +
                     "GROUP BY s.skill " +
                     "HAVING COUNT(st.student_id) > 1 " +
                     "ORDER BY student_count DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-18s %-15s %-45s\n", "Skill", "Student Count", "Students Sharing Skill");
            System.out.println("----------------------------------------------------------------------------------");

            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                System.out.printf("%-18s %-15d %-45s\n",
                        rs.getString("skill"),
                        rs.getInt("student_count"),
                        rs.getString("students_with_skill"));
            }
            if (!hasRecords) {
                System.out.println("No shared skills found among multiple students.");
            }
        } catch (SQLException e) {
            System.err.println("Query Error: " + e.getMessage());
        }
    }

    /**
     * Requirement 7: Display students needing improvement in programming (<50) but math >= 75
     */
    private static void displayProgImprovementWithMathAtLeast75() {
        System.out.println("\n--- [Requirement 7] Students Needing Programming Improvement (< 50) with Math >= 75 ---");
        String sql = "SELECT student_id, student_name, course, email, programming_score, mathematics_score " +
                     "FROM students " +
                     "WHERE programming_score < 50 AND mathematics_score >= 75";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-10s %-20s %-10s %-22s %-15s %-15s\n",
                    "ID", "Name", "Course", "Email", "Programming", "Mathematics");
            System.out.println("----------------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-10d %-20s %-10s %-22s %-15.1f %-15.1f\n",
                        rs.getLong("student_id"),
                        rs.getString("student_name"),
                        rs.getString("course"),
                        rs.getString("email"),
                        rs.getDouble("programming_score"),
                        rs.getDouble("mathematics_score"));
            }
            if (!found) {
                System.out.println("No students match this criterion.");
            }
        } catch (SQLException e) {
            System.err.println("Query Error: " + e.getMessage());
        }
    }

    /**
     * Requirement 8: Insert student with validation (duplicate ID prevention & score 0-100)
     */
    private static void insertStudentWithValidation() {
        System.out.println("\n--- [Requirement 8] Insert Student with Validation ---");
        try {
            System.out.print("Enter Student ID (e.g., 105): ");
            long id = Long.parseLong(scanner.nextLine().trim());

            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Email Address: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Course Name (e.g. CSE): ");
            String course = scanner.nextLine().trim();

            System.out.print("Enter Programming Score (0-100): ");
            double progScore = Double.parseDouble(scanner.nextLine().trim());
            if (progScore < 0 || progScore > 100) {
                System.out.println("-> [VALIDATION FAILED]: Programming score must be between 0 and 100! Provided: " + progScore);
                return;
            }

            System.out.print("Enter Mathematics Score (0-100): ");
            double mathScore = Double.parseDouble(scanner.nextLine().trim());
            if (mathScore < 0 || mathScore > 100) {
                System.out.println("-> [VALIDATION FAILED]: Mathematics score must be between 0 and 100! Provided: " + mathScore);
                return;
            }

            System.out.print("Enter Technical Skills (comma-separated, e.g., Java, Spring, MySQL): ");
            String skillsInput = scanner.nextLine().trim();

            // Insert into students table using PreparedStatement
            String insertSql = "INSERT INTO students (student_id, student_name, email, course, programming_score, mathematics_score) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertSql)) {
                pstmt.setLong(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, email);
                pstmt.setString(4, course);
                pstmt.setDouble(5, progScore);
                pstmt.setDouble(6, mathScore);

                pstmt.executeUpdate();
                System.out.println("-> [SUCCESS]: Student record inserted into MySQL successfully!");

                // Insert skills into student_skills table
                if (!skillsInput.isEmpty()) {
                    String[] skills = skillsInput.split(",");
                    String skillSql = "INSERT INTO student_skills (student_id, skill) VALUES (?, ?)";
                    try (PreparedStatement skillPstmt = connection.prepareStatement(skillSql)) {
                        for (String skill : skills) {
                            skillPstmt.setLong(1, id);
                            skillPstmt.setString(2, skill.trim());
                            skillPstmt.executeUpdate();
                        }
                    }
                    System.out.println("-> [SUCCESS]: Technical skills saved successfully!");
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("-> [DUPLICATE VIOLATION ERROR]: Student with ID " + id + " or Email '" + email + "' already exists in MySQL! Insertion blocked.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input!");
        } catch (SQLException e) {
            System.err.println("Database Error during insertion: " + e.getMessage());
        }
    }

    private static void printStudentDetails(ResultSet rs) throws SQLException {
        System.out.printf("   [ID: %d | Name: %s | Course: %s | Email: %s | Prog: %.1f | Math: %.1f]\n",
                rs.getLong("student_id"),
                rs.getString("student_name"),
                rs.getString("course"),
                rs.getString("email"),
                rs.getDouble("programming_score"),
                rs.getDouble("mathematics_score"));
    }

    private static void setupDatabaseAndTables() {
        try (Connection serverConn = DriverManager.getConnection(SERVER_URL, DB_USER, DB_PASSWORD);
             Statement stmt = serverConn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS student_db;");
            stmt.executeUpdate("USE student_db;");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS students (" +
                    "student_id BIGINT PRIMARY KEY, " +
                    "student_name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL UNIQUE, " +
                    "course VARCHAR(50) NOT NULL, " +
                    "programming_score DOUBLE NOT NULL CHECK (programming_score BETWEEN 0 AND 100), " +
                    "mathematics_score DOUBLE NOT NULL CHECK (mathematics_score BETWEEN 0 AND 100));");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS student_skills (" +
                    "student_id BIGINT NOT NULL, " +
                    "skill VARCHAR(50) NOT NULL, " +
                    "PRIMARY KEY (student_id, skill), " +
                    "FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE);");

            // Seed sample data if empty
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM students")) {
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate("INSERT INTO students VALUES " +
                            "(101, 'Aabhas Katiyar', 'aabhas@gmail.com', 'CSE', 92.0, 88.0)," +
                            "(102, 'Priya Sharma', 'priya@gmail.com', 'CSEAI', 45.0, 82.0)," +
                            "(103, 'Rohan Verma', 'rohan@gmail.com', 'IT', 74.0, 78.0)," +
                            "(104, 'Ananya Patel', 'ananya@gmail.com', 'CSE', 40.0, 48.0);");

                    stmt.executeUpdate("INSERT INTO student_skills VALUES " +
                            "(101, 'Java'), (101, 'Spring Boot'), (101, 'MySQL')," +
                            "(102, 'Python'), (102, 'MySQL')," +
                            "(103, 'Java'), (103, 'HTML/CSS')," +
                            "(104, 'C++');");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database auto-setup notice: " + e.getMessage());
        }
    }

    private static void closeResources() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
            if (scanner != null) scanner.close();
        } catch (SQLException ignored) {}
    }
}
