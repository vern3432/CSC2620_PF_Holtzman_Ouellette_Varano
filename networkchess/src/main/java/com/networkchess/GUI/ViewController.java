package com.networkchess.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ViewController is responsible for the view and Controlling the actions of our GUI
 */
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
    /**
     * Current game being viewed
     * This may be useful in the future such as with "surrender" button
     */
    private Model currGame;
    /**
     * Each model runs on its own thread
     * We may want to add a config for the number of threads we use
     */
    private int threads;
    /**
     * String of the IP address of the central server
     * We may want to add a config for IP address of the central server
     */
    private String serverAddr;
    /**
     * int of the port the central server is listening on
     * We may want to add a config for port of the central server
     */
    private int serverPort;
    /**
     * Thread pool we will use to execute tasks on, put threads into a pool and uses next available one
     */
    private ExecutorService pools;
    /**
     * Constructor creates ViewController as JFrame
     */
    public ViewController() {
        super("SAS Chess"); //Sean Aidan Seth

        //use border layout
        this.setLayout(new BorderLayout());

        //set the center panel to a card layout and add it to the center
        chessPanel.setLayout(centerCard);
        this.add(chessPanel, BorderLayout.CENTER);

        //create main center menu and create add game button
        StartUp startup = new StartUp();
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Welcome enter the name of this game");
                if (input != null && !games.containsKey(input)) {
                    addGame(input);
                } else if (input != null) {
                    JOptionPane.showMessageDialog(null, "Game name is already taken please try again");
                }
            }
        });
        startup.setNewGameButton(newGameButton);

        //add menu to chessPanel
        chessPanel.add("_menu",startup);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Build the first menu.
        JMenu menu = new JMenu("Game Option");
        menuBar.add(menu);

        // Menu item for returning to the main menu
        JMenuItem menuItemMenu = new JMenuItem("Return to Menu");
        menuItemMenu.addActionListener(e -> centerCard.show(chessPanel, "_menu"));
        menu.add(menuItemMenu);

        // Menu item for creating a new game
        JMenuItem menuItemNewGame = new JMenuItem("New Game");
        menuItemNewGame.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Welcome enter the name of this game");
            if (input != null && !games.containsKey(input)) {
                addGame(input);
            } else if (input != null) {
                JOptionPane.showMessageDialog(null, "Game name is already taken please try again");
            }
        });
        menu.add(menuItemNewGame);

        // Test game menu item for quick testing
        JMenuItem menuItemTestGame = new JMenuItem("Test Game");
        menuItemTestGame.addActionListener(e -> addGame("Test game"));
        menu.add(menuItemTestGame);

        // Add a new menu for the current section
        JMenu currentSectionMenu = new JMenu("Current Session");
        menuBar.add(currentSectionMenu);

        JMenuItem giveUpItem = new JMenuItem("Give Up");
        giveUpItem.addActionListener(e -> giveUpGame());
        currentSectionMenu.add(giveUpItem);

        //add menu item for removing game
        JMenuItem removeGameMenu = new JMenuItem("Remove Game");
        removeGameMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Welcome enter the name of this game");
                if (input == null) {
                    return;
                }

                if (!games.containsKey(input)) {
                    JOptionPane.showMessageDialog(null, "Game name does not exist");
                }

                removeGame(input);
            }
        });
        currentSectionMenu.add(removeGameMenu);

        // Set the menu bar
        setJMenuBar(menuBar);

        // Layout and other initializations follow...
        // (rest of the constructor)

        //we should move this to config file later
        threads = 3;
        serverAddr = "127.0.0.1";
        serverPort = 5001;

        //create Thread pool we will use
        pools = Executors.newFixedThreadPool(threads);

        //add game buttons to east(this may be changed just easy)
        this.add(gameButtons,BorderLayout.EAST);
    }

    /**
     * Adds a new game with a button to view that game and a new Model to represent that game
     * @param name Name given to game
     */
    private void addGame(String name) {
        //add game to list of games
        Model newGameModel = new Model(name, serverAddr, serverPort, this);
        pools.execute(newGameModel);

        games.put(name, newGameModel);

        //add game to game panel in center
        chessPanel.add(name, games.get(name));

        //button to view game
        JButton newGameButton = new JButton(name);
        //when pressed show game
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
        //when we create a game we most likely want to go directly to it
        currGame = games.get(name);
        centerCard.show(chessPanel,name);

        revalidate();
    }

    /**
     * Removes a game from our gui
     * @param name String of name
     */
    public void removeGame(String name) {
        //surrender game
        currGame = games.get(name);
        currGame.handleSurrender();
        //remove game from hashmap
        games.remove(name);

        //remove all game buttons
        gameButtons.removeAll();

        //add each game from our hashmap back to GUI
        for (String game : games.keySet()) {
            System.out.println("adding game " + game);
            //button to view game
            JButton newGameButton = new JButton(game);
            //when pressed show game
            newGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //show that current game in the center
                    centerCard.show(chessPanel,game);
                    //set currGame to that game
                    currGame = games.get(game);
                }
            });

            //add one row to button layout
            gameButtonsLayout.setRows(gameButtonsLayout.getRows()+1);
            //add new button to side bar
            gameButtons.add(newGameButton);

            centerCard.show(chessPanel,game);
            currGame = games.get(game);
        }

        if (games.isEmpty()) {
            System.out.println("no games");
            centerCard.show(chessPanel,"_menu");
        }

        System.out.println(games);
        revalidate();
    }


    /**
     * Gives up the current game(model) being viewed
     */
    private void giveUpGame() {
        if (currGame == null) {
            JOptionPane.showMessageDialog(this,"No game to end");
            return;
        }

        currGame.handleSurrender();
        removeGame(currGame.getName());

    }
}
