package com.networkchess.GameLogic.pieces;
import com.networkchess.GameLogic.*;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * King
 */
public class King extends Piece {

    private Boolean movedYet = false;

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
        if (queenSideCastle()) {
            posMoves.add("O-O-O");
        }
        if (kingSideCastle()) {
            posMoves.add("O-O");
        }

        return posMoves;
    }

    @Override
    public String toString() {
        if (getColor().equals("white")) {
            return "K";
        }
        else
        {
            return "k";
        }
    }

    public void moved() {
        this.movedYet = true;
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

    private Boolean queenSideCastle() {
        Board b = getCurrBoard();
        Rook rookQS = null;
        if (getColor().equals("white")) {
            rookQS = b.getWhiteRookQ();
            if (!rookQS.getMovedYet() &&
                b.getPosition(5,1) == null &&
                b.getPosition(6,1) == null &&
                b.getPosition(7,1) == null) {
                for (Piece key : b.getAttSquares().keySet()) {
                    if (key.isOp(this) &&
                        !b.getAttSquares().get(key).contains("5;1") &&
                        !b.getAttSquares().get(key).contains("6;1") &&
                        !b.getAttSquares().get(key).contains("7;1")) {
                        return true;
                    }
                }
            }
            return false;
        }
        else
        {
            rookQS = b.getBlackRookQ();
            if (!rookQS.getMovedYet() &&
                b.getPosition(5,1) == null &&
                b.getPosition(6,1) == null &&
                b.getPosition(7,1) == null) {
                for (Piece key : b.getAttSquares().keySet()) {
                    if (key.isOp(this) &&
                        !b.getAttSquares().get(key).contains("5;1") &&
                        !b.getAttSquares().get(key).contains("6;1") &&
                        !b.getAttSquares().get(key).contains("7;1")) {
                        return true;
                    }
                }
            }

            return false;
        }

    }

    private Boolean kingSideCastle() {
        Board b = getCurrBoard();
        Rook rookKS = null;
        if (getColor().equals("white")) {
            rookKS = b.getWhiteRookK();
            if (!rookKS.getMovedYet() &&
                b.getPosition(2,1) == null &&
                b.getPosition(3,1) == null) {
                for (Piece key : b.getAttSquares().keySet()) {
                    if (key.isOp(this) &&
                        !b.getAttSquares().get(key).contains("3;1") &&
                        !b.getAttSquares().get(key).contains("2;1")) {
                        return true;
                    }
                }
            }
            return false;
        }
        else
        {
            rookKS = b.getBlackRookK();
            if (!rookKS.getMovedYet() &&
                b.getPosition(2,1) == null &&
                b.getPosition(3,1) == null) {
                for (Piece key : b.getAttSquares().keySet()) {
                    if (key.isOp(this) &&
                        !b.getAttSquares().get(key).contains("2;1") &&
                        !b.getAttSquares().get(key).contains("3;1")) {
                        return true;
                    }
                }
            }

            return false;
        }

    }
    
}