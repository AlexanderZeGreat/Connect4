/**
 * REPLACE THIS COMMENT WITH YOUR OWN JAVA DOC COMMENT
 * THAT DESCRIBES THE PURPOSE OF YOUR CLASS
 * 
 * You may change this file as much as you like, including:
 * 	- adding constructor parameters
 *  - overloading the constructor
 *  - adding instance variables
 *  - adding methods (public or private)
 *  
 * HOWEVER:
 *  - You may not move the file to a different package
 * 	- You may not change the name of the file or the class
 */
package edu.wm.cs.cs301.connectn;

import java.util.Scanner;

public class ConnectNGame {
    private GameBoard board; 
    private Player humanPlayer;
    private Player computerPlayer;
    private Scanner scanner;
    private int currentPlayerIndex; 
    private LeaderboardManager leaderboardManager = new LeaderboardManager();
    

    public ConnectNGame() {
        this.scanner = new Scanner(System.in);
        initializeGame();
    }

    private void initializeGame() {
        this.board = initializeBoardWithSize(); 
        this.humanPlayer = new HumanPlayer('X', scanner); 
        this.computerPlayer = new ComputerPlayer('O'); 
        this.currentPlayerIndex = 0;
        displayWelcomeMessage();
    }

    private GameBoard initializeBoardWithSize() {
        System.out.println("Choose your board size:");
        System.out.println("1. Small (4x5)");
        System.out.println("2. Medium (6x7)");
        System.out.println("3. Large (8x9)");
        System.out.print("Enter choice (1-3): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1: return new GameBoard(4, 5);
            case 2: return new GameBoard(6, 7);
            case 3: return new GameBoard(8, 9);
            default:
                System.out.println("Invalid choice, defaulting to Medium.");
                return new GameBoard(6, 7);
        }
    }

    private void displayWelcomeMessage() {
        System.out.println("Welcome to Connect N Game!");
        System.out.println("Rules:");
        System.out.println("1. Players take turns dropping their discs into one of the seven columns.");
        System.out.println("2. The first player to align four of their discs vertically, horizontally, or diagonally wins.");
        System.out.println("3. The game ends in a draw if the board is filled without any player winning.");
        System.out.println("4. Player 1 uses 'X', and Player 2 (Computer) uses 'O'.");
        leaderboardManager.displayBestScores();
        leaderboardManager.displayLeaderboard();
        System.out.println("Let's start the game. Best of luck!\n");
    }

    public void startGame() {
        System.out.println("Welcome to Connect N Game!");
        boolean gameContinues = true;
        int turnCount = 0; 
    
        while (gameContinues) {
            board.displayBoard(); 
            System.out.println("\nTurn: " + (turnCount + 1)); 
    
            if (currentPlayerIndex == 0) {
                System.out.println("Human player's turn.");
                humanPlayer.takeTurn(board);
            } else {
                System.out.println("Computer player's turn.");
                computerPlayer.takeTurn(board);
            }
    
            turnCount++; 
            currentPlayerIndex = (currentPlayerIndex + 1) % 2; 
    
            if (board.checkWin()) {
                board.displayBoard();
                String winner = (currentPlayerIndex == 1) ? "Human" : "Computer";
                System.out.println(winner + " wins in " + turnCount + " turns!");
                gameContinues = false;
            } else if (board.isFull()) {
                board.displayBoard();
                System.out.println("The game is a draw after " + turnCount + " turns!");
                gameContinues = false;
            }
    
            if (!gameContinues) {
                String boardSize = board.getBoardSize();
                String playerName = currentPlayerIndex == 1 ? "Human" : "Computer"; 
                int finalScore = turnCount; 
                leaderboardManager.addEntry(playerName, finalScore, boardSize);

                System.out.print("Play again? (yes/no): ");
                String playAgain = scanner.next().trim();
                if (playAgain.equalsIgnoreCase("yes")) {
                    initializeGame(); 
                    currentPlayerIndex = 0; 
                    turnCount = 0; 
                    gameContinues = true;
                } else {
                    System.out.println("Thank you for playing!");
                    break; 
                }
            }
        }
    }
}    

