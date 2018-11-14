package com.example.parul.expensemonitor;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    String monthNames[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    ExpenseListAdapter adapter;
    List<ExpenseData> dataList;
    RecyclerView rvMainActivity;
    Calendar today = Calendar.getInstance();
    int todate = today.get(Calendar.DATE);
    String monthName = monthNames[today.get(Calendar.MONTH)];
    int year = today.get(Calendar.YEAR);
    DataBaseOperations db;
    String byMonth = "";
    int byYear;
    Spinner spinnerMonth;
    Spinner spinnerYear;
    TextView tvtotalamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBaseOperations(this);
        fab = findViewById(R.id.fabAddExpense);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAddItemDialog(MainActivity.this);        // calling alertBox method
            }
        });

        rvMainActivity = findViewById(R.id.RecyclerListOfDailyExpense);
        rvMainActivity.setLayoutManager(new LinearLayoutManager(this));

        dataList = new ArrayList<>();
        adapter = new ExpenseListAdapter(dataList, this);
        rvMainActivity.setAdapter(adapter);

        showTodaysExpense();

    }

    private void showTodaysExpense() {

        dataList.clear();
        db.open();
        //Toast.makeText(this, "Date -> " + today.get(Calendar.DATE) + " m ->" + monthNames[today.get(Calendar.MONTH)] + " y-> " + today.get(Calendar.YEAR), Toast.LENGTH_SHORT).show();
        Cursor dataItems = db.getDataByDate
                (todate, monthName, year);
        TextView tvNo = findViewById(R.id.TVNoDataFound);
        if (dataItems.getCount() > 0) {

            if (tvNo != null)
                tvNo.setVisibility(View.GONE);
        } else {
            if (tvNo != null)
                tvNo.setVisibility(View.VISIBLE);
        }
        long sum = 0;
        while (dataItems.moveToNext()) {
            dataList.add
                    (
                            new ExpenseData
                                    (
                                            dataItems.getInt(0),
                                            dataItems.getString(4)
                                            , dataItems.getInt(5)
                                            , dataItems.getInt(1)
                                            , dataItems.getInt(3)
                                            , dataItems.getString(2)
                                    )
                    );
            adapter.notifyDataSetChanged();
            sum = sum + dataItems.getInt(5); // kyu ki  indx 5 pee amount h toh ek ek amount aega or sum variable me add hota rhega
        }
        tvtotalamount = (TextView) findViewById(R.id.tvtotalamount);
        tvtotalamount.setText("Today's Total Expense = Rs. " + sum); //hihihaha

        db.close();


    }


    private void showAddItemDialog(Context c) {
        final View view = LayoutInflater.from(this).inflate(R.layout.add_expense_alert, null);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Add a new Expense")
                .setMessage("What do you want to add next?")
                .setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edtExpense = view.findViewById(R.id.edtExpense);
                        EditText edtAmount = view.findViewById(R.id.edtAmount);
                        String exp = edtExpense.getText().toString();
                        int amt = Integer.parseInt(edtAmount.getText().toString());
                        ExpenseData currentData = new ExpenseData(exp, amt, todate, year, monthName);
                        db.open();
                        db.insert(currentData);
                        db.close();
                        showTodaysExpense();
                        Toast.makeText(MainActivity.this, "Expense added \n" + exp + "\n" + "amount Rs. " + amt, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.previous_date_menu, menu); // this will inflate or show menu in main activity (we can use this in any activity)
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_showByDate) // this is for clicked item -> If there are more than one menu then we will distinguish it by using if else if
        {
            getDate();
        } else if (item.getItemId() == R.id.menu_selectByMonth) {
            final View view = LayoutInflater.from(this).inflate(R.layout.month_year_selector, null);

            spinnerMonth = view.findViewById(R.id.spinner_select_month);
            spinnerYear = view.findViewById(R.id.spinner_select_year);
            List<String> monthList = new ArrayList<String>(Arrays.asList(monthNames));
            final ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, monthList);
            monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMonth.setAdapter(monthAdapter);
            spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    byMonth = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            ArrayList<Integer> years = new ArrayList<>();
            for (int start = 2018; start < 2026; start++) {
                years.add(start);
            }
            ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, years);
            yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerYear.setAdapter(yearAdapter);
            spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    byYear = (int) parent.getItemAtPosition(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("By Month")
                    .setMessage("Select Month And Year")
                    .setView(view)
                    .setPositiveButton("Find", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Toast.makeText(MainActivity.this, "month - > " + byMonth + " year -> " + byYear, Toast.LENGTH_SHORT).show();

                            Intent gotoByDateActivity = new Intent(MainActivity.this, ByDate.class);
                            gotoByDateActivity.putExtra("year", byYear);
                            gotoByDateActivity.putExtra("month", byMonth);
                            gotoByDateActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(gotoByDateActivity);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    void getDate() {
        final Calendar calendar = Calendar.getInstance();   //object creation current time
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                calendar.set(year, month, dayOfMonth); // ye data bhejna hai
                // Toast.makeText(MainActivity.this, "Showing data of \n" + dayOfMonth + "/" + monthNames[month] + "/" + year, Toast.LENGTH_SHORT).show();

                Intent gotoByDateActivity = new Intent(MainActivity.this, ByDateOfMonth.class);
                gotoByDateActivity.putExtra("day", dayOfMonth);
                gotoByDateActivity.putExtra("year", year);
                gotoByDateActivity.putExtra("month", monthNames[month]);
                gotoByDateActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoByDateActivity);

            }
        }, calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


}
