package com.networkchess.GUI;

import com.networkchess.GameLogic.Board;

import javax.swing.*;

public class Model extends JPanel implements Runnable {
    private Board board = new Board();
    JTextArea boardLabel = new JTextArea();
    public Model(String name) {
        this.add(boardLabel);

        boardLabel.setText(board.toString());
        System.out.println(board);
    }

    @Override
    public void run() {

    }
}
