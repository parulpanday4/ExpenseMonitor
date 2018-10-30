package com.example.parul.expensemonitor;

import android.app.DatePickerDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fabAddExpense);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "FAB CLICKED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.previous_date_menu,menu); // this will inflate or show menu in main activity (we can use this in any activity)
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.menu_showByDate) // this is for clicked item -> If there are more than one menu then we will distinguish it by using if else if
        {
            final Calendar calendar = Calendar.getInstance();   //object creation
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                {
                    calendar.set(year,month,dayOfMonth);
                    Toast.makeText(MainActivity.this, dayOfMonth+"/"+month+1+"/"+year, Toast.LENGTH_SHORT).show();
                }
            },Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH).show();


        }
        return super.onOptionsItemSelected(item);
    }


}
