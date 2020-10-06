package com.mriss.products.mailprocessorservice.api.dto;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ClockinEntryTest {

    @Test
    public void testClockinEntry() {
        ClockinEntry ce = new ClockinEntry();
        assertNotNull(ce);
        ce.setDate("06/10/2020");
        assertEquals("06/10/2020", ce.getDate());
        ArrayList<SingleRecord> records = new ArrayList<SingleRecord>();
        ce.setRecords(records);
        assertEquals(records, ce.getRecords());
        SingleRecord record = new SingleRecord("16:55", "17:55");
        ce.addRecord(record);
        assertEquals(1, ce.getRecords().size());
        assertEquals(record, ce.getRecords().get(0));
        System.out.println(ce.toString());
    }

}
