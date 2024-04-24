import java.util.LinkedList;

/**
 * Board
 */
public class Board {

    // The move number of the game
    private Integer moveNum;
    // The piece of the game that have been taken
    private LinkedList<Piece> takenPieces;

    /**
     * Method that returns a piece if there is a piece in that given position, null otherwise
     * 
     * @param x value of matrix
     * @param y value of matrix
     * @return a piece if there is a piece in that given position, null otherwise
     */
    public Piece getPosition(Integer x, Integer y) {
        
        if (x == 1 && y == 3) {
            Piece piece = new Pawn("black", "1;3", this);
            return piece;
        }

        if (x == 2 && y == 3) {
            Piece piece = new Pawn("white", "2;2", this);
            return piece;
        }
        
        return null;
    }

    // THIS IS GOING TO HAVE TO CHANGE DEPENDING ON HOW WE WANT THE USER TO MOVE THE PIECE
    /**
     * When a piece is moved the board should update
     * 
     * @param piece that is being moved
     * @param newPosition that the piece is going to
     */
    public void updateBoard(Piece piece, String newPosition) {

    }

    /**
     * Builds the board with all the pieces in the correct locations
     */
    public void buildBoard() {


    }

    /**
     * Gets the current number of moves in the game
     * 
     * @return current move of game
     */
    public Integer getMoveNum() {
        return this.moveNum;
    }

    /**
     * Adds one to the move number
     */
    public void updateMoveNum() {
        this.moveNum++;
    }

}
