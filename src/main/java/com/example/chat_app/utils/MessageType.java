package com.example.chat_app.utils;

public enum MessageType {
    TEXT("TEXT"),

    IMAGE("IMAGE");

    private final String text;

    MessageType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
