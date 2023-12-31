package com.example.chat_app.utils;

public enum ReferralStatus {
    WAIT("WAIT"),
    YES("YES");

    private final String text;

    ReferralStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }


}
