package com.mriss.products.mailprocessorservice.clockin.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

@SpringBootTest
class MailInfoExtractorTest {

    @Autowired
    MailInfoExtractor extractor;

    private MimeMessage message;

    private MockHttpServletRequest request;

    public void setup() throws MessagingException, FileNotFoundException {
        request = new MockHttpServletRequest();
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        message = new MimeMessage(session, request.getInputStream());
        message.addFrom(new Address[] {new InternetAddress("test@test.com")});
        request.setAttribute(MailInfoExtractor.MIME_MESSAGE_ATTRIBUTE, message);
    }

    @Test
    void testGetFrom() throws MessagingException, FileNotFoundException {
        setup();
        extractor.setRequest(request);
        Set<String> from = extractor.getFrom();
        assertNotNull(from);
        assertEquals("test@test.com", from.toArray()[0]);
    }

    @Test
    void testGetContent() throws MessagingException, IOException {
        setup();
        MimeMessage messageSpy = Mockito.spy(message);
        HttpServletRequest requestSpy = Mockito.spy(request);
        Mockito.when(requestSpy.getAttribute(MailInfoExtractor.MIME_MESSAGE_ATTRIBUTE)).thenReturn(messageSpy);
        extractor.setRequest(requestSpy);
        Multipart multipart = Mockito.mock(Multipart.class);
        BodyPart bodyPart = Mockito.mock(BodyPart.class);
        InputStream is = Mockito.mock(InputStream.class);
        Mockito.when(messageSpy.getContent()).thenReturn(multipart);
        Mockito.when(multipart.getCount()).thenReturn(1);
        Mockito.when(multipart.getBodyPart(Mockito.anyInt())).thenReturn(bodyPart);
        Mockito.when(bodyPart.getContentType()).thenReturn("text/html");
        Mockito.when(bodyPart.getInputStream()).thenReturn(is);
        assertNotNull(extractor.getContent());
    }

}
