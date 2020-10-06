package com.mriss.products.mailprocessorservice.api.dto;

import static org.junit.Assert.*;

import org.junit.Test;

public class SingleRecordTest {

    @Test
    public void testSingleRecord() {
        SingleRecord sr = new SingleRecord();
        assertNotNull(sr);
        sr.setClockIn("16:55");
        assertEquals("16:55", sr.getClockIn());
        sr.setClockOut("17:55");
        assertEquals("17:55", sr.getClockOut());
    }

    @Test
    public void testSingleRecordStringString() {
        SingleRecord sr = new SingleRecord("16:55", "17:55");
        assertEquals("16:55", sr.getClockIn());
        assertEquals("17:55", sr.getClockOut());
        System.out.println(sr.toString());
    }

}
