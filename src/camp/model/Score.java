package camp.model;

public class Score{

    private String subjectId;
    private String studentId;
    private String scoreId;
    private int testCount; // 시험회차
    private int testScore; // 점수
    private char rank; // 등급


    public Score(String subjectId, String studentId, String scoreId, int testScore,char rank) {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.scoreId = scoreId;
        this.testCount = 0;
        this.testScore = testScore;
        this.rank = rank;
    }

    public void increaseTestCount(){ // 시험회차 증가
        testCount++;
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

    public int getTestScore() {
        return testScore;
    }
}
