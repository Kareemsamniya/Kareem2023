package com.example.kareem2023.data.productTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kareem2023.data.usersTable.MyUser;

import java.util.List;

@Dao

public interface ProductQuery
{
    /**
     * اعادة جميع معطيات جدول المهمات
     * @return قائمة من المهمات
     */
    @Query("SELECT * FROM Product")
    List<Product> getAllTasks();

    @Query("SELECT AlergyName FROM Product")
    List<Product> getAllAlergieses();


    @Query("SELECT * FROM Product WHERE Barcode=:Barcode_p")
    List<Product> getTaskBySubjId(long Barcode_p);


    /**
     * ادخال مهمات
     * @param t مجموعة مهمات
     */
    @Insert
    void insertTask(Product...t); //ثلث نقاط تعني ادخال مجموعة

    /**
     * تعديل المهمات
     * @param task مجموعة مهمات للتعديل (التعديل ب المفتاح الرئيسي)
     */
    @Update
    void updateTask(Product... task);

    /**
     * حذف مهمة او مهمات
     * @param tasks حذف المهمات (حسب المفتاح الرئيسي)
     */
    @Delete
    void deleteTask(Product...tasks);

    @Query("DELETE FROM Product WHERE Barcode=:Barcode")
    void delTaskBarcode(long Barcode);

}
