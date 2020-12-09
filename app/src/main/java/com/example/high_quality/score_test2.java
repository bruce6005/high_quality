package com.example.high_quality;

import android.app.Service;
import android.app.usage.UsageEvents;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.renderscript.Sampler;
import android.text.Html;
import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.facebook.stetho.Stetho;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class score_test2 extends AppCompatActivity {

    private final String DB_NAME = "ImageTable.db";
    private String TABLE_NAME = "newTable2";
    private final int DB_VERSION = 1;
    SQLiteDataBaseHelper mDBHelper;

    public int page=0;
    public  Boolean ending_page=Boolean.FALSE;
    public Button login_btn,btn_score_jump,btn_login_jump,btn_caculate_jump;
    public Button btn_get_all,clear_all,search_user,kill_id,add_data,pin;
    public Button meanless_vibrate;
    public TextView text_debug,no_user;
    public EditText usr_name;
    public Boolean Vibrate_switch=Boolean.FALSE,get_all_bool=Boolean.FALSE,search_bool=Boolean.FALSE;
    public RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_test2);

        call_all(); //call all button

        final Globalvariable gv= (Globalvariable)getApplicationContext();
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.addItemDecoration(new DividerItemDecoration(
//                this, DividerItemDecoration.VERTICAL));//為RecyclerView每個item畫底線
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if(!gv.getUser_global().equals("")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    search_user.setText("以 '"+ gv.getUser_global() + "' 搜尋");
                    kill_id.setText("以' " + gv.getUser_global() + "' 刪除");
                }
            });
        }



        meanless_vibrate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case  MotionEvent.ACTION_DOWN:
                    {
                        setVibrate(75);
                        meanless_vibrate.setBackground(score_test2.this.getResources().getDrawable(R.drawable.green_btn_shallow));
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {
                        setVibrate(35);
                        meanless_vibrate.setBackground(score_test2.this.getResources().getDrawable(R.drawable.green_btn));
                        page+=1;
                        if(ending_page==Boolean.TRUE)
                        {
                            page=0;
                            ending_page=Boolean.FALSE;
                        }
//
//                        btn_get_all.onTouchEvent(btn_get_all.MotionEvent.ACTION_UP);


//                        if (Vibrate_switch == Boolean.FALSE) {
//                            Vibrate_switch = Boolean.TRUE;
//                            } else {
//                            Vibrate_switch = Boolean.FALSE;
//                        }
//
//                        if (Vibrate_switch == Boolean.TRUE) {
//                            meanless_vibrate.setText("停止");
//                            Vibrator myVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//                            Random r = new Random();
//
//                            myVibrator.vibrate(new long[]{1, 300, 100, 150, 50, 150, 1500, 300, 100, 150, 50, 150, 3500, 300, 100, 150, 50, 150, 2500}, 1);
//                        }
//                        if (Vibrate_switch == Boolean.FALSE) {
//                            meanless_vibrate.setText("震動");
//                            Vibrator myVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//                            myVibrator.cancel();
//                        }
//                        break;
                    }
                }

                return  true;
            }
        });




        btn_caculate_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setClass(score_test2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_score_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(score_test2.this,"你已經在計分頁面",Toast.LENGTH_SHORT).show();
                    }
                });
//                Intent intent= new Intent();
//                intent.setClass(score_test2.this, score_test2.class);
//                startActivity(intent);
            }
        });

        btn_login_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setClass(score_test2.this, login.class);
                startActivity(intent);
            }
        });




        mDBHelper = new SQLiteDataBaseHelper(this, DB_NAME
                , null, DB_VERSION, TABLE_NAME);//初始化資料庫



        add_data.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:{
                        setVibrate(75);
                        add_data.setBackground(score_test2.this.getResources().getDrawable(R.drawable.green_btn_shallow));
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        setVibrate(35);
                        add_data.setBackground(score_test2.this.getResources().getDrawable(R.drawable.green_btn));
                        if(gv.getGm_or_not()==Boolean.TRUE) {
                            if (gv.getUser_global().equals("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(score_test2.this,"尚未登入",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (!gv.getUser_global().contains(" ") && !gv.getUser_global().contains("\n")) {
                                Calendar mCal = Calendar.getInstance();
                                CharSequence s = DateFormat.format("yyyy-MM-dd_kk:mm:ss", mCal.getTime());
                                byte[] a = new byte[0];
                                mDBHelper.addData(gv.getUser_global(),s.toString(), "6", "2","a");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(score_test2.this,gv.getUser_global() + "已增加資料",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        else
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(score_test2.this,"應該沒甚麼用會拔掉",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
                return false;
            }

        });


        kill_id.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        setVibrate(75);
                        kill_id.setBackground(score_test2.this.getResources().getDrawable(R.drawable.green_btn_shallow));
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        kill_id.setBackground(score_test2.this.getResources().getDrawable(R.drawable.green_btn));
                        setVibrate(35);
                        if(gv.getUser_global().equals(""))
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(score_test2.this,"尚未登入",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else if(get_all_bool==Boolean.TRUE && search_bool==Boolean.FALSE){

                            mDBHelper.deleteByIdEZ(gv.getUser_global());
                            ArrayList<String> arrayList = new ArrayList<>();//做陣列
                            ///////////////////////////////////////
                            final ArrayList<HashMap<String, String>> list = mDBHelper.showAll();
                            String list_open=list.toString();
                            final String[] sentence =list_open.split("[}]");
                            String[] sp1;
                            for(int i=0;i<sentence.length-1;i++) {
                                sp1 = sentence[i].split(",");
                                final String[] finalSp = sp1;
                                if (i == 0)
                                {
                                    final String username = sp1[0].substring(12);
                                    String time = sp1[2].substring(6);
                                    arrayList.add(username+"              "+ time);
                                    continue;
                                }
                                String username = sp1[1].substring(12);
                                String time = sp1[3].substring(6);
                                arrayList.add(username+"              "+ time);

                            }

                            ///////////////////////////////////////
                            MyAdapter myAdapter = new MyAdapter(arrayList);
                            recyclerView.setAdapter(myAdapter);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(score_test2.this,gv.getUser_global() +" 已全部刪除",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else if((get_all_bool==Boolean.FALSE && search_bool==Boolean.TRUE)||(get_all_bool==Boolean.FALSE && search_bool==Boolean.FALSE))
                        {
                            mDBHelper.deleteByIdEZ(gv.getUser_global());
                            ArrayList<String> arrayList = new ArrayList<>();
                            MyAdapter myAdapter = new MyAdapter(arrayList);
                            recyclerView.setAdapter(myAdapter);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(score_test2.this,gv.getUser_global() +" 已全部刪除",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
                return false;
            }
        });


        search_user.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        setVibrate(75);
                        search_user.setBackground(score_test2.this.getResources().getDrawable(R.drawable.green_btn_shallow));
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        search_bool=Boolean.TRUE;
                        get_all_bool=Boolean.FALSE;
                        setVibrate(35);

                        search_user.setBackground(score_test2.this.getResources().getDrawable(R.drawable.green_btn));
                        final ArrayList<HashMap<String, String>> list= mDBHelper.searchByUserName(gv.getUser_global());
                        String list_open=list.toString();
                        final String[] sentence =list_open.split("[}]");
                        final ArrayList <String> mylist = new ArrayList<String>();
                        String[] sp1;

                        ArrayList<String> arrayList = new ArrayList<>();//做陣列
                        for(int i=0;i<sentence.length-1;i++) {

                            sp1 = sentence[i].split(",");
                            if (i == 0)
                            {
                                final String username = sp1[0].substring(12);
                                String time = sp1[2].substring(6);
                                mylist.add("aaaa");
                                arrayList.add(username+"              "+time);
                                continue;
                            }

                            String username = sp1[1].substring(12);
                            String time = sp1[3].substring(6);
                            arrayList.add(username + "              " + time);
                        }
                        MyAdapter myAdapter = new MyAdapter(arrayList);
                        recyclerView.setAdapter(myAdapter);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(gv.getUser_global().equals(""))
                                {
                                    Toast.makeText(score_test2.this,"尚未登入",Toast.LENGTH_SHORT).show();
                                }
                                else if(mylist.toString().equals("[]"))
                                {
                                    Toast.makeText(score_test2.this,"查無資料",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(score_test2.this,"已顯示 '" +gv.getUser_global()+ "' 資料",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                return false;
            }
        });

        clear_all.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        setVibrate(75);
                        clear_all.setBackground(score_test2.this.getResources().getDrawable(R.drawable.green_btn_shallow));
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        setVibrate(35);
                        clear_all.setBackground(score_test2.this.getResources().getDrawable(R.drawable.green_btn));
                        if(gv.getGm_or_not()==Boolean.TRUE) {
                            mDBHelper.deleteAll();

                            ArrayList<String> arrayList = new ArrayList<>();//做陣列
                            MyAdapter myAdapter = new MyAdapter(arrayList);
                            recyclerView.setAdapter(myAdapter);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(score_test2.this,"已刪除資料庫全部資料",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(score_test2.this,"你不是管理者",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
                return false;
            }
        });

        btn_get_all.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        setVibrate(75);
                        btn_get_all.setBackground(score_test2.this.getResources().getDrawable(R.drawable.green_btn_shallow));
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        btn_get_all.setBackground(score_test2.this.getResources().getDrawable(R.drawable.green_btn));
                        get_all_bool=Boolean.TRUE;
                        search_bool=Boolean.FALSE;
                        setVibrate(35);


                        if(ending_page==Boolean.TRUE)
                        {
                            page=0;
                            ending_page=Boolean.FALSE;
                        }
                        final ArrayList<HashMap<String, String>> list = mDBHelper.showAll();
//                mDBHelper.showAll();
                        String list_open=list.toString();
                        final String[] sentence =list_open.split("[}]");

                        String[] sp1;

                        ArrayList<String> arrayList = new ArrayList<>();//做陣列


//                        for(int i=0;i<sentence.length-1;i++) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int print_p=page+1;
                                Toast.makeText(score_test2.this,"頁數 "+ print_p , Toast.LENGTH_SHORT).show();
                                btn_get_all.setText("頁數 "+ print_p);
                            }
                        });

                        int num_of_page=0+(page)*10;
                        int end_of_page=num_of_page+10;
                        page+=1;
                        if(sentence.length-1<end_of_page)
                        {
                            end_of_page=sentence.length-1;
                            ending_page=Boolean.TRUE;
                        }
                        for(int i=num_of_page;i<end_of_page;i++) {

                            sp1 = sentence[i].split(",");
                            final String[] finalSp = sp1;



                            if (i == 0)
                            {
                                final String username = sp1[0].substring(12);
                                String time = sp1[1].substring(6);

//                        mylist.add( username + ' ' + "精度:" +  pre + ' ' + "準度:" + acc + "\n\n");
                                arrayList.add(username+"              "+ time);
                                continue;
                            }

                            String username = sp1[1].substring(12);
                            String time = sp1[2].substring(6);
//                    mylist.add( username + ' ' + "精度:" +  pre + ' ' + "準度:" + acc + "\n\n");
//                    arrayList.add(String.valueOf(Html.fromHtml("<font color=#ff0000>" + username + "</font> "+"<font color=#ffffff>"+ time +"</font>")));
                            arrayList.add(username+"              "+ time);

                        }

                        MyAdapter myAdapter = new MyAdapter(arrayList);
                        recyclerView.setAdapter(myAdapter);
                    }
                }
                return false;
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
                        score_test2.this.finishAffinity();
                    }
                })
                .setNegativeButton("否", null)
                .show();
    }
    public void setVibrate(int time){
        Vibrator myVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        myVibrator.vibrate(time);
    }

    public void call_all()
    {
        Stetho.initializeWithDefaults(this);
        btn_get_all=(Button)findViewById(R.id.get_all);
        clear_all=(Button)findViewById(R.id.clear_all);
        search_user=(Button)findViewById(R.id.search_user);
        kill_id=(Button)findViewById(R.id.kill_id);
        add_data=(Button)findViewById(R.id.add_data);
        meanless_vibrate =(Button)findViewById(R.id.vibrate);
        btn_score_jump=(Button)findViewById(R.id.score);
        btn_caculate_jump=(Button)findViewById(R.id.caculate);
        btn_login_jump=(Button)findViewById(R.id.login_user);
        relativeLayout =(RelativeLayout)findViewById(R.id.relative_layout);
//        pin=(Button)findViewById(R.id.pin_color);
        no_user=(TextView)findViewById(R.id.no_user);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL));//為RecyclerView每個item畫底線
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();//(第一步)
        private ArrayList<String> arrayList;

        public MyAdapter(ArrayList<String> arrayList) {
            this.arrayList = arrayList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private View parent;
            private TextView tvValue;
            private Button btDelete, btGetInfo;
            private SwipeRevealLayout swipeRevealLayout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvValue = itemView.findViewById(R.id.textView);
                parent = itemView;
                btDelete = itemView.findViewById(R.id.button_Delete);
                btGetInfo = itemView.findViewById(R.id.button_Show);
                swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
            }
        }//ViewHolder

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ViewHolder(view);
        }//

//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return null;
//        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            viewBinderHelper.setOpenOnlyOne(true);//設置swipe只能有一個item被拉出
            viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(position));//綁定Layout (第三步)
            holder.tvValue.setText(arrayList.get(position));
            final Globalvariable gv= (Globalvariable)getApplicationContext();
//            final tmp_info tmp = (tmp_info)getApplicationContext();

            holder.btGetInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setVibrate(75);
                    String[] s1 = arrayList.get(position).split("              ");

                    final ArrayList<HashMap<String, String>> list = mDBHelper.searchByname_and_time(s1[0],s1[1]);
                    String list_open = list.toString();
                    final String[] sentence = list_open.split("[}]");
                    String len = String.valueOf(sentence.length);
                    gv.setAcc(s1[1]);
                    String username = null;
                    String tmp_time = null;
                    for (int i = 0; i < sentence.length - 1; i++) {

                        String[] sp1 = sentence[i].split(",");
                        if (i == 0) {
                            //0=username 1=precise 2=time 3=id 4=image 5=acc    substring(word+2)
                            username = sp1[0].substring(12);
                            String time = sp1[2].substring(6);
                            String image = sp1[4].substring(7);
                            gv.setAcc(sp1.toString());
                            if (time.equals(s1[1])) {
                                gv.setImage(image);
                                gv.setAcc("使用者:" + username +"\n" + "花費時間: " + time +"\n" +"準確: " + sp1[5].substring(10) +"\n");
                                break;
                            }

                        } else {
                            //0=' ' 1=username 2=precise 3=time 4=id 5=image 6=acc    substring(word+2)
                            username = sp1[1].substring(12);
                            String time = sp1[3].substring(6);
                            String acc = sp1[5].substring(10);
                            if (time.equals(s1[1])) {
//                                gv.setAcc(sp1[6]);
                                gv.setImage(sp1[5].substring(7));
                                gv.setAcc("使用者:" + username +"\n" + "花費時間: " + time +"\n" +"準確: " + sp1[6].substring(10) +"\n");
                                break;
                                }
                        }
                    }



//                    Toast.makeText(score_test2.this, arrayList.get(position), Toast.LENGTH_SHORT).show();
                    holder.swipeRevealLayout.close(true);//關閉已被拉出的視窗
                    Intent intent= new Intent();
                    intent.setClass(score_test2.this, imformation.class);
                    startActivity(intent);
                }
            });

            holder.btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setVibrate(75);
                    final String[] s1 = arrayList.get(position).split("              ");
                    if (gv.getUser_global().equals(s1[0])) {
                        mDBHelper.deleteByTime(s1[1]);
                        holder.swipeRevealLayout.close(true);
                        arrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, arrayList.size());
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(score_test2.this,"你不是此用戶",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            });

        }


        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}














//        meanless_vibrate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                meanless_vibrate.setBackgroundColor(Color.parseColor("#cacaff"));
//                if(Vibrate_switch==Boolean.FALSE) {
//                    Vibrate_switch=Boolean.TRUE;
//                }
//                else {
//                    Vibrate_switch=Boolean.FALSE;
//                }
//
//                if(Vibrate_switch==Boolean.TRUE) {
//                    meanless_vibrate.setText("停止");
//                    Vibrator myVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//                    Random r = new Random();
//
//                    myVibrator.vibrate(new long[]{1, 300, 100, 150, 50, 150, 1500,300, 100, 150, 50, 150,3500,300, 100, 150, 50, 150,2500}, 1);
//                }
//                if(Vibrate_switch==Boolean.FALSE)
//                {
//                    meanless_vibrate.setText("震動");
//                    Vibrator myVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//                    myVibrator.cancel();
//                }
//            }
//        });

//add_data.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        setVibrate(75);
//        if(gv.getGm_or_not()==Boolean.TRUE) {
//        if (gv.getUser_global().equals("")) {
//        runOnUiThread(new Runnable() {
//@Override
//public void run() {
//        Toast.makeText(score_test2.this,"尚未登入",Toast.LENGTH_SHORT).show();
//        }
//        });
//        } else if (!gv.getUser_global().contains(" ") && !gv.getUser_global().contains("\n")) {
//        Calendar mCal = Calendar.getInstance();
//        CharSequence s = DateFormat.format("yyyy-MM-dd_kk:mm:ss", mCal.getTime());
//        mDBHelper.addData(gv.getUser_global(),s.toString(), "6", "2");
//
//        runOnUiThread(new Runnable() {
//@Override
//public void run() {
//        Toast.makeText(score_test2.this,gv.getUser_global() + "已增加資料",Toast.LENGTH_SHORT).show();
//        }
//        });
//        }
//        }
//        else
//        {
//        runOnUiThread(new Runnable() {
//@Override
//public void run() {
//        Toast.makeText(score_test2.this,"應該沒甚麼用會拔掉",Toast.LENGTH_SHORT).show();
//        }
//        });
//        }
//
//        }
//        });

// kill_id.setOnClickListener(new View.OnClickListener() {
////            String a=usr_name.getText().toString();
//@Override
//public void onClick(View view) {
//        setVibrate(75);
//        if(gv.getUser_global().equals(""))
//        {
//        runOnUiThread(new Runnable() {
//@Override
//public void run() {
//        Toast.makeText(score_test2.this,"尚未登入",Toast.LENGTH_SHORT).show();
//        }
//        });
//        }
//        else if(get_all_bool==Boolean.TRUE && search_bool==Boolean.FALSE){
//
//        mDBHelper.deleteByIdEZ(gv.getUser_global());
//        ArrayList<String> arrayList = new ArrayList<>();//做陣列
/////////////////////////////////////////
//final ArrayList<HashMap<String, String>> list = mDBHelper.showAll();
//        String list_open=list.toString();
//final String[] sentence =list_open.split("[}]");
//        String[] sp1;
//        for(int i=0;i<sentence.length-1;i++) {
//        sp1 = sentence[i].split(",");
//final String[] finalSp = sp1;
//        if (i == 0)
//        {
//final String username = sp1[0].substring(12);
//        String time = sp1[2].substring(6);
//        arrayList.add(username+"              "+ time);
//        continue;
//        }
//        String username = sp1[1].substring(12);
//        String time = sp1[3].substring(6);
//        arrayList.add(username+"              "+ time);
//
//        }
//
//        ///////////////////////////////////////
//        MyAdapter myAdapter = new MyAdapter(arrayList);
//        recyclerView.setAdapter(myAdapter);
//
//        runOnUiThread(new Runnable() {
//@Override
//public void run() {
//        Toast.makeText(score_test2.this,gv.getUser_global() +" 已全部刪除",Toast.LENGTH_SHORT).show();
//        }
//        });
//        }
//        else if((get_all_bool==Boolean.FALSE && search_bool==Boolean.TRUE)||(get_all_bool==Boolean.FALSE && search_bool==Boolean.FALSE))
//        {
//        mDBHelper.deleteByIdEZ(gv.getUser_global());
//        ArrayList<String> arrayList = new ArrayList<>();
//        MyAdapter myAdapter = new MyAdapter(arrayList);
//        recyclerView.setAdapter(myAdapter);
//        runOnUiThread(new Runnable() {
//@Override
//public void run() {
//        Toast.makeText(score_test2.this,gv.getUser_global() +" 已全部刪除",Toast.LENGTH_SHORT).show();
//        }
//        });
//        }
//
//        }
//        });

//            search_user.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        search_bool=Boolean.TRUE;
//        get_all_bool=Boolean.FALSE;
//        setVibrate(75);
//
//final ArrayList<HashMap<String, String>> list= mDBHelper.searchByUserName(gv.getUser_global());
//        String list_open=list.toString();
//final String[] sentence =list_open.split("[}]");
//final ArrayList <String> mylist = new ArrayList<String>();
//        String[] sp1;
//
//        ArrayList<String> arrayList = new ArrayList<>();//做陣列
//        for(int i=0;i<sentence.length-1;i++) {
//
//        sp1 = sentence[i].split(",");
//        if (i == 0)
//        {
//final String username = sp1[0].substring(12);
//        String time = sp1[2].substring(6);
//        mylist.add("aaaa");
//        arrayList.add(username+"              "+time);
//        continue;
//        }
//
//        String username = sp1[1].substring(12);
//        String time = sp1[3].substring(6);
//        arrayList.add(username + "              " + time);
//        }
//        MyAdapter myAdapter = new MyAdapter(arrayList);
//        recyclerView.setAdapter(myAdapter);
//
//        runOnUiThread(new Runnable() {
//@Override
//public void run() {
//        if(gv.getUser_global().equals(""))
//        {
//        Toast.makeText(score_test2.this,"尚未登入",Toast.LENGTH_SHORT).show();
//        }
//        else if(mylist.toString().equals("[]"))
//        {
//        Toast.makeText(score_test2.this,"查無資料",Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//        Toast.makeText(score_test2.this,"已顯示 '" +gv.getUser_global()+ "' 資料",Toast.LENGTH_SHORT).show();
//        }
//        }
//        });
//        }
//        });

// clear_all.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        setVibrate(75);
//
//        if(gv.getGm_or_not()==Boolean.TRUE) {
//        mDBHelper.deleteAll();
//
//        ArrayList<String> arrayList = new ArrayList<>();//做陣列
//        MyAdapter myAdapter = new MyAdapter(arrayList);
//        recyclerView.setAdapter(myAdapter);
//        runOnUiThread(new Runnable() {
//@Override
//public void run() {
//        Toast.makeText(score_test2.this,"已刪除資料庫全部資料",Toast.LENGTH_SHORT).show();
//        }
//        });
//        }
//        else
//        {
//        runOnUiThread(new Runnable() {
//@Override
//public void run() {
//        Toast.makeText(score_test2.this,"你不是管理者",Toast.LENGTH_SHORT).show();
//        }
//        });
//        }
//        }
//        });

//btn_get_all.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        get_all_bool=Boolean.TRUE;
//        search_bool=Boolean.FALSE;
//        setVibrate(75);
//final ArrayList<HashMap<String, String>> list = mDBHelper.showAll();
////                mDBHelper.showAll();
//        String list_open=list.toString();
//final String[] sentence =list_open.split("[}]");
//
//        String[] sp1;
//
//        ArrayList<String> arrayList = new ArrayList<>();//做陣列
//
//
//        for(int i=0;i<sentence.length-1;i++) {
//
//        sp1 = sentence[i].split(",");
//final String[] finalSp = sp1;
//
//
//
//        if (i == 0)
//        {
//final String username = sp1[0].substring(12);
//        String time = sp1[2].substring(6);
//
////                        mylist.add( username + ' ' + "精度:" +  pre + ' ' + "準度:" + acc + "\n\n");
//        arrayList.add(username+"              "+ time);
//        continue;
//        }
//
//        String username = sp1[1].substring(12);
//        String time = sp1[3].substring(6);
////                    mylist.add( username + ' ' + "精度:" +  pre + ' ' + "準度:" + acc + "\n\n");
////                    arrayList.add(String.valueOf(Html.fromHtml("<font color=#ff0000>" + username + "</font> "+"<font color=#ffffff>"+ time +"</font>")));
//        arrayList.add(username+"              "+ time);
//
//        }
//
//        MyAdapter myAdapter = new MyAdapter(arrayList);
//        recyclerView.setAdapter(myAdapter);
//
//        }
//        });