package com.example.kareem2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kareem2023.data.AppDatabase;
import com.example.kareem2023.data.productTable.MyProduct;
import com.example.kareem2023.data.productTable.MyProductQuery;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddProduct extends AppCompatActivity {
    private TextInputEditText etAddProductProductName;
    private TextInputEditText etAddProductCompanyName;
    private TextInputEditText etAddProductBarcode;
    private AutoCompleteTextView autoEtAddProductAlergy;

    private Button btnAddProductScan;
    private Button btnAddProductCancelProduct;
    private Button btnAddProductSaveProduct;

    //upload: 1 add Xml image view or button and upload button
//upload: 2 add next fileds
    private final int IMAGE_PICK_CODE=100;// קוד מזהה לבקשת בחירת תמונה
    private final int PERMISSION_CODE=101;//קוד מזהה לבחירת הרשאת גישה לקבצים
    private ImageButton imgBtnl;//כפתור/ לחצן לבחירת תמונה והצגתה
    private Button btnUpload;// לחצן לביצוע העלאת התמונה
    private Uri toUploadimageUri;// כתוב הקובץ(תמונה) שרוצים להעלות
    private Uri downladuri;//כתובת הקוץ בענן אחרי ההעלאה

    private  MyProduct product = new MyProduct();//עצם/נתון שרוצים לשמור

    boolean toEdit = false;


    @Override
    protected void onResume() {
        super.onResume();
        final Intent intent = getIntent();
        if(intent!=null&&intent.getExtras()!=null&&intent.getExtras().get("prod")!=null)
        {
            toEdit=true;
            product = (MyProduct) intent.getExtras().get("prod");
            etAddProductCompanyName.setText(product.getCompanyName());
            etAddProductBarcode.setText(product.getBarcode());
            etAddProductProductName.setText(product.getProductName());
            autoEtAddProductAlergy.setText(product.getAlergyName());

        }

    }

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

//upload: 3
        imgBtnl=findViewById(R.id.imgBtnProductn);
        imgBtnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkPermission();

            }
        });


        btnAddProductCancelProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        btnAddProductSaveProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                checkAddProduct_FB();

            }
        });
        initAutoEtSubjects();
    }



    private void pickImageFromGallery(){
        //implicit intent (מרומז) to pick image
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);//הפעלתה האינטנט עם קוד הבקשה
    }



//upload: 5:handle result of picked images
    /**
     *
     * @param requestCode מספר הקשה
     * @param resultCode תוצאה הבקשה (אם נבחר משהו או בוטלה)
     * @param data הנתונים שנבחרו
     */
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //אם נבחר משהו ואם זה קוד בקשת התמונה
        if (resultCode==RESULT_OK && requestCode== IMAGE_PICK_CODE){
            //a עידכון תכונת כתובת התמונה
            toUploadimageUri = data.getData();//קבלת כתובת התמונה הנתונים שניבחרו
            imgBtnl.setImageURI(toUploadimageUri);// הצגת התמונה שנבחרה על רכיב התמונה
        }
    }


    //upload: 6
    /**
     * בדיקה האם יש הרשאה לגישה לקבצים בטלפון
     */
    private void checkPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//בדיקת גרסאות
            //בדיקה אם ההשאה לא אושרה בעבר
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //רשימת ההרשאות שרוצים לבקש אישור
                String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
                //בקשת אישור ההשאות (שולחים קוד הבקשה)
                //התשובה תתקבל בפעולה onRequestPermissionsResult
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                //permission already granted אם יש הרשאה מקודם אז מפעילים בחירת תמונה מהטלפון
                pickImageFromGallery();
            }
        }
        else {//אם גרסה ישנה ולא צריך קבלת אישור
            pickImageFromGallery();
        }
    }


//upload: 7
    /**
     * @param requestCode The request code passed in מספר בקשת ההרשאה
     * @param permissions The requested permissions. Never null. רשימת ההרשאות לאישור
     * @param grantResults The grant results for the corresponding permissions תוצאה עבור כל הרשאה
     *   PERMISSION_GRANTED אושר or PERMISSION_DENIED נדחה . Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_CODE) {//בדיקת קוד בקשת ההרשאה
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission was granted אם יש אישור
                pickImageFromGallery();
            } else {
                //permission was denied אם אין אישור
                Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
            }
        }

    }



    private void uploadImage(Uri filePath) {
        if (filePath != null) {
            //יצירת דיאלוג התקדמות
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();//הצגת הדיאלוג
            //קבלת כתובת האחסון בענן
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            //יצירת תיקיה ושם גלובלי לקובץ
            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            // יצירת ״תהליך מקביל״ להעלאת תמונה
            ref.putFile(filePath)
                    //הוספת מאזין למצב ההעלאה
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();// הסתרת הדיאלוג
                                //קבלת כתובת הקובץ שהועלה
                                ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        downladuri = task.getResult();
                                        Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                        product.setImage(downladuri.toString());//עדכון כתובת התמונה שהועלתה
                                       saveProduct_FB();
                                    }
                                });
                            } else {
                                progressDialog.dismiss();//הסתרת הדיאלוג
                                Toast.makeText(getApplicationContext(), "Failed " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    //הוספת מאזין שמציג מהו אחוז ההעלאה
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //חישוב מה הגודל שהועלה
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
          saveProduct_FB();
        }
    }



//    public void onClickAddProduct(View V)
//    {
//
//        checkAddProduct_FB();
//    }


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


    private void checkAddProduct()
    {
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
                product.alergyName =ProductAlergy;
                productQuery.insert(product);
            }
            //استخراج المنتج لاننا بحاجة لرقمه التسلسلي id
            MyProduct product = productQuery.checkAlergyName(ProductAlergy);
            //بناء منتج جديد وتحديد صفاته
            MyProduct Product = new MyProduct();
            //تجديد قيم الصفات بالقيم التي استخرجناها
            Product.productName =ProductName;
            Product.companyName =CompanyName;
            Product.barcode = Barcode;
            Product.id=product.getId();// تحديد رقم المنتج للمهمة
            db.getMyProductQuery().insertProduct(Product);// اضافة المنتج للجدول
            finish();//اغلاق الشاشة الخالية

        }
    }

    /**
     * عملية تقوم بأستقبال المعطيات وفحصها
     */
    private void checkAddProduct_FB()
    {
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
            //بناء الكائن الذي سيتم حفظه
            product.setProductName(ProductName);
            product.setCompanyName(CompanyName);
            product.setBarcode(Barcode);
            product.setAlergyName(ProductAlergy);
            //استخراج الرقم المميز للمستعمل الذي سجل الدخول لاستعماله كاسم لل "دوكيومنت"
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            product.setUid(uid);
                uploadImage(toUploadimageUri);

        }
        }


//    /**
//     * دالة تقوم باتخراج المعطيات وبناء كائن واضافته لمجموعة ال Products قي قاعدة البيانات FireStore
//     * @param ProductName
//     * @param CompanyName
//     * @param Barcode
//     * @param ProductAlergy
//     */
//
//    private void saveProduct() {
//        //קבלת הפניה למסד הניתונים
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        //קבלת מזהה המשתמש שנכנס לאפליקציה
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        //קבלת קישור לאוסף המקצועות שנמצא במסמך המשתמש-לפי המזהה שלו-
//        CollectionReference subjCollection = db.collection("MyProducts")
//                .document(uid)
//                .collection("products");
//        String sbjId = subjCollection.document().getId();//  לקבל מזהה ייחודי למסמך החדש
//
//
//        subject.id = sbjId;
//        subject.userId = uid;
//        //הוספת מקצוע לאוסף המקצועות (מזהה המקצוע הוא השם שלו)
//        //הוספת מאזין שבודק אם ההוספה הצליחה
//        subjCollection.document(subject.title).set(subject).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    //אם ההוספה של המקצוע הצליחה מוסיפים משימה למקצוע
//                    //קבלת קישור/כתובת לאוסף המשימות
//                    CollectionReference tasksCollection = subjCollection.document(subject.title).collection("Tasks");
//                    // קבלת מזהה למסמך החדש
//                    String taskId = tasksCollection.document().getId();
//                    myTask.id = taskId;//עידכון תכונת המזהה של המשימה
//                    // הוספת (מסמך) המשימה לאוסף המשימות
//                    //הוספת המאזין לבדיקת הצלחת ההוספה
//                    tasksCollection.document(taskId).set(myTask).addOnCompleteListener(new OnCompleteListener<Void>(){
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(AddTaskActivity.this, "Adding myTask Succeeded", Toast.LENGTH_SHORT).show();
//                                finish();
//                            } else {
//                                Toast.makeText(AddTaskActivity.this, "Adding myTask failed" + task.getException().toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                } else {
//                    Toast.makeText(AddTaskActivity.this, "Adding mySubject failed" + task.getException().toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//


    private void saveProduct_FB()
    {
        //مؤشر لقاعدة البيانات
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        if(!toEdit) {
            //استخراج رقم مميز للكائن المخزون بقاعدة البيانات
            final String id = db.collection("MyProducts").document().getId();
            product.setId(id);
        }
        //اضافة كائن "لمجموعة" المنتجات ومعالج حدث لفحص نجاح المطلوب
        //معالج حدث لفحص هل تم المطلوب من قاعدة البيانات
        db.collection("MyProducts").document(product.getId()).set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            //دالة معالج لبحدث
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