package com.networkchess.GameLogic.pieces;
import com.networkchess.GameLogic.*;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Queen
 */
public class Queen extends Piece{

    /**
     * Constructs the Queen piece with the proper information
     * 
     * @param color of the piece (white or black)
     * @param currPosition of the piece on the board
     * @param currBoard the piece is part of
     */
    public Queen(String color, String currPosition, Board currBoard) {
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
        
        checkDirection(0, 1, posMoves);
        checkDirection(1, 1, posMoves);
        checkDirection(1, 0, posMoves);
        checkDirection(1, -1, posMoves);
        checkDirection(0, -1, posMoves);
        checkDirection(-1, -1, posMoves);
        checkDirection(-1, 0, posMoves);
        checkDirection(-1, 1, posMoves);

        return posMoves;
    }

    @Override
    public String toString() {
        if (getColor().equals("white")) {
            return "Q";
        }
        else
        {
            return "q";
        }
    }

    private void checkDirection(int xc, int yc, LinkedList<String> posMovesList) {
        String[] xy = this.getCurrPosition().split(";");
        Integer xp = Integer.valueOf(xy[0]);
        Integer yp = Integer.valueOf(xy[1]);
        
        Boolean flagDirction = true;
        Board board = getCurrBoard();

        for (int i = 1; i <= 7; i++) {
            int x = xp + (i * xc);
            int y = yp + (i * yc);
            if (flagDirction && x <=8 && x >=1 && y >= 1 && y <= 8) {
                if ((board.getPosition(x, y) != null)) {
                    if (isOp(board.getPosition(x, y))) {
                        posMovesList.add((x) + ";" + (y));
                        return;
                    }
                    else 
                    {
                        return;
                    }
                }
                else 
                {
                    posMovesList.add((x) + ";" + (y));
                }   
            }
        }
        
    }
    
}