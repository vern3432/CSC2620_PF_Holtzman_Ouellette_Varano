
package com.networkchess.GameLogic;

import com.networkchess.GameLogic.pieces.*;
/**
 * Main
 */
public class Main {
    public static void main(String[] args) {

        // Pawn testW = new Pawn("white", "2;2", new Board());
        // System.out.println("White:" +testW.possibleMoves());

        // Pawn testB = new Pawn("black", "7;7", new Board());
        // System.out.println("Black:" +testB.possibleMoves());

        // Knight knightTest1 = new Knight("white", "4;4", new Board());
        // System.out.println("Knight:" +knightTest1.possibleMoves());

        // Bishop bishopTest1 = new Bishop("white", "4;4", new Board());
        // System.out.println("Bishop:" +bishopTest1.possibleMoves());

        // Rook rookTest1 = new Rook("white", "4;4", new Board());
        // System.out.println("Rook:" +rookTest1.possibleMoves());

        // Queen queenTest1 = new Queen("white", "4;4", new Board());
        // System.out.println("Queen:" +queenTest1.possibleMoves());

        // King kingTest = new King("white", "4;4", new Board());
        // System.out.println("King:" +kingTest.possibleMoves());

        System.out.println();

        Board b = new Board();

        System.out.println(b);

        System.out.println(b.getPosition(5, 2).possibleMoves());
        System.out.println(b.getPosition(5, 2).getClass());
        System.out.println(b.getPosition(5, 2).getColor());

        b.updateBoard(b.getPosition(5, 2), "5;4");

        System.out.println();
        System.out.println(b);

        b.updateBoard(b.getPosition(5, 7), "5;6");
        System.out.println(b);
        b.updateBoard(b.getPosition(5, 4), "5;5");
        System.out.println(b);
        b.updateBoard(b.getPosition(6, 7), "6;5");
        System.out.println(b);

        System.out.println(b.getPosition(5, 5).possibleMoves());
        System.out.println(b.getPosition(5, 5).getClass());
        System.out.println(b.getPosition(5, 5).getColor());

        b.updateBoard(b.getPosition(5, 5), "6;6");

        System.out.println(b);

    }
}
