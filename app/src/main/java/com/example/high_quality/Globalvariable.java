package com.example.high_quality;

import android.app.Application;

public class Globalvariable extends Application {
    private String user_global="";
    private  String password_global="";
    private Boolean login_or_not=Boolean.FALSE;
    private  Boolean gm_or_not = Boolean.FALSE;
    private Boolean pin_or_not = Boolean.FALSE;

    private int test_len=0;

    private  String acc="";
    private  String pre="";
    private String  image="";

    public  void setPin_or_not_True(){this.pin_or_not=Boolean.TRUE;}
    public void setPin_or_not_False(){
        this.pin_or_not=Boolean.FALSE;
    }
    public  void  setUser_global(String user)
    {
        this.user_global = user;
    }
    public void  setPassword_global(String password)
    {
        this.password_global = password;
    }
    public void  setLogin_or_not_True()
    {
        this.login_or_not=Boolean.TRUE;
    }
    public void  setLogin_or_not_False()
    {
        this.login_or_not=Boolean.FALSE;
    }
    public  void setGm_True()
    {
        this.gm_or_not=Boolean.TRUE;
    }
    public  void setGm_False()
    {
        this.gm_or_not=Boolean.FALSE;
    }
    public void setAcc(String tmpacc)
    {
        this.acc=tmpacc;
    }
    public  void setImage(String image){this.image = image;}
    public void setPre(String tmppre)
    {
        this.pre=tmppre;
    }
    public  void  setTest_len(int len){this.test_len=len;}



    public  String getUser_global()
    {
        return user_global;
    }


    public  String getPassword_global()
    {
        return password_global;
    }
    public Boolean getPin_or_not()
    {
        return pin_or_not;
    }
    public Boolean getLogin_or_not()
    {
        return login_or_not;
    }
    public  Boolean getGm_or_not()
    {
        return gm_or_not;
    }
    public String rt_acc()
    {
        return acc;
    }
    public String rt_pre()
    {
        return pre;
    }
    public String rt_image(){return image;}
    public int rt_len(){return test_len;}

}
