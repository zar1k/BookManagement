package com.gmail.andreyzarazka.model;

import java.util.ArrayList;
import java.util.List;

public class MarketDataIncrementalRefresh {
    private String sendingTime;
    private String receivingTime;
    private long difference;
    List<MarketDataMessage> messages = new ArrayList<>();

    public String getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(String sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getReceivingTime() {
        return receivingTime;
    }

    public void setReceivingTime(String receivingTime) {
        this.receivingTime = receivingTime;
    }

    public long getDifference() {
        return difference;
    }

    public void setDifference(long difference) {
        this.difference = difference;
    }

    public List<MarketDataMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<MarketDataMessage> messages) {
        this.messages = messages;
    }
}