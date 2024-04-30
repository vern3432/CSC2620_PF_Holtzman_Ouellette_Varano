package com.networkchess.GUI;

import com.networkchess.GameLogic.Board;

import javax.swing.*;

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
     * Creates model for internal representation of chess game
     * @param name - name given to game
     */
    public Model(String name) {
        //adds board object toString as a placeholder
        this.add(boardLabel);

        boardLabel.setText(board.toString());
        System.out.println(board);
    }

    @Override
    public void run() {
        //TO:DO implement run method and threads
    }
}
