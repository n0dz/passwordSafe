package com.nodz.datasafe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nodz.datasafe.Model.InfoModel;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {

    final static String DBNAME = "USERINFO.db";
    final static int DBVERSION = 1;

    final static String TABLE_NAME = "INFO";
    final static String id = "id";
    final static String APP_NAME = "Appname";
    final static String USER_NAME = "Username";
    final static String PASS = "Password";


    public DBhelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (id Integer Primary Key Autoincrement, Appname Text,Username Text, Password Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String appName, String username, String password) {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(APP_NAME,appName);
        cv.put(USER_NAME,username);
        cv.put(PASS,password);

        long res = db.insert(TABLE_NAME, null, cv);
        Log.i("DATABASE QUERY RESPONSE",String.valueOf(res));

        if(res == -1)
            return false;

        return true;
    }

    public ArrayList<InfoModel> getData(){
        ArrayList<InfoModel> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select id , Appname , Username , Password from "+TABLE_NAME,null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                InfoModel model = new InfoModel();
                model.setAppid(cursor.getInt(0)+"");
                model.setAppname(cursor.getString(1));
                model.setUsername(cursor.getString(2));
                model.setPassword(cursor.getString(3));
                data.add(model);
            }
        }
        cursor.close();
        db.close();
        return data;
    }

    public void deleteData(String appname,String pass){
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, "Appname=? and Password=?", new String[]{appname,pass});
        db.close();
    }

    public Boolean checkAppExists(String appname) {

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from "+TABLE_NAME+" where Appname = ? ", new String[] {appname});

        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }




}
