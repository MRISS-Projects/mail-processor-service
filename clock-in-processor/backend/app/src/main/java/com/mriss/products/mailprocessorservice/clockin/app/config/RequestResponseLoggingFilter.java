package com.mriss.products.mailprocessorservice.clockin.app.config;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mriss.products.mailprocessorservice.clockin.app.service.MailInfoExtractor;
import com.mriss.products.mailprocessorservice.clockin.app.service.Validator;

/**
 * Filter.
 * 
 * @author riss
 *
 */
@WebFilter("/_ah/mail/*")
public class RequestResponseLoggingFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Autowired
    Validator validator;

    @Autowired
    MailInfoExtractor mailInfoExtractor;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOGGER.info("Entering filter...");
        HttpServletRequest req = (HttpServletRequest) request;
        if (mailInfoExtractor.setRequest(req)) {
            LOGGER.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
            if (validator.validate(req.getRequestURI(), mailInfoExtractor.getFrom())) {
                if (processEmail(mailInfoExtractor.getContent())) {
                    return;
                } else {
                    LOGGER.error("Invalid message processing!!!");
                }
            } else {
                LOGGER.warn("Invalid message!!!");
            }
        }
        chain.doFilter(request, response);
    }

    private boolean processEmail(InputStream content) {
        if (content == null) {
            return false;
        }
        LOGGER.info("Success processing message!!!");
        return true;
    }

}
