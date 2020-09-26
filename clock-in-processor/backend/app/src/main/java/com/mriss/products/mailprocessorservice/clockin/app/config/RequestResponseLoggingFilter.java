package com.mriss.products.mailprocessorservice.clockin.app.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
        assert mailInfoExtractor.setRequest(req);
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
        chain.doFilter(request, response);
    }

    private boolean processEmail(InputStream content) {
        if (content == null) return false;
        return true;
    }

}
