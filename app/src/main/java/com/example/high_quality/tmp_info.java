package com.example.high_quality;

import android.app.Application;

public class tmp_info extends Application {
    private  String acc="";
    private  String pre="";
    public void setAcc(String tmpacc)
    {
        this.acc=tmpacc;
    }
    public void setPre(String tmppre)
    {
        this.pre=tmppre;
    }

    public String rt_acc()
    {
        return acc;
    }
    public String rt_pre()
    {
        return pre;
    }
}
