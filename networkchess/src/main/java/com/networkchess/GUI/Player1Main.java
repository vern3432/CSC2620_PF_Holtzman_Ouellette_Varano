package com.networkchess.GUI;

import javax.swing.*;

public class Player1Main {
    public static void main(String[] args) {
        ViewController gui = new ViewController();
        // What to do when the window closes:
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Size of the window, in pixels
        gui.setSize(800,800);
        // Make the window "visible"
        gui.setVisible(true);
    }
}
