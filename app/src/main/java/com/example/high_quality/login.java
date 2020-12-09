package com.example.high_quality;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import com.example.urlconnect.R;



public class login extends AppCompatActivity {

    public EditText usr,pswd;
    public Button login_btn,btn_score_jump,btn_login_jump,btn_caculate_jump,register_btn;
    public Boolean logout_or_not;
    public  Boolean login_or_not;
    public Boolean register_or_not=Boolean.FALSE;
    public DrawerLayout myDrawer;
    public ActionBarDrawerToggle myDrawer_toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_or_not=Boolean.FALSE;
        usr = (EditText)findViewById(R.id.user_edit);
        pswd = (EditText)findViewById(R.id.password_edit);
        login_btn = (Button)findViewById(R.id.btn_login);
        register_btn = (Button)findViewById(R.id.btn_register);
        btn_score_jump=(Button)findViewById(R.id.score);
        btn_caculate_jump=(Button)findViewById(R.id.caculate);
        btn_login_jump=(Button)findViewById(R.id.login_user);
        myDrawer=(DrawerLayout)findViewById(R.id.my_drawer_layout);


        final Globalvariable gv=(Globalvariable)getApplicationContext();

        if(gv.getUser_global().equals(""))
        {
            logout_or_not=Boolean.FALSE;
        }
        else
        {
            logout_or_not=Boolean.TRUE;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    login_btn.setText("登出");
                    register_btn.setVisibility(View.INVISIBLE);
                }
            });
        }

        myDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                usr.setEnabled(Boolean.FALSE);
                pswd.setEnabled(Boolean.FALSE);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                usr.setEnabled(Boolean.TRUE);
                pswd.setEnabled(Boolean.TRUE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        btn_caculate_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setClass(login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_score_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setClass(login.this, score_test2.class);
                startActivity(intent);
            }
        });

        btn_login_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(login.this,"你已經在登入頁面",Toast.LENGTH_SHORT).show();
                    }
                });
//                Intent intent= new Intent();
//                intent.setClass(login.this, login.class);
//                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVibrate(75);
                if(logout_or_not==Boolean.TRUE)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this,gv.getUser_global() + " 登出成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                    gv.setUser_global("");
                    gv.setLogin_or_not_False();
                    gv.setPassword_global("");
                    login_btn.setText("登入");
                    logout_or_not=Boolean.FALSE;
                    register_btn.setVisibility(View.VISIBLE);
                }
                else if(usr.getText().toString().equals("") || pswd.getText().toString().equals(""))
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this,"請填入帳號或密碼",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if(usr.getText().toString().contains(" ") || pswd.getText().toString().contains(" ") || usr.getText().toString().contains("\n") || pswd.getText().toString().contains("\n"))
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this,"帳號密碼不能有空白或換行",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    connect_login();
                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVibrate(75);
                register_or_not=Boolean.TRUE;
                if(logout_or_not==Boolean.TRUE)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this,gv.getUser_global() + " 登出成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                    logout_or_not=Boolean.FALSE;
                    gv.setUser_global("");
                    gv.setLogin_or_not_False();
                    gv.setPassword_global("");
                    login_btn.setText("登入");

                    register_btn.setVisibility(View.VISIBLE);
                }
                else if(usr.getText().toString().equals("") || pswd.getText().toString().equals(""))
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this,"請填入帳號或密碼",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if(usr.getText().toString().contains(" ") || pswd.getText().toString().contains(" ") || usr.getText().toString().contains("\n") || pswd.getText().toString().contains("\n"))
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this,"帳號密碼不能有空白或換行",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    connect_register();
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
                        login.this.finishAffinity();
                    }
                })
                .setNegativeButton("否", null)
                .show();
    }

    public void connect_login()
    {
        final long startTime = System.currentTimeMillis()/1000;

        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(30, TimeUnit.MINUTES).readTimeout(30,TimeUnit.MINUTES).build();

        FormBody formBody = new FormBody.Builder()
                .add("user_name",usr.getText().toString())
                .add("password",pswd.getText().toString())
                .build();
        final Request request = new Request.Builder()
                .url("http://140.123.102.161:9999")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
                         @Override
                         public void onFailure(@NotNull Call call, @NotNull IOException e) {

                         }

                         @Override
                         public void onResponse(Call call, final Response response) throws IOException {
                             final String resStr = response.body().string();
                             Globalvariable gv = (Globalvariable)getApplicationContext();
                             String success = "login success";
                             String new_usr="new user added!";
                             String fail ="password error";
                             String fail_no_user="none user";
                             String gm = "gm";

                             if (resStr.equals(gm))
                             {
                                 logout_or_not=Boolean.TRUE;
                                 gv.setLogin_or_not_True();
                                 gv.setUser_global(usr.getText().toString());
                                 gv.setPassword_global(pswd.getText().toString());
                                 gv.setGm_True();
                                 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         login_btn.setText("登出");
                                         register_btn.setVisibility(View.INVISIBLE);
                                         Toast.makeText(login.this,"水喔",Toast.LENGTH_SHORT).show();
                                     }
                                 });
                             }
                             else if(resStr.equals(success) )
                             {
                                 logout_or_not=Boolean.TRUE;
                                 gv.setLogin_or_not_True();
                                 gv.setUser_global(usr.getText().toString());
                                 gv.setPassword_global(pswd.getText().toString());
                                 gv.setGm_False();
                                 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         login_btn.setText("登出");
                                         register_btn.setVisibility(View.INVISIBLE);
                                         Toast.makeText(login.this,"登入成功",Toast.LENGTH_SHORT).show();
                                     }
                                 });
                             }
//                             else if(resStr.equals(new_usr) )
//                             {
//                                 gv.setLogin_or_not_True();
//                                 gv.setUser_global(usr.getText().toString());
//                                 gv.setPassword_global(pswd.getText().toString());
//                                 register_or_not=Boolean.FALSE;
//                                 runOnUiThread(new Runnable() {
//                                     @Override
//                                     public void run() {
//                                         login_btn.setText("新用戶註冊完成，已登入");
//                                     }
//                                 });
//                             }

                             else if(resStr.equals(fail))
                             {
                                 gv.setLogin_or_not_False();
                                 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         Toast.makeText(login.this,"密碼錯誤",Toast.LENGTH_SHORT).show();

                                     }
                                 });
                             }
                             else if (resStr.equals(fail_no_user))
                             {
                                 gv.setLogin_or_not_False();
                                 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         Toast.makeText(login.this,"無此帳號",Toast.LENGTH_SHORT).show();
                                     }
                                 });
                             }
                             else
                             {
                                 gv.setLogin_or_not_False();
                                 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         Toast.makeText(login.this,"我也不知道為什麼跟我們講一下",Toast.LENGTH_SHORT).show();
                                     }
                                 });
                             }

                         }
                     });
    }


    public void connect_register()
    {
        final long startTime = System.currentTimeMillis()/1000;

        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(30, TimeUnit.MINUTES).readTimeout(30,TimeUnit.MINUTES).build();

        FormBody formBody = new FormBody.Builder()
                .add("user_name",usr.getText().toString())
                .add("password",pswd.getText().toString())
                .build();
        final Request request = new Request.Builder()
                .url("http://140.123.102.161:9910")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String resStr = response.body().string();
                Globalvariable gv = (Globalvariable)getApplicationContext();
                String success = "login success";
                String new_usr="new user added!";
                String fail ="user exist";
//                if(resStr.equals(success) )
//                {
//                    gv.setLogin_or_not_True();
//                    gv.setUser_global(usr.getText().toString());
//                    gv.setPassword_global(pswd.getText().toString());
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            login_btn.setText("登入成功");
//                        }
//                    });
//                }
                if(resStr.equals(new_usr) )
                {
                    logout_or_not=Boolean.TRUE;
                    gv.setLogin_or_not_True();
                    gv.setUser_global(usr.getText().toString());
                    gv.setPassword_global(pswd.getText().toString());
                    register_or_not=Boolean.FALSE;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            login_btn.setText("登出");
                            register_btn.setVisibility(View.INVISIBLE);
                            Toast.makeText(login.this,"新用戶註冊完成，已登入",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                else if(resStr.equals(fail))
                {
                    gv.setLogin_or_not_False();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this,"用戶已存在",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    gv.setLogin_or_not_False();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this,"我也不知道為什麼跟我們講一下",Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });
    }



    public void setVibrate(int time){
        Vibrator myVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        myVibrator.vibrate(time);
    }

}