package com.mriss.products.mailprocessorservice.clockin.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

public class ClockInParserTest {

    @Test
    public void testParser() throws FileNotFoundException {
        ClockInParser parser = new ClockInParser();
        parser.parse(new FileInputStream(new File("target/test-classes/test.txt")));
    }

}
