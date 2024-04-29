package com.networkchess.RequestsResponses;

import merrimackutil.json.JSONSerializable;
import merrimackutil.json.types.JSONType;
import merrimackutil.json.types.JSONObject;
import java.io.InvalidObjectException;

public abstract class AbstractMessage implements JSONSerializable {
    String type; 
    String content; 

    @Override
    public void deserialize(JSONType json) throws InvalidObjectException {
        JSONObject jsonObj = (JSONObject) json;
        this.type = jsonObj.getString("type");
        this.content = jsonObj.getString("content");
    }

    @Override
    public String serialize() {
        return toJSONType().toString();
    }

    @Override
    public JSONType toJSONType() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("type", type);
        jsonObj.put("content", content);
        return jsonObj;
    }
}