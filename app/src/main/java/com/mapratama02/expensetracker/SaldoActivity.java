package com.mapratama02.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapratama02.expensetracker.adapters.CustomCursorAdapter;
import com.mapratama02.expensetracker.adapters.DBHelper;

import java.text.NumberFormat;
import java.util.Currency;

public class SaldoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "SaldoActivity";

    ListView lv;
    Context ctx;
    DBHelper dbHelper;
    TextView tvSaldo, tvTotalIncome, tvTotalExpense;
    int saldo = 0, incomes = 0, expenses = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);

        setTitle("Dashboard");

        lv = findViewById(R.id.lvRecent);

        dbHelper = new DBHelper(this);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(0);
        formatter.setCurrency(Currency.getInstance("IDR"));

        tvTotalIncome = (TextView) findViewById(R.id.tv_total_income);
        tvTotalExpense = (TextView) findViewById(R.id.tv_total_expense);
        tvSaldo = (TextView) findViewById(R.id.tv_saldo);

        Cursor cur_income = dbHelper.getAllIncomesOrExpenses("Income");
        if(cur_income.moveToFirst()){
            if(cur_income.isNull(0)){
                incomes = 0;
                tvTotalIncome.setText("IDR0");
            } else {
                String incomesTxt = formatter.format(Integer.parseInt(cur_income.getString(0).toString())).toString();
                incomes = Integer.parseInt(cur_income.getString(0).toString());
                tvTotalIncome.setText(incomesTxt);
            }
        }

        Cursor cur_expense = dbHelper.getAllIncomesOrExpenses("Expense");
        if(cur_expense.moveToFirst()){
            if(cur_expense.isNull(0)) {
                expenses = 0;
                tvTotalExpense.setText("IDR0");
            } else {
                String expensesTxt = formatter.format(Integer.parseInt(cur_expense.getString(0).toString())).toString();
                expenses = Integer.parseInt(cur_expense.getString(0).toString());
                tvTotalExpense.setText(expensesTxt);
            }
        }
        saldo = incomes - expenses;
        String saldoTxt = formatter.format(saldo).toString();
        tvSaldo.setText(saldoTxt);

        lv.setOnItemClickListener(this);
        setupListView();
    }

    private void setupListView(){
        Cursor cur = dbHelper.getLimitData(5);
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cur, 1);
        lv.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }
}