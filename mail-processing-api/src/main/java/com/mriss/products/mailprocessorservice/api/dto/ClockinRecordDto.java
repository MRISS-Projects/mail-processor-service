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

public class ClockinRecordDto {

    private String user;
    private List<ClockinEntry> entries = new ArrayList<ClockinEntry>();

    public ClockinRecordDto() {
        super();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<ClockinEntry> getEtries() {
        return entries;
    }

    public void setEtries(List<ClockinEntry> etries) {
        this.entries = etries;
    }

    public void addEntry(ClockinEntry entry) {
        this.entries.add(entry);
    }

    @Override
    public String toString() {
        return "ClockinRecordDto [user=" + user + ", entries=" + entries + "]";
    }

}
