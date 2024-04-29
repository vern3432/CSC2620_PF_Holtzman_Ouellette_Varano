package com.networkchess.GameLogic.pieces;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.networkchess.GameLogic.Board;

/**
 * Pawn
 */
public class Pawn extends Piece {

    // Flag to tell if piece has moved yet
    private Boolean movedYet = false;
    // Move number the pawn moved forward two
    private Integer moveMF2;

    /**
     * Constructs the Pawn piece with the proper information
     * 
     * @param color of the piece (white or black)
     * @param currPosition of the piece on the board
     * @param currBoard the piece is part of
     */
    public Pawn(String color, String currPosition, Board currBoard) {
        super(color, currPosition, currBoard);
    }

    /**
     * Gets the move number that the pawn moved forward two spaces on
     * 
     * @return move number pawn moved forward two
     */
    public Integer getMoveMF2() {
        return moveMF2;
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
        Board b = getCurrBoard();

        LinkedList<String> posMoves = new LinkedList<>();

        if (getColor().equals("white")) {
            if (!movedYet) {
                if ((y + 2) <= 8 && (b.getPosition(x, y + 2) == null && b.getPosition(x, y + 1) == null))
                    posMoves.add(x + ";" + (y + 2));
                if (y + 1 <= 8 && b.getPosition(x, y + 1) == null)
                    posMoves.add(x + ";" + (y + 1));
            }
            else
            {
                if (y + 1 <= 8 && b.getPosition(x, y + 1) == null)
                    posMoves.add(x + ";" + (y + 1));
            }

            if (x > 1 && x < 8 && y != 8) {
                if (b.getPosition(x-1, y+1) != null && isOp(b.getPosition(x-1, y+1))) {
                    posMoves.add((x - 1) + ";" + (y + 1));
                }
                if (b.getPosition(x+1, y+1) != null && isOp(b.getPosition(x-1, y+1))) {
                    posMoves.add((x + 1) + ";" + (y + 1));
                }
                Piece leftPice = b.getPosition(x-1, y);
                Piece rightPice = b.getPosition(x+1, y);
                if (leftPice != null && leftPice instanceof Pawn && ((Pawn)leftPice).getMoveMF2() == b.getMoveNum() && isOp(leftPice)) {
                    posMoves.add((x-1) + ";" + (y));
                }
                if (rightPice != null && rightPice instanceof Pawn && ((Pawn)rightPice).getMoveMF2() == b.getMoveNum() && isOp(rightPice)) {
                    posMoves.add((x+1) + ";" + (y));
                }
            }
            if (x == 1 && y != 8) {
                if (b.getPosition(x+1, y+1) != null && isOp(b.getPosition(x+1, y+1))) {
                    posMoves.add((x + 1) + ";" + (y + 1));
                }
                Piece rightPice = b.getPosition(x+1, y);
                if (rightPice != null && rightPice instanceof Pawn && ((Pawn)rightPice).getMoveMF2() == b.getMoveNum() && isOp(rightPice)) {
                    posMoves.add((x+1) + ";" + (y));
                }
            }
            if (x == 8 && y != 8) {
                if (b.getPosition(x-1, y+1) != null && isOp(b.getPosition(x-1, y+1))) {
                    posMoves.add((x - 1) + ";" + (y + 1));
                }
                Piece leftPice = b.getPosition(x-1, y);
                if (leftPice != null && leftPice instanceof Pawn && ((Pawn)leftPice).getMoveMF2() == b.getMoveNum() && isOp(leftPice)) {
                    posMoves.add((x-1) + ";" + (y));
                }
            }
        }
        else 
        {
            if (!movedYet) {
                if (y + 1 <= 8 && (b.getPosition(x, y - 2) == null && b.getPosition(x, y - 1) == null))
                    posMoves.add(x + ";" + (y - 2));
                if (y + 1 <= 8 && b.getPosition(x, y - 1) == null)
                    posMoves.add(x + ";" + (y - 1));
            }
            else 
            {
                if (y + 1 <= 8 && b.getPosition(x, y - 1) == null)
                    posMoves.add(x + ";" + (y - 1));
            }
            if (x > 1 && x < 8 && y != 8) {
                if (b.getPosition(x-1, y-1) != null && isOp(b.getPosition(x-1, y+1))) {
                    posMoves.add((x - 1) + ";" + (y - 1));
                }
                if (b.getPosition(x+1, y-1) != null && isOp(b.getPosition(x+1, y+1))) {
                    posMoves.add((x + 1) + ";" + (y - 1));
                }
                Piece leftPice = b.getPosition(x-1, y);
                Piece rightPice = b.getPosition(x+1, y);
                if (leftPice != null && leftPice instanceof Pawn && ((Pawn)leftPice).getMoveMF2() == b.getMoveNum() && isOp(leftPice)) {
                    posMoves.add((x-1) + ";" + (y));
                }
                if (rightPice != null && rightPice instanceof Pawn && ((Pawn)rightPice).getMoveMF2() == b.getMoveNum() && isOp(rightPice)) {
                    posMoves.add((x+1) + ";" + (y));
                }
            }
            if (x == 1 && y != 0) {
                if (b.getPosition(x+1, y-1) != null && isOp(b.getPosition(x+1, y-1))) {
                    posMoves.add((x + 1) + ";" + (y - 1));
                }
                Piece rightPice = b.getPosition(x+1, y);
                if (rightPice != null && rightPice instanceof Pawn && ((Pawn)rightPice).getMoveMF2() == b.getMoveNum() && isOp(rightPice)) {
                    posMoves.add((x+1) + ";" + (y));
                }
            }
            if (x == 8 && y != 0) {
                if (b.getPosition(x-1, y-1) != null && isOp(b.getPosition(x-1, y-1))) {
                    posMoves.add((x - 1) + ";" + (y - 1));
                }
                Piece leftPice = b.getPosition(x-1, y);
                if (leftPice != null && leftPice instanceof Pawn && ((Pawn)leftPice).getMoveMF2() == b.getMoveNum() && isOp(leftPice)) {
                    posMoves.add((x-1) + ";" + (y));
                }
            }
        }

        return posMoves;
    }

    public LinkedList<String> posAttackMoves() {
        LinkedList<String> posAttMoves = new LinkedList<>();
        String[] xy = getCurrPosition().split(";");
        Integer x = Integer.valueOf(xy[0]);
        Integer y = Integer.valueOf(xy[1]);

        if (getColor().equals("white")) {
            if (x+1 <= 8) {
                posAttMoves.add((x+1)+";"+(y+1));
            }
            if (x-1 >= 1) {
                posAttMoves.add((x-1)+";"+(y+1));
            }
        }
        else
        {
            if (x+1 <= 8) {
                posAttMoves.add((x+1)+";"+(y-1));
            }
            if (x-1 >= 1) {
                posAttMoves.add((x-1)+";"+(y-1));
            }
        }

        return posAttMoves;
    }

    @Override
    public String toString() {
        if (getColor().equals("white")) {
            return "P";
        }
        else
        {
            return "p";
        }
    }

}