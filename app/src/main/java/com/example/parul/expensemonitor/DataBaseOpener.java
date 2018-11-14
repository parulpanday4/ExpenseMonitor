package com.example.parul.expensemonitor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseOpener extends SQLiteOpenHelper
{
    final static String TABLE_NAME = "expense";
    final static String DB_NAME = "expense.db";


    public DataBaseOpener(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (sno integer primary key autoincrement, day text, month text,year text,expenseOn text,amount text)");//0,1,2,3,4,5
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

}
