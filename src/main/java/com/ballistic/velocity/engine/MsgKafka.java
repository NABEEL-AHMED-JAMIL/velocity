package com.ballistic.velocity.engine;

import com.google.gson.Gson;

public class MsgKafka<F> {

    private String id;
    private String timestamp;
    private F field;

    public MsgKafka() { }

    public MsgKafka(String id, String timestamp, F field) {
        this.id = id;
        this.timestamp = timestamp;
        this.field = field;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public F getField() { return field; }
    public void setField(F field) { this.field = field; }

    @Override
    public String toString() { return new Gson().toJson(this); }

}