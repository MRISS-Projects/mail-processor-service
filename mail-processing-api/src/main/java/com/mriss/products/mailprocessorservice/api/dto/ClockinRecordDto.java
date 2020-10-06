package com.mriss.products.mailprocessorservice.api.dto;

import java.util.ArrayList;
import java.util.List;

public class ClockinRecordDto {

    private String user;
    private List<ClockinEntry> entries = new ArrayList<ClockinEntry>();

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
