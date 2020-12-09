package com.example.high_quality;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.facebook.stetho.Stetho;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
{
    public String uid;
    private ImageView img_view;
    private Uri imageuri;
    private Button btn,db_btn;
    private  Button btn_camera,btn_login,btn_caculate_jump;
    private  Button btn_album,btn_caculate;
    private  Button btn_score_jump,pin;
    private static final String TAG = "MainActivity";
    private  TextView qq,no_user;
    private int CAMERA_RE=100;
    private  int ALBUM_RE=200;
    private int dont_post=0;
    private Bitmap photo;
    private String mPath="";
    private  String currentPhotoPath;
    private Uri photoUri;
    public LinearLayout Linear_main;
    public Toolbar tool_main;
//    private EditText usr_name;

    private final String DB_NAME = "ImageTable.db";
    private String TABLE_NAME = "newTable2";
    private final int DB_VERSION = 1;
    public Boolean running=Boolean.FALSE;
    SQLiteDataBaseHelper mDBHelper;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();//取得所有資料
    ArrayList<HashMap<String, String>> getNowArray = new ArrayList<>();//取得被選中的項目資料

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
//        mDBHelper = new SQLiteDataBaseHelper(this, DB_NAME
//                , null, DB_VERSION, TABLE_NAME);//初始化資料庫
//        mDBHelper.addData("pon","200","345678");

//        toolbar.setTitleTextColor
        camera_permmission();

        no_user=(TextView)findViewById(R.id.no_user);
        btn_camera=(Button)findViewById(R.id.btn_camera);
        btn=(Button)findViewById(R.id.btn_url);
        btn_album = (Button)findViewById(R.id.btn_album);
        img_view=(ImageView)findViewById(R.id.imageview);
        qq=(TextView)findViewById(R.id.test_text);
        btn_score_jump=(Button)findViewById(R.id.score);
        btn_caculate_jump=(Button)findViewById(R.id.caculate);
        btn_login=(Button)findViewById(R.id.login_user);
//        pin=(Button)findViewById(R.id.pin);
        Linear_main=(LinearLayout)findViewById(R.id.Linear_main);
//        tool_main=(Toolbar)findViewById(R.id.tool_main);

        final Globalvariable gv=(Globalvariable)getApplicationContext();
        gv.setLogin_or_not_False();

        stopRunning();

        mDBHelper = new SQLiteDataBaseHelper(this, DB_NAME
                , null, DB_VERSION, TABLE_NAME);//初始化資料庫


        if(!gv.getUser_global().equals(""))
        {
            gv.setLogin_or_not_True();
            if(gv.getUser_global().equals("pon")){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qq.setText("你4管理者");
                    }
                });
            }
            else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qq.setText("歡迎 " + gv.getUser_global());
                    }
                });
            }
        }

//        pin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(gv.getPin_or_not()==Boolean.TRUE)
//                {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            pin.setText("品澤");
//                        }
//                    });
//
//                    gv.setPin_or_not_False();
////                    tool_main.setBackgroundColor(Color.parseColor("#99d1d6"));
//                    no_user.setBackgroundColor(Color.parseColor("#99d1d6"));
//                }
//                else
//                {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            pin.setText("子程");
//                        }
//                    });
//                    gv.setPin_or_not_True();
////                    tool_main.setBackgroundColor(Color.parseColor("#b00020"));
//                    no_user.setBackgroundColor(Color.parseColor("#b00020"));
//                }
//            }
//        });

        btn_caculate_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"你已經在上傳頁面",Toast.LENGTH_SHORT).show();
                    }
                });
//                Intent intent= new Intent();
//                intent.setClass(MainActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });

        btn_score_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setClass(MainActivity.this, score_test2.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setClass(MainActivity.this, login.class);
                startActivity(intent);
            }
        });

        btn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVibrate(75);
                camera_permmission();
                op_album();
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVibrate(75);
                op_camera();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVibrate(75);
                if(photo!=null && dont_post==0 && gv.getLogin_or_not()==Boolean.TRUE)
                {
                    dont_post=1;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            qq.setText("連線中...");
                        }
                    });
                    startRunning();
                    connect();
                }

                else if(photo==null)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            qq.setText("沒有要上傳的照片");
                        }
                    });

                }
                else if(photo!=null&&dont_post==1)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            qq.setText("請等待上一筆資料回傳");
                        }
                    });
                }
                else if(gv.getLogin_or_not()==Boolean.FALSE)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            qq.setText("請先登入");
                        }
                    });
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("是否要退出?")
                .setCancelable(false)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finishAffinity();
                    }
                })
                .setNegativeButton("否", null)
                .show();
    }


    private void camera_permmission()
    {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){}
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
    }





    private void op_album()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,ALBUM_RE);
    }



    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public void op_camera()
    {
        Intent pic =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pic.resolveActivity(getPackageManager()) != null)
        {
            File photofile=null;
            try
            {
                photofile=createImageFile();
            }
            catch (IOException e)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qq.setText("get photo error");
//                        btn.setText(result);
                    }
                });
            }

            if(photofile!=null)
            {
                photoUri = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photofile);

                pic.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(pic,CAMERA_RE);


            }
            else
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qq.setText("photofile is null");
//                        btn.setText(result);
                    }
                });
            }

        }


    }



    public void connect()
    {

//        qq_thread.start();
        final long startTime = System.currentTimeMillis()/1000;

        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(30,TimeUnit.MINUTES).readTimeout(30,TimeUnit.MINUTES).build();


        byte[] to_post=encode_bitmap_to_byte(photo);
        final MediaType MEDIA_TYPE_JPEG=MediaType.parse("image/jpeg");
        ByteArrayOutputStream b=new ByteArrayOutputStream();

        MultipartBody body =new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("usrfile","pic.jpg",RequestBody.create(to_post,MediaType.parse("image/jpeg")))
                .build();
        final Request request = new Request.Builder()
                .url("http://140.123.102.161:9874")
                .post(body)
                .build();

        Call call = client.newCall(request);

        startRunning();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                final Globalvariable gv =(Globalvariable)getApplicationContext();
                String resStr = response.body().string();
                JSONObject json = null;
                try {
                    Calendar mCal = Calendar.getInstance();
                    CharSequence s = DateFormat.format("yyyy-MM-dd_kk:mm:ss", mCal.getTime());
                    json = new JSONObject(resStr);

                    final JSONObject finalJson = json;
                    String image = json.getString("image");
                    String acc = json.getString("acc");
                    String pre = json.getString("pre");
                    String rotate = json.getString("rotate");


                    final Bitmap screen = encode_string_to_bitmap(image);


                    if(acc.equals("-1") || pre.equals("-1"))
                    {
                        acc="nan";
                        pre="nan";
                    }

                    byte[] a=encode_bitmap_to_byte(screen);



                    long endTime = System.currentTimeMillis()/1000;
                    final String MethodeDuration = Long.toString(endTime - startTime);
//                    qq_thread.;
//                    qq_thread.interrupt();
//                    stopRunning();

                    if (rotate.equals("true")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stopRunning();
                                img_view.setImageBitmap(screen);
//                            String len1= String.valueOf(image.length());
//                            qq.setText(image.substring(image.length()-15));
//                            gv.setTest_len(image.length());
//                            qq.setText(len1);
                                qq.setText("連線完成!資料已上傳 花費" + MethodeDuration + "秒");
                                dont_post = 0;
                            }
                        });
                    }
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stopRunning();
                                img_view.setImageBitmap(screen);
                                qq.setText("連線完成!但線條資訊不足無法轉正 花費" + MethodeDuration + "秒");
                                dont_post = 0;
                            }
                        });
                    }

                    image=compress_string(image);
                    mDBHelper.addData(gv.getUser_global(), s.toString(), acc, pre,image);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dont_post=0;







                //回傳圖片
//                // 連線成功
//                long endTime = System.currentTimeMillis()/1000;
//
//                final String result = response.body().toString();
//                final Bitmap screen = encode_string_to_bitmap(result);
//
//                InputStream inputStream = response.body().byteStream();
//                final Bitmap bitmap2=BitmapFactory.decodeStream(inputStream);
//                final String MethodeDuration = Long.toString(endTime - startTime);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        img_view.setImageBitmap(bitmap2);
//                        qq.setText("connect success. Time consume: " + MethodeDuration + "second");
//                    }
//                });
                ;

            }

            @Override
            public void onFailure(Call call, IOException e)
            {
                final String ex = e.getMessage();
                // 連線失敗
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(MainActivity.this,ex.toString(),Toast.LENGTH_SHORT).show();
                        qq.setText(ex.toString());
                    }
                });
                dont_post=0;
            }
        });
    }

    Thread qq_thread = new Thread() {

        @Override
        public void  run() {

            try {

                String wait_connect = "Start connect please wait.";

                while (running && !qq_thread.isInterrupted()) {
                    wait_connect = wait_connect + ".";
                    final String text_print = wait_connect;
                    Thread.sleep(500);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            qq.setText(text_print);
                        }
                    });


                }
            } catch (InterruptedException e) {
            }catch (IllegalThreadStateException e){
            }
        }
    };

    public  void startRunning(){
        running = Boolean.TRUE;
    }
    public void stopRunning(){
        running = Boolean.FALSE;
    }

    public byte[] encode_bitmap_to_byte(Bitmap photo)
    {
        Bitmap im_encode=photo;
        ByteArrayOutputStream b=new ByteArrayOutputStream();
        im_encode.compress(Bitmap.CompressFormat.JPEG,100,b);
        byte[] b2=b.toByteArray();
        return b2;
    }
    public Bitmap encode_string_to_bitmap(String str)
    {
        String str_cvrt = str;
        byte[] encodeByte= Base64.decode(str_cvrt,Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
        return bitmap;
    }
    public String encode_bitmap_to_string(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    public String compress_string(String be_cmp){
        Bitmap B2=encode_string_to_bitmap(be_cmp);

        Matrix matrix = new Matrix();
//        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(B2, (int)(B2.getWidth()*0.5), (int)(B2.getHeight()*0.5), true);

        Bitmap out = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(),scaledBitmap.getHeight(), matrix, true);
        return encode_bitmap_to_string(out);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode==CAMERA_RE && resultCode==RESULT_OK)
        {
            int photo_thre;

            if(data==null)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        qq.setText("etf");
                    }
                });
            }
            try {
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                Matrix matrix = new Matrix();

                matrix.postRotate(90);

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(photo, (int)(photo.getWidth()*0.2), (int)(photo.getHeight()*0.2), true);
                photo = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(),scaledBitmap.getHeight(), matrix, true);
                img_view.setImageBitmap(photo);
//                photo=scaledBitmap;
//                img_view.setImageBitmap(scaledBitmap);
            } catch (IOException e) {

            }
        }
        else if(requestCode==ALBUM_RE && resultCode==RESULT_OK)
        {
            Uri uri = data.getData();
            try {
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(photo, (int)(photo.getWidth()*0.2), (int)(photo.getHeight()*0.2), true);

                photo = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(),scaledBitmap.getHeight(), matrix, true);
//                photo= scaledBitmap;
                img_view.setImageBitmap(photo);

            } catch (IOException e) {}
        }
    }

    public void setVibrate(int time){
        Vibrator myVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        myVibrator.vibrate(time);
    }


}