package com.networkchess.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.net.URL;
public class StartUp extends JPanel {
    private JButton newGameButton;

    public StartUp() {
        // Load the banner image from resources
        URL imageUrl = getClass().getResource("/v1.jpg");
        ImageIcon imageIcon = new ImageIcon(imageUrl);
        Image image = imageIcon.getImage(); // Transform it into an Image first
        Image newimg = image.getScaledInstance(500, 500,  Image.SCALE_SMOOTH); // Scale it smoothly to 500x500
        imageIcon = new ImageIcon(newimg);  // Transform it back to an ImageIcon
        JLabel imageLabel = new JLabel(imageIcon);

        // Initialize the button and add ActionListener
        newGameButton = new JButton("Start New Game");

        // Layout configuration
        this.setLayout(new BorderLayout());

        // Add the image and button to the JFrame
        this.add(imageLabel, BorderLayout.NORTH);
        this.add(newGameButton, BorderLayout.CENTER);
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

    }

    public void setNewGameButton(JButton button) {
        newGameButton = button;
        this.add(newGameButton, BorderLayout.CENTER);
    }
}
