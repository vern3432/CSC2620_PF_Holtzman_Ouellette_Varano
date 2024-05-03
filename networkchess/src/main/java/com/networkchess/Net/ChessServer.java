package com.networkchess.Net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is the server that handles the connection between the players
 */
public class ChessServer {
    /**
     * IP address the server is on
     */
    private String addr;
    /**
     * Port that this server is listening on
     */
    private int port;
    /**
     * Threads for server to use
     */
    private int threads;
    /**
     * ServerSocket object to accept connections
     */
    private ServerSocket server;

    /**
     * Creates a new ChessServer to run on a specified IP address and Port with a specified number of threads
     *
     * @param _addr String IP address of this server
     * @param _port Int port this server is listening on
     * @param _threads Int number of threads this server can use, number of concurrent games we can handle
     */
    public ChessServer(String _addr, int _port, int _threads) {
        addr = _addr;
        port = _port;
        threads = _threads;

        //create thread pool to handle connections
        ExecutorService pool = Executors.newFixedThreadPool(threads);

        try {
            //start server to listen for connections
            server = new ServerSocket(port);

            while(true) {
                //wait for players to join then start game
                System.out.println();
                System.out.print("Server waiting for connections...");

                //accept player and send WELCOME message, to let them know they are the white player
                Socket whiteSock = server.accept();

                //create message
                Message whiteWelcome = new Message.Builder("WELCOME").setWelcome("white").build();
                //send message over output stream
                PrintWriter whiteSend = new PrintWriter(whiteSock.getOutputStream());
                whiteSend.println(whiteWelcome.serialize());

                System.out.print("White Player connected: " + whiteSock.getRemoteSocketAddress() +"...");

                //accept player and send WELCOME message, to let them know they are the black player
                Socket blackSock = server.accept();

                //create message
                Message blackWelcome = new Message.Builder("WELCOME").setWelcome("white").build();
                //send message over output stream
                PrintWriter blackSend = new PrintWriter(blackSock.getOutputStream());
                blackSend.println(blackWelcome.serialize());

                System.out.print("Black Player connected: " + blackSock.getRemoteSocketAddress() +"...");

                //Create and handle game on a new thread
                pool.execute(new ConnectionHandler(whiteSock, blackSock));
                System.out.print("Game Started!");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This class handles the game connection between the two players
     */
    private class ConnectionHandler implements Runnable{
        /**
         * Socket of the white player
         */
        Socket whiteSock;
        /**
         * Socket of the black player
         */
        Socket blackSock;

        /**
         * Create new connection handler object with players
         * @param _whiteSock Socket of the white player
         * @param _blackSock Socket of the black player
         */
        public ConnectionHandler(Socket _whiteSock, Socket _blackSock) {
            whiteSock = _whiteSock;
            blackSock = _blackSock;
        }

        /**
         * Run method handles the connection a seperate thread and will handle the game
         */
        @Override
        public void run() {
            try {
                //get input and output streams for players
                Scanner whiteRecv = new Scanner(whiteSock.getInputStream());
                PrintWriter whiteSend = new PrintWriter(whiteSock.getOutputStream());

                Scanner blackRecv = new Scanner(blackSock.getInputStream());
                PrintWriter blackSend = new PrintWriter(blackSock.getOutputStream());

                //send message to both players letting them know the game can begin
                Message startGame = new Message.Builder("GAME").setGame(true,"Both players have joined").build();

                whiteSend.println(startGame.serialize());
                blackSend.println(startGame.serialize());

                //enter loop to deal with game
                while (true) {
                    //TO:DO handle game connection
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
