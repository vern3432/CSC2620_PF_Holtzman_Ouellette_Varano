package com.networkchess.GUI;

import com.networkchess.GameLogic.Board;
import com.networkchess.GameLogic.pieces.Piece;
import com.networkchess.Net.Message;
import merrimackutil.json.types.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import static merrimackutil.json.JsonIO.readObject;

/**
 * Model is our internal representation of the chess game which deals with updating the board and communicating with the server
 * Model should run on a separate thread so it can wait for server updates
 */
public class Model extends JPanel implements Runnable {
    /**
     * Board object which has all of our pieces
     */
    private Board board = new Board();
    /**
     * Board label us text of board for testing
     */
    private JTextArea boardLabel = new JTextArea();
    /**
     * String of name of current game
     */
    private String name;
    /**
     * String of the IP address to the central server we communicate with
     */
    private String serverAddr;
    /**
     * int of the port the central server is listening on
     */
    private int serverPort;
    /**
     * The color of player we are: white or black
     */
    private String color = "";
    /**
     * boolean for if it is our turn
     */
    private boolean isTurn = false;
    /**
     * Output stream to server
     */
    private PrintWriter send;
    /**
     * Boolean for if we should end the game
     *
     * May do more with this later
     */
    private boolean endGame = false;

    /**
     * HashMap to store icons for each chess piece
     */
    private HashMap<String, ImageIcon> pieceIcons = new HashMap<>();
    JPanel gameJpanel = new JPanel(new GridBagLayout());
    private HashMap<String, JButton> buttonMap = new HashMap<>();

    /**
     * Creates model for internal representation of chess game
     * @param _name - name given to game
     */
    public Model(String _name, String _serverAddr, int _serverPort) {
        name = _name;
        serverAddr = _serverAddr;
        serverPort = _serverPort;

        loadPieceIcons();

        //adds board object toString as a placeholder
        this.setLayout(new BorderLayout());

        //add placeholder center
        boardLabel.setText(board.toString());
        this.add(boardLabel,BorderLayout.CENTER);

        //add new Label
        this.add(new JLabel(name), BorderLayout.NORTH);

        //updates game
        updateGame();
    }

    /**
     * Load icons for each chess piece into the HashMap
     */
    private void loadPieceIcons() {
        try {
            pieceIcons.put("white_king", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PiecePNGS/whiteKing.png"))));
            pieceIcons.put("black_king", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PiecePNGS/blackKing.png"))));
            pieceIcons.put("white_queen", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PiecePNGS/whiteQueen.png"))));
            pieceIcons.put("black_queen", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PiecePNGS/blackQueen.png"))));
            pieceIcons.put("white_rook", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PiecePNGS/whiteRook.png"))));
            pieceIcons.put("black_rook", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PiecePNGS/blackRook.png"))));
            pieceIcons.put("white_bishop", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PiecePNGS/whiteBishop.png"))));
            pieceIcons.put("black_bishop", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PiecePNGS/blackBishop.png"))));
            pieceIcons.put("white_knight", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PiecePNGS/whiteKnight.png"))));
            pieceIcons.put("black_knight", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PiecePNGS/blackKnight.png"))));
            pieceIcons.put("white_pawn", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PiecePNGS/whitePawn.png"))));
            pieceIcons.put("black_pawn", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/PiecePNGS/blackPawn.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects to chess server and handles messages
     */
    @Override
    public void run() {
        try {

            //connect to server and get IO streams
            Socket sock = new Socket(serverAddr, serverPort);
            Scanner recv = new Scanner(sock.getInputStream());
            send = new PrintWriter(sock.getOutputStream(),true);

            //get server Welcome message
            JSONObject messageJSON = readObject(recv.nextLine());
            Message serverWelcome = new Message(messageJSON);

            //make sure we get expected message if not show an error
            if (!serverWelcome.getType().equals("WELCOME")) {
                System.err.println("Server should have sent welcome message exciting");
                System.err.println(serverWelcome);
                JOptionPane.showMessageDialog(null,"An error has occured game ended please try" +
                        "again later");
                return;
            }

            color = serverWelcome.getColor();

            //set turn based on server color
            if (color.equals("white")) {
                isTurn = true;
                JOptionPane.showMessageDialog(null,"You are the white player waiting for other player to join");
            }
            else {
                isTurn = false;
                JOptionPane.showMessageDialog(null,"You are the black player!");
            }

            System.out.println("Starting game as " + color + " player");

            while (true) {
                JSONObject recvJSON = readObject(recv.nextLine());
                Message recvMessage = new Message(recvJSON);

                switch (recvMessage.getType()) {
                    case "MOVE":
                        //update board with the new move
                        Piece pieceToMove = board.getPosition(recvMessage.getPieceX(),recvMessage.getPieceY());
                        board.updateBoard(pieceToMove, recvMessage.getMove());

                        isTurn = true;
                        updateGame();
                        break;

                    case "GAME":
                        //if the game is not running we will end it
                        endGame = !recvMessage.isRunning();

                        if (endGame) {
                            JOptionPane.showMessageDialog(null, "Game has ended \n"
                                    + recvMessage.getReason());
                            return;
                        }

                        JOptionPane.showMessageDialog(null,recvMessage.getReason());
                        updateGame();
                        break;
                }
            }

        } catch (ConnectException e) {
            System.err.println("Server cannot be reached on: " + serverAddr + ":" + serverPort);
            System.err.println("Please make sure server is running");
            System.err.println(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method will update the game window to whatever the current boards view of the game is
     */
    private void updateGame() {
        //create new game JPanel
        this.gameJpanel = new JPanel(new GridBagLayout());
        gameJpanel.setBackground(new Color(209, 135, 61));

        // Turn indicator
        JLabel turnIndicator = new JLabel();
        turnIndicator.setOpaque(true);
        turnIndicator.setPreferredSize(new Dimension(10, 10));
        if (isTurn) {
            turnIndicator.setBackground(Color.GREEN);
        } else {
            turnIndicator.setBackground(Color.RED);
        }
        gameJpanel.add(turnIndicator, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));

        //set grid bag constants to place buttons
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(35, 35, 35, 35);

        //create chessboard row by row
        for (int row = 8; row >= 1; row--) {
            //increase the row and columns when placing items
            gridBagConstraints.gridy += 1;
            gridBagConstraints.gridx += 1;

            //add row label math magic used to get rows in "reverse order"
            JLabel rowButton = new JLabel(String.valueOf((row-9) * -1));
            gameJpanel.add(rowButton,gridBagConstraints);
            for (int column = 1; column <= 8; column++) {
                //increase x
                gridBagConstraints.gridx += 1;

                // Declare pieceX and pieceY here
                int pieceX = column;
                int pieceY = row;

                //get piece at current position
                Piece currPiece = board.getPosition(column, row);
                JButton pieceButton;

                //if piece is not null add a button to represent it
                if (currPiece != null) {
                    String pieceKey = currPiece.getColor() + "_" + currPiece.getClass().getSimpleName().toLowerCase();
                    ImageIcon icon = pieceIcons.get(pieceKey);
                    pieceButton = new JButton(icon);
                    pieceButton.setActionCommand(column + ";" + row); // Set the position as action command
                    pieceButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //check if it is our turn
                            if (!isTurn) {
                                JOptionPane.showMessageDialog(null, "Cannot move it is not our turn");
                                return;
                            }

                            //check if it is our piece
                            if (!currPiece.getColor().equals(color)) {
                                JOptionPane.showMessageDialog(null,"Cannot move the opponents pieces" +
                                        "you are the " + color + " player");
                                return;
                            }
                            //get user input
                            LinkedList<String> moves = currPiece.possibleMoves();
                            highlightMoves(moves, currPiece, pieceX, pieceY);
                        }
                    });
                }
                //if null add placeholder button
                else {
                    pieceButton = new JButton("             ");
                }

                //add piece button in position
                gameJpanel.add(pieceButton,gridBagConstraints);
                pieceButton.setActionCommand(column + ";" + row); // Set the position as action command
                gameJpanel.add(pieceButton, gridBagConstraints);
                // Save the button with its grid position in the map
                buttonMap.put(pieceX + ";" + pieceY, pieceButton);

            }
            //reset row back to 0
            gridBagConstraints.gridx -= 9;
        }

        //increase x and y for bottom column labels
        gridBagConstraints.gridy += 1;
        gridBagConstraints.gridx += 2;

        //add letter label for each column
        for (int i = 0; i < 8; i++) {
            //get letter from number
            char column = (char) (i + 'A');
            //create JLabel with letter
            JLabel columnLabel = new JLabel(String.valueOf(column));
            //add JLabel
            gameJpanel.add(columnLabel, gridBagConstraints);
            //increase x
            gridBagConstraints.gridx += 1;
        }

        //remove everything in gui
        this.removeAll();

        //update top bar
        this.add(new JLabel("Game: " + name + " isTurn: " + isTurn + " Color: " +color + " Move number: " +
                board.getMoveNum()), BorderLayout.NORTH);
        //update chess game
        this.add(new JScrollPane(gameJpanel), BorderLayout.CENTER);

        System.out.println("current board:");
        System.out.println(board);
        //revalidate gui
        revalidate();
    }


    private void updateGame2() {
        //create new game JPanel
        this.gameJpanel = new JPanel(new GridBagLayout());
        gameJpanel.setBackground(new Color(209, 135, 61));


        //set grid bag constants to place buttons
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(35, 35, 35, 35);

        //create chessboard row by row
        for (int row = 8; row >= 1; row--) {
            //increase the row and columns when placing items
            gridBagConstraints.gridy += 1;
            gridBagConstraints.gridx += 1;

            //add row label math magic used to get rows in "reverse order"
            JLabel rowButton = new JLabel(String.valueOf((row-9) * -1));
            gameJpanel.add(rowButton,gridBagConstraints);
            for (int column = 1; column <= 8; column++) {
                //increase x
                gridBagConstraints.gridx += 1;

                // Declare pieceX and pieceY here
                int pieceX = column;
                int pieceY = row;

                //get piece at current position
                Piece currPiece = board.getPosition(column,row);
                JButton pieceButton;

                //if piece is not null add a button to represent it
                if (currPiece != null) {
                    String pieceKey = currPiece.getColor() + "_" + currPiece.getClass().getSimpleName().toLowerCase();
                    ImageIcon icon = pieceIcons.get(pieceKey);
                    pieceButton = new JButton(icon);
                    //column and row of current piece to send to server
                    pieceButton.setActionCommand(column + ";" + row); // Set the position as action command
                    pieceButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //check if it is our turn
                            if (!isTurn) {
                                JOptionPane.showMessageDialog(null, "Cannot move it is not our turn");
                                return;
                            }

                            //check if it is our piece
                            if (!currPiece.getColor().equals(color)) {
                                JOptionPane.showMessageDialog(null,"Cannot move the opponents pieces" +
                                        "you are the " + color + " player");
                                return;
                            }
                            //get user input
                            LinkedList<String> moves = currPiece.possibleMoves();
                            highlightMoves(moves, currPiece, pieceX, pieceY);
                        }
                    });
                }
                //if null add placeholder button
                else {
                    pieceButton = new JButton("             ");
                }

                //add piece button in position
                gameJpanel.add(pieceButton,gridBagConstraints);
                pieceButton.setActionCommand(column + ";" + row); // Set the position as action command
                gameJpanel.add(pieceButton, gridBagConstraints);
                // Save the button with its grid position in the map
                buttonMap.put(pieceX + ";" + pieceY, pieceButton);

            }
            //reset row back to 0
            gridBagConstraints.gridx -= 9;
        }

        //increase x and y for bottom column labels
        gridBagConstraints.gridy += 1;
        gridBagConstraints.gridx += 2;

        //add letter label for each column
        for (int i = 0; i < 8; i++) {
            //get letter from number
            char column = (char) (i + 'A');
            //create JLabel with letter
            JLabel columnLabel = new JLabel(String.valueOf(column));
            //add JLabel
            gameJpanel.add(columnLabel, gridBagConstraints);
            //increase x
            gridBagConstraints.gridx += 1;
        }

        //remove everything in gui
        this.removeAll();

        //update top bar
        this.add(new JLabel("Game: " + name + " isTurn: " + isTurn + " Color: " +color + " Move number: " +
                board.getMoveNum()), BorderLayout.NORTH);
        //update chess game
        this.add(new JScrollPane(gameJpanel), BorderLayout.CENTER);

        System.out.println("current board:");
        System.out.println(board);
        //revalidate gui
        revalidate();
    }
    
    
    private void resetHighlights() {
        for (Component comp : gameJpanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setBackground(new JButton().getBackground()); // Reset to default color
                // for (ActionListener al : button.getActionListeners()) {
                //     // button.removeActionListener(al); // Remove all action listeners
                // }
            }
        }
    }

    private void highlightMoves(LinkedList<String> moves, Piece selectedPiece, int pieceX, int pieceY) {
        resetHighlights(); // Reset all highlights before setting new ones
        updateGame2();
        for (String move : moves) {
            System.out.println("Move: " + move);
            int x = Integer.parseInt(move.split(";")[0]);
            int y = Integer.parseInt(move.split(";")[1]);
            JButton moveButton = (JButton) buttonMap.get(move);
            moveButton.setBackground(Color.YELLOW);
            
            moveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    confirmMove(move, selectedPiece, pieceX, pieceY);
                }
            });
        }
    }

    private void confirmMove(String move, Piece selectedPiece, int pieceX, int pieceY) {
        int result = JOptionPane.showConfirmDialog(null, "Move to " + move + "?", "Confirm Move", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            String[] parts = move.split(";");
            int newX = Integer.parseInt(parts[0]);
            int newY = Integer.parseInt(parts[1]);

            if (board.updateBoard(selectedPiece, newX + ";" + newY)) {
                sendMoveToServer(newX, newY, pieceX, pieceY);
                this.isTurn = false;
                updateGame();
            }
        }
    }

    private void sendMoveToServer(int newX, int newY, int pieceX, int pieceY) {
        Message move = new Message.Builder("MOVE").setMove(newX + ";" + newY, pieceX, pieceY).build();
        send.println(move.serialize());
    }
}
