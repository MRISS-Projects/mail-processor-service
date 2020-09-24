package com.mriss.products.mailprocessorservice.clockin.app.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter.
 * 
 * @author riss
 *
 */
@WebFilter("/_ah/mail/*")
public class RequestResponseLoggingFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOGGER.info("Entering filter...");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        LOGGER.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
        chain.doFilter(request, response);
        LOGGER.info("Logging Response :{}", res.getContentType());

    }
}
