package com.example.kareem2023.data.productTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.kareem2023.data.Alergy.MyAlergy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي يُنتجح بشكل تلقائي
    public long key_id;
    public String Barcode;
    public String ProductName;
    public String CompanyName;
    public String AlergyName;
    //todo do ait later when
    //public ArrayList<MyAlergy> Alergieses;


    public Product(String barcode, String productName, String companyName, String alergyName) {
        Barcode = barcode;
        ProductName = productName;
        CompanyName = companyName;
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

