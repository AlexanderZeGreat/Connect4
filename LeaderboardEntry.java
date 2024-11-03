package edu.wm.cs.cs301.connectn;

public class LeaderboardEntry implements Comparable<LeaderboardEntry> {
    private final String name;
    private final int score;
    private final String boardSize; 

    public LeaderboardEntry(String name, int score, String boardSize) {
        this.name = name;
        this.score = score;
        this.boardSize = boardSize;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getBoardSize() { 
        return boardSize;
    }

    @Override
    public String toString() {
        return String.format("%s - %d moves (Board Size: %s)", name, score, boardSize);
    }

    @Override
    public int compareTo(LeaderboardEntry o) {
        return Integer.compare(this.score, o.score); 
    }

}
