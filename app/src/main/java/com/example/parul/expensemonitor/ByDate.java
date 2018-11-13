package com.example.parul.expensemonitor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ByDate extends AppCompatActivity
{
    RecyclerView rvByDate;
    DataBaseOperations db;
    ExpenseListAdapter adapter;
    String byMonth = "";
    List<ExpenseData> dataList;
    int byYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_date);
        Intent intent = getIntent();
        byMonth = intent.getStringExtra("month");
        byYear = intent.getIntExtra("year",2018);

        getUi();

        showDataByDate(byMonth,byYear);
    }

    private void showDataByDate(String byMonth, int byYear)
    {
        dataList.clear();
        db.open();
        Cursor data = db.getByMonthYear(byMonth,byYear);
        if (data.getCount() > 0)
        {
            Toast.makeText(this, "Found "+data.getCount()+" entries", Toast.LENGTH_SHORT).show();
        }
        while (data.moveToNext())
        {
            dataList.add
                    (new ExpenseData
                            (
                                    data.getString(4),
                                    data.getInt(5),
                                    data.getInt(1),
                                    data.getInt(3),
                                    data.getString(2)
                    ));
            adapter.notifyDataSetChanged();
        }
    }

    private void getUi()
    {
        rvByDate = findViewById(R.id.rv_ByDate);
        db = new DataBaseOperations(this);
        dataList  = new ArrayList<>();
        adapter = new ExpenseListAdapter(dataList,this);

        rvByDate.setLayoutManager(new LinearLayoutManager(this));
        rvByDate.setAdapter(adapter);
        rvByDate.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }
}
