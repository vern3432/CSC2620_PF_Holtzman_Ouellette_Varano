package com.networkchess.GUI;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
public class Player2Main {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");

        ViewController gui = new ViewController();
        // What to do when the window closes:
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Size of the window, in pixels
        gui.setSize(800,800);
        // Make the window "visible"
        gui.setVisible(true);

    }
}
