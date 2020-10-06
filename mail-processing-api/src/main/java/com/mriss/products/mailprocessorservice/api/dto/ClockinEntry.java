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

import java.util.ArrayList;
import java.util.List;

public class ClockinEntry {

    private String date;
    private List<SingleRecord> records = new ArrayList<SingleRecord>();

    public ClockinEntry() {
        super();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<SingleRecord> getRecords() {
        return records;
    }

    public void setRecords(List<SingleRecord> records) {
        this.records = records;
    }

    public void addRecord(SingleRecord record) {
        this.records.add(record);
    }

    @Override
    public String toString() {
        return "ClockinEntry [date=" + date + ", records=" + records + "]";
    }

}
