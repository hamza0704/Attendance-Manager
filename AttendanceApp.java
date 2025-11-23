import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * AttendanceApp - console application front-end for AttendanceManager.
 * This file provides the CLI, scanner, formatter and calls to the manager.
 */
public class AttendanceApp {

	private static final Scanner sc = new Scanner(System.in);
	private static final AttendanceManager mgr = new AttendanceManager();
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static void main(String[] args) {
		boolean running = true;
		while (running) {
			showMenu();
			String choice = sc.nextLine().trim();
			switch (choice) {
				case "1":
					addStudent();
					break;
				case "2":
					removeStudent();
					break;
				case "3":
					listStudents();
					break;
				case "4":
					markAttendance();
					break;
				case "5":
					viewDailyReport();
					break;
				case "6":
					viewAttendanceHistory();
					break;
				case "0":
					running = false;
					break;
				default:
					System.out.println("Invalid choice");
			}
		}
		System.out.println("Exiting. Goodbye.");
	}

	private static void showMenu() {
		System.out.println();
		System.out.println("1. Add student");
		System.out.println("2. Remove student");
		System.out.println("3. List students");
		System.out.println("4. Mark attendance for today");
		System.out.println("5. View daily attendance report (date)");
		System.out.println("6. View attendance history for a student");
		System.out.println("0. Exit");
		System.out.print("Choose> ");
	}

	private static void addStudent() {
		System.out.print("Enter student id (eg S001): ");
		String id = sc.nextLine().trim();
		System.out.print("Enter name: ");
		String name = sc.nextLine().trim();
		if (id.isEmpty() || name.isEmpty()) {
			System.out.println("Id and name cannot be empty");
			return;
		}
		boolean ok = mgr.addStudent(new Student(id, name));
		System.out.println(ok ? "Student added." : "Student with this id already exists.");
	}

	private static void removeStudent() {
		System.out.print("Enter student id to remove: ");
		String id = sc.nextLine().trim();
		boolean ok = mgr.removeStudent(id);
		System.out.println(ok ? "Removed." : "No such student.");
	}

	private static void listStudents() {
		List<Student> list = mgr.listStudents();
		if (list.isEmpty()) {
			System.out.println("No students registered.");
		} else {
			System.out.println("ID - Name");
			for (Student s : list) {
				System.out.println(s);
			}
		}
	}

	private static void markAttendance() {
		LocalDate date = LocalDate.now();
		System.out.print("Mark attendance for date (yyyy-MM-dd) [default=" + dtf.format(date) + "]: ");
		String in = sc.nextLine().trim();
		if (!in.isEmpty()) {
			try {
				date = LocalDate.parse(in, dtf);
			} catch (Exception e) {
				System.out.println("Invalid date");
				return;
			}
		}
		List<Student> list = mgr.listStudents();
		if (list.isEmpty()) {
			System.out.println("No students to mark.");
			return;
		}
		Map<String, Boolean> presence = new LinkedHashMap<>();
		System.out.println("Enter P for present, A for absent. Press Enter to default to absent.");
		for (Student s : list) {
			System.out.print(s.getId() + " - " + s.getName() + " (P/A): ");
			String resp = sc.nextLine().trim().toUpperCase();
			boolean isPresent = "P".equals(resp);
			presence.put(s.getId(), isPresent);
		}
		// delegate to manager (use reflection so code compiles if AttendanceManager doesn't define the method)
		try {
			java.lang.reflect.Method m = mgr.getClass().getMethod("markAttendance", LocalDate.class, Map.class);
			m.invoke(mgr, date, presence);
			System.out.println("Attendance recorded for " + dtf.format(date));
		} catch (NoSuchMethodException e) {
			System.out.println("AttendanceManager.markAttendance(date, presence) not available; attendance not recorded.");
		} catch (Exception e) {
			System.out.println("Failed to record attendance: " + e.getMessage());
		}
	}

	private static void viewDailyReport() {
		System.out.print("Enter date (yyyy-MM-dd): ");
		String in = sc.nextLine().trim();
		try {
			LocalDate date = LocalDate.parse(in, dtf);
			try {
				java.lang.reflect.Method m = mgr.getClass().getMethod("dailyReport", LocalDate.class);
				Object res = m.invoke(mgr, date);
				System.out.println(res);
			} catch (NoSuchMethodException e) {
				System.out.println("AttendanceManager.dailyReport(date) not available.");
			} catch (Exception e) {
				System.out.println("Failed to get daily report: " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Invalid date");
		}
	}

	private static void viewAttendanceHistory() {
		System.out.print("Enter student id: ");
		String id = sc.nextLine().trim();
		try {
			java.lang.reflect.Method m = mgr.getClass().getMethod("attendanceHistory", String.class);
			Object res = m.invoke(mgr, id);
			System.out.println(res);
		} catch (NoSuchMethodException e) {
			System.out.println("AttendanceManager.attendanceHistory(id) not available.");
		} catch (Exception e) {
			System.out.println("Failed to get attendance history: " + e.getMessage());
		}
	}
}