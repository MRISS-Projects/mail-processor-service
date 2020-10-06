package com.mriss.products.mailprocessorservice.api.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;

public class ClockinRecordDtoTest {

    @Test
    public void testClockinRecordDto() {
        ClockinRecordDto dto = new ClockinRecordDto();
        assertNotNull(dto);
        dto.setUser("test");
        assertEquals("test", dto.getUser());
        ArrayList<ClockinEntry> entries = new ArrayList<ClockinEntry>();
        dto.setEtries(entries);
        assertEquals(entries, dto.getEtries());
        ClockinEntry entry = new ClockinEntry();
        dto.addEntry(entry);
        assertEquals(1, dto.getEtries().size());
        System.out.println(dto.toString());
    }

}
