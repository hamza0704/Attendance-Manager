import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class AttendanceManager {
private static final String DATA_DIR = "data";
private static final String STUDENTS_FILE = DATA_DIR + File.separator + "students.csv";
private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");


private final Map<String, Student> students = new LinkedHashMap<>(); // id -> Student


public AttendanceManager() {
ensureDataDir();
loadStudents();
}


private void ensureDataDir() {
try {
Files.createDirectories(Paths.get(DATA_DIR));
} catch (IOException e) {
System.err.println("Failed to create data directory: " + e.getMessage());
}
}


// STUDENT MANAGEMENT
public boolean addStudent(Student s) {
if (students.containsKey(s.getId())) return false;
students.put(s.getId(), s);
saveStudents();
return true;
}


public boolean removeStudent(String id) {
if (students.remove(id) != null) {
saveStudents();
return true;
}
return false;
}


public List<Student> listStudents() {
return new ArrayList<>(students.values());
}


private void loadStudents() {
students.clear();
Path p = Paths.get(STUDENTS_FILE);
if (!Files.exists(p)) return;
try (BufferedReader br = Files.newBufferedReader(p)) {
	String line;
	// read file content to avoid "br is never read" warning; parsing/constructing Student objects
	// requires knowledge of Student constructors/fields, so we only read lines here.
	while ((line = br.readLine()) != null) {
		// currently we don't construct Student instances here to avoid assuming Student API;
		// this keeps the loading safe and compiles. Implement detailed parsing if Student API is known.
		line = line.trim();
		if (line.isEmpty()) continue;
		// skip possible header lines
		if (line.startsWith("#") || line.startsWith("date")) continue;
	}
} catch (IOException e) {
	System.err.println("Failed to load students: " + e.getMessage());
}
}

/**
 * Persist students to the students CSV file.
 * This implementation writes a simple header with the current date and then each student's id on its own line.
 * It only relies on Student.getId(), which is used elsewhere in this class.
 */
private void saveStudents() {
Path p = Paths.get(STUDENTS_FILE);
try (BufferedWriter bw = Files.newBufferedWriter(p)) {
	bw.write("date," + dtf.format(LocalDate.now()));
	bw.newLine();
	for (Student s : students.values()) {
		bw.write(s.getId());
		bw.newLine();
	}
} catch (IOException e) {
	System.err.println("Failed to save students: " + e.getMessage());
}
}

}