
package com.networkchess.GameLogic;
import com.networkchess.GameLogic.pieces.*;
/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        Pawn testW = new Pawn("white", "2;2", new Board());
        System.out.println("White:" +testW.possibleMoves());

        Pawn testB = new Pawn("black", "7;7", new Board());
        System.out.println("Black:" +testB.possibleMoves());

        Knight knightTest1 = new Knight("white", "4;4", new Board());
        System.out.println("Knight:" +knightTest1.possibleMoves());

        Bishop bishopTest1 = new Bishop("white", "4;4", new Board());
        System.out.println("Bishop:" +bishopTest1.possibleMoves());

        Rook rookTest1 = new Rook("white", "4;4", new Board());
        System.out.println("Rook:" +rookTest1.possibleMoves());

        Queen queenTest1 = new Queen("white", "4;4", new Board());
        System.out.println("Queen:" +queenTest1.possibleMoves());




    }
}
