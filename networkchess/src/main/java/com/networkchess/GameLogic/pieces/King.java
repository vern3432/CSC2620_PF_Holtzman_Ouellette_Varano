package com.networkchess.GameLogic.pieces;
import com.networkchess.GameLogic.*;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * King
 */
public class King extends Piece {

    /**
     * Constructs the King piece with the proper information
     * 
     * @param color of the piece (white or black)
     * @param currPosition of the piece on the board
     * @param currBoard the piece is part of
     */
    public King(String color, String currPosition, Board currBoard) {
        super(color, currPosition, currBoard);

    }

    /**
     * Takes in where the user wants to move the piece too and makes the move if its a legal move
     * 
     * @param position to try and move the piece too
     */
    @Override
    public Boolean movePos(String position) {
        if (!possibleMoves().contains(position)) {
            JOptionPane.showMessageDialog(new JPanel(), "That is not a possible move");
            return false;
        }
        return true;
    }

    @Override
    public LinkedList<String> possibleMoves() {
        return new LinkedList<>();
    }

    @Override
    public String toString() {
        return "King: " + getColor();
    }

    
}