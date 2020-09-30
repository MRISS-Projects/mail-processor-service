package com.mriss.products.mailprocessorservice.clockin.app.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class MailProcessingRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTestGetMethod() throws Exception {
        this.mockMvc.perform(get("/test")).andExpect(status().isOk());
    }

    @Test
    public void testTestPostMethod() throws Exception {
        this.mockMvc.perform(post("/test")).andExpect(status().isOk());
    }

    @Test
    public void testWarmUp() throws Exception {
        this.mockMvc.perform(get("/_ah/warmup")).andExpect(status().isOk());
    }

}
