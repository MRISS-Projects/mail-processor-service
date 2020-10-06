package com.mriss.products.mailprocessorservice.clockin.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mriss.products.mailprocessorservice.api.dto.ClockinRecordDto;
import com.mriss.products.mailprocessorservice.clockin.parser.JsoupClockInParser;

@SpringBootTest
class JSoupParserFactoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailInfoExtractor.class);

    @Autowired
    JSoupParserFactory factory;

    @Test
    void testGetRecordsFromContent() throws FileNotFoundException {
        JsoupClockInParser parser = factory.getNewMailParser();
        parser.parse(new FileInputStream(new File("target/test-classes/test.txt")));
        ClockinRecordDto dto = factory.getRecordsFromContent(parser);
        assertNotNull(dto);
        assertEquals(3, dto.getEtries().size());
        LOGGER.info("dto: " + dto);
    }

}
