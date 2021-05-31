package com.example.all_in_one;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {

    SQLiteDatabase db1 = this.getWritableDatabase();

    public Dbhelper(@Nullable Context context) {
        super(context, "student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE student (ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,NAME TEXT,AGE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS student");
        onCreate(db);
    }

    public void update(String Name,int id,int age)
    {
        ContentValues cv = new ContentValues();
        cv.put("NAME",Name);
        cv.put("AGE",age);
        cv.put("ID",id);
        db1.update("student", cv, "ID = ? ", new String[]{id + ""});
    }

    public void delete(int id)
    {
        db1.delete("student","ID = ?",new String[]{id+""});
    }

    public long insert(String Name,int id,int age)
    {
        ContentValues cv = new ContentValues();
        cv.put("NAME",Name);
        cv.put("AGE",age);
        cv.put("ID",id);
        long x = db1.insert("student", null, cv);
        return x;
    }

    public Cursor show()
    {
        Cursor c = db1.rawQuery("SELECT * FROM student",null);
        return  c;
    }

}