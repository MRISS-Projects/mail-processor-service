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
package com.mriss.products.mailprocessorservice.clockin.service.api;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.RequestContextFilter;

@SpringBootTest
@AutoConfigureMockMvc
class HelloworldControllerTest {

    Logger LOGGER = LoggerFactory.getLogger(HelloworldControllerTest.class);

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Value("${app.version}")
    private String appVersion;

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).dispatchOptions(true)
                .addFilter(new RequestContextFilter(), "/*").alwaysDo(result -> {
                    result.getResponse().setContentType("application/json;charset=UTF-8");
                }).build();
    }

    @Test
    void testHelloPost() throws Exception {
        LOGGER.info("**** Test without authentication header.");
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/backend/service/hello").contentType(contentType)
                        .accept(contentType))
                .andExpect(status().is(HttpStatus.OK.value())).andExpect(content().contentType(contentType))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().indexOf(appVersion) != -1);
        LOGGER.info("**** Result: " + result.getResponse().getContentAsString());
    }

    @Test
    void testHelloGet() throws Exception {
        LOGGER.info("**** Test without authentication header.");
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/backend/service/hello").contentType(contentType)
                        .accept(contentType))
                .andExpect(status().is(HttpStatus.OK.value())).andExpect(content().contentType(contentType))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().indexOf(appVersion) != -1);
        LOGGER.info("**** Result: " + result.getResponse().getContentAsString());
    }

}
