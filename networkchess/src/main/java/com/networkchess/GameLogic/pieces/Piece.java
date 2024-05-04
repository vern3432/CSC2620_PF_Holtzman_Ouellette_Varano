package com.networkchess.GameLogic.pieces;
import com.networkchess.GameLogic.*;
import java.util.LinkedList;

/**
 * Piece
 */
public abstract class Piece {

    // the entire current
    private Board currBoard;
    // white ot black
    private String color;
    // This will be formatted as "x;y"
    private String currPosition;

    /**
     * Constructs the piece with the proper information
     * 
     * @param color of the piece (white or black)
     * @param currPosition of the piece on the board
     * @param currBoard the piece is part of
     */
    public Piece(String color, String currPosition, Board currBoard) {
        this.color = color;
        this.currPosition = currPosition;
        this.currBoard = currBoard;
    }

    public String getColor() {
        return color;
    }

    public Board getCurrBoard() {
        return currBoard;
    }

    public String getCurrPosition() {
        return currPosition;
    }

    public void setCurrBoard(Board newBoard) {
        this.currBoard = newBoard;
    }

    public void setCurrPosition(String currPosition) {
        this.currPosition = currPosition;
    }

    public int getX() {
        return Integer.parseInt(currPosition.split(";")[0]);
    }

    public int getY() {
        return Integer.parseInt(currPosition.split(";")[1]);
    }

    public Boolean isOp(Piece piece) {
        if (this.color.equals(piece.getColor())) {
            return false;
        }
        return true;
    }

    /**
     * Takes in where the user wants to move the piece too and makes the move if its a legal move
     * 
     * @param position to try and move the piece too
     */
    public abstract Boolean movePos(String position);

    /**
     * Looks at the board and checks to see all the possible moves that the piece can make
     * 
     * @return a linked list that contains the possible moves the piece can make
     */
    public abstract LinkedList<String> possibleMoves();
    
}
