package com.cdp.ecodoctapp.entity;

public class Message {
    private String message;
    private boolean isOK;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public Message(String message, boolean isOK) {
        this.message = message;
        this.isOK = isOK;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean OK) {
        isOK = OK;
    }
}
