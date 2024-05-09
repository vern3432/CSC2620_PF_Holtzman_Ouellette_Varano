# CSC2620_PF_Holtzman_Ouellette_Varano
Final Project  for Object Oriented Design

We created a Java application to play chess over a network using a TCP connection to a central server. The clients use a GUI
in order to play the game.

Our code is divided into three packages
- peiceLogic - Handles the logic of the board and pieces
- [Net](#net-package) - Handles the server and related communication
- GUI - Handles the GUI and client decisions 

# Quick Start Guide
## Creating Jar files and releases
Please see releases for precompiled Jar files

To create Jar files from sources
```shell
mvn clean package 
```
## Server
To start the server run the jar file

```Shell
java -jar driver.jar
```

## Client 
````Shell
java -jar startup.jar
````
# Net Package
The Net package is responsible for managing the Chess Server, which allows players to play a game with each other. It also holds the messages that our protocol uses to communicate. All protocol messages are JSON messages using the Merrimackutil library. 

## Messages
There are three Message types WELCOME, GAME and MOVE

### WELCOME - Message
The Welcome message is sent by the server to a player to welcome them to the game and tell them what color they are playing as.

There are two fields:

- type : String - "WELCOME"
- color : String - either "white" or "black"

#### Example messages
```JSON
{
"type" : "WELCOME",
"color" : "white"
}
```
```JSON
{
"type" : "WELCOME",
"color" : "black"
}
```

### GAME - Message
The game message is used to convey information about the game such as the game being started or ended

There are two fields
- type : String - "GAME"
- isRunning : Boolean - true if game is running false if game has stopped
- reason : String - Reason that the game has started or stopped

#### Example messages
```JSON
{
"type" : "GAME",
"isRunning" : true,
"reason" : "Both players have joined starting game good luck!"
}
```
```JSON
{
"type" : "GAME",
"isRunning" : false,
"reason" : "Server encountered an error, please try again later"
}
```
```JSON
{
   "type" : "GAME",
   "isRunning" : false,
   "reason" : "White has won the game"
}
```

### MOVE - Message
This message is sent to convey a move a player has made, the server will just forward this message to the other player to update their boards. It is the client's responsibility to make sure this move is possible and legal. 

There are four fields
- type : String - "MOVE"
- pieceX : int - X position of piece before this move
- pieceY : int - Y position of piece before this move
- move : String - The new position of this piece 

These fields are **directly** related to the boards API. From the board class we have 
```java
public Piece getPosition(Integer x, Integer y)

public void updateBoard(Piece piece, String newPosition)
```

The pieceX and pieceY keys are used to get the piece using the getPosition method from Board then that piece is given to the updateBoard method with the new position from the move key. 

### Chess Server operation
1. The Server waits for players as incoming connections, the first player to connect is sent a Welcome message assigning it as the white player and the second player to connect is sent a welcome message assigning it as the black player. Then the server will send the connection off to a new thread so that it can handle concurrent games.
2. We send both players a GAME message with isRunning set to true to let them know both players have joined and the game has started.
3. We enter a subroutine until the game has ended:
    1. Wait for Message from the white player, parse the message if it seems okay we forward it to the black player
    2. Wait for Message from the black player, parse the Message if it seems okay we forward it to the white player
    3. Repeat until game is done

### Client operations 
The Server connection is done in the run method so it can be handled independently and will not block the rest of our GUI while waiting for messages.

1. Connect to server and wait for Welcome message, parse the message if it seems okay set our color to our assigned color
2. Wait for message from Server, parse the message:
    1. If it is a move message, get the piece at pieceX and pieceY keys then update the board with that piece along with the the new position from move key. Then set isTurn flag to true.
    2. If it is a GAME message, if isRunning key is set to false set endGame flag to true, else set endGame to false. Then display the reason key as an option pane to the user.

*Premoving may provide a better experience for making moves while it is not our turn*
1. Check if isTurn flag is true, piece is our color and if move is possible. If all cases are true so proceed
2. Update our board with the move
3. Send the move to the server on the output Stream
4. Set isTurn to false
5. Update our view of the board

When user makes a move, this is done in the ActionListener for each piece button.

## Client Gui: 

### StartUp Menu:

![image1](https://github.com/vern3432/CSC2620_PF_Holtzman_Ouellette_Varano/assets/111017456/1bbd1ce7-c185-4027-8ec9-e664e44b098f)

#### Board:

![image2](https://github.com/vern3432/CSC2620_PF_Holtzman_Ouellette_Varano/assets/111017456/cb331cb1-265b-4e6c-8fa1-e7f626897ce2)
Usage:
1. Players may select a piece, represented by a gridlayout of buttons. 
2. One selected, available moves by piece are highlighted, and can then be selected.
3. A player can select one of these highlighted spaces, automatically sending the move following  confirmation. 


