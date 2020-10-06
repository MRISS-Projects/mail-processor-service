package com.mriss.products.mailprocessorservice.api.dto;

public class SingleRecord {

    private String clockIn;
    private String clockOut;

    public SingleRecord() {
        super();
    }

    public SingleRecord(String clockIn, String clockOut) {
        super();
        this.clockIn = clockIn;
        this.clockOut = clockOut;
    }

    public String getClockIn() {
        return clockIn;
    }

    public void setClockIn(String clockIn) {
        this.clockIn = clockIn;
    }

    public String getClockOut() {
        return clockOut;
    }

    public void setClockOut(String clockOut) {
        this.clockOut = clockOut;
    }

    @Override
    public String toString() {
        return "SingleRecord [clockIn=" + clockIn + ", clockOut=" + clockOut + "]";
    }

}
