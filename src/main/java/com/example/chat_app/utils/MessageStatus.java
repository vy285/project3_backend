package com.example.chat_app.utils;

public enum MessageStatus {
    WAITING("WAITING"), // tin nhan bi loi chua the gui

    DELETED("DELETED"), // thu hoi tin nhan

    NORMAL("NORMAL"), // tin nhan binh thuong

    CHANGED("CHANGED"); // tin nhan da sua

    private final String text;

    MessageStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
