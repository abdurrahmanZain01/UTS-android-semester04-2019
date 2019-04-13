package com.zain.uts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BiodataKos.db";
    private static final int DATABASE_VERSION = 1;

//    konstruktor
    public DataHelper(Context context ){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
//        buat tabel
        String sql = "Create table biodata(no integer primary key,nama text null,jk text null, alamat text null );";
        Log.d("Data","onCreate" + sql);
        db.execSQL(sql);
    }

    @Override
    public  void onUpgrade(SQLiteDatabase arg0,int arg1, int arg2){

    }
}
