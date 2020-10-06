package com.mriss.products.mailprocessorservice.clockin.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClockInParserTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClockInParserTest.class);

    @Test
    public void testParser() throws FileNotFoundException {
        ClockInParser parser = new ClockInParser();
        List<Element> parsingResult = parser.parse(new FileInputStream(new File("target/test-classes/test.txt")));
        assertNotNull(parsingResult);
        assertTrue(parsingResult.size() > 0);
        for (Iterator iterator = parsingResult.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            LOGGER.debug(""+element);
        }
    }

}
