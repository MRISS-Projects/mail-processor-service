package com.mriss.products.mailprocessorservice.clockin.app.service;

import com.mriss.products.mailprocessorservice.api.MailContentParser;
import com.mriss.products.mailprocessorservice.api.dto.ClockinRecordDto;

public interface ClockinMailParserFactory<T extends MailContentParser<?>> {

    public T getNewMailParser();

    public ClockinRecordDto getRecordsFromContent(T parser);

}
