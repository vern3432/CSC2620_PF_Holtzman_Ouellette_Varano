package com.networkchess.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class VictoryPopup extends JFrame {

    private Clip clip;

    public VictoryPopup() {
        initializeUI();
        playSound();
    }

    private void initializeUI() {
        setTitle("Victory Popup");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Adding GIF
        ImageIcon icon = new ImageIcon(getClass().getResource("/lose.gif"));
        JLabel label = new JLabel(icon);
        add(label, BorderLayout.CENTER);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener((ActionEvent event) -> {
            clip.stop();
            clip.close();
            dispose();
        });
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void playSound() {
        try {
            // Load the sound file
            URL url = getClass().getResource("/Victory.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Loop the clip
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VictoryPopup::new);
    }
}
