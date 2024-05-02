package camp.model;

public class Score{

    private String scoreId;
    private int testCount; // 시험회차
    private char rank; // 등급
    private String studentId;
    private String subjectId;

    public Score(String seq, int testCount, char rank, String studentId, String subjectId) {
        this.scoreId = seq;
        this.testCount = testCount;
        this.rank = rank;
        this.studentId = studentId;
        this.subjectId = subjectId;
    }

    // Getter
    public String getScoreId() {
        return scoreId;
    }

    public int getTestCount() {
        return testCount;
    }

    public char getRank() {
        return rank;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

}
