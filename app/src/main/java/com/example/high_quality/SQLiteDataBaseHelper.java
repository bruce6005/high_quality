package com.example.high_quality;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.strictmode.SqliteObjectLeakedViolation;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {
    String Tablename;

    public SQLiteDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version,String TableName) {
        super(context, name, factory, version);
        this.Tablename = TableName;
    }

    public void checkTable(){
        Cursor cursor = getWritableDatabase().rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + Tablename + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0)
                getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + Tablename + "( " +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "User_name TEXT,"+
                        "Time TEXT," +
                        "Accurate TEXT, " +
                        "Precise TEXT," +
                        "Image TEXT" +
                        ");");
            cursor.close();
        }
    }

    public ArrayList<String> getTables(){
        Cursor cursor = getWritableDatabase().rawQuery(
                "select DISTINCT tbl_name from sqlite_master", null);
        ArrayList<String> tables = new ArrayList<>();
        while (cursor.moveToNext()){
            String getTab = new String (cursor.getBlob(0));
            if (getTab.contains("android_metadata")){}
            else if (getTab.contains("sqlite_sequence")){}
            else tables.add(getTab.substring(0,getTab.length()-1));

        }
        return tables;
    }

    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("delete from " + Tablename);
    }

    public void deleteByIdEZ(String get_user_name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + Tablename +" where User_name= '" + get_user_name +"'" );
//        db.delete(Tablename,"User_name" + "=" + get_user_name,null);
    }

    public  void deleteByTime(String get_usr_time){
        SQLiteDatabase db =getWritableDatabase();
        db.execSQL("delete from " + Tablename + " where Time= '" + get_usr_time + "'");
    }

    public ArrayList<HashMap<String, String>> showAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + Tablename, null);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        while (c.moveToNext()) {
            HashMap<String, String> hashMap = new HashMap<>();

            String id = c.getString(0);
            String user_name = c.getString(1);
            String time = c.getString(2);
//            String accurate = c.getString(3);
//            String precise = c.getString(4);
//            String image = c.getString(5);

            hashMap.put("id", id);
            hashMap.put("User_name", user_name);
            hashMap.put("Time",time);
//            hashMap.put("Accurate", accurate);
//            hashMap.put("Precise", precise);
//            hashMap.put("Image",image);
            arrayList.add(hashMap);
        }
        return arrayList;

    }

    public void addData(String user_name,String time,String accurate, String precise,String image) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User_name", user_name);
        values.put("Time",time);
        values.put("Accurate", accurate);
        values.put("Precise", precise);
        values.put("image",image);
        db.insert(Tablename, null, values);
    }
    public ArrayList<HashMap<String, String>> searchByname_and_time(String get_user_name,String get_user_time) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + Tablename
                + " WHERE User_name =" + "'" + get_user_name + "'" +" AND Time = " + "'" + get_user_time + "'", null);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        while (c.moveToNext()) {
            HashMap<String, String> hashMap = new HashMap<>();

            String id = c.getString(0);
            String user_name = c.getString(1);
            String time = c.getString(2);
            String accurate = c.getString(3);
            String precise = c.getString(4);
            String image = c.getString(5);

            hashMap.put("id", id);
            hashMap.put("User_name", user_name);
            hashMap.put("Time",time);
            hashMap.put("Accurate", accurate);
            hashMap.put("Precise", precise);
            hashMap.put("Image",image);
            arrayList.add(hashMap);
        }
        return arrayList;
    }
    public ArrayList<HashMap<String, String>> searchByUserName(String get_user_name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + Tablename
                + " WHERE User_name =" + "'" + get_user_name + "'", null);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        while (c.moveToNext()) {
            HashMap<String, String> hashMap = new HashMap<>();

            String id = c.getString(0);
            String user_name = c.getString(1);
            String time = c.getString(2);
            String accurate = c.getString(3);
            String precise = c.getString(4);
//            String image = c.getString(5);

            hashMap.put("id", id);
            hashMap.put("User_name", user_name);
            hashMap.put("Time",time);
            hashMap.put("Accurate", accurate);
            hashMap.put("Precise", precise);
//            hashMap.put("Image",image);
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    public ArrayList<HashMap<String, String>> searchByTime(String search_Time) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + Tablename
                + " WHERE Time =" + "'" + search_Time + "'", null);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        while (c.moveToNext()) {
            HashMap<String, String> hashMap = new HashMap<>();

            String id = c.getString(0);
            String user_name = c.getString(1);
            String time = c.getString(2);
            String accurate = c.getString(3);
            String precise = c.getString(4);
            String image = c.getString(5);

            hashMap.put("id", id);
            hashMap.put("User_name", user_name);
            hashMap.put("Time",time);
            hashMap.put("Accurate", accurate);
            hashMap.put("Precise", precise);
            hashMap.put("Image",image);
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQLTable = "CREATE TABLE IF NOT EXISTS " + Tablename + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "User_name TEXT,"+
                "Time TEXT," +
                "Accurate TEXT, " +
                "Precise TEXT," +
                "Image TEXT"+
                ");";
        sqLiteDatabase.execSQL(SQLTable);
        sqLiteDatabase.execSQL(SQLTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE" + Tablename;
        sqLiteDatabase.execSQL(SQL);
    }
}
