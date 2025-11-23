import java.util.Objects;


public class Student {
private String id; // unique id (e.g., S001)
private String name;


public Student(String id, String name) {
this.id = id.trim();
this.name = name.trim();
}


public String getId() {
return id;
}


public String getName() {
return name;
}


public String toCSV() {
return id + "," + name;
}


public static Student fromCSV(String line) {
String[] parts = line.split(",", 2);
if (parts.length < 2) return null;
return new Student(parts[0].trim(), parts[1].trim());
}


@Override
public String toString() {
return id + " - " + name;
}


@Override
public boolean equals(Object o) {
if (this == o) return true;
if (o == null || getClass() != o.getClass()) return false;
Student student = (Student) o;
return Objects.equals(id, student.id);
}


@Override
public int hashCode() {
return Objects.hash(id);
}
}