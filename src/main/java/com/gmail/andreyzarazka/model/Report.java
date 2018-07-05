package com.gmail.andreyzarazka.model;

import java.time.LocalDateTime;
import java.util.List;

public class Report {
    private String logFile;
    private String symbolName;
    private int bookDepth;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long counter;
    private List<MarketDataIncrementalRefresh> marketDataIncrementalRefreshes;

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public int getBookDepth() {
        return bookDepth;
    }

    public void setBookDepth(int bookDepth) {
        this.bookDepth = bookDepth;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public List<MarketDataIncrementalRefresh> getMarketDataIncrementalRefreshes() {
        return marketDataIncrementalRefreshes;
    }

    public void setMarketDataIncrementalRefreshes(List<MarketDataIncrementalRefresh> marketDataIncrementalRefreshes) {
        this.marketDataIncrementalRefreshes = marketDataIncrementalRefreshes;
    }
}
