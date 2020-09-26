package com.mriss.products.mailprocessorservice.clockin.app.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Validator {

    @Value("${ALLOWED_ACCOUNT}")
    private String allowedAccount;

    @Value("${ALLOWED_SENDER_LIST}")
    private String allowedSenderList;

    public boolean validate(String requestURI, Set<String> set) {
        return false;
    }

}
