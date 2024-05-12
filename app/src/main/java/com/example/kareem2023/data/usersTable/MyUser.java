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
        public String fullName;
        /**
         * الاسم الشخصي
         */
        //بحالة لم يتم اعطاء اسم للعامود يكون اسم الصفه هو اسم العامود
        public String email;
        /**
         * حساب الجيميل الشخصي
         */
        public String phone;
        /**
         * رقم الهاتف الشخصي
         */
        public String passw;
        /**
         * الرقم السري
         */
        public String alergy;
        /**
         * اسم الحساسية
         */
        public String id;
        /**
         * מזהה
         */

        public Boolean isManager = false;

        public long getKeyid() {
            return keyid;
        }

        public void setKeyid(long keyid) {
            this.keyid = keyid;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassw() {
            return passw;
        }

        public void setPassw(String passw) {
            this.passw = passw;
        }

        public String getAlergy() {
            return alergy;
        }

        public void setAlergy(String alergy) {
            this.alergy = alergy;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Boolean getManager() {
            return isManager;
        }

        public void setManager(Boolean manager) {
            isManager = manager;
        }



        @Override
        public String toString() {
            return "MyUser{" +
                    "keyid=" + keyid +
                    ", fullName='" + fullName + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    ", passw='" + passw + '\'' +
                    ", alergy='" + alergy + '\'' +
                    ", id='" + id + '\'' +
                    ", manager='" + isManager + '\'' +
                    '}';
        }
    }


