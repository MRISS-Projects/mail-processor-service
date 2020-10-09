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
        dto.setUserEmail("test@test.com");
        assertEquals("test@test.com", dto.getUserEmail());
        ArrayList<ClockinEntry> entries = new ArrayList<ClockinEntry>();
        dto.setEntries(entries);
        assertEquals(entries, dto.getEntries());
        ClockinEntry entry = new ClockinEntry();
        dto.addEntry(entry);
        assertEquals(1, dto.getEntries().size());
        System.out.println(dto.toString());
    }

}
