package com.example.kareem2023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.kareem2023.data.AppDatabase;
import com.example.kareem2023.data.productTable.Product;
import com.example.kareem2023.data.productTable.ProductQuery;
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
        ProductQuery ProductQuery = db.getProductQuery();
        // مصدر المعطيات: استخراج جميع المواضيع من الجدول
        List<Product> allAlergieses = ProductQuery.getAllAlergieses();
        // تجهيز الوسيط
        ArrayAdapter<Product> adapter=new ArrayAdapter<Product>(this, android.R.layout.simple_dropdown_item_1line);
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
        String Barcode = autoEtAddTaskSubject.getText().toString();
        //استخراج الأهمية
        int Importance = skbrAddTaskImportance.getProgress();
        //فحص النص القصير ان كان فارغ
        if (ShortTitle.length() < 2 || ShortTitle.contains(" ") == true) {
            //تعديل المتغير ليدل على ان الفحص يعطي نتيجة خاطئة
            isAllOk = false;
            //عرض ملاحظة خطأ على الشاشة داخل حقل النص القصير
            etAddTaskShortTitle.setError("Wrong Short Title");
        }
        if (Text.length() < 2 )
        {
            isAllOk = false;
            etAddTaskText.setError("Wrong Text");
        }
        if (SubjText.length() < 2 )
        {
            isAllOk = false;
            autoEtAddTaskSubject.setError("Wrong Subject");
        }
        if(isAllOk)
        {
            Toast.makeText(this, "All Ok", Toast.LENGTH_SHORT).show();
            //بناء قاعدة بيانات وارجاع مؤشر عليها 1
            AppDatabase db= AppDatabase.getDB(getApplicationContext());
            MySubjectQuery subjectQuery = db.getMySubjectQuery();
            if(subjectQuery.checkSubject(SubjText)==null)// فحص هل الموضوع موجود من قبل بالجدول
            {// بناء موضوع جديد واضافته
                MySubject subject=new MySubject();
                subject.Title=SubjText;
                subjectQuery.insert(subject);
            }
            //استخراج الموضوع لاننا بحاجة لرقمه التسلسلي id
            MySubject subject = subjectQuery.checkSubject(SubjText);
            //بناء مهمة جديدة وتحديد صفاتها
            MyTask task = new MyTask();
            //تجديد قيم الصفات بالقيم التي استخرجناها
            task.ShortTitle=ShortTitle;
            task.text=Text;
            task.importance= Importance;
            task.subjId=subject.getKey_id();// تحديد رقم الموضوع للمهمة
            db.getMyTaskQuery().insertTask(task);// اضافة المهمة للجدول
            finish();//اغلاق الشاشة الخالية

        }
    }
}