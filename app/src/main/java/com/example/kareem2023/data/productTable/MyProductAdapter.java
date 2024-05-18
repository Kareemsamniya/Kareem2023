package com.example.kareem2023.data.productTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kareem2023.R;

public class MyProductAdapter extends ArrayAdapter<MyProduct>
{
    //המזהה של קובץ עיצוב הפריט
    private final int itemLayout;
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

        vitem= LayoutInflater.from(getContext()).inflate(R.layout.myproduct_item_layout,parent,false);
        //קבלת הפניות לרכיבים בקובץ העיצוב
        ImageView imageView=vitem.findViewById(R.id.imgVitm);
        TextView tvitmName=vitem.findViewById(R.id.tvitmName);
        TextView tvitmCompany=vitem.findViewById(R.id.tvitmCompany);
        TextView tvitmBarcode=vitem.findViewById(R.id.tvitmBarcode);
        TextView tvitmAlergies=vitem.findViewById(R.id.tvitmAlergies);
        ImageButton imgBtnDelete=vitem.findViewById(R.id.imgBtnDelete);
        ImageButton imgBtnEdit=vitem.findViewById(R.id.imgBtnEdit);


        //קבלת הנתון (עצם) הנוכחי
        MyProduct current=getItem(position);
        //הצגת הנתונים על שדות הרכיב הגרפי
        tvitmName.setText(current.getProductName());
        tvitmCompany.setText(current.getCompanyName());
        tvitmBarcode.setText(current.getProductName());
        tvitmAlergies.setText(current.getAlergyName());

        return vitem;
    }



}


