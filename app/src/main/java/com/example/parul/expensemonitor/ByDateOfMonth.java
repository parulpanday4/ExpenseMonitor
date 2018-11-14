package com.example.parul.expensemonitor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ByDateOfMonth extends AppCompatActivity {

    int byDate;
    int byYear;
    String byMonth;
    RecyclerView rvByDateOfMonth;
    DataBaseOperations db;
    ExpenseListAdapter adapter;
    List<ExpenseData> dataList;
    TextView tvTotalDateAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_date_of_month);
        Intent intent = getIntent();
        byDate = intent.getIntExtra("day", 0);
        byMonth = intent.getStringExtra("month");
        byYear = intent.getIntExtra("year", 2018);
        getUi();
        showDataByDateOfMonth(byDate, byMonth, byYear);
    }

    private void showDataByDateOfMonth(int byDate, String byMonth, int byYear) {
        dataList.clear();
        db.open();
        Cursor data = db.getDataByDate(byDate, byMonth, byYear);
        if (data.getCount() > 0) {
            //Toast.makeText(this, "Found "+data.getCount()+" entries", Toast.LENGTH_SHORT).show();
        }
        long sum = 0;
        while (data.moveToNext()) {
            dataList.add
                    (new ExpenseData
                            (
                                    data.getInt(0),
                                    data.getString(4),
                                    data.getInt(5),
                                    data.getInt(1),
                                    data.getInt(3),
                                    data.getString(2)
                            ));
            adapter.notifyDataSetChanged();
            sum = sum + data.getInt(5);
        }

        tvTotalDateAmount = (TextView) findViewById(R.id.tvTotalDateAmount);
        tvTotalDateAmount.setText(" Total Expense=" + sum);

    }

    public void getUi() {
        rvByDateOfMonth = findViewById(R.id.rv_ByDateOfMonth);
        db = new DataBaseOperations(this);
        dataList = new ArrayList<>();
        adapter = new ExpenseListAdapter(dataList, this);
        rvByDateOfMonth.setLayoutManager(new LinearLayoutManager(this));
        rvByDateOfMonth.setAdapter(adapter);
        rvByDateOfMonth.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }
}
