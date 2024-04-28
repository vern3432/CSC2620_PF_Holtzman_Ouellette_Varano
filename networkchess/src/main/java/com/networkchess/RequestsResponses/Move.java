package com.networkchess.RequestsResponses;

import java.io.InvalidObjectException;

import merrimackutil.json.JSONSerializable;
import merrimackutil.json.types.JSONType;
import merrimackutil.json.JSONSerializable;
import merrimackutil.json.types.JSONObject;
import merrimackutil.json.types.JSONType;


public class Move implements JSONSerializable {
    String pieceType; 
    String prevX; 
    String prevY; 
    String newX; 
    String newY;

    @Override
    public void deserialize(JSONType json) throws InvalidObjectException {
        JSONObject jsonObj = (JSONObject) json;
        this.pieceType = jsonObj.getString("pieceType");
        this.prevX = jsonObj.getString("prevX");
        this.prevY = jsonObj.getString("prevY");
        this.newX = jsonObj.getString("newX");
        this.newY = jsonObj.getString("newY");
    }
    
    @Override
    public String serialize() {
        return toJSONType().toString();
    }

    @Override
    public JSONType toJSONType() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("pieceType", pieceType);
        jsonObj.put("prevX", prevX);
        jsonObj.put("prevY", prevY);
        jsonObj.put("newX", newX);
        jsonObj.put("newY", newY);
        return jsonObj;
    }
}
