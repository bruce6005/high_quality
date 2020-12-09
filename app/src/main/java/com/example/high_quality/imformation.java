package com.example.high_quality;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.urlconnect.R;

public class imformation extends AppCompatActivity {

    public TextView acc;
    public ImageView imageqwer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imformation);

        final Globalvariable gv=(Globalvariable) getApplicationContext();
        acc=(TextView)findViewById(R.id.acc);
        imageqwer=(ImageView)findViewById(R.id.image_bitmap);
        final String a=gv.rt_image();

        final Bitmap screen = encode_string_to_bitmap(a);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(screen!=null) {

                    imageqwer.setImageBitmap(screen);
                }
                acc.setText(gv.rt_acc());
//                acc.setText(gv.rt_acc());
            }
        });

    }

    public Bitmap encode_string_to_bitmap(String str)
    {
        String str_cvrt = str;
        byte[] encodeByte= Base64.decode(str_cvrt,Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
        return bitmap;
    }

}