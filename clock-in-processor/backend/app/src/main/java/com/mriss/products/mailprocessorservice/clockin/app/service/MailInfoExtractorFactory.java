package com.mriss.products.mailprocessorservice.clockin.app.service;

import org.springframework.stereotype.Service;

@Service
public class MailInfoExtractorFactory {

    public MailInfoExtractor getExtractor() {
        return new MailInfoExtractor();
    }

}
