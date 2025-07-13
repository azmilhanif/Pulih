package com.nai.pulih;

public class Message {
    public static final int USER = 0;
    public static final int BOT = 1;

    private final String text;
    private final int sender;

    public Message(String text, int sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public int getSender() {
        return sender;
    }
}