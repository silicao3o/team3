package camp.model;

import java.util.List;

public class Student  {

    private final String studentId;
    private final String studentName;
    private final List<Subject> subjects;

    public Student(String seq, String studentName, List<Subject> subjects) {
        this.studentId = seq;
        this.studentName = studentName;
        this.subjects = subjects;
    }

    // Getter
    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }
    
    public List<Subject> getSubjects() {return subjects;}
}
