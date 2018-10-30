package com.example.parul.expensemonitor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.MyViewHolder>
{
    List<ExpenseData> listExpense;//created list for listing multiple expense data
    Context context; //for creating layout inflator

    public ExpenseListAdapter(List<ExpenseData> listExpense, Context context) { // constructor that will use at time of setting data
        this.listExpense = listExpense;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.daily_expense_model,parent,false); //created view
        MyViewHolder myViewHolder = new MyViewHolder(view);  //object instantiation for myviewHolder
        return myViewHolder; // retunrning object of MyViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        ExpenseData expenseData = listExpense.get(position); //  object for current index data
        holder.textVthings.setText(expenseData.getMoneySpendOn()); // setting current text to view
        holder.textVmoney.setText(expenseData.getAmount());  // same
    }

    @Override
    public int getItemCount() {
        return listExpense.size(); // returning how many objects are there to show
    }

    class MyViewHolder extends RecyclerView.ViewHolder // to hold view
    {
        TextView textVthings;
        TextView textVmoney;
        public MyViewHolder(View itemView) {
            super(itemView);

            textVthings = itemView.findViewById(R.id.TVmoneySpendOnThings);
            textVmoney = itemView.findViewById(R.id.TVmoneySpend);
        }
    }
}
