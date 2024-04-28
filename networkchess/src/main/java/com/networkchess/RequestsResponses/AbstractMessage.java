package com.networkchess.RequestsResponses;

import merrimackutil.json.JSONSerializable;
import merrimackutil.json.types.JSONType;
import merrimackutil.json.types.JSONObject;
import java.io.InvalidObjectException;

public abstract class AbstractMessage implements JSONSerializable {
    @Override
    public abstract JSONType toJSONType();

    @Override
    public void deserialize(JSONType json) throws InvalidObjectException {
        // Common deserialization logic (if any)
    }

    @Override
    public String serialize() {
        return toJSONType().toJSON();
    }
}