package com.example.kareem2023.data.productTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyProduct {
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي يُنتجح بشكل تلقائي

    public long RoomId;

    public String id;//رقم المنتج
    public String uid;
    public String Barcode;//الباركود للمنتج
    public String ProductName;//اسم المنتج
    public String CompanyName;//اسم شركة المنتج
    public String AlergyName;//اسم الحساسية
    //todo do ait later when


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getAlergyName() {
        return AlergyName;
    }

    public void setAlergyName(String alergyName) {
        AlergyName = alergyName;
    }

    @Override
    public String toString() {
        return "MyProduct{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", Barcode='" + Barcode + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", AlergyName='" + AlergyName + '\'' +
                '}';
    }
}

