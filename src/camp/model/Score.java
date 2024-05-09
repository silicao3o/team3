package camp.model;

public class Score {

    private final String scoreId;
    private final String studentId;
    private final String subjectId;
    private final int round; // 회차
    private int score; // 점수
    private char rank; // 등급

    public Score(String scoreId, String studentId, String subjectId, int round, int score, char rank) {
        this.scoreId = scoreId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.round = round;
        this.score = score;
        this.rank = rank;
    }

    // Getter
    public String getScoreId() {
        return scoreId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public int getRound() {
        return round;
    }

    public int getScore() {
        return score;
    }

    public char getRank() {
        return rank;
    }

    // Setter
    public void setScore(int score) {
        this.score = score;
    }

    public void setRank(char rank) {
        this.rank = rank;
    }
}
