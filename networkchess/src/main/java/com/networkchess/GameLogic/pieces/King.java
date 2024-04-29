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

    /**
     * Checks if a move is possible for the king
     * 
     * @return the list of possible moves that can be made
     */
    @Override
    public LinkedList<String> possibleMoves() {
        LinkedList<String> posMoves = new LinkedList<>();

        String[] xy = getCurrPosition().split(";");
        Integer x = Integer.valueOf(xy[0]);
        Integer y = Integer.valueOf(xy[1]);

        if (moveCheck(x, y+1)) {
            posMoves.add(x+";"+(y+1));
        }
        if (moveCheck(x+1, y+1)) {
            posMoves.add((x+1)+";"+(y+1));
        }
        if (moveCheck(x+1, y)) {
            posMoves.add((x+1)+";"+y);
        }
        if (moveCheck(x+1, y-1)) {
            posMoves.add((x+1)+";"+(y-1));
        }
        if (moveCheck(x, y-1)) {
            posMoves.add(x+";"+(y-1));
        }
        if (moveCheck(x-1, y-1)) {
            posMoves.add((x-1)+";"+(y-1));
        }
        if (moveCheck(x-1, y)) {
            posMoves.add((x-1)+";"+y);
        }
        if (moveCheck(x-1, y+1)) {
            posMoves.add((x-1)+";"+(y+1));
        }

        return posMoves;
    }

    @Override
    public String toString() {
        return "King: " + getColor();
    }

    /**
     * checks if a move is legal for the king
     * 
     * @param x posiiton on the board
     * @param y position on the board
     * @return true if the move if possible, false otherwise
     */
    private Boolean moveCheck(Integer x, Integer y) {
        Boolean xIn = x <=8 && x >= 1;
        Boolean yIn = y <=8 && y >= 1;

        if (xIn && yIn) {
            String position = x + ";" + y;
            for (Piece key : getCurrBoard().getAttSquares().keySet()) {
                if (key.isOp(this)) {
                    if (getCurrBoard().getAttSquares().get(key).contains(position)) {
                        return false;
                    } 
                }
            }

            return true;
        }
        
        return false;

    }
    
}