package com.networkchess.Net;

import merrimackutil.json.JSONSerializable;
import merrimackutil.json.types.JSONObject;
import merrimackutil.json.types.JSONType;

import java.io.InvalidObjectException;

/**
 * Message is a JSON representation of the messages we send from the chess server to our client games
 */
public class Message implements JSONSerializable {
    /**
     * String of type of message
     */
    private String type;
    /**
     * String of color assignment to player either: white or black
     */
    private String color;
    /**
     * String of move player has made
     */
    private String move;
    /**
     * Boolean true if game is running false if game has ended
     */
    private boolean isRunning;
    /**
     * Reason game has started or stopped
     * Mainly used to describe to user reason game has stopped
     */
    private String reason;

    private int pieceX;
    private int pieceY;

    /**
     * Creates Message object from builder
     * @param builder Builder that creates message object
     */
    private Message(Builder builder) {
        type = builder.type;
        color = builder.color;
        move = builder.move;
        pieceX = builder.pieceX;
        pieceY = builder.pieceY;
        isRunning = builder.isRunning;
        reason = builder.reason;
    }

    public Message(JSONObject messageJSON) throws InvalidObjectException {
        deserialize(messageJSON);
    }

    /**
     * Serializes the JSON object into JSON string representation
     *
     * @return JSON String
     */
    @Override
    public String serialize() {
        return toJSONType().toJSON();
    }

    /**
     * Deserializes the JSONObject into internal representation of that message
     *
     * @param jsonType JSONObject that we want to deserialize
     * @throws InvalidObjectException Throws if JSON does not contain expected keys or if it does not have JSONObject
     */
    @Override
    public void deserialize(JSONType jsonType) throws InvalidObjectException {
        if (!(jsonType instanceof JSONObject))
            throw new InvalidObjectException("JSONObject expected.");

        JSONObject messageJSON = (JSONObject) jsonType;

        if (!messageJSON.containsKey("type")) {
            throw new InvalidObjectException("Model.Message json does not have TYPE field");
        }

        //get type of message
        type = messageJSON.getString("type");

        //deserialize JSONObject based on type of Message JSON
        switch(type) {
            case "HELLO":
                //HELLO only has type so do nothing
                break;
            case "WELCOME":
                color = messageJSON.getString("color");

                break;
            case "GAME":
                isRunning = messageJSON.getBoolean("isRunning");
                reason = messageJSON.getString("reason");

                break;
            case "MOVE":
                move = messageJSON.getString("move");
                pieceX = messageJSON.getInt("pieceX");
                pieceY = messageJSON.getInt("pieceY");

                break;
            default:
                throw new IllegalArgumentException("Bad type - Must be HELLO, WELCOME, GAME, MOVE");
        }

    }

    /**
     * Creates JSONObject based on type of Message
     * @return JSONObject of message
     */
    @Override
    public JSONType toJSONType() {
        JSONObject messageJSON = new JSONObject();

        //switch between types and put needed fields into JSONObject
        switch(type) {
            case "HELLO":
                messageJSON.put("type",type);

                return messageJSON;
            case "WELCOME":
                messageJSON.put("type",type);
                messageJSON.put("color",color);

                return messageJSON;
            case "GAME":
                messageJSON.put("type",type);
                messageJSON.put("isRunning", isRunning);
                messageJSON.put("reason", reason);

                return messageJSON;
            case "MOVE":
                messageJSON.put("type",type);
                messageJSON.put("move",move);
                messageJSON.put("pieceX",pieceX);
                messageJSON.put("pieceY",pieceY);

                return messageJSON;
            default:
                throw new IllegalArgumentException("Bad type - Must be HELLO, WELCOME, GAME, MOVE");
        }
    }

    public String getType() {
        return type;
    }

    public String getMove() {
        return move;
    }

    public String getColor() {
        return color;
    }

    public int getPieceX() {
        return pieceX;
    }

    public int getPieceY() {
        return pieceY;
    }

    public String getReason() {
        return reason;
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Gets formatted JSON
     * @return String of Formatted JSON
     */
    @Override
    public String toString() {
       return toJSONType().getFormattedJSON();
    }

    /**
     * Builder class used to create different types of messages
     */
    public static class Builder {
        private String type;
        private String color;
        private String move;
        private boolean isRunning;
        private String reason;
        private int pieceX;
        private int pieceY;

        /**
         * Creates basic message object
         * @param _type String of type of message: HELLO, WELCOME, GAME, MOVE
         */
        public Builder(String _type) {
            type = _type;
        }

        /**
         * Creates WELCOME message
         * @param _color Assignment of color to the player
         * @return This builder
         */
        public Builder setWelcome(String _color) {
            color = _color;
            return this;
        }

        /**
         * Creates MOVE message
         * @param _move Move that the other player has made
         * @return This Builder
         */
        public Builder setMove(String _move, int _pieceX, int _pieceY) {
            move = _move;
            pieceX = _pieceX;
            pieceY = _pieceY;

            return this;
        }

        /**
         * Creates GAME message
         * @param _isRunning Boolean true if game is running(such as starting game) and false if game is ended
         * @param _reason Reason for game start(both players join) or game end(player leaves or quit)
         * @return This Builder
         */
        public Builder setGame(Boolean _isRunning, String _reason) {
            isRunning = _isRunning;
            reason = _reason;

            return this;
        }

        /**
         * Builds Message from builder fields
         * @return Message object
         */
        public Message build() {
            return new Message(this);
        }
    }
}
