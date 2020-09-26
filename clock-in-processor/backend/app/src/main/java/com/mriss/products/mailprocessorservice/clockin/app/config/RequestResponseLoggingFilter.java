package com.mriss.products.mailprocessorservice.clockin.app.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
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
import org.springframework.beans.factory.annotation.Value;

/**
 * Filter.
 * 
 * @author riss
 *
 */
@WebFilter("/_ah/mail/*")
public class RequestResponseLoggingFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Value("${ALLOWED_ACCOUNT}")
    private String allowedAccount;

    @Value("${ALLOWED_SENDER_LIST}")
    private String allowedSenderList;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOGGER.info("Entering filter...");
        LOGGER.info("allowedAccount: " + allowedAccount);
        LOGGER.info("allowedSenderList: " + allowedSenderList);
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        LOGGER.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
        chain.doFilter(request, response);
        LOGGER.info("Logging Response :{}", res.getContentType());
        MimeMessage msg = getMessageFromRequest(req);
        try {
            LOGGER.info("Message subject: " + msg.getSubject());
            LOGGER.info("Message subject: " + msg.getContent());
            if (msg.getContent() instanceof Multipart) {
                Multipart multipart = (Multipart) msg.getContent();
                LOGGER.info("Multipart count: " + multipart.getCount());
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    if (bodyPart != null) {
                        try (InputStream is = bodyPart.getInputStream(); StringWriter sw = new StringWriter()) {
                            IOUtils.copy(is, sw, StandardCharsets.UTF_8);
                            StringBuffer buffer = sw.getBuffer();
                            LOGGER.info("Content: " + buffer.toString());
                        }
                    }
                }
            }
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    protected MimeMessage getMessageFromRequest(ServletRequest req) throws ServletException {
        MimeMessage message = (MimeMessage) req.getAttribute("mimeMessage");
        if (message == null) {
            try {
                Properties props = new Properties();
                Session session = Session.getDefaultInstance(props, null);
                message = new MimeMessage(session, req.getInputStream());
                req.setAttribute("mimeMessage", message);
            } catch (MessagingException e) {
                throw new ServletException("Error processing inbound message", e);
            } catch (IOException e) {
                throw new ServletException("Error processing inbound message", e);
            }
        }
        return message;
    }

}
