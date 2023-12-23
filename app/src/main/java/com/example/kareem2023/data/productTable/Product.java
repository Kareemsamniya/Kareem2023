package com.example.kareem2023.data.productTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;
@Entity
public class Product
{
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي يُنتجح بشكل تلقائي
    public  long key_id;
    public String Barcode;
    public String ProductName;
    public String CompanyName;
    public Product [] p;

    @Override
    public String toString() {
        return "Product{" +
                "key_id=" + key_id +
                ", Barcode='" + Barcode + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", p=" + Arrays.toString(p) +
                '}';
    }
}
