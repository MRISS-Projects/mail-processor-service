package com.mriss.products.mailprocessorservice.api.dto;

import java.util.ArrayList;
import java.util.List;

public class ClockinEntry {

    private String date;
    private List<SingleRecord> records = new ArrayList<SingleRecord>();

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
