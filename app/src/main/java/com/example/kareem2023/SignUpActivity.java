package com.example.kareem2023;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kareem2023.data.AppDatabase;
import com.example.kareem2023.data.productTable.MyProduct;
import com.example.kareem2023.data.usersTable.MyUser;
import com.example.kareem2023.data.usersTable.MyUserQuery;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class  SignUpActivity extends AppCompatActivity  {


    private Spinner spnrAlerg;
    private TextInputEditText etSignUpEmail;
    private TextInputEditText etSignUpPassword;
    private TextInputEditText etSignUpRepassword;
    private TextInputEditText etSignUpName;
    private TextInputEditText etSignUpPhone;
    private TextInputEditText etSignUpAlergyName;
    private Button btnSignUpSave;
    private Button btnSignUpCancel;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etSignUpAlergyName=findViewById(R.id.etSignUpAlergyName);
        etSignUpEmail=  findViewById(R.id.etSignUpEmail);
        etSignUpPassword=  findViewById(R.id.etSignUpPassword);
        etSignUpRepassword=  findViewById(R.id.etSignUpRepassword);
        etSignUpName= findViewById(R.id.etSignUpName);
        etSignUpPhone=  findViewById(R.id.etSignUpPhone);
        btnSignUpSave= findViewById(R.id.btnSignUpSave);
        btnSignUpCancel= findViewById(R.id.btnSignUpCancel);


    }
    public void onClickSignUpCancel(View V)
    {

        finish();
    }

    public void onClickSignUp(View V)
    {

        checkAndSignUP_FB();
    }
    //دالة لحفظ المعطيات في firebase
    private void saveUser_FB(String email, String name, String phone, String passw , String alergy)
    {
        //مؤشر لقاعدة البيانات
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //استخراج الرقم المميز للمستعمل الذي سجل الدخول لاستعماله كاسم لل "دوكيومنت"
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //بناء الكائن الذي سيتم حفظه
        MyUser user = new MyUser();
        user.setEmail(email);
        user.setFullName(name);
        user.setPhone(phone);
        user.setPassw(passw);
        user.setId(uid);
        user.setAlergy(alergy);
        //اضافة كائن "لمجموعة" المستعملين ومعالج حدث لفحص نجاح المطلوب
        //معالج حدث لفحص هل تم المطلوب من قاعدة البيانات
        db.collection("MyUsers").document(uid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            //دلبة معلبج لبحدث
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                //هل تم تنفيذ المطلوب بنجاح
                if(task.isSuccessful())
                {
                    Toast.makeText(SignUpActivity.this,"Succeeded to add User",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this,"failed to add User",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
//    private void checkSignUp()
//    {
//        boolean isAllOk = true;// يحوي نتيجة فحص الحقول ان كانت سليمة
//        //استخراج النص من حقل الحساسية
//        String Alergy = etSignUpAlergyName.getText().toString();
//        //استخراج النص من حقل الايميل
//        String Email = etSignUpEmail.getText().toString();
//        //استخراج نص كلمة المرور
//        String Password = etSignUpPassword.getText().toString();
//        //استخراج نص تأكيد كلمة المرور
//        String RePassword = etSignUpRepassword.getText().toString();
//        //استخراج نص الاسم
//        String Name = etSignUpName.getText().toString();
//        //استخراج نص رقم الهاتف
//        String Phone = etSignUpPhone.getText().toString();
//        //فحص الايميل ان كان طوله اقل من 6 او لا يحوي @ فهو خطأ
//        if (Email.length() < 6 || Email.contains("@") == false)
//        {
//            //تعديل المتغير ليدل على ان الفحص يعطي نتيجة خاطئة
//            isAllOk = false;
//            //عرض ملاحظة خطأ على الشاشة داخل حقل البريد
//            etSignUpEmail.setError("Wrong Email");
//        }
//        if(Password.length() < 8 || Password.contains(" ") == true)
//        {
//            isAllOk = false;
//            etSignUpPassword.setError("Wrong Password");
//        }
//        if(RePassword.length() == 0 || RePassword.equals(Password) == false )
//        {
//            isAllOk = false;
//            etSignUpRepassword.setError("Wrong Re-Password");
//        }
//        if(Name.length() < 2 || Name.contains(" ") == true)
//        {
//            isAllOk = false;
//            etSignUpName.setError("Wrong Name");
//        }
//        if(Phone.length() != 10 || Phone.contains(" ") == true )
//        {
//            isAllOk = false;
//            etSignUpPhone.setError("Wrong Phone");
//        }
//        if(Alergy.length() < 2 || Alergy.contains(" ") == true )
//        {
//            isAllOk = false;
//            etSignUpAlergyName.setError("Wrong Alergy Name");
//        }
//
//        if(isAllOk)
//        {
//            Toast.makeText(this, "All Ok", Toast.LENGTH_SHORT).show();
//            //بناء قاعدة بيانات وارجاع مؤشر عليها 1
//            AppDatabase db= AppDatabase.getDB(getApplicationContext());
//            //مؤشر لكائن عمليات الجدول 2
//            MyUserQuery userQuery=db.getMyUserQuery();
//            //فحص هل البريد الالكتروني موجود من قبل اي تم التسجيل من قبل
//            if(userQuery.checkEmail(Email)!=null)
//            {
//                etSignUpEmail.setError("found email");
//            }
//            else// ان لم يكن البريد موجودا نقوم ببناء كائن للمستعمل وادخاله في الجدول MyUser المستعملين
//            {
//                //بناء كائن
//                MyUser myUser = new MyUser();
//                //تجديد قيم الصفات بالقيم التي استخرجناها
//                myUser.email=Email;
//                myUser.fullName=Name;
//                myUser.phone=Phone;
//                myUser.passw=Password;
//                myUser.alergy=Alergy;
//                //اضافة الكائن للجدول
//                userQuery.insert(myUser);
//                //اغلاق الشاشة الخالية
//                finish();
//            }
//        }
//
//
//    }


    private void checkAndSignUP_FB()
    {
        boolean isAllOk = true;// يحوي نتيجة فحص الحقول ان كانت سليمة
        //استخراج النص من حقل الحساسية
        String Alergy = etSignUpAlergyName.getText().toString();
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
        if(RePassword.contains(" ") == true || RePassword.equals(Password) == false )
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
            //עצם לביצוע רישום كائن لعملية التسجيل
            FirebaseAuth auth= FirebaseAuth.getInstance();
            //יצירת חשבון בעזרת מיל וסיסמא
            auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override//התגובה שמתקבל הניסיון הרישום בענן
                public void onComplete(@NonNull Task<AuthResult> task)
                {//הפרמטר מכיל מידע מהשרת על תוצאת הבקשה לרישום
                    if(task.isSuccessful())
                    {
                        Toast.makeText(SignUpActivity.this, "Signing up succeeded", Toast.LENGTH_SHORT).show();
                        saveUser_FB(Email,Name,Password,Phone,Alergy);//دالة لحفظ المعطيات في firebase
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SignUpActivity.this, "Signing up failed", Toast.LENGTH_SHORT).show();
                        etSignUpEmail.setError(task.getException().getMessage());//הצגת הודעת השגיאה שהתקבלה מהענן
                    }
                }
            });
        }
    }




}