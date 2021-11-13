package com.example.littledinosaur;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class UserDataBase extends SQLiteOpenHelper {
    //    建表语句
    private static final String Creat_db = "create table User(" + "id integer primary key autoincrement," + "UserEmail text," + "UserPassword integer,"+"UserName text,"+"Extra text)";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        执行数据库语句
        sqLiteDatabase.execSQL(Creat_db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists User");
        onCreate(sqLiteDatabase);
    }

    public UserDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

}
