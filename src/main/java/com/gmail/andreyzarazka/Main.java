package com.gmail.andreyzarazka;

import com.gmail.andreyzarazka.model.MarketDataIncrementalRefresh;
import com.gmail.andreyzarazka.model.MarketDataMessage;
import com.gmail.andreyzarazka.model.Report;
import quickfix.*;
import quickfix.field.*;
import com.gmail.andreyzarazka.utils.ReportBuilder;
import com.gmail.andreyzarazka.utils.TypeFields;
import com.gmail.andreyzarazka.utils.UpdateAction;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Main {
    private static final String FILTER_FIX_MESSAGE = "8[=].*\u0001";
    private static final String FILTER_SYMBOL = "55=EUR/USD";
    private static final String FILTER_BOOK_DEPTH = "279=2";
    private static final String FILTER_TYPE_MSG = "35=X";

    public static void main(String[] args) throws ConfigError, InvalidMessage, URISyntaxException, IOException, FieldNotFound {
        Path path = Paths.get(Objects.requireNonNull(Main.class.getClassLoader()
                .getResource("1001061-77MARKETS-PRICE_03082232215361.R0.summary"))
                .toURI());

        Pattern pattern = Pattern.compile(FILTER_FIX_MESSAGE);

        LocalDateTime startTime = LocalDateTime.of(2015, 03, 8, 00, 00, 0);
        LocalDateTime endTime = LocalDateTime.of(2015, 03, 8, 23, 00, 0);

        List<String> msgFIX = reader(path, pattern);
        List<Message> messages = сonverter(msgFIX, startTime, endTime);
        Report report = buildMDMsg(path, "EUR/USD", 2, startTime, endTime, messages);

        ReportBuilder test = new ReportBuilder();
        Files.write(Paths.get("D://report.html"), test.htmlReport(report).getBytes());
    }

    private static Report buildMDMsg(Path path, String symbol, int bookDepth, LocalDateTime startTime, LocalDateTime endTime, List<Message> messages) throws FieldNotFound {
        Report report = new Report();
        report.setLogFile(path.toString());
        report.setSymbolName(symbol);
        report.setBookDepth(bookDepth);
        report.setStartTime(startTime);
        report.setEndTime(endTime);

        List<MarketDataIncrementalRefresh> result = new ArrayList<>();

        MDUpdateAction mdUpdateAction = new MDUpdateAction();
        MDEntryType mdEntryType = new MDEntryType();
        MDEntryID mdEntryID = new MDEntryID();
        MDEntryPx mdEntryPx = new MDEntryPx();
        MDEntrySize mdEntrySize = new MDEntrySize();

        for (Message message : messages) {
            LocalDateTime startTime1 = message.getHeader().getUtcTimeStamp(TypeFields.SENDING_TIME.getValue());
            LocalDateTime endTime1 = message.getHeader().getUtcTimeStamp(TypeFields.SENDING_TIME.getValue());

            MarketDataIncrementalRefresh refresh = new MarketDataIncrementalRefresh();
            refresh.setSendingTime(message.getHeader().getString(TypeFields.SENDING_TIME.getValue()));
            refresh.setReceivingTime(message.getHeader().getString(TypeFields.SENDING_TIME.getValue()));
            refresh.setDifference(Duration.between(startTime1, endTime1).toMillis());

            List<Group> groups = message.getGroups(TypeFields.NO_MD_ENTRIES.getValue());
            List<MarketDataMessage> dataMessages = new ArrayList<>(groups.size());

            for (Group group : groups) {
                MarketDataMessage mdm = new MarketDataMessage();
                if (group.getField(mdUpdateAction).getValue() == UpdateAction.NEW.getValue()) {
                    mdm.setOrderID(group.getField(mdEntryID).getValue());
                    mdm.setAction(MarketDataMessage.getActionType(group.getField(mdUpdateAction).getValue()));
                    mdm.setSide(MarketDataMessage.getSideType(group.getField(mdEntryType).getValue()));
                    mdm.setPrice((int) (group.getField(mdEntryPx).getValue() * 100000));
                    mdm.setSize((int) group.getField(mdEntrySize).getValue());
                } else if (group.getField(mdUpdateAction).getValue() == UpdateAction.DELETE.getValue()) {
                    mdm.setAction(MarketDataMessage.getActionType(group.getField(mdUpdateAction).getValue()));
                    mdm.setOrderID(group.getField(mdEntryID).getValue());
                }
                dataMessages.add(mdm);
            }
            refresh.setMessages(dataMessages);
            result.add(refresh);
        }
        report.setMarketDataIncrementalRefreshes(result);

        return report;
    }

    private static List<String> reader(Path path, Pattern pattern) throws IOException {
        return Files.newBufferedReader(path).lines()
                .filter(s -> s.contains(FILTER_TYPE_MSG))
                .filter(s -> s.contains(FILTER_BOOK_DEPTH))
                .filter(s -> s.contains(FILTER_SYMBOL))
                .map(s -> {
                    Matcher matcher = pattern.matcher(s);
                    matcher.find();
                    return matcher.group();
                })

                .collect(Collectors.toList());
    }

    private static List<Message> сonverter(List<String> messages, LocalDateTime startTime, LocalDateTime endTime) throws ConfigError, FieldNotFound, InvalidMessage {
        DefaultMessageFactory factory = new DefaultMessageFactory();
        InputStream stream = Main.class.getResourceAsStream("FIX44.xml");
        DataDictionary dataDictionary = new DataDictionary(stream);

        List<Message> result = new ArrayList<>();

        for (String msg : messages) {
            Message message = MessageUtils.parse(factory, dataDictionary, msg);
            if (startTime.isBefore(message.getHeader().getUtcTimeStamp(TypeFields.SENDING_TIME.getValue())) && endTime.isAfter(message.getHeader().getUtcTimeStamp(TypeFields.SENDING_TIME.getValue()))) {
                result.add(message);
            }
        }
        return result;
    }
}