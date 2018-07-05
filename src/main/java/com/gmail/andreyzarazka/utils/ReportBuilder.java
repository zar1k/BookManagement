package com.gmail.andreyzarazka.utils;

import com.gmail.andreyzarazka.model.Report;

import static j2html.TagCreator.*;

public class ReportBuilder {
    public String htmlReport(Report report) {
        return document().render() +
                html().withLang("en").with(
                        head().with(
                                // Required meta tags
                                meta().withCharset("utf-8"),
                                meta().withName("viewport").withContent("width=device-width, initial-scale=1, shrink-to-fit=no"),
                                // Bootstrap CSS
                                link().withRel("stylesheet").withHref("https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"),
                                title().withText("Report")
                        ),
                        body().with(
                                // Report header - start.
                                table().withClass("table table-bordered table-condensed").with(
                                        tr().with(
                                                td().withText("Log file"),
                                                td().withText(report.getLogFile())
                                        ),
                                        tr().with(
                                                td().withText("Symbol Name"),
                                                td().withText(report.getSymbolName())
                                        ),
                                        tr().with(
                                                td().withText("Book depth"),
                                                td().withText(String.valueOf(report.getBookDepth()))
                                        ),
                                        tr().with(
                                                td().withText("Start Time"),
                                                td().withText(String.valueOf(report.getStartTime()))
                                        ),
                                        tr().with(
                                                td().withText("End Time"),
                                                td().withText(String.valueOf(report.getEndTime()))
                                        )

                                ),
                                // Report header - end.
                                table().withClass("table table-bordered table-condensed").with(
                                        // Book management template - start
                                        each(report.getMarketDataIncrementalRefreshes(), message ->
                                                tr().attr("valign", "top").attr("width", "100%").with(
                                                        td().attr("width", "100%").with(
                                                                table().withClass("table table-bordered table-condensed").attr("width", "100%").with(
                                                                        tr().with(
                                                                                td().withClass("table table-bordered table-condensed").attr("width", "100%").with(
                                                                                        table().attr("width", "100%").with(
                                                                                                tr().with(
                                                                                                        td().attr("width", "50%").with(
                                                                                                                h1().withText(String.valueOf(report.getMarketDataIncrementalRefreshes().indexOf(message) + 1))
                                                                                                        ),
                                                                                                        td().attr("width", "50%").with(
                                                                                                                table().withClass("table table-bordered table-condensed").with(
                                                                                                                        tr().with(
                                                                                                                                td().withText("Sending Time"),
                                                                                                                                td().withText(message.getSendingTime())
                                                                                                                        ),
                                                                                                                        tr().with(
                                                                                                                                td().withText("Receiving Time"),
                                                                                                                                td().withText(message.getReceivingTime())
                                                                                                                        ),
                                                                                                                        tr().with(
                                                                                                                                td().withText("Difference(ms)"),
                                                                                                                                td().withText(String.valueOf(message.getDifference()))
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        ),
                                                                        tr().with(
                                                                                td().with(
                                                                                        table().withClass("table table-bordered table-condensed").attr("width", "100%").with(
                                                                                                tr().attr("valign", "top").with(
                                                                                                        td().attr("width", "33%").with(
                                                                                                                table().attr("width", "100%").attr("border", "1").with(
                                                                                                                        thead().with(
                                                                                                                                tr().with(
                                                                                                                                        th().attr("width", "30%").withText("ID"),
                                                                                                                                        th().attr("width", "10%").withText("Action"),
                                                                                                                                        th().attr("width", "10%").withText("Side"),
                                                                                                                                        th().attr("width", "25%").withText("Price"),
                                                                                                                                        th().attr("width", "25%").withText("Size")
                                                                                                                                )
                                                                                                                        ),
                                                                                                                        tbody().with(
                                                                                                                                each(message.getMessages(), marketDataMessage ->
                                                                                                                                        tr().with(
                                                                                                                                                td().attr("align", "center").withText(marketDataMessage.getOrderID()),
                                                                                                                                                td().withText(marketDataMessage.getAction()),
                                                                                                                                                td().attr("align", "center").withText(iffElse(marketDataMessage.getSide() != null, marketDataMessage.getSide(), "")),
                                                                                                                                                td().attr("align", "right").withText(iffElse(marketDataMessage.getPrice() != 0, String.valueOf(marketDataMessage.getPrice()), "")),
                                                                                                                                                td().attr("align", "right").withText(iffElse(marketDataMessage.getSize() != 0, String.valueOf(marketDataMessage.getSize()), ""))
                                                                                                                                        ))
                                                                                                                        )
                                                                                                                )
                                                                                                        ),
                                                                                                        td().attr("width", "33%").with(
                                                                                                                table().attr("width", "100%").attr("border", "1").with(
                                                                                                                        tr().with(
                                                                                                                                th().attr("width", "50%").attr("colspan", "2").withText("BID"),
                                                                                                                                th().attr("width", "50%").attr("colspan", "2").withText("ASK")
                                                                                                                        ),
                                                                                                                        tr().with(
                                                                                                                                th().attr("width", "25%").withText("Price"),
                                                                                                                                th().attr("width", "25%").withText("Size"),
                                                                                                                                th().attr("width", "25%").withText("Price"),
                                                                                                                                th().attr("width", "25%").withText("Size")
                                                                                                                        ),
                                                                                                                        tr().with(
                                                                                                                                td().attr("align", "right").withText(""),
                                                                                                                                td().attr("align", "right").withText(""),
                                                                                                                                td().attr("align", "right").withText("116114"),
                                                                                                                                td().attr("align", "right").withText("3500000")
                                                                                                                        ),
                                                                                                                        tr().with(
                                                                                                                                td().attr("align", "right").withText("116111"),
                                                                                                                                td().attr("align", "right").withText("500000"),
                                                                                                                                td().attr("align", "right").withText(""),
                                                                                                                                td().attr("align", "right").withText("")
                                                                                                                        )
                                                                                                                )
                                                                                                        ),
                                                                                                        td().attr("width", "33%").with(
                                                                                                                table().attr("width", "100%").attr("border", "1").with(
                                                                                                                        tr().with(
                                                                                                                                th().attr("width", "20%").withText("Action"),
                                                                                                                                th().attr("width", "20%").withText("Side"),
                                                                                                                                th().attr("width", "20%").withText("Price"),
                                                                                                                                th().attr("width", "20%").withText("Size")
                                                                                                                        ),
                                                                                                                        tr().with(
                                                                                                                                td().withText("New"),
                                                                                                                                td().withText("Bid"),
                                                                                                                                td().attr("align", "right").withText("116111"),
                                                                                                                                td().attr("align", "right").withText("500000")
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                ))
                                        // Book management template - start
                                ),
                                // Optional JavaScript
                                // jQuery first, then Popper.js, then Bootstrap JS
                                script().withSrc("https://code.jquery.com/jquery-3.3.1.slim.min.js"),
                                script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"),
                                script().withSrc("https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js")
                        )
                ).render();
    }
}