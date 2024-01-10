package com.example.kareem2023.data.productTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyProduct {
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي يُنتجح بشكل تلقائي
    public long key_id;//رقم المنتج
    public String Barcode;
    public String ProductName;
    public String CompanyName;
    public String AlergyName;
    //todo do ait later when
    //public ArrayList<MyAlergy> Alergieses;


    public long getKey_id() {
        return key_id;
    }

    public void setKey_id(long key_id) {
        this.key_id = key_id;
    }

    public String getAlergyName() {
        return AlergyName;
    }

    public void setAlergyName(String alergyName) {
        AlergyName = alergyName;
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

