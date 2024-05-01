package com.networkchess.GUI;

import com.networkchess.GameLogic.Board;
import com.networkchess.GameLogic.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    JTextArea boardLabel = new JTextArea();

    /**
     * String of name of current game
     */
    private String name;

    /**
     * Creates model for internal representation of chess game
     * @param _name - name given to game
     */
    public Model(String _name) {
        name = _name;

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
        //TO:DO implement run method and threads
        //handle server code and possibly timer
    }

    /**
     * This method will update the game window to whatever the current boards view of the game is
     */
    private void updateGame() {
        //create new game Jpanel
        JPanel gameJpanel = new JPanel(new GridBagLayout());

        //set grid bag constants to place buttons
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);

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
                    pieceButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
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
        this.add(new JLabel("Game: " + name +  "Move number " + board.getMoveNum()), BorderLayout.NORTH);
        //update chess game
        this.add(new JScrollPane(gameJpanel), BorderLayout.CENTER);

        System.out.println("current board:");
        System.out.println(board);
        //revalidate gui
        revalidate();
    }
}
