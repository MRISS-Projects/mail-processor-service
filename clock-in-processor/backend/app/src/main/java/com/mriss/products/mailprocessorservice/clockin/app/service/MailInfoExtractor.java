/*******************************************************************************
 * Copyright 2020 Marcelo Riss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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

public class MailInfoExtractor {

    public static final String MIME_MESSAGE_ATTRIBUTE = "mimeMessage";

    private static final String VALID_CONTENT_TYPE = "text/html";

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
                        content = bodyPart.getInputStream();
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
        try {
            LOGGER.info("bodyPart: " + bodyPart.getContentType());
            return bodyPart.getContentType().indexOf(VALID_CONTENT_TYPE) != -1;
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    protected void getMessageFromRequest() throws ServletException {
        message = (MimeMessage) request.getAttribute(MIME_MESSAGE_ATTRIBUTE);
        if (message == null) {
            try {
                Properties props = new Properties();
                Session session = Session.getDefaultInstance(props, null);
                message = new MimeMessage(session, request.getInputStream());
                request.setAttribute(MIME_MESSAGE_ATTRIBUTE, message);
            } catch (MessagingException e) {
                throw new ServletException("Error processing inbound message", e);
            } catch (IOException e) {
                throw new ServletException("Error processing inbound message", e);
            }
        }
    }

    public String getSender() {
        if (message != null) {
            try {
                if (message.getSender() != null) {
                    return message.getSender().toString();
                } else {
                    Set<String> from = getFrom();
                    if (from != null && !from.isEmpty()) {
                        return from.iterator().next();
                    } else {
                        return null;
                    }
                } 
            } catch (MessagingException e) {
                LOGGER.error(e.getMessage(), e);
                return null;
            }
        }
        return null;
    }

}
