package com.example.kareem2023;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kareem2023.data.productTable.MyProduct;
import com.example.kareem2023.data.productTable.MyProductAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

/**
 * الشاشة الرئيسية
 */
public class MainActivity extends AppCompatActivity
{

    private Button btnMainCkScan;

    private FloatingActionButton fabMainAdd;
    private ListView lstProducts;
    private MyProductAdapter productsAdapter;
    private TextInputEditText etCode;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        lstProducts = findViewById(R.id.lstvProducts);//הפניה לרכיב הגרפי שמציג אוסף
        productsAdapter = new MyProductAdapter(this,R.layout.myproduct_item_layout);//בניית המתאם
        lstProducts.setAdapter(productsAdapter);//קישור המתאם עם המציג הגרפי לאוסף
        btnMainCkScan = findViewById(R.id.btnMainChkScan);
        fabMainAdd = findViewById(R.id.fabMainAdd);
        etCode=findViewById(R.id.etCode);
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                productsAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    productsAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });




        /**
         * هذه الدالة اذا قمت بالضعط على اشارة الزائد لأضافة منتج الى صفحط اضافة المنتجات (هذه الصفحة مخصصة فقط للمدير)
         */
        fabMainAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddProduct.class);
                startActivity(i);

            }
        });

        /**
         * اذا كان البريد المستخدم للمدير يستطيع اضافة منتجات واذا لم يكن للمدير لا يستطيع الاضافة
         */
        final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if(email.equals("kareem.samniya@gmail.com")==false)
        {
            fabMainAdd.setVisibility(View.GONE);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        readProductFrom_FB();
    }

    /**
     *  קריאת נתונים ממסד הנתונים firestore
     * @return .... רשימת הנתונים שנקראה ממסד הנתונים
     */
//    public void readProductFrom_FB()
//    {
//        //בניית רשימה ריקה
//        ArrayList<MyProduct> arrayList =new ArrayList<>();
//        //קבלת הפנייה למסד הנתונים
//        FirebaseFirestore ffRef = FirebaseFirestore.getInstance();
//        //קישור לקבוצה לקבוצה שרוצים לקרוא
//        ffRef.collection("MyProducts")
//
//                //הוספת מאזין לקריאת הנתונים
//        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    /**
//                     * תגובה לאירוע השלמת קריאת הנתונים
//                     * @param task הנתונים שהתקבלו מענן מסד הנתונים
//                     */
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()) {// אם בקשת הנתונים התקבלה בהצלחה
//                            //מעבר על כל ה״מסמכים״= עצמים והוספתם למבנה הנתונים
//                            arrayList.clear();
//                            productsAdapter.clear();
//                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
//                                //המרת העצם לטיפוס שלו// הוספת העצם למבנה הנתונים
//                                arrayList.add(document.toObject(MyProduct.class));
//                            }
//                            productsAdapter.addAll(arrayList);
//                            ArrayAdapter<MyProduct>tsksAdapter=new ArrayAdapter<MyProduct>(MainActivity.this, android.R.layout.simple_dropdown_item_1line);
//                           tsksAdapter.addAll(arrayList);
//                            lstProducts.setAdapter(tsksAdapter);
//                        }
//
//                        else{
//                            Toast.makeText(MainActivity.this, "Error Reading data"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//    }

    /**
     *  קריאת נתונים ממסד הנתונים firestore
     * @return .... רשימת הנתונים שנקראה ממסד הנתונים
     */
    public void readProductFrom_FB() {
        //בניית רשימה ריקה
        ArrayList<MyProduct> arrayList = new ArrayList<>();
        //קבלת הפנייה למסד הנתונים
        FirebaseFirestore ffRef = FirebaseFirestore.getInstance();
        //קישור לקבוצה collection שרוצים לקרוא
        ffRef.collection("MyProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    /**
                     * תגובה לאירוע השלמת קריאת הנתונים
                     *
                     * @param task הנתונים שהתקבלו מענן מסד הנתונים
                     */
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {// אם בקשת הנתונים התקבלה בהצלחה
                            //מעבר על כל ה״מסמכים״= עצמים והוספתם למבנה הנתונים
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                //המרת העצם לטיפוס שלו// הוספת העצם למבנה הנתונים
                                arrayList.add(document.toObject(MyProduct.class));
                            }
                            productsAdapter.clear();//ניקוי המתאם מכל הנתונים
                            productsAdapter.addAll(arrayList);//הוספת כל הנתונים למתאם
                            productsAdapter.setOrginal(arrayList);
                        } else {
                            Toast.makeText(MainActivity.this, "Error Reading data" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    public void onClickCheckWithScan(View V)
    {

//        Intent i = new Intent(MainActivity.this, Scannnner.class);
//        startActivity(i);
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("scan any QR/Bar code");
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.initiateScan();

    }

    @Override//بناء القائمة
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override//معالجة حدث اختيار عنصر من القائمة
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if(item.getItemId()==R.id.itemSignOut)
        {
            Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();
            showYesNoDialog();
        }
        return true;
    }
    /**
     * تجهيو ديالوج
     */
    public void  showYesNoDialog()
    {
        //تجهيز بناء شباك حوار "ديالوج" يتلقى بارامتر مؤشر للنشاط (اكتيفيتي) الحالي
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log out");//تحديد العنوان
        builder.setMessage("Are you sure?");//تحديد فحوى شباك الحوار

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //
                Toast.makeText(MainActivity.this, "Signing out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(MainActivity.this, "Signing out", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();//بناء شباك الحوار(ديالوج)
        dialog.show();//عرض الشباك
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data)
    {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents()!=null)
        {
            if(intentResult.getContents()!=null)
            {
                etCode.setText(intentResult.getContents());
            }
            else
            {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }


}
