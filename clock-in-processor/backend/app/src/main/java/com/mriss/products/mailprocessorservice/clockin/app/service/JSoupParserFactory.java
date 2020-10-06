package com.mriss.products.mailprocessorservice.clockin.app.service;

import java.util.List;

import org.jsoup.nodes.Element;

import com.mriss.products.mailprocessorservice.api.MailContentParser;
import com.mriss.products.mailprocessorservice.clockin.parser.JsoupClockInParser;

public class JSoupParserFactory implements ClockinMailParserFactory {

    @Override
    public MailContentParser<List<Element>> getNewMailParser() {
        return new JsoupClockInParser();
    }

}
