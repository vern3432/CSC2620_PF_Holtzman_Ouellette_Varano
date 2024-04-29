package com.networkchess.GameLogic.pieces;
import com.networkchess.GameLogic.*;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Bishop
 */
public class Bishop extends Piece{

    /**
     * Constructs the Bishop piece with the proper information
     * 
     * @param color of the piece (white or black)
     * @param currPosition of the piece on the board
     * @param currBoard the piece is part of
     */
    public Bishop(String color, String currPosition, Board currBoard) {
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
        String[] xy = this.getCurrPosition().split(";");
        Integer x = Integer.valueOf(xy[0]);
        Integer y = Integer.valueOf(xy[1]);


        LinkedList<String> posMoves = new LinkedList<>();
        Board board = getCurrBoard();

        Boolean flagUR = true;
        Boolean flagUL = true;
        Boolean flagDR = true;
        Boolean flagDL = true;
        for (int i = 1; i <= 7; i++) {
            if (flagUL && x-i >= 1 && y+i <= 8) {
                if ((board.getPosition(x-i, y+i) != null)) {
                    if (isOp(board.getPosition(x-i, y+i))) {
                        posMoves.add((x-i) + ";" + (y+i));
                        flagUL = false;
                    }
                    else
                    {
                        flagUL = false;
                    }
                }
                else 
                {
                    posMoves.add((x-i) + ";" + (y+i));
                }
            }
            if (flagUR && x+i <= 8 && y+i <= 8) {
                if ((board.getPosition(x+i, y+i) != null)) {
                    if (isOp(board.getPosition(x+i, y+i))) {
                        posMoves.add((x+i) + ";" + (y+i));
                        flagUR = false;
                    }
                    else
                    {
                        flagUR = false;
                    }
                }
                else 
                {
                    posMoves.add((x+i) + ";" + (y+i));
                }
            }
            if (flagDR && x+i <= 8 && y-i >= 1) {
                if ((board.getPosition(x+i, y-i) != null)) {
                    if (isOp(board.getPosition(x+i, y-i))) {
                        posMoves.add((x+i) + ";" + (y-i));
                        flagDR = false;
                    }
                    else
                    {
                        flagDR = false;
                    }
                }
                else 
                {
                    posMoves.add((x+i) + ";" + (y-i));
                }
            }
            if (flagDL && x-i >= 1 && y-i >= 1) {
                if ((board.getPosition(x-i, y-i) != null)) {
                    if (isOp(board.getPosition(x-i, y-i))) {
                        posMoves.add((x-i) + ";" + (y-i));
                        flagDL = false;
                    }
                    else
                    {
                        flagDL = false;
                    }
                }
                else 
                {
                    posMoves.add((x-i) + ";" + (y-i));
                }
            }
        }

        return posMoves;
    }

    @Override
    public String toString() {
        if (getColor().equals("white")) {
            return "B";
        }
        else
        {
            return "b";
        }
    }

    
}