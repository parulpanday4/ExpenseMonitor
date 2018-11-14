package com.example.parul.expensemonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.example.parul.expensemonitor.DataBaseOpener.TABLE_NAME;

public class DataBaseOperations {
    Context context;
    public SQLiteDatabase db;
    public DataBaseOpener dbOpener;


    public DataBaseOperations(Context context) {
        this.context = context;
        this.dbOpener = new DataBaseOpener(context, DataBaseOpener.DB_NAME, null, 1);
    }

    public DataBaseOperations open() throws SQLException {
        db = dbOpener.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public long insert(ExpenseData expDataObject) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("day", expDataObject.getDay());
        contentValues.put("month", expDataObject.getMonth());
        contentValues.put("year", expDataObject.getYear());
        contentValues.put("expenseOn", expDataObject.getMoneySpendOn());
        contentValues.put("amount", expDataObject.getAmount());

        return db.insert(TABLE_NAME, null, contentValues);

    }

    public Cursor getDataByDate(int day, String month, int year) {
        return db.query(TABLE_NAME,
                null,
                "day = ? AND month = ? AND year = ?"
                ,new String[]{String.valueOf(day),month, String.valueOf(year)}
                ,null,null,null
        );
    }

    public Cursor getByMonthYear(String month,int year)
    {
        return db.query(TABLE_NAME,
                null,
                "month = ? AND year = ?"
                ,new String[]{month, String.valueOf(year)}
                ,null,null,null
        );
    }

    public int update(ContentValues values, int primaryKey)//contentvalues stores expense and amount and key stores the  sno
    {
        String qry = "sno = ?"; // ? run time pe 4th argument se replace hoga
        return db.update(TABLE_NAME, values, qry, new String[]{String.valueOf(primaryKey)}); // new string hai 4th arg
    }

    public Cursor getById(int sno) {
        return db.query(TABLE_NAME, null, "sno = ?", new String[]{String.valueOf(sno)}, null, null, null);
    }

    public int delete(int sno) throws SQLException {
        return db.delete(TABLE_NAME, "sno = ?", new String[]{String.valueOf(sno)});
    }


}
