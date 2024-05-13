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

    /**
     * الدالة تقوم باعادة جميع الحساسيات
     * @return
     */
    @Query("SELECT alergyName FROM MyProduct")
    List<String> getAllAlergieses();

    /**
     * تفحص الدالة اذا الحساسية الذي استخرجناها من المنتج موجودة عند الشخص
     * @param myAlergy
     * @return
     */
    @Query("SELECT * FROM MyProduct WHERE alergyName = :myAlergy")
    MyProduct checkAlergyName(String myAlergy);

    /**
     *الدالة تقوم باضافة المنتجات الى الجدول
     * @param t مجموعة مهمات
     */
    @Insert
    void insertProduct(MyProduct...t); //ثلث نقاط تعني ادخال مجموعة

    /**
     * الدالة تضيف كائن الى الجدول
     * @param myProduct
     */
    @Insert
    void insert(MyProduct myProduct);
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

    @Query("SELECT * FROM MyProduct WHERE barcode=:Barcode_p")
    List<MyProduct> getProductBySubjId(long Barcode_p);

    @Query("DELETE FROM MyProduct WHERE barcode=:Barcode")
    void delProductBarcode(long Barcode);
}
