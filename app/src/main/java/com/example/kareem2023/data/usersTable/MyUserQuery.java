package com.example.kareem2023.data.usersTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
//2
@Dao//لتحديد ان الواجهة تحتوي استعلامات على قاعدة بيانات

public interface MyUserQuery
{
    /**
     * الدالة تقوم بادخال الكائن الى الجدول
     * @param myUser
     */
    @Insert
    void insert(MyUser myUser);

    /**
     *الدالة تقوم بتحديث القيمة ي الجدول
     * @param values
     */
    @Update
    void update(MyUser...values);

    /**
     *الدالة تقوم بفحص حساب الجيميل والرقم السري اذا موجود من قبل في الجدول
     * @param myEmail
     * @param myPassw
     * @return
     */
    @Query("SELECT * FROM MyUser WHERE email = :myEmail AND " +
            "passw = :myPassw LIMIT 1")
    MyUser checkEmailPassw(String myEmail, String myPassw);

    /**
     *      *الدالة تقوم بفحص حساب الجيميل اذا موجود من قبل في الجدول
     * @param myEmail
     * @return
     */
    @Query("SELECT * FROM MyUser WHERE email = :myEmail")
    MyUser checkEmail(String myEmail);

    @Insert
    void insertAll(MyUser... users);

    @Delete
    void delete(MyUser user);

    @Query("SELECT * FROM MyUser WHERE keyid IN (:userIds)")
    List<MyUser> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM MyUser")
    List<MyUser> getAll();


}