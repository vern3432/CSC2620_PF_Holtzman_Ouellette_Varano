package com.networkchess.GUI;

import com.networkchess.GameLogic.Board;
import com.networkchess.GameLogic.pieces.Piece;
import com.networkchess.Net.Message;
import merrimackutil.json.types.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

import static merrimackutil.json.JsonIO.readObject;

/**
 * Model is our internal representation of the chess game which deals with updating the board and communicating with the server
 * Model should run on a separate thread so it can wait for server updates
 */
public class Model extends JPanel implements Runnable {
    /**
     * Board object which has all of our peices
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

    private String color = "";
    private boolean isTurn = false;

    private PrintWriter send;
    private boolean endGame = false;


    /**
     * Creates model for internal representation of chess game
     * @param _name - name given to game
     */
    public Model(String _name, String _serverAddr, int _serverPort) {
        name = _name;
        serverAddr = _serverAddr;
        serverPort = _serverPort;

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
        //create new game Jpanel
        JPanel gameJpanel = new JPanel(new GridBagLayout());
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

                //get piece at current position
                Piece currPiece = board.getPosition(column,row);
                JButton pieceButton;

                //if piece is not null add a button to represent it
                if (currPiece != null) {
                    pieceButton = new JButton(currPiece.getColor() + " " + currPiece.toString());
                    int pieceX = column;
                    int pieceY = row;
                    pieceButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!isTurn) {
                                JOptionPane.showMessageDialog(null, "Cannot move it is not our turn");
                                return;
                            }

                            //get user input
                            String input = JOptionPane.showInputDialog("Please enter position to move piece, current pos is: " + currPiece.getCurrPosition()
                            + "\n Can move: " + currPiece.possibleMoves()
                            );

                            //could be null if so return
                            if (input == null) {
                                return;
                            }

                            //move piece
                            board.updateBoard(currPiece,input);

                            //update board
                            updateGame();

                            //send move to server
                            Message move = new Message.Builder("MOVE").setMove(input,pieceX,pieceY).build();
                            send.println(move.serialize());

                            //update turn
                            isTurn = false;
                        }
                    });
                }
                //if null add placeholder button
                else {
                    pieceButton = new JButton("             ");
                    pieceButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null,"Feature not added cant move nothing");
                        }
                    });
                }

                //add piece button in position
                gameJpanel.add(pieceButton,gridBagConstraints);

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
            //create Jlabel with letter
            JLabel columnLabel = new JLabel(String.valueOf(column));
            //add JLabel
            gameJpanel.add(columnLabel, gridBagConstraints);
            //increase x
            gridBagConstraints.gridx += 1;
        }

        //remove everything in gui
        this.removeAll();

        //update top bar
        this.add(new JLabel("Game: " + name + " isTurn: " + isTurn + "Color: " +color + " Move number: " +
                board.getMoveNum()), BorderLayout.NORTH);
        //update chess game
        this.add(new JScrollPane(gameJpanel), BorderLayout.CENTER);

        System.out.println("current board:");
        System.out.println(board);
        //revalidate gui
        revalidate();
    }
}
