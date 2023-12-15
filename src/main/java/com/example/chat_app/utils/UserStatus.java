package com.example.chat_app.utils;

public enum UserStatus {
    OFFLINE("online"),

    ONLINE("offline");

    private final String text;

    UserStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
