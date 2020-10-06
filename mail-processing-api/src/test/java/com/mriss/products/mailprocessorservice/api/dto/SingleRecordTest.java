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
