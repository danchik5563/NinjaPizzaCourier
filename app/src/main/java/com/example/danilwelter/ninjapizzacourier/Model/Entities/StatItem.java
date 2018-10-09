package com.example.danilwelter.ninjapizzacourier.Model.Entities;

import java.util.Date;

public class StatItem {

    private String date;
    private int count;

    //region getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    //endregion

    public StatItem(String date, int count) {
        this.date = date;
        this.count = count;
    }
}
