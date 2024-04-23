package com.example.kareem2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.kareem2023.data.AppDatabase;
import com.example.kareem2023.data.productTable.MyProduct;
import com.example.kareem2023.data.productTable.MyProductQuery;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddProduct extends AppCompatActivity {
    private TextInputEditText etAddProductProductName;
    private TextInputEditText etAddProductCompanyName;
    private TextInputEditText etAddProductBarcode;
    private AutoCompleteTextView autoEtAddProductAlergy;

    private Button btnAddProductScan;
    private Button btnAddProductCancelProduct;
    private Button btnAddProductSaveProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        etAddProductProductName = findViewById(R.id.etAddProductProductName);
        etAddProductCompanyName = findViewById(R.id.etAddProductCompanyName);
        etAddProductBarcode = findViewById(R.id.etAddProductBarcode);
        autoEtAddProductAlergy = findViewById(R.id.autoEtAddProductAlergy);
        btnAddProductScan = findViewById(R.id.btnAddProductSaveProduct);
        btnAddProductCancelProduct = findViewById(R.id.btnAddProductCancelProduct);
        btnAddProductSaveProduct = findViewById(R.id.btnAddProductSaveProduct);
        btnAddProductCancelProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        btnAddProductSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(AddProduct.this, MainActivity.class);
                startActivity(i);
            }
        });
        initAutoEtSubjects();
    }
    /**
     * استخراج اسماء المواضيع من جدول الحساسيات وعرضه بالحقل من نوع
     * AutoCompleteTextView
     * طريقة التعامل معه شبيه بال "spinner"
     */
    private void initAutoEtSubjects()
    {
//        // مؤشر لقاعدة البيانات
//        AppDatabase db=AppDatabase.getDB(getApplicationContext());
//        // مؤشر لواجهة استعلامات جدول المنتجات
//        MyProductQuery ProductQuery = db.getMyProductQuery();
//        // مصدر المعطيات: استخراج جميع المواضيع من الجدول
//        List<String> allAlergieses = ProductQuery.getAllAlergieses();
//        // تجهيز الوسيط
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
//        adapter.addAll(allAlergieses); //اضافة جميع المعطيات للوسيط
//        autoEtAddProductAlergy.setAdapter(adapter);// ربط الحقل بالوسيط
//        // معالجة حدث لعرض المواضيع عند الضغط على الحقل
//        autoEtAddProductAlergy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                autoEtAddProductAlergy.showDropDown();
//            }
//        });
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
            Product.id=product.getId();// تحديد رقم المنتج للمهمة
            db.getMyProductQuery().insertProduct(Product);// اضافة المنتج للجدول
            finish();//اغلاق الشاشة الخالية

        }
    }
    private void checkAddProduct_FB() {
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
            saveProduct_FB(ProductName,CompanyName,Barcode,ProductAlergy);
        }
    }
    private void saveProduct_FB(String ProductName, String CompanyName, String Barcode,String ProductAlergy)
    {
        //مؤشر لقاعدة البيانات
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //استخراج الرقم المميز للمستعمل الذي سجل الدخول لاستعماله كاسم لل "دوكيومنت"
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //بناء الكائن الذي سيتم حفظه
        MyProduct product = new MyProduct();
        product.setProductName(ProductName);
        product.setCompanyName(CompanyName);
        product.setBarcode(Barcode);
        product.setAlergyName(ProductAlergy);
        product.setUid(uid);
        //استخراج رقم مميز للكائن المخزون بقاعدة البيانات
        final String id = db.collection("MyProducts").document().getId();
        product.setId(id);
        //اضافة كائن "لمجموعة" المنتجات ومعالج حدث لفحص نجاح المطلوب
        //معالج حدث لفحص هل تم المطلوب من قاعدة البيانات
        db.collection("MyProducts").document(id).set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            //دلبة معلبج لبحدث
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                //هل تم تنفيذ المطلوب بنجاح
                if(task.isSuccessful())
                {
                    Toast.makeText(AddProduct.this,"Succeeded to add product",Toast.LENGTH_SHORT).show();
                   finish();
                }
                else
                {
                    Toast.makeText(AddProduct.this,"failed to add product",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}