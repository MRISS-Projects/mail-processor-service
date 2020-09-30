package com.mriss.products.mailprocessorservice.clockin.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

@SpringBootTest
public class MailInfoExtractorTest {

    @Autowired
    MailInfoExtractor extractor;

    private MimeMessage message;

    private MockHttpServletRequest request;

    @BeforeEach
    public void setUp() throws MessagingException, FileNotFoundException {
        request = new MockHttpServletRequest();
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        message = new MimeMessage(session, request.getInputStream());
        message.addFrom(new Address[] {new InternetAddress("test@test.com")});
        request.setAttribute(MailInfoExtractor.MIME_MESSAGE_ATTRIBUTE, message);
    }

    @Test
    public void testGetFrom() throws MessagingException, FileNotFoundException {
        extractor.setRequest(request);
        Set<String> from = extractor.getFrom();
        assertNotNull(from);
        assertEquals("test@test.com", from.toArray()[0]);
    }

    @Test
    public void testGetFromException() throws MessagingException, FileNotFoundException {
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        MimeMessage mockMessage = Mockito.mock(MimeMessage.class);
        Mockito.when(mockRequest.getAttribute(Mockito.anyString())).thenReturn(mockMessage);
        Mockito.when(mockMessage.getFrom()).thenThrow(MessagingException.class);
        extractor.setRequest(mockRequest);
        Set<String> from = extractor.getFrom();
        assertNull(from);
    }

    @Test
    public void testGetFromNull() throws MessagingException, FileNotFoundException {
        Set<String> from = extractor.getFrom();
        assertNull(from);
    }

    @Test
    public void testGetContent() throws MessagingException, IOException {
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

    @Test
    public void testGetContentException() throws MessagingException, IOException {
        MimeMessage messageSpy = Mockito.mock(MimeMessage.class);
        HttpServletRequest requestSpy = Mockito.mock(HttpServletRequest.class);
        Mockito.when(requestSpy.getAttribute(MailInfoExtractor.MIME_MESSAGE_ATTRIBUTE)).thenReturn(messageSpy);
        extractor.setRequest(requestSpy);
        Mockito.when(messageSpy.getContent()).thenThrow(MessagingException.class);
        assertNull(extractor.getContent());
    }

    @Test
    public void testGetContentTypeException() throws MessagingException, IOException {
        MimeMessage messageSpy = Mockito.spy(message);
        HttpServletRequest requestSpy = Mockito.spy(request);
        Mockito.when(requestSpy.getAttribute(MailInfoExtractor.MIME_MESSAGE_ATTRIBUTE)).thenReturn(messageSpy);
        extractor.setRequest(requestSpy);
        Multipart multipart = Mockito.mock(Multipart.class);
        BodyPart bodyPart = Mockito.mock(BodyPart.class);
        Mockito.when(messageSpy.getContent()).thenReturn(multipart);
        Mockito.when(multipart.getCount()).thenReturn(1);
        Mockito.when(multipart.getBodyPart(Mockito.anyInt())).thenReturn(bodyPart);
        Mockito.when(bodyPart.getContentType()).thenThrow(MessagingException.class);
        assertNull(extractor.getContent());
    }

    @Test
    public void testSetRequestException() throws MessagingException, IOException {
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.doThrow(IOException.class).when(mockRequest).getInputStream();
        assertFalse(extractor.setRequest(mockRequest));
    }

    @Test
    public void testSetRequestNoMessage() throws MessagingException, IOException {
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        ServletInputStream mockedInputStream = Mockito.mock(ServletInputStream.class);
        Mockito.when(mockRequest.getAttribute(Mockito.anyString())).thenReturn(null);
        Mockito.when(mockRequest.getInputStream()).thenReturn(mockedInputStream);
        assertTrue(extractor.setRequest(mockRequest));
    }

}
