package com.example.kareem2023.data.productTable;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.ContextCompat.startActivity;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.PermissionChecker;

import com.example.kareem2023.AddProduct;
import com.example.kareem2023.HaveAlergy;
import com.example.kareem2023.MainActivity;
import com.example.kareem2023.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import android.widget.Filter;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MyProductAdapter extends ArrayAdapter<MyProduct>
{
    //המזהה של קובץ עיצוב הפריט
    private final int itemLayout;
    private MyFilter myfilter;
    private ArrayList<MyProduct> orginal;
    private ArrayList<MyProduct> filteredTasks;

    public void setOrginal(ArrayList<MyProduct> orginal) {
        this.orginal = orginal;
    }

    public ArrayList<MyProduct> getOrginal() {
        return orginal;
    }

    /**
     * פעולה בונה מתאם
     * @param context קישור להקשר (מסך- אקטיביטי)
     * @param resource עיצוב של פריט שיציג הנתונים של העצם
     */
    public MyProductAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.itemLayout =resource;
    }

    /**
     * בונה פריט גרפי אחד בהתאם לעיצוב והצגת נתוני העצם עליו
     * @param position מיקום הפריט החל מ 0
     * @param convertView
     * @param parent רכיב האוסף שיכיל את הפריטים כמו listview
     * @return  . פריט גרפי שמציג נתוני עצם אחד
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
//בניית הפריט הגרפי מתו קובץ העיצוב
        View vitem= convertView;
        if(vitem==null)
            vitem=convertView=LayoutInflater.from(getContext()).inflate(itemLayout,parent,false);

        vitem= LayoutInflater.from(getContext()).inflate(R.layout.myproduct_item_layout,parent,false);
        //קבלת הפניות לרכיבים בקובץ העיצוב
        ImageView imageView=vitem.findViewById(R.id.imgVitm);
        TextView tvitmName=vitem.findViewById(R.id.tvitmName);
        TextView tvitmCompany=vitem.findViewById(R.id.tvitmCompany);
        TextView tvitmBarcode=vitem.findViewById(R.id.tvitmBarcode);
        TextView tvitmAlergies=vitem.findViewById(R.id.tvitmAlergies);
        ImageButton imgBtnDelete=vitem.findViewById(R.id.imgBtnDelete);
        ImageButton imgBtnEdit=vitem.findViewById(R.id.imgBtnEdit);
        ImageButton imgBtnShare=vitem.findViewById(R.id.imgBtnShare);
        ImageButton imgBtnWhats=vitem.findViewById(R.id.imgBtnWhats);


        //קבלת הנתון (עצם) הנוכחי
        MyProduct current=getItem(position);
        //הצגת הנתונים על שדות הרכיב הגרפי
        tvitmName.setText(current.getProductName());
        tvitmCompany.setText(current.getCompanyName());
        tvitmBarcode.setText(current.getBarcode());
        tvitmAlergies.setText(current.getAlergyName());

        imgBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AddProduct.class);
                i.putExtra("prod",current);
              getContext().  startActivity(i);
            }
        });


        imgBtnWhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSendWhatsAppV2(current.toString(),"");
            }
        });

        imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delMyTaskFromDB_FB(current);
            }
        });
        imgBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSendSmsApp(current.toString(),"");// אם יש טלפון המשימה מעבירים במקום ה ״״
            }
        });
        if (current.getImage() == null || current.getImage().length()==0)
        {

        }else
        downloadImageUsingPicasso(current.getImage(),imageView);
        /**
         * اذا كان البريد المستخدم للمدير يستطيع اضافة منتجات واذا لم يكن للمدير لا يستطيع الاضافة
         */
        final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if(email.equals("kareem.samniya@gmail.com")==false)
        {
            imgBtnEdit.setVisibility(View.GONE);
            imgBtnDelete.setVisibility(View.GONE);
        }
        return vitem;
    }



    /**
     * הצגת תמונה ישירות מהענן בעזרת המחלקה ״פיקאסו״
     * @param imageUrL כתובת התמונה בענן/שרת
     * @param toView רכיב תמונה המיועד להצגת התמונה אחרי ההורדה
     */
    private void downloadImageUsingPicasso(String imageUrL, ImageView toView)
    {
        // אם אין תמונה= כתובת ריקה אז לא עושים כלום מפסיקים את הפעולה
        if(imageUrL==null) return;
        //todo: add dependency to module gradle:
        //    implementation 'com.squareup.picasso:picasso:2.5.2'
        Picasso.with(getContext())
                .load(imageUrL)//הורדת התמונה לפי כתובת
                .centerCrop()
                .error(R.mipmap.ic_launcher)//התמונה שמוצגת אם יש בעיה בהורדת התמונה
                .resize(90,90)//שינוי גודל התמונה
                .into(toView);// להציג בריכיב התמונה המיועד לתמונה זו
    }



    /**
     *  פתיחת אפליקצית שליחת sms
     * @param msg .. ההודעה שרוצים לשלוח
     * @param phone
     */
    public void openSendSmsApp(String msg, String phone)
    {
        //אינטנט מרומז לפתיחת אפליקצית ההודות סמס
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        //מעבירים מספר הטלפון
        smsIntent.setData(Uri.parse("smsto:"+phone));
        //ההודעה שנרצה שתופיע באפליקצית ה סמס
        smsIntent.putExtra("sms_body",msg);
        smsIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
        //פתיחת אפליקציית ה סמס
        getContext().startActivity(smsIntent);
    }



    /**
     * מחיקת פריט כולל התמונה מבסיס הנתונים
     * @param myProduct הפריט שמוחקים
     */
    private void delMyTaskFromDB_FB(MyProduct myProduct)
    {
        //הפנייה/כתובת  הפריט שרוצים למחוק
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("MyProducts").document(myProduct.id).
                delete().//מאזין אם המחיקה בוצעה
                addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    remove(myProduct);// מוחקים מהמתאם
                    deleteFile(myProduct.getImage());// מחיקת הקובץ
                    Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * מחיקת קובץ האיחסון הענן
     * @param fileURL כתובת הקובץ המיועד למחיקה
     */
    private void deleteFile(String fileURL) {
        // אם אין תמונה= כתובת ריקה אז לא עושים כלום מפסיקים את הפעולה
        if(fileURL==null){
            Toast.makeText(getContext(), "Theres no file to delete!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        // הפניה למיקום הקובץ באיחסון
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(fileURL);
        //מחיקת הקובץ והוספת מאזין שבודק אם ההורדה הצליחה או לא
        storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "file deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "onFailure: did not delete file " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                }

            }
        });
    }

    /**
     *  פתיחת אפליקצית שליחת whatsapp
     * @param msg .. ההודעה שרוצים לשלוח
     * @param phone
     */
    public void openSendWhatsAppV2(String msg, String phone)
    {
        //אינטנט מרומז לפתיחת אפליקצית ההודות סמס
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);;
        String url = null;
        try {
            url = "https://api.whatsapp.com/send?phone="+phone+"&text="+ URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
            Toast.makeText(getContext(), "there is no whatsapp!!", Toast.LENGTH_SHORT).show();
        }
        sendIntent.setData(Uri.parse(url));
        sendIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        sendIntent.addCategory(Intent.CATEGORY_DEFAULT);
        //פתיחת אפליקציית ה סמס
        getContext().startActivity(sendIntent);
    }




    @NonNull
    @Override
    public Filter getFilter() {

        if(myfilter==null)
            myfilter=new MyFilter();
        return myfilter;
    }

    class MyFilter extends Filter{
        ArrayList<MyProduct> filteredProducts=new ArrayList<>();

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            ArrayList<MyProduct> temp=new ArrayList<>();

            int countChanges=0;
            for (MyProduct myProduct : orginal) {



                if(myProduct. toString().toLowerCase().contains(charSequence.toString().toLowerCase()))
                {
                    countChanges++;
                    temp.add(myProduct);
                }
            }
            if(charSequence.length()>0 ) {
                results.values = new ArrayList<>( temp);
                results.count = temp.size();
            }
            else {
                results.values = new ArrayList<>(orginal);
                results.count = orginal.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredTasks= (ArrayList<MyProduct>) filterResults.values;
            clear();
            addAll(new ArrayList<>(filteredTasks));
            notifyDataSetChanged();

        }
    }

}
