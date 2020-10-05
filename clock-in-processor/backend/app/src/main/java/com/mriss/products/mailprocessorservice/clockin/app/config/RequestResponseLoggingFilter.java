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
import com.mriss.products.mailprocessorservice.clockin.app.service.MailInfoExtractorFactory;
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
    MailInfoExtractorFactory mailInfoExtractorFactory;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOGGER.info("Entering filter...");
        HttpServletRequest req = (HttpServletRequest) request;
        MailInfoExtractor mailInfoExtractor = mailInfoExtractorFactory.getExtractor();
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
