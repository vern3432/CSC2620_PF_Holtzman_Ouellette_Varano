package com.networkchess.ServerLogic;

import com.networkchess.RequestsResponses.AbstractMessage;
import com.networkchess.RequestsResponses.MoveRequest;
import com.networkchess.RequestsResponses.MoveResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import merrimackutil.json.JsonIO;

public class ChessServer {
    private int port;
    private ServerSocket serverSocket;
    private ExecutorService pool;

    public ChessServer(int port) {
        this.port = port;
        pool = Executors.newFixedThreadPool(2); // Assuming a two-player game
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Chess Server started on port " + port);

            while (true) {
                Socket playerOne = serverSocket.accept();
                Socket playerTwo = serverSocket.accept();
                System.out.println("Two players connected.");

                // Handle game session for two players
                pool.execute(new GameSession(playerOne, playerTwo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class GameSession implements Runnable {
        private Socket playerOne;
        private Socket playerTwo;

        public GameSession(Socket playerOne, Socket playerTwo) {
            this.playerOne = playerOne;
            this.playerTwo = playerTwo;
        }

        @Override
        public void run() {
            try {
                // Setup input and output streams for both players
                DataInputStream inputOne = new DataInputStream(playerOne.getInputStream());
                DataOutputStream outputOne = new DataOutputStream(playerOne.getOutputStream());

                DataInputStream inputTwo = new DataInputStream(playerTwo.getInputStream());
                DataOutputStream outputTwo = new DataOutputStream(playerTwo.getOutputStream());

                boolean gameOn = true;
                while (gameOn) {
                    // Read move from player one
                    String moveOne = inputOne.readUTF();
                    MoveRequest moveRequestOne = new MoveRequest(); // Assuming MoveRequest can parse the move
                    moveRequestOne.deserialize(JsonIO.readObject(new String(moveOne)));
                    // Process move and generate response
                    MoveResponse responseOne = processMove(moveRequestOne);

                    // Send move to player two
                    outputTwo.writeUTF(responseOne.serialize());

                    // Read move from player two
                    String moveTwo = inputTwo.readUTF();
                    MoveRequest moveRequestTwo = new MoveRequest(); // Assuming MoveRequest can parse the move
                    moveRequestTwo.deserialize(JsonIO.readObject(new String(moveTwo)));

                    // Process move and generate response
                    MoveResponse responseTwo = processMove(moveRequestTwo);

                    // Send move to player one
                    outputOne.writeUTF(responseTwo.serialize());
                }

                playerOne.close();
                playerTwo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private MoveResponse processMove(MoveRequest move) {
            // Logic to process the move
            return new MoveResponse(true,"Piece Moved Successfully"); // Placeholder for actual response generation
        }
    }

    public static void main(String[] args) {
        ChessServer server = new ChessServer(5000);
        server.startServer();
    }
}
