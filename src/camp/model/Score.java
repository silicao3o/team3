package camp.model;

public class Score{

    private String subjectId;
    private String studentId;
    private String scoreId;
    private int testCount;
    private int score;// 시험회차
    private char rank; // 등급



    public Score(String subjectId, String studentId, String scoreId, int testCount, int score,char rank) {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.scoreId = scoreId;
        this.testCount = testCount;
        this.score = score;
        this.rank = rank;
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
