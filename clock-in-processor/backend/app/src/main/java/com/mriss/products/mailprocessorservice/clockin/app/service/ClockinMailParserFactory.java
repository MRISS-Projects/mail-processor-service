package com.mriss.products.mailprocessorservice.clockin.app.service;

import com.mriss.products.mailprocessorservice.api.MailContentParser;

public interface ClockinMailParserFactory {

    public MailContentParser<?> getNewMailParser();

}
