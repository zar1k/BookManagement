package com.gmail.andreyzarazka.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

// FIX 4.4 : Market Data - Incremental Refresh <X> message
public class MarketDataMessage {
    private static final Map<Character, String> ACTION_TYPE = ImmutableMap.of(
            '0', "New",
            '1', "Update",
            '2', "Delete"
    );
    private static final Map<Character, String> SIDE_TYPE = ImmutableMap.of(
            '0', "Bid",
            '1', "Ask",
            '2', "Delete"
    );
    private String orderID;
    private String action;
    private String side;
    private int price;
    private int size;

    public static String getActionType(char key) {
        return ACTION_TYPE.get(key);
    }

    public static String getSideType(char key) {
        return SIDE_TYPE.get(key);
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
