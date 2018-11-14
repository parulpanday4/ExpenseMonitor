package com.example.parul.expensemonitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.MyViewHolder> {
    List<ExpenseData> listExpense;//created list for listing multiple expense data
    Context context; //for creating layout inflator

    public ExpenseListAdapter(List<ExpenseData> listExpense, Context context) { // constructor that will use at time of setting data
        this.listExpense = listExpense;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.daily_expense_model, parent, false); //created view
        return new MyViewHolder(view);  //object instantiation for myviewHolder
        // retunrning object of MyViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExpenseData expenseData = listExpense.get(position); //  object for current index data
        holder.textVthings.setText(expenseData.getMoneySpendOn()); // setting current text to view
        holder.textVmoney.setText(String.format("Rs. %d", expenseData.getAmount()));  // same
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showAddItemDialog(context);
                }
            });
        }

        private void showAddItemDialog(Context c) {
            final ExpenseData data = listExpense.get(getAdapterPosition());

            AlertDialog dialog = new AlertDialog.Builder(c)
                    .setTitle("Click edit to Update")
                    .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final View view = LayoutInflater.from(context).inflate(R.layout.add_expense_alert, null);//wah ri wah

                            final EditText edtExpense = view.findViewById(R.id.edtExpense);
                            final EditText edtAmount = view.findViewById(R.id.edtAmount);



                            final DataBaseOperations db = new DataBaseOperations(context);
                            db.open();
                            Cursor dbData = db.getById(data.getSno());
                            //Log.i("PARUL", String.valueOf(dbData.getCount()+" mile "));
                            while (dbData.moveToNext()) {

                                edtExpense.setText(dbData.getString(4)); // data base me jo exp val pehle se save h wo isske through dikhegi
                                edtAmount.setText(dbData.getString(5));
                            }
                            new AlertDialog.Builder(context) // creating alert box without object after user clck edit
                                    .setView(view)
                                    .setCancelable(false)
                                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            String exp = edtExpense.getText().toString();
                                            int amt = Integer.parseInt(edtAmount.getText().toString().trim());
                                            ContentValues cv = new ContentValues();
                                            cv.put("expenseOn", exp);
                                            cv.put("amount", amt);
                                            int z = db.update(cv, data.getSno());
                                            if (z > 0)
                                                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                            db.close();
                                            ((Activity) context).recreate();
                                            //chk kro
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setTitle("Edit data")
                                    .show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataBaseOperations db = new DataBaseOperations(context);
                            db.open();//opening database
                            int z = db.delete(data.getSno()); //calling delete method
                            if (z > 0)
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

                            db.close();
                            ((Activity) context).recreate(); // recreating activity


                        }
                    })
                    .create();
            dialog.show();
        }
    }
}
