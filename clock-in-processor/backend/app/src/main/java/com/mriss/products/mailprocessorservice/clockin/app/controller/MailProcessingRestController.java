package com.mriss.products.mailprocessorservice.clockin.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailProcessingRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailProcessingRestController.class);

    @GetMapping("/test")
    public void testGetMethod(HttpServletRequest request) {
        LOGGER.info("Test Get method.");
    }

    @PostMapping("/test")
    public void testPostMethod(HttpServletRequest request) {
        LOGGER.info("Test Post method.");
    }

    @GetMapping("/_ah/warmup")
    public void warmUp(HttpServletRequest request) {
        LOGGER.info("Warming up...");
    }
}
