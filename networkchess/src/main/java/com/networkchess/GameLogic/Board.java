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
import javax.swing.JOptionPane;
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
    private HashMap<Piece, LinkedList<String>> attSquares = new HashMap<>();

    // Instance of all the single pieces
    private King whiteKing;
    private King blackKing;
    private Queen whiteQueen;
    private Queen blackQueen;

    // Instances of all the pieces that appear twice
    //  (Q means queen side and K means king side)
    private Rook whiteRookK;
    private Rook whiteRookQ;
    private Rook blackRookK;
    private Rook blackRookQ;
    private Bishop whiteBishopK;
    private Bishop whiteBishopQ;
    private Bishop blackBishopK;
    private Bishop blackBishopQ;
    private Knight whiteKnightK;
    private Knight whiteKnightQ;
    private Knight blackKnightK;
    private Knight blackKnightQ;

    // Instance of all the pawns on the board
    private LinkedList<Pawn> whitePawns = new LinkedList<>();
    private LinkedList<Pawn> blackPawns = new LinkedList<>();

    /**
     * Builds the board with all the pieces in the correct locations
     */
    public Board() {
        // creates the lists in the taken pieces hashp for later use
        takenPieces.put("white", new LinkedList<Piece>());
        takenPieces.put("black", new LinkedList<Piece>());

        // adds the black pieces to the board
        this.blackRookQ = new Rook("black", "1;8", this);
        gameBoard[0][7] = this.blackRookQ;
        this.blackKnightQ = new Knight("black", "2;8", this);
        gameBoard[1][7] = this.blackKnightQ;
        this.blackBishopQ = new Bishop("black", "3;8", this);
        gameBoard[2][7] = this.blackBishopQ;
        this.blackQueen = new Queen("black", "4;8", this);
        gameBoard[3][7] = this.blackQueen;
        this.blackKing = new King("black", "5;8", this);
        gameBoard[4][7] = this.blackKing;
        this.blackBishopK = new Bishop("black", "6;8", this);
        gameBoard[5][7] = this.blackBishopK;
        this.blackKnightK = new Knight("black", "7;8", this);
        gameBoard[6][7] = this.blackKnightK;
        this.blackRookK = new Rook("black", "8;8", this);
        gameBoard[7][7] = this.blackRookK;
        for (int i = 0; i <= 7; i++) {  
            this.blackPawns.add(new Pawn("black", (i + 1) + ";7", this));
            gameBoard[i][6] = this.blackPawns.get(i);
        }

        // adds the white pieces to the board
        this.whiteRookQ = new Rook("white", "1;1", this);
        gameBoard[0][0] = this.whiteRookQ;
        this.whiteKnightQ = new Knight("white", "2;1", this);
        gameBoard[1][0] = this.whiteKnightQ;
        this.whiteBishopQ = new Bishop("white", "3;1", this);
        gameBoard[2][0] = this.whiteBishopQ;
        this.whiteQueen = new Queen("white", "4;1", this);
        gameBoard[3][0] = this.whiteQueen;
        this.whiteKing = new King("white", "5;1", this);
        gameBoard[4][0] = this.whiteKing;
        this.whiteBishopK = new Bishop("white", "6;1", this);
        gameBoard[5][0] = this.whiteBishopK;
        this.whiteKnightK = new Knight("white", "7;1", this);
        gameBoard[6][0] = this.whiteKnightK;
        this.whiteRookK = new Rook("white", "8;1", this);
        gameBoard[7][0] = this.whiteRookK;
        for (int i = 0; i <= 7; i++) {
            this.whitePawns.add(new Pawn("white", (i + 1) + ";2", this));
            gameBoard[i][1] = this.whitePawns.get(i);
        }

        // adds all the possible moves to the hashmap for later use
        for (int x = 0; x <= 7; x++) {
            for (int y = 0; y <= 7; y++) {
                if (gameBoard[x][y] != null) {
                    if (gameBoard[x][y] instanceof Pawn) {
                        attSquares.put(gameBoard[x][y], ((Pawn) gameBoard[x][y]).posAttackMoves());
                    }
                    else
                    {
                        attSquares.put(gameBoard[x][y], gameBoard[x][y].possibleMoves());
                    }
                }
            }
        }
    }

    // All the getter for all the pieces on the board
    public Bishop getBlackBishopQ() {
        return blackBishopQ;
    }
    public Bishop getBlackBishopK() {
        return blackBishopK;
    }
    public King getBlackKing() {
        return blackKing;
    }
    public Knight getBlackKnightQ() {
        return blackKnightQ;
    }
    public Knight getBlackKnightK() {
        return blackKnightK;
    }
    public LinkedList<Pawn> getBlackPawns() {
        return blackPawns;
    }
    public Queen getBlackQueen() {
        return blackQueen;
    }
    public Rook getBlackRookQ() {
        return blackRookQ;
    }
    public Rook getBlackRookK() {
        return blackRookK;
    }
    public Bishop getWhiteBishopQ() {
        return whiteBishopQ;
    }
    public Bishop getWhiteBishopK() {
        return whiteBishopK;
    }
    public King getWhiteKing() {
        return whiteKing;
    }
    public Knight getWhiteKnightQ() {
        return whiteKnightQ;
    }
    public Knight getWhiteKnightK() {
        return whiteKnightK;
    }
    public Rook getWhiteRookQ() {
        return whiteRookQ;
    }
    public LinkedList<Pawn> getWhitePawns() {
        return whitePawns;
    }
    public Queen getWhiteQueen() {
        return whiteQueen;
    }
    public Rook getWhiteRookK() {
        return whiteRookK;
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
                if (checkCheck(piece.getColor())) {
                    JOptionPane.showMessageDialog(new JPanel(), "That is not a possible move. You will still/be in check.");
                    gameBoard[px][py] = piece;
                }
                else
                {
                    piece.setCurrPosition(newPosition);
                
                    PromotionPopUp popUp = new PromotionPopUp(this, (Pawn) piece);
                    popUp.setSize(800, 500);
                    popUp.setVisible(true);
                }
            }
            else if (gameBoard[nx][ny] != null) {
                Piece takenP = gameBoard[nx][ny];

                gameBoard[nx][ny] = piece;
                piece.setCurrPosition(newPosition);
                updateAttSquares();

                if (checkCheck(piece.getColor())) {
                    JOptionPane.showMessageDialog(new JPanel(), "That is not a possible move. You will still/be in check.");
                    gameBoard[nx][ny] = takenP;
                    gameBoard[px][py] = piece;
                    piece.setCurrPosition(prevPosition);
                    updateAttSquares();
                }
                else
                {
                    takenPieces.get(takenP.getColor().toLowerCase()).add(takenP);
                    attSquares.remove(takenP);
                    if (piece instanceof Pawn) {
                        ((Pawn) piece).moved();
                    }
                    if (piece instanceof King) {
                        ((King) piece).moved();
                    }
                    if (piece instanceof Rook) {
                        ((Rook) piece).moved();
                    }
                    this.moveNum++;
                }

            }
            else
            {
                gameBoard[nx][ny] = piece;
                piece.setCurrPosition(newPosition);
                updateAttSquares();

                if (checkCheck(piece.getColor())) {
                    JOptionPane.showMessageDialog(new JPanel(), "That is not a possible move. You will still/be in check.");
                    gameBoard[px][py] = piece;
                    piece.setCurrPosition(prevPosition);
                    updateAttSquares();
                }
                else
                {
                    if (piece instanceof Pawn) {
                        ((Pawn) piece).moved();
                    }
                    if (piece instanceof King) {
                        ((King) piece).moved();
                    }
                    if (piece instanceof Rook) {
                        ((Rook) piece).moved();
                    }
                    this.moveNum++;
                }
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
        this.moveNum++;
    }

    /**
     * This method checks if the inputed colors king is in check
     * 
     * @param color of the king in quesiton
     * @return true if the given kings color is in check, false otherwise
     */
    public Boolean checkCheck(String color) {
        if (color.equals("white")) {
            for (Piece key : attSquares.keySet()) {
                if (whiteKing.isOp(key)) {
                    if (attSquares.get(key).contains(whiteKing.getCurrPosition())) {
                        return true;
                    }
                }
            }
        }
        else
        {
            for (Piece key : attSquares.keySet()) {
                if (blackKing.isOp(key)) {
                    if (attSquares.get(key).contains(blackKing.getCurrPosition())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gets the current number of moves in the game
     * 
     * @return current move of game
     */
    public Integer getMoveNum() {
        return this.moveNum;
    }

    /**
     * Gets the squares that are underattack from a piece
     * 
     * @return the hash map of squares that are under attack
     */
    public HashMap<Piece, LinkedList<String>> getAttSquares() {
        return attSquares;
    }
    
    public void updateAttSquares() {
        for (int x  = 0; x <=7; x++) {
            for (int y = 0; y <= 7; y++) {
                if (gameBoard[x][y] != null) {
                    if (attSquares.keySet().contains(gameBoard[x][y])) {
                        if (gameBoard[x][y] instanceof Pawn) {
                            attSquares.get(gameBoard[x][y]).addAll(((Pawn) gameBoard[x][y]).posAttackMoves());
                        }
                        else
                        {
                            attSquares.get(gameBoard[x][y]).addAll(gameBoard[x][y].possibleMoves());
                        }
                    }
                    else
                    {
                        attSquares.put(gameBoard[x][y], new LinkedList<>());
                        if (gameBoard[x][y] instanceof Pawn) {
                            attSquares.get(gameBoard[x][y]).addAll(((Pawn) gameBoard[x][y]).posAttackMoves());
                        }
                        else
                        {
                            attSquares.get(gameBoard[x][y]).addAll(gameBoard[x][y].possibleMoves());
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {

        String boardPositions = "";

        boardPositions += "    [a][b][c][d][e][f][g][h]\n\n";
        for (int y = 7; y >= 0; y--) {
            boardPositions += "[" + (y+1) + "] ";
            for (int x = 7; x >= 0; x--) {
                if (gameBoard[x][y] == null) {
                    boardPositions += "[ ]";
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
        private JFrame window = this;

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
                    window.dispose();
                }
            });
            buttonPanel.add(qButton);

            JButton rButton = new JButton("Rook");
            rButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    promotedToPiece = new Rook(piece.getColor(), piece.getCurrPosition(), piece.getCurrBoard());
                    b.updateBoard(piece, promotedToPiece);
                    window.dispose();
                }
            });
            buttonPanel.add(rButton);

            JButton kButton = new JButton("Knight");
            kButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    promotedToPiece = new Knight(piece.getColor(), piece.getCurrPosition(), piece.getCurrBoard());
                    b.updateBoard(piece, promotedToPiece);
                    window.dispose();
                }
            });
            buttonPanel.add(kButton);

            JButton bButton = new JButton("Bishop");
            bButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    promotedToPiece = new Bishop(piece.getColor(), piece.getCurrPosition(), piece.getCurrBoard());
                    b.updateBoard(piece, promotedToPiece);
                    window.dispose();
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
