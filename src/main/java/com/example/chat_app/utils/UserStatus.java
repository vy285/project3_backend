package com.example.chat_app.utils;

public enum UserStatus {
    OFFLINE("OFFLINE"),

    ONLINE("ONLINE");

    private final String text;

    UserStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
