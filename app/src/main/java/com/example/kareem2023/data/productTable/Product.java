package com.example.kareem2023.data.productTable;

import androidx.room.PrimaryKey;

public class Product
{
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي يُنتجح بشكل تلقائي
    public  long key_id;
    public String Barcode;
    public String P

}
