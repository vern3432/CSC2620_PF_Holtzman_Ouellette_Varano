package com.networkchess.GameLogic;
import com.networkchess.GameLogic.pieces.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Board
 */
public class Board {

    // The move number of the game
    private Integer moveNum;
    // Stores the pieces that have been taken by color
    private HashMap<String, LinkedList<Piece>> takenPieces = new HashMap<>();
    // This is the current game board that 
    private Piece[][] gameBoard = new Piece[8][8];
    // This holds all the possibles move that are currently possible so the kings knows where it can go
    private HashMap<Piece, LinkedList<String>> allPosMoves = new HashMap<>();

    /**
     * Builds the board with all the pieces in the correct locations
     */
    public Board() {
        // creates the lists in the taken pieces hashp for later use
        takenPieces.put("white", new LinkedList<Piece>());
        takenPieces.put("black", new LinkedList<Piece>());

        // adds the black pieces to the board
        gameBoard[0][7] = new Rook("black", "1;8", this);
        gameBoard[1][7] = new Knight("black", "2;8", this);
        gameBoard[2][7] = new Bishop("black", "3;8", this);
        gameBoard[3][7] = new Queen("black", "4;8", this);
        gameBoard[4][7] = new King("black", "5;8", this);
        gameBoard[5][7] = new Bishop("black", "6;8", this);
        gameBoard[6][7] = new Knight("black", "7;8", this);
        gameBoard[7][7] = new Rook("black", "8;8", this);
        for (int i = 0; i <= 7; i++) {
            gameBoard[i][6] = new Pawn("black", (i + 1) + ";7", this);
        }

        // adds the white pieces to the board
        gameBoard[0][0] = new Rook("white", "1;1", this);
        gameBoard[1][0] = new Knight("white", "2;1", this);
        gameBoard[2][0] = new Bishop("white", "3;1", this);
        gameBoard[3][0] = new Queen("white", "4;1", this);
        gameBoard[4][0] = new King("white", "5;1", this);
        gameBoard[5][0] = new Bishop("white", "6;1", this);
        gameBoard[6][0] = new Knight("white", "7;1", this);
        gameBoard[7][0] = new Rook("white", "8;1", this);
        for (int i = 0; i <= 7; i++) {
            gameBoard[i][1] = new Pawn("white", (i + 1) + ";2", this);
        }

        // adds all the possible moves to the hashmap for later use
        for (int x = 0; x <= 7; x++) {
            for (int y = 0; y <= 7; y++) {
                if (gameBoard[x][y] != null) {
                    allPosMoves.put(gameBoard[x][y], gameBoard[x][y].possibleMoves());
                }
            }
        }
    }

    /**
     * Method that returns a piece if there is a piece in that given position, null otherwise
     * 
     * @param x value of matrix
     * @param y value of matrix
     * @return a piece if there is a piece in that given position, null otherwise
     */
    public Piece getPosition(Integer x, Integer y) {
        return gameBoard[x-1][y-1];
    }

    // THIS IS GOING TO HAVE TO CHANGE DEPENDING ON HOW WE WANT THE USER TO MOVE THE PIECE
    /**
     * When a piece is moved the board should update
     * 
     * @param piece that is being moved
     * @param prevPosition that the piece is at
     * @param newPosition that the piece is going to
     */
    public void updateBoard(Piece piece, String prevPosition, String newPosition) {
        if (piece.movePos(newPosition)) {  
            String[] pxy = newPosition.split(";");
            Integer px = Integer.valueOf(pxy[0]);
            Integer py = Integer.valueOf(pxy[1]);
            
            String[] nxy = newPosition.split(";");
            Integer nx = Integer.valueOf(nxy[0]);
            Integer ny = Integer.valueOf(nxy[1]);

            gameBoard[px][py] = null;
            if (piece instanceof Pawn && ny == 8) {
                PromotionPopUp popUp = new PromotionPopUp(this, (Pawn) piece);
                popUp.setSize(800, 800);
                popUp.setVisible(true);

                Piece p = popUp.getPromotedToPiece();
                while (p == null) {
                    p = popUp.getPromotedToPiece();
                }

                gameBoard[nx][ny] = p;
                allPosMoves.put(p, p.possibleMoves());
                allPosMoves.remove(piece);
            }
            else if (gameBoard[nx][ny] != null) {
                Piece takenP = gameBoard[nx][ny];
                takenPieces.get(takenP.getColor().toLowerCase()).add(takenP);
                allPosMoves.remove(takenP);

                gameBoard[nx][ny] = piece;
                piece.setCurrPosition(newPosition);
                allPosMoves.replace(piece, piece.possibleMoves());
            }
            else
            {
                gameBoard[nx][ny] = piece;
                piece.setCurrPosition(newPosition);
                allPosMoves.replace(piece, piece.possibleMoves());
            }
        }
    }

    /**
     * This method handles updating the board whne a pawn is promoted
     * 
     * @param p pawn to promote
     * @param np piece to promote the pawn too
    */
    public void updateBoard(Pawn p, Piece np) {
        String[] xy = p.getCurrPosition().split(";");
        Integer x = Integer.valueOf(xy[0]);
        Integer y = Integer.valueOf(xy[1]);
        
        gameBoard[x][y] = np;
    }

    /**
     * Gets the current number of moves in the game
     * 
     * @return current move of game
     */
    public Integer getMoveNum() {
        return this.moveNum;
    }

    @Override
    public String toString() {

        String boardPositions = "";

        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                if (gameBoard[x][y] == null) {
                    boardPositions += "[blank space]";
                }
                else
                {
                    boardPositions += "[" + gameBoard[x][y] + "]";
                }
            }
            boardPositions += "\n";
        }

        return boardPositions;
    }

    public class PromotionPopUp extends JFrame {

        private Piece promotedToPiece;

        public PromotionPopUp(Board b, Pawn piece) {
            super("Pawn Promotion");

            GridLayout mainGrid = new GridLayout(2, 1, 5, 5);
            setLayout(mainGrid);

            JLabel promText = new JLabel("You are able to promote a pawn. Choose from one the pieces that are listed below.");
            add(promText);

            GridLayout promButtonLayout = new GridLayout(1, 4, 5, 5);
            JPanel buttonPanel = new JPanel(promButtonLayout);

            JButton qButton = new JButton("Queen");
            qButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    promotedToPiece = new Queen(piece.getColor(), piece.getCurrPosition(), piece.getCurrBoard());
                    b.updateBoard(piece, promotedToPiece);
                }
            });
            buttonPanel.add(qButton);

            JButton rButton = new JButton("Rook");
            rButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    promotedToPiece = new Rook(piece.getColor(), piece.getCurrPosition(), piece.getCurrBoard());
                    b.updateBoard(piece, promotedToPiece);
                }
            });
            buttonPanel.add(rButton);

            JButton kButton = new JButton("Knight");
            kButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    promotedToPiece = new Knight(piece.getColor(), piece.getCurrPosition(), piece.getCurrBoard());
                    b.updateBoard(piece, promotedToPiece);
                }
            });
            buttonPanel.add(kButton);

            JButton bButton = new JButton("Bishop");
            bButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    promotedToPiece = new Bishop(piece.getColor(), piece.getCurrPosition(), piece.getCurrBoard());
                    b.updateBoard(piece, promotedToPiece);
                }
            });
            buttonPanel.add(bButton);

            add(buttonPanel);
        }

        public Piece getPromotedToPiece() {
            return promotedToPiece;
        }

    }

}
