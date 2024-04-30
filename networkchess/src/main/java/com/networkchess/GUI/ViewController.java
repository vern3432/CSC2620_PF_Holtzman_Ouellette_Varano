package com.networkchess.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ViewController extends JFrame {
    /**
     * Jpanel in center that holds current chess game
     */
    private JPanel chessPanel = new JPanel();
    /**
     * CardLayout that allows us to show different panels in the center chessPanel
     */
    private CardLayout centerCard = new CardLayout(10,10);
    /**
     * Layout for each button in the game
     */
    private GridLayout gameButtonsLayout = new GridLayout(0,1,10,10);
    /**
     * Buttons for each game
     */
    private JPanel gameButtons = new JPanel(gameButtonsLayout);
    /**
     * HashMap with each of our games
     */
    private HashMap<String,Model> games = new HashMap<>();
    private Model currGame;

    public ViewController() {
        super("SAS Chess"); //Sean Aidan Seth

        this.setLayout(new BorderLayout());

        chessPanel.setLayout(centerCard);
        this.add(chessPanel, BorderLayout.CENTER);

        //doing this as a simple JButton layout for now I may change it for tabs

        //create menu Panel to add menu later
        JPanel menuPanel = new JPanel();
        menuPanel.add(new JLabel("Example menu"));

        //add it to our center panels
        chessPanel.add("_menu",menuPanel);

        //create menu button to go back to menu
        JButton menuButton = new JButton("menu");
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerCard.show(chessPanel,"_menu");

                revalidate();
            }
        });


        JButton newGame = new JButton("New game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get user input
                String input = JOptionPane.showInputDialog("Welcome enter the name of this game");

                //could be null if so return
                if (input == null) {
                    return;
                }

                //check if name exists
                if (games.containsKey(input)) {
                    JOptionPane.showMessageDialog(null,"Game name is already taken please try again");
                    return;
                }
                addGame(input);
            }
        });

        JPanel buttonHolder = new JPanel(new GridLayout(1,10,10,10));
        buttonHolder.add(menuButton);
        buttonHolder.add(newGame);

        this.add(buttonHolder,BorderLayout.NORTH);


        this.add(gameButtons,BorderLayout.EAST);
    }

    private void addGame(String name) {
        //add game to list of games
        games.put(name, new Model(name));

        //add game to game panel in center
        chessPanel.add(name, games.get(name));

        JButton newGameButton = new JButton(name);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //show that current game in the center
                centerCard.show(chessPanel,name);
                //set currGame to that game
                currGame = games.get(name);
            }
        });

        //add one row to button layout
        gameButtonsLayout.setRows(gameButtonsLayout.getRows()+1);
        //add new button to side bar
        gameButtons.add(newGameButton);

        //set and show the new game
        currGame = games.get(name);
        centerCard.show(chessPanel,name);

        revalidate();
    }

}
