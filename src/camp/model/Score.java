package camp.model;

public class Score{

    private String scoreId;
    private int testCount; // 시험회차
    private char rank; // 등급

    public Score(String seq, int testCount, char rank) {
        this.scoreId = seq;
        this.testCount = testCount;
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

}
