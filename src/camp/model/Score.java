package camp.model;

public class Score {

    private String scoreId;
    private String studentId;
    private String subjectId;
    private int round; // 회차
    private int score; // 점수
    private char rank; // 등급

    public Score(String scoreId, String studentId, String subjectId, int score, char rank) {
        this.scoreId = scoreId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.score = score;
        this.rank = rank;
        this.round = 0;
    }

    // 시험 회차 증가 메서드
    public void increaseRound() {
        round++;
    }

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public char getRank() {
        return rank;
    }

    public void setRank(char rank) {
        this.rank = rank;
    }
}
