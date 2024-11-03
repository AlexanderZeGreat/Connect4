package edu.wm.cs.cs301.connectn;

import java.io.*;
import java.util.*;

public class LeaderboardManager {
    private List<LeaderboardEntry> leaderboard = new ArrayList<>();
    private final String FILE_PATH = "resources/leaderboard.txt";

    public LeaderboardManager() {
        File directory = new File("resources");
        if (!directory.exists()) {
            boolean directoryCreated = directory.mkdirs();
            if (!directoryCreated) {
                System.out.println("Failed to create directories.");
            }
        }
        
        loadLeaderboard();
    }


    private void loadLeaderboard() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Leaderboard file not found. A new one will be created.");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    String boardSize = parts[2];
                    leaderboard.add(new LeaderboardEntry(name, score, boardSize));
                }
            }
            Collections.sort(leaderboard);
        } catch (IOException e) {
            System.out.println("An error occurred while loading the leaderboard.");
            e.printStackTrace();
        }
    }
    
    public void saveLeaderboard() {
        File directory = new File(FILE_PATH).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            System.out.println("Failed to create directories for the leaderboard file.");
            return;
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (LeaderboardEntry entry : leaderboard) {
                String line = entry.getName() + "," + entry.getScore() + "," + entry.getBoardSize();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the leaderboard.");
            e.printStackTrace();
        }
    }
    
    public void displayBestScores() {
        Map<String, Integer> bestScores = new HashMap<>();
        for (LeaderboardEntry entry : leaderboard) {
            String boardSize = entry.getBoardSize();
            int score = entry.getScore();
            if (!bestScores.containsKey(boardSize) || score < bestScores.get(boardSize)) {
                bestScores.put(boardSize, score);
            }
        }
    
        System.out.println("Best Scores:");
        for (Map.Entry<String, Integer> entry : bestScores.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue() + " moves");
        }
    }
    

    public void addEntry(String name, int score, String boardSize) {
        leaderboard.add(new LeaderboardEntry(name, score, boardSize));
        Collections.sort(leaderboard); 
        saveLeaderboard();
    }

    

    
    public void displayLeaderboard() {
        System.out.println("Leaderboard:");
        int count = 0;
        for (LeaderboardEntry entry : leaderboard) {
            System.out.println(entry);
            count++;
            if (count == 10) {
                break; 
            }
        }
    }
}    