package com.example.kareem2023.data.usersTable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
//1
//Entity = Table =جدول
//عندما نريد ان نتعامل مع هذه الفئة كجدول معطيات
@Entity
public class MyUser
    {
        @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي يُنتجح بشكل تلقائي
        public long keyid;
        @ColumnInfo(name = "full_Name")//اعطاء اسم جديد للعامود-الصفة في الجدول
        public String fullName;//الاسم الشخصي
        //بحالة لم يتم اعطاء اسم للعامود يكون اسم الصفه هو اسم العامود
        public String email;//حساب الجيميل الشخصي
        public String phone;//رقم الهاتف الشخص
        public String passw;//الرقم السري
        public String alergy;//اسم الحساسية



        @Override
        public String toString() {
            return "MyUser{" +
                    "keyid=" + keyid +
                    ", fullName='" + fullName + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    ", passw='" + passw + '\'' +
                    ", alergy='" + alergy +
                    '}';
        }
    }


