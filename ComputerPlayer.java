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


public class ComputerPlayer implements Player {
    private final char token;

    public ComputerPlayer(char token) {
        this.token = token;
    }

    @Override
    public void takeTurn(GameBoard board) {
        board.placeDiscForComputer(this.token); 
    }
}
