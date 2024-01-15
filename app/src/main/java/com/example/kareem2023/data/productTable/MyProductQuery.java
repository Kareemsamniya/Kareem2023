package com.example.kareem2023.data.productTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao

public interface MyProductQuery
{


    @Query("SELECT AlergyName FROM MyProduct")
    List<String> getAllAlergieses();


    @Query("SELECT * FROM MyProduct WHERE Barcode=:Barcode_p")
    List<MyProduct> getProductBySubjId(long Barcode_p);

    @Query("SELECT * FROM MyProduct WHERE AlergyName = :myAlergy")
    MyProduct checkAlergyName(String myAlergy);

    @Insert
    void insert(MyProduct myProduct);
    /**
     * ادخال مهمات
     * @param t مجموعة مهمات
     */
    @Insert
    void insertProduct(MyProduct...t); //ثلث نقاط تعني ادخال مجموعة

    /**
     * تعديل المهمات
     * @param task مجموعة مهمات للتعديل (التعديل ب المفتاح الرئيسي)
     */
    @Update
    void updateProduct(MyProduct... task);

    /**
     * حذف مهمة او مهمات
     * @param tasks حذف المهمات (حسب المفتاح الرئيسي)
     */
    @Delete
    void deleteProduct(MyProduct...tasks);

    @Query("DELETE FROM MyProduct WHERE Barcode=:Barcode")
    void delProductBarcode(long Barcode);

}
