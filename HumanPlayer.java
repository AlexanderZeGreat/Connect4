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
 *  - You must implement the Player interface
 */
package edu.wm.cs.cs301.connectn;

import java.util.Scanner;

public class HumanPlayer implements Player {
    private final char token;
    private final Scanner scanner;

    public HumanPlayer(char token, Scanner scanner) {
        this.token = token;
        this.scanner = scanner;
    }

    @Override
    public void takeTurn(GameBoard board) {
        while (true) {
            System.out.print("Enter column number to place your token or QUIT to quit the game: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("QUIT")) {
                System.out.println("Player has chosen to quit the game.");
                System.exit(0);
            }

            try {
                int column = Integer.parseInt(input) - 1;
                if (column < 0 || column >= board.getColumns()) {
                    System.out.println("Invalid column. Please enter a number between 1 and " + board.getColumns() + ".");
                    continue;
                }
                
                if (board.placeDisc(column, token)) {
                    break; 
                } else {
                    System.out.println("Column full, try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid column number or QUIT to exit.");
            }
        }
    }
}

