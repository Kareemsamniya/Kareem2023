package com.example.kareem2023.data.Alergy;

import androidx.room.PrimaryKey;

import java.util.ArrayList;

public class MyAlergy {
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي يُنتجح بشكل تلقائي
    public long key_id;
    public ArrayList<String> AlergyName;

    public ArrayList<String> getAlergyName() {
        return AlergyName;
    }

    public void setAlergyName(ArrayList<String> alergyName) {
        AlergyName = alergyName;
    }
}