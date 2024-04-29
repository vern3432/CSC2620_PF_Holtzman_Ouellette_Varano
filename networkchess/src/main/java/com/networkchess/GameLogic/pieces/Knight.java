package com.networkchess.GameLogic.pieces;
import com.networkchess.GameLogic.*;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Knight
 */
public class Knight extends Piece {

    /**
     * Constructs the Knight piece with the proper information
     * 
     * @param color of the piece (white or black)
     * @param currPosition of the piece on the board
     * @param currBoard the piece is part of
     */
    public Knight(String color, String currPosition, Board currBoard) {
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
     * Looks at the board and checks to see all the possible moves that the piece can make
     * 
     * @return a linked list that contains the possible moves the piece can make
     */
    @Override
    public LinkedList<String> possibleMoves() {
        String[] xy = getCurrPosition().split(";");
        Integer x = Integer.valueOf(xy[0]);
        Integer y = Integer.valueOf(xy[1]);

        LinkedList<String> posMoves = new LinkedList<>();

        if (flag(x-2, y+1)) {
            posMoves.add((x-2) + ";" + (y+1));
        }
        if (flag(x-1, y+2)) {
            posMoves.add((x-1) + ";" + (y+2));
        }
        if (flag(x+1, y+2)) {
            posMoves.add((x+1) + ";" + (y+2));
        }
        if (flag(x+2, y+1)) {
            posMoves.add((x+2) + ";" + (y+1));
        }
        if (flag(x+2, y-1)) {
            posMoves.add((x+2) + ";" + (y-1));
        }
        if (flag(x+1, y-2)) {
            posMoves.add((x+1) + ";" + (y-2));
        }
        if (flag(x-2, y-1)) {
            posMoves.add((x-2) + ";" + (y-1));
        }
        if (flag(x-1, y-2)) {
            posMoves.add((x-1) + ";" + (y-2));
        }

        return posMoves;
    }

    /**
     * Makes for easy checking if that move can be made
     * 
     * @param x value on board
     * @param y value on board
     * @return true if the move can be made, false otherwise
     */
    private Boolean flag(int x, int y) {
        Board board = getCurrBoard();
        boolean xInBounds = x<=8 && x>=1;
        boolean yInBounds = y<=8 && y>=1;

        return (xInBounds && yInBounds && (board.getPosition(x, y) == null || isOp(board.getPosition(x, y))));
    }

    @Override
    public String toString() {
        if (getColor().equals("white")) {
            return "N";
        }
        else
        {
            return "n";
        }
    }

}