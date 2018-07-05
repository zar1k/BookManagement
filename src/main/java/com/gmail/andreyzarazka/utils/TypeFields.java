package com.gmail.andreyzarazka.utils;

public enum TypeFields {
    SENDING_TIME(52), NO_MD_ENTRIES(268);

    private int value;

    TypeFields(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}