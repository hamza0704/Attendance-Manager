# Student Attendance Management System (Java)
## Overview
A simple console-based Java application to register students and record daily
attendance. Data is stored in CSV files under the `data/` directory.
## Requirements
- Java 8+ (JDK)
- No external libraries required
## How to compile & run
1. Open terminal and navigate to the `src` directory.
2. Compile:
 ```bash
javac *.java
 ```
3. Run:
 ```bash
java AttendanceApp
 ```
4. Data files will be created inside a `data/` folder in the project root.
## Features
- Add / remove students
- Mark attendance for a chosen date
- View daily report for any date
- View attendance history for a student
8
## File format
- `data/students.csv` — each line: `id,name`
- `data/attendance_YYYY-MM-DD.csv` — each line: `id,name,P|A`
## Extensions (suggested)
- GUI using Swing/JavaFX
- Persist using SQLite
- Generate PDF report
- Add authentication for teacher/admin