package com.mriss.products.mailprocessorservice.clockin.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class MailInfoExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailInfoExtractor.class);

    private HttpServletRequest request;
    private MimeMessage message;

    private InputStream content;

    public boolean setRequest(HttpServletRequest request) {
        this.request = request;
        try {
            getMessageFromRequest();
            return true;
        } catch (ServletException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    public Set<String> getFrom() {
        if (message != null) {
            try {
                Set<String> result = new HashSet<String>();
                Arrays.stream(message.getFrom()).forEach(address -> {
                    result.add(address.toString());
                });
                return result;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return null;
            }
        }
        return null;
    }

    public InputStream getContent() {
        try {
            if (message != null && content == null) {
                Multipart multipart = (Multipart) message.getContent();
                LOGGER.info("Multipart count: " + multipart.getCount());
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    if (bodyPart != null && isValid(bodyPart)) {
                        return bodyPart.getInputStream();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return content;
    }

    private boolean isValid(BodyPart bodyPart) {
        // TODO Auto-generated method stub
        return false;
    }

    protected void getMessageFromRequest() throws ServletException {
        message = (MimeMessage) request.getAttribute("mimeMessage");
        if (message == null) {
            try {
                Properties props = new Properties();
                Session session = Session.getDefaultInstance(props, null);
                message = new MimeMessage(session, request.getInputStream());
                request.setAttribute("mimeMessage", message);
            } catch (MessagingException e) {
                throw new ServletException("Error processing inbound message", e);
            } catch (IOException e) {
                throw new ServletException("Error processing inbound message", e);
            }
        }
    }

}
