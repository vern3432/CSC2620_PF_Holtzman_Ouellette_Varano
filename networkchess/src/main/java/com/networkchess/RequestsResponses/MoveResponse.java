package com.networkchess.RequestsResponses;
import java.io.InvalidObjectException;

import merrimackutil.json.JSONSerializable;
import merrimackutil.json.types.JSONObject;
import merrimackutil.json.types.JSONType;


public class MoveResponse implements JSONSerializable {
    private boolean success;
    private String message;

    public MoveResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public MoveResponse() {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void deserialize(JSONType json) throws UnsupportedOperationException {
        JSONObject jsonObj = (JSONObject) json;
        this.success = jsonObj.getBoolean("success");
        this.message = jsonObj.getString("message");
    }

    @Override
    public String serialize() {
        return toJSONType().toString();
    }

    @Override
    public JSONType toJSONType() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("success", success);
        jsonObj.put("message", message);
        return jsonObj;
    }
}
