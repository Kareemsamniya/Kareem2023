package com.example.kareem2023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.kareem2023.data.AppDatabase;
import com.example.kareem2023.data.productTable.MyProduct;
import com.example.kareem2023.data.productTable.MyProductQuery;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AddProduct extends AppCompatActivity {
    private TextInputEditText etAddProductProductName;
    private TextInputEditText etAddProductCompanyName;
    private TextInputEditText etAddProductBarcode;
    private AutoCompleteTextView autoEtAddProductAlergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        etAddProductProductName = findViewById(R.id.etAddProductProductName);
        etAddProductCompanyName = findViewById(R.id.etAddProductCompanyName);
        etAddProductBarcode = findViewById(R.id.etAddProductBarcode);
        autoEtAddProductAlergy = findViewById(R.id.autoEtAddProductAlergy);
        initAutoEtSubjects();
    }
    /**
     * استخراج اسماء المواضيع من جدول الحساسيات وعرضه بالحقل من نوع
     * AutoCompleteTextView
     * طريقة التعامل معه شبيه بال "spinner"
     */
    private void initAutoEtSubjects()
    {
        // مؤشر لقاعدة البيانات
        AppDatabase db=AppDatabase.getDB(getApplicationContext());
        // مؤشر لواجهة استعلامات جدول المنتجات
        MyProductQuery ProductQuery = db.getMyProductQuery();
        // مصدر المعطيات: استخراج جميع المواضيع من الجدول
        List<String> allAlergieses = ProductQuery.getAllAlergieses();
        // تجهيز الوسيط
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
        adapter.addAll(allAlergieses); //اضافة جميع المعطيات للوسيط
        autoEtAddProductAlergy.setAdapter(adapter);// ربط الحقل بالوسيط
        // معالجة حدث لعرض المواضيع عند الضغط على الحقل
        autoEtAddProductAlergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                autoEtAddProductAlergy.showDropDown();
            }
        });
    }

    public void onClickAddProductCancel(View V)
    {

        finish();
    }
    public void onClickAddProductSave(View V)
    {

        checkAddProduct();
    }
    private void checkAddProduct() {
        boolean isAllOk = true;// يحوي نتيجة فحص الحقول ان كانت سليمة
        //استخراج النص من حقل اسم المنتج
        String ProductName = etAddProductProductName.getText().toString();
        //استخراج اسم الشركة
        String CompanyName = etAddProductCompanyName.getText().toString();
        //استخراج نص الموضوع
        String Barcode = etAddProductBarcode.getText().toString();
        //استخراج الأهمية
        String ProductAlergy = autoEtAddProductAlergy.getText().toString();
        //فحص اسم الشركة ان كان فارغ
        if (ProductName.length() < 2 || ProductName.contains(" ") == true) {
            //تعديل المتغير ليدل على ان الفحص يعطي نتيجة خاطئة
            isAllOk = false;
            //عرض ملاحظة خطأ على الشاشة داخل حقل النص القصير
            etAddProductProductName.setError("Wrong ProductName");
        }
        if (CompanyName.length() < 2 || CompanyName.contains(" ") == true )
        {
            isAllOk = false;
            etAddProductCompanyName.setError("Wrong Company Name");
        }
        if (Barcode.length() < 10 )
        {
            isAllOk = false;
            etAddProductBarcode.setError("Wrong Barcode");
        }
        if (ProductAlergy.length() < 2 || ProductAlergy.contains(" ") == true)
        {
            isAllOk = false;
            autoEtAddProductAlergy.setError("Wrong Product Alergy");
        }
        if(isAllOk)
        {
            Toast.makeText(this, "All Ok", Toast.LENGTH_SHORT).show();
            //بناء قاعدة بيانات وارجاع مؤشر عليها 1
            AppDatabase db= AppDatabase.getDB(getApplicationContext());
            MyProductQuery productQuery = db.getMyProductQuery();
            if(productQuery.checkAlergyName(ProductAlergy)==null)// فحص هل الموضوع موجود من قبل بالجدول
            {// بناء موضوع جديد واضافته
                MyProduct product=new MyProduct();
                product.AlergyName=ProductAlergy;
                productQuery.insert(product);
            }
            //استخراج المنتج لاننا بحاجة لرقمه التسلسلي id
            MyProduct product = productQuery.checkAlergyName(ProductAlergy);
            //بناء منتج جديد وتحديد صفاته
            MyProduct Product = new MyProduct();
            //تجديد قيم الصفات بالقيم التي استخرجناها
            Product.ProductName=ProductName;
            Product.CompanyName=CompanyName;
            Product.Barcode= Barcode;
            Product.key_id=product.getKey_id();// تحديد رقم المنتج للمهمة
            db.getMyProductQuery().insertProduct(Product);// اضافة المنتج للجدول
            finish();//اغلاق الشاشة الخالية

        }
    }
}