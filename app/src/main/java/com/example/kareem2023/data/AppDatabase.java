package com.example.kareem2023.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kareem2023.data.usersTable.MyUser;
import com.example.kareem2023.data.usersTable.MyUserQuery;

    /*
تعريف الجداول ورقم النسخة
version
عند احداث تغيير يخص جدول او جداول علينا تغيير رقم الأصدار ليتم بناء قاعدة البينات من جديد
 */
    @Database(entities = {MyUser.class},version = 7)
/**
 * الفئة المسئولة عن بناء قاعدة البيانات لكل جداولها
 * وتوفر لنا كائن للتعامل مع قاعدة البيانات
 */
    public abstract class AppDatabase extends RoomDatabase
    {
        /**
         * كائن للتعامل مع قاعدة البيانات
         */
        private static AppDatabase db;

        /**
         * يعيد كائن لعمليات جدول المستعملين
         * @return
         */

        public abstract MyUserQuery getMyUserQuery();






        /**
         * بناء قاعدة البيانات واعادة كائن يؤشر عليها
         * @param context
         * @return
         */

        /**
         *singilton(عند اعادة الكائن يعيده مرة واحدة)
         */
        public static AppDatabase getDB(Context context) {
            if (db == null) {
                db = Room.databaseBuilder(context,
                                AppDatabase.class, "database-name")//اسم
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
            return db;

        }
    }

