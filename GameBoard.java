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
 *  - you must use the provided board instance variable to 
 *    store the state of your board.
 *  - you must implement the displayBoard method 
 */
package edu.wm.cs.cs301.connectn;

import java.util.Random;

public class GameBoard {
    private final Location[][] board;
    private final int rows;
    private final int columns;
    private int turn = 1;

    public GameBoard(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.board = new Location[rows][columns];
        initializeBoard();
    }

    public int getColumns() {
        return this.columns;
    }

    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = new Location();
            }
        }
    }

    public String getBoardSize() {
        if (rows == 4 && columns == 5) {
            return "Small";
        } else if (rows == 6 && columns == 7) {
            return "Medium";
        } else if (rows == 8 && columns == 9) {
            return "Large";
        } else {
            return "Custom";
        }
    }

    public void placeDiscForComputer(char token) {
        Integer winningColumn = findWinningColumn(token);
        if (winningColumn != null) {
            placeDisc(winningColumn, token);
            System.out.println("Computer placed on column " + (winningColumn + 1) + " for the win!");
            return;
        }
    
        Random random = new Random();
        int column;
        do {
            column = random.nextInt(this.columns);
        } while (!placeDisc(column, token));
        System.out.println("Computer placed on column " + (column + 1));
    }
    
    private Integer findWinningColumn(char token) {
        Integer move = checkHorizontal(token);
        if (move != null) return move;
    
        move = checkVertical(token);
        if (move != null) return move;
    
        move = checkAscendingDiagonal(token);
        if (move != null) return move;
    
        move = checkDescendingDiagonal(token);
        if (move != null) return move;
    
        return null;
    }

    private Integer checkHorizontal(char token) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= columns - 4; col++) {
                int countToken = 0;
                int emptySpot = -1;
                for (int offset = 0; offset < 4; offset++) {
                    if (board[row][col + offset].getToken() == token) {
                        countToken++;
                    } else if (board[row][col + offset].isEmpty()) {
                        emptySpot = col + offset;
                    } else {
                        break; 
                    }
                }
                if (countToken == 3 && emptySpot != -1) {
                    return emptySpot;
                }
            }
        }
        return null; 
    }

    private Integer checkVertical(char token) {
        for (int col = 0; col < columns; col++) {
            for (int row = 0; row <= rows - 4; row++) {
                int countToken = 0;
                boolean emptySpotAbove = false;
                for (int offset = 0; offset < 4; offset++) {
                    if (board[row + offset][col].getToken() == token) {
                        countToken++;
                    } else if (board[row + offset][col].isEmpty() && offset == 3) {
                        emptySpotAbove = true;
                    }
                }
                if (countToken == 3 && emptySpotAbove) {
                    return col;
                }
            }
        }
        return null; 
    }
    
    private Integer checkDescendingDiagonal(char token) {
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col <= columns - 4; col++) {
                int countToken = 0;
                Integer emptySpotColumn = null;
                int emptySpotRow = -1;
                for (int offset = 0; offset < 4; offset++) {
                    int currentRow = row + offset;
                    int currentCol = col + offset;
                    if (board[currentRow][currentCol].getToken() == token) {
                        countToken++;
                    } else if (board[currentRow][currentCol].isEmpty()) {
                        emptySpotColumn = currentCol;
                        emptySpotRow = currentRow;
                    } else {
                        break; 
                    }
                }
                if (countToken == 3 && emptySpotColumn != null) {
                    if (emptySpotRow + 1 == rows || !board[emptySpotRow + 1][emptySpotColumn].isEmpty()) {
                        return emptySpotColumn;
                    }
                }
            }
        }
        return null;
    }

    private Integer checkAscendingDiagonal(char token) {
        for (int row = 3; row < rows; row++) {
            for (int col = 0; col <= columns - 4; col++) {
                int countToken = 0;
                Integer emptySpotColumn = null;
                int emptySpotRow = -1;
                for (int offset = 0; offset < 4; offset++) {
                    int currentRow = row - offset;
                    int currentCol = col + offset;
                    if (board[currentRow][currentCol].getToken() == token) {
                        countToken++;
                    } else if (board[currentRow][currentCol].isEmpty()) {
                        emptySpotColumn = currentCol;
                        emptySpotRow = currentRow;
                    } else {
                        break; 
                    }
                }
                if (countToken == 3 && emptySpotColumn != null) {
                    if (emptySpotRow + 1 == rows || !board[emptySpotRow + 1][emptySpotColumn].isEmpty()) {
                        return emptySpotColumn;
                    }
                }
            }
        }
        return null;
    }
    
    
    public boolean placeDisc(int column, char token) {
        for (int i = rows - 1; i >= 0; i--) {
            if (board[i][column].isEmpty()) {
                board[i][column].setToken(token);
                return true;
            }
        }
        return false;
    }

    public void displayBoard() {
        System.out.print("  ");
        for (int col = 1; col <= columns; col++) {
            System.out.print(col + "   ");
        }
        System.out.println();
    
        System.out.print(" ");
        for (int col = 0; col < columns; col++) {
            System.out.print("====");
        }
        System.out.println("=");
    
        for (int i = 0; i < rows; i++) {
            System.out.print("|");
            for (int j = 0; j < columns; j++) {
                System.out.print(" " + board[i][j].getToken() + " |");
            }
            System.out.println();
            
            if (i < rows - 1) {
                System.out.print(" ");
                for (int col = 0; col < columns; col++) {
                    System.out.print("----");
                }
                System.out.println("-");
            }
        }
    
        System.out.print(" ");
        for (int col = 0; col < columns; col++) {
            System.out.print("====");
        }
        System.out.println("=");
    }
    

    public void incrementTurn() {
        turn++;
    }

    public boolean isFull() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j].isEmpty()) {
                    return false; 
                }
            }
        }
        return true; 
    }
    
    public boolean checkWin() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns - 3; col++) {
                if (!board[row][col].isEmpty() && 
                    board[row][col].getToken() == board[row][col+1].getToken() && 
                    board[row][col].getToken() == board[row][col+2].getToken() && 
                    board[row][col].getToken() == board[row][col+3].getToken()) {
                    return true; 
                }
            }
        }
        
        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows - 3; row++) {
                if (!board[row][col].isEmpty() && 
                    board[row][col].getToken() == board[row+1][col].getToken() && 
                    board[row][col].getToken() == board[row+2][col].getToken() && 
                    board[row][col].getToken() == board[row+3][col].getToken()) {
                    return true; 
                }
            }
        }
        
        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < columns - 3; col++) {
                if (!board[row][col].isEmpty() && 
                    board[row][col].getToken() == board[row+1][col+1].getToken() && 
                    board[row][col].getToken() == board[row+2][col+2].getToken() && 
                    board[row][col].getToken() == board[row+3][col+3].getToken()) {
                    return true; 
                }
            }
        }
        
        for (int row = 0; row < rows - 3; row++) {
            for (int col = 3; col < columns; col++) {
                if (!board[row][col].isEmpty() && 
                    board[row][col].getToken() == board[row+1][col-1].getToken() && 
                    board[row][col].getToken() == board[row+2][col-2].getToken() && 
                    board[row][col].getToken() == board[row+3][col-3].getToken()) {
                    return true; 
                }
            }
        }
        
        return false; 
    }
}