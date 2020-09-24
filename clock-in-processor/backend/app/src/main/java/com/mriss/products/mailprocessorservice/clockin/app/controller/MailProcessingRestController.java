package com.mriss.products.mailprocessorservice.clockin.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mriss.products.mailprocessorservice.clockin.app.config.RequestResponseLoggingFilter;

@RestController
public class MailProcessingRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @GetMapping("/_ah/mail")
    public void getMethod(HttpServletRequest request) {
        LOGGER.info("Get method.");
    }

    @PostMapping("/_ah/mail")
    public void postMethod(HttpServletRequest request) {
        LOGGER.info("Posts method.");
    }

    @GetMapping("/test")
    public void testGetMethod(HttpServletRequest request) {
        LOGGER.info("Test Get method.");
    }

}
