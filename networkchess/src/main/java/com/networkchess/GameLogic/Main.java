
package com.networkchess.GameLogic;
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

        /* check en passant

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
        */

        /* check the null pointer error
        System.out.println();

        Board b = new Board();

        System.out.println(b);

        b.updateBoard(b.getPosition(1, 2), "1;4");
        System.out.println(b);

        b.updateBoard(b.getPosition(2, 7), "2;5");
        System.out.println(b);

        b.updateBoard(b.getPosition(1, 4), "2;5");
        System.out.println(b);

        b.updateBoard(b.getPosition(3, 7), "3;6");
        System.out.println(b);

        b.updateBoard(b.getPosition(2, 5), "3;6");

        System.out.println(b);
        */

        /* test duplicate piece if check comes from that move
        System.out.println();

        Board b = new Board();

        b.updateBoard(b.getPosition(4, 2), "4;4");
        System.out.println(b);

        b.updateBoard(b.getPosition(5, 2), "5;4");
        System.out.println(b);

        b.updateBoard(b.getPosition(4, 7), "4;5");
        System.out.println(b);

        b.updateBoard(b.getPosition(5, 7), "5;5");
        System.out.println(b);

        b.updateBoard(b.getPosition(4, 8), "8;4");
        System.out.println(b);

        b.updateBoard(b.getPosition(6, 2), "6;4");
        System.out.println(b);
        */
        

    }
}
