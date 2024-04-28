package com.networkchess.RequestsResponses;

import java.io.InvalidObjectException;

import java.io.InvalidObjectException;

import merrimackutil.json.JSONSerializable;
import merrimackutil.json.types.JSONType;
import merrimackutil.json.JSONSerializable;
import merrimackutil.json.types.JSONObject;
import merrimackutil.json.types.JSONType;


public class Promotion implements JSONSerializable {
     int x; 
     int y;  
     String newType; 

    @Override
    public void deserialize(JSONType json) throws InvalidObjectException {
        JSONObject jsonObj = (JSONObject) json;
        this.x = jsonObj.getInt("x");
        this.y = jsonObj.getInt("y");
        this.newType = jsonObj.getString("newType");
    }

    @Override
    public String serialize() {
        return toJSONType().toString();
    }

    @Override
    public JSONType toJSONType() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("x", x);
        jsonObj.put("y", y);
        jsonObj.put("newType", newType);
        return jsonObj;
    }
    
}
