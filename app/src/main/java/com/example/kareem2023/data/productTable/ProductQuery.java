package com.example.kareem2023.data.productTable;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.kareem2023.data.usersTable.MyUser;

@Dao

public interface ProductQuery
{
    @Query("SELECT * FROM Product WHERE ProductName = :ProductName  AND " +
            "Barcode = :Barcode LIMIT 1")
    Product checkBarcode(String Barcode, String ProductName);

}
