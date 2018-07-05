package com.gmail.andreyzarazka.utils;

public enum UpdateAction {
    NEW('0', "New"), UPDATE('1', "Update"), DELETE('2', "Delete");

    private char value;
    private String description;

    UpdateAction(char value, String description) {
        this.value = value;
        this.description = description;
    }

    public char getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}