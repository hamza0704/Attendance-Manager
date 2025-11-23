# Report: Student Attendance Management System
## 1. Title
Student Attendance Management System
## 2. Abstract
This project implements a console-based attendance management system in Java.
It allows adding/removing students, marking attendance by date, viewing daily
attendance, and viewing a student's attendance history. Data persistence is
handled using CSV files.
## 3. Objectives
- Provide a lightweight system to manage attendance records.
- Use basic Java concepts: OOP, file I/O, collections, and date/time API.
## 4. Functional Requirements
- Register new students with unique IDs.
- Remove existing students.
- Mark students as present/absent for a date.
- Generate a daily attendance report.
- Generate attendance history for a student.
## 5. Non-Functional Requirements
- Simple command-line interface.
- Data persisted across runs using CSV files.
- Platform independent (Java-based).
## 6. Design and Implementation
### 6.1 Classes
- `Student` — stores id & name, CSV serialization helpers.
- `AttendanceManager` — manages student registry and attendance files.
- `AttendanceApp` — user interface (console) and the `main` method.
### 6.2 Data Storage
All data is stored as CSV files in the `data/` directory. Students are in
9
`students.csv`. Each day's attendance is in `attendance_YYYY-MM-DD.csv`.
### 6.3 Flow
1. On start, the app loads students from `students.csv`.
2. The teacher can add/remove/list students.
3. Mark attendance will write a CSV for the selected date.
4. Reports read those CSVs and present information.
## 7. Sample execution
$ javac *.java $ java AttendanceApp === Student Attendance Management ===
Add student
Remove student
List students
Mark attendance for today
View daily attendance report (date)
View attendance history for a student
Exit Choose> 1 Enter student id (eg S001): S001 Enter name: Rahul Sharma Student added.
Choose> 4 Mark attendance for date (yyyy-MM-dd) [default=2025-11-23]: S001 - Rahul Sharma
[P/A]: P Attendance saved for 2025-11-23 Choose> 5 Enter date (yyyy-MM-dd) [default=today]:
Attendance report for 2025-11-23 S001 Rahul Sharma : Present
## 8. Testing
- Add multiple students and mark attendance for several dates.
- Delete a student and ensure they no longer appear in new attendance
files.
- Attempt to view report for a date without an attendance file (system
shows a placeholder `-`).
## 9. Limitations
- No concurrency handling for simultaneous writes.
- Simple CSV storage; not suitable for very large sets.
- No authentication.
## 10. Future Work
- Add graphical UI (Swing/JavaFX).
- Use SQLite or embedded DB for robustness.
- Add export to PDF/Excel for reports.
## 11. References
- Java SE documentation for `java.io`, `java.nio.file`, and `java.time`
APIs.
