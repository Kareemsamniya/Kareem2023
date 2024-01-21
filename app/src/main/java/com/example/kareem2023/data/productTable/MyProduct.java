package com.example.kareem2023.data.productTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyProduct {
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي يُنتجح بشكل تلقائي
    public long key_id;//رقم المنتج
    public String Barcode;//الباركود للمنتج
    public String ProductName;//اسم المنتج
    public String CompanyName;//اسم شركة المنتج
    public String AlergyName;//اسم الحساسية
    //todo do ait later when





    @Override
    public String toString() {
        return "Product{" +
                "Barcode='" + Barcode + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", AlergyName='" + AlergyName + '\'' +
                '}';
    }
}

