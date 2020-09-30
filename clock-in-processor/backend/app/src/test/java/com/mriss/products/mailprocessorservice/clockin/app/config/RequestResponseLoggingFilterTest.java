package com.mriss.products.mailprocessorservice.clockin.app.config;

import java.io.InputStream;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mriss.products.mailprocessorservice.clockin.app.service.MailInfoExtractor;
import com.mriss.products.mailprocessorservice.clockin.app.service.Validator;

public class RequestResponseLoggingFilterTest {

    @Mock
    Validator validator;

    @Mock
    MailInfoExtractor mailInfoExtractor;

    @InjectMocks
    RequestResponseLoggingFilter filter = new RequestResponseLoggingFilter();

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testDoFilter() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        ServletResponse response = Mockito.mock(ServletResponse.class);
        FilterChain chain = Mockito.mock(FilterChain.class);
        Set<String> mockSet = Mockito.mock(Set.class);
        InputStream mockStream = Mockito.mock(InputStream.class);
        Mockito.when(mailInfoExtractor.setRequest(request)).thenReturn(true);
        Mockito.when(request.getRequestURI()).thenReturn("test");
        Mockito.when(mailInfoExtractor.getFrom()).thenReturn(mockSet);
        Mockito.when(validator.validate("test", mockSet)).thenReturn(true);
        Mockito.when(mailInfoExtractor.getContent()).thenReturn(mockStream);
        filter.doFilter(request, response, chain);
    }

}
