package com.example.kareem2023;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kareem2023.data.Alergy.MyAlergy;
import com.example.kareem2023.data.AppDatabase;
import com.example.kareem2023.data.productTable.Product;
import com.example.kareem2023.data.usersTable.MyUser;
import com.example.kareem2023.data.usersTable.MyUserQuery;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class  SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Spinner spnrAlerg;
    private TextInputEditText etSignUpEmail;
    private TextInputEditText etSignUpPassword;
    private TextInputEditText etSignUpRepassword;
    private TextInputEditText etSignUpName;
    private TextInputEditText etSignUpPhone;
    private Button btnSignUpSave;
    private Button btnSignUpCancel;
    private ArrayList<Product> arrProducts;
    private ArrayList<MyAlergy> AlergyTypes1;
    private MyAlergy BambaAlergieses = new MyAlergy() ;
    private ArrayList<MyAlergy> AlergyTypes2;
    private MyAlergy BisleyAlergieses = new MyAlergy() ;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        spnrAlerg=findViewById(R.id.spnrAlergy);
        etSignUpEmail=  findViewById(R.id.etSignUpEmail);
        etSignUpPassword=  findViewById(R.id.etSignUpPassword);
        etSignUpRepassword=  findViewById(R.id.etSignUpRepassword);
        etSignUpName= findViewById(R.id.etSignUpName);
        etSignUpPhone=  findViewById(R.id.etSignUpPhone);
        btnSignUpSave= findViewById(R.id.btnSignUpSave);
        btnSignUpCancel= findViewById(R.id.btnSignUpCancel);

        //تجهيز سبنر يشمل جميع الحساسيات
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Alg, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrAlerg.setAdapter(adapter);
        spnrAlerg.setOnItemSelectedListener(this);

        ArrayList <String> arrAlergiesesBamba = new ArrayList<>();
        arrAlergiesesBamba.add("milk");
        arrAlergiesesBamba.add("butter");
        BambaAlergieses.setAlergyName(arrAlergiesesBamba);
        AlergyTypes1=new ArrayList<>();
        AlergyTypes1.set(0,BambaAlergieses);
        arrProducts = new ArrayList<>();
        arrProducts.add(0,new Product("87820","Bamba","Osem",AlergyTypes1));

        ArrayList <String> arrAlergiesesBisley = new ArrayList<>();
        arrAlergiesesBisley.add("milk");
        arrAlergiesesBisley.add("butter");
        BisleyAlergieses.setAlergyName(arrAlergiesesBamba);
        AlergyTypes2=new ArrayList<>();
        AlergyTypes2.set(0,BisleyAlergieses);
        arrProducts.add(1,new Product("45960","Bisley","Osem",AlergyTypes2));

        MyAlergy gg = new MyAlergy();
        gg.AlergyName=arrAlergiesesBamba;
        gg.Info="sdvd dfdv dfbdv";




    }
    public void onClickSignUpCancel(View V)
    {

        finish();
    }

    public void onClickSignUp(View V)
    {

        checkSignUp();
    }
    private void checkSignUp()
    {
        boolean isAllOk = true;// يحوي نتيجة فحص الحقول ان كانت سليمة
        //استخراج النص من حقل السبينر
        String Alergy = spnrAlerg.getSelectedItem().toString();
        //استخراج النص من حقل الايميل
        String Email = etSignUpEmail.getText().toString();
        //استخراج نص كلمة المرور
        String Password = etSignUpPassword.getText().toString();
        //استخراج نص تأكيد كلمة المرور
        String RePassword = etSignUpRepassword.getText().toString();
        //استخراج نص الاسم
        String Name = etSignUpName.getText().toString();
        //استخراج نص رقم الهاتف
        String Phone = etSignUpPhone.getText().toString();
        //فحص الايميل ان كان طوله اقل من 6 او لا يحوي @ فهو خطأ
        if (Email.length() < 6 || Email.contains("@") == false)
        {
            //تعديل المتغير ليدل على ان الفحص يعطي نتيجة خاطئة
            isAllOk = false;
            //عرض ملاحظة خطأ على الشاشة داخل حقل البريد
            etSignUpEmail.setError("Wrong Email");
        }
        if(Password.length() < 8 || Password.contains(" ") == true)
        {
            isAllOk = false;
            etSignUpPassword.setError("Wrong Password");
        }
        if(RePassword.length() == 0 || RePassword.equals(Password) == false )
        {
            isAllOk = false;
            etSignUpRepassword.setError("Wrong Re-Password");
        }
        if(Name.length() < 2 || Name.contains(" ") == true)
        {
            isAllOk = false;
            etSignUpName.setError("Wrong Name");
        }
        if(Phone.length() != 10 || Phone.contains(" ") == true )
        {
            isAllOk = false;
            etSignUpPhone.setError("Wrong Phone");
        }

        if(isAllOk)
        {
            Toast.makeText(this, "All Ok", Toast.LENGTH_SHORT).show();
            //بناء قاعدة بيانات وارجاع مؤشر عليها 1
            AppDatabase db= AppDatabase.getDB(getApplicationContext());
            //مؤشر لكائن عمليات الجدول 2
            MyUserQuery userQuery=db.getMyUserQuery();
            //فحص هل البريد الالكتروني موجود من قبل اي تم التسجيل من قبل
            if(userQuery.checkEmail(Email)!=null)
            {
                etSignUpEmail.setError("found email");
            }
            else// ان لم يكن البريد موجودا نقوم ببناء كائن للمستعمل وادخاله في الجدول MyUser المستعملين
            {
                //بناء كائن
                MyUser myUser = new MyUser();
                //تجديد قيم الصفات بالقيم التي استخرجناها
                myUser.email=Email;
                myUser.fullName=Name;
                myUser.phone=Phone;
                myUser.passw=Password;
                myUser.alergy=Alergy;
                //اضافة الكائن للجدول
                userQuery.insert(myUser);
                //اغلاق الشاشة الخالية
                finish();
            }
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}