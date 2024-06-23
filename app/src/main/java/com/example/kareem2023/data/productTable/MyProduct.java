package com.example.kareem2023.data.productTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * wseftrwer
 */
@Entity                  //لكي نتمكن من نقل الكائن بواسطة intent من شاشة الى اخرى
public class MyProduct implements Serializable {
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي يُنتجح بشكل تلقائي
    public long roomId;//
    /**رقم المنتج
     *
     */
    public String id;
    /**
     * رقم المنتج
     */
    public String uid;

    public String barcode;
    /**
     * الباركود للمنتج
     */
    public String productName;
    /**
     * اسم المنتج
     */
    public String companyName;
    /**
     * اسم شركة المنتج
     */
    public String alergyName;
    /**
     * اسم الحساسية
     */
    //todo do ait later when

    public String image;
    /**
     * صورة للمنتج
     */

    public String isApproved;


    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

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
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAlergyName() {
        return alergyName;
    }

    public void setAlergyName(String alergyName) {
        this.alergyName = alergyName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    @Override
    public String toString() {
        return "MyProduct{" +

                ", barcode='" + barcode + '\'' +
                ", productName='" + productName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", alergyName='" + alergyName + '\'' +
                '}';
    }
}

