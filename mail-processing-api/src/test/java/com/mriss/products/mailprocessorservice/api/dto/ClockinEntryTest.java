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
