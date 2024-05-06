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
public class StartUp extends JFrame implements ActionListener {
    private JButton newGameButton;

    public StartUp() {
        // Set the title of the JFrame
        super("StartUp - Network Chess");

        // Load the banner image from resources
        URL imageUrl = getClass().getResource("/v1.jpg");
        ImageIcon imageIcon = new ImageIcon(imageUrl);
        Image image = imageIcon.getImage(); // Transform it into an Image first
        Image newimg = image.getScaledInstance(500, 500,  Image.SCALE_SMOOTH); // Scale it smoothly to 500x500
        imageIcon = new ImageIcon(newimg);  // Transform it back to an ImageIcon
        JLabel imageLabel = new JLabel(imageIcon);

        // Initialize the button and add ActionListener
        newGameButton = new JButton("Start New Game");
        newGameButton.addActionListener(this);

        // Layout configuration
        this.setLayout(new BorderLayout());

        // Add the image and button to the JFrame
        this.add(imageLabel, BorderLayout.NORTH);
        this.add(newGameButton, BorderLayout.CENTER);
        this.setSize(1400, 1000); // Adjusted size to better fit the resized image and button
        // Basic JFrame setup
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 700); // Adjusted size to better fit the resized image and button
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            // Open a new ViewController window
            // try {
            //     UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            // } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
            //         | UnsupportedLookAndFeelException e1) {
            //     // TODO Auto-generated catch block
            //     e1.printStackTrace();
            // }

            ViewController vc = new ViewController();
            vc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the VC window on close
            vc.setSize(800, 800); // Size of the game window
            vc.setVisible(true);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {            // try {
            //     UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            // } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
            //         | UnsupportedLookAndFeelException e1) {
            //     // TODO Auto-generated catch block
            //     e1.printStackTrace();
            // }
        // Set look and feel to be the system default for aesthetics
        // Create and show the StartUp GUI
        UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
        new StartUp();
    }
}
