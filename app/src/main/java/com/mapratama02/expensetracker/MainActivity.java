package com.mapratama02.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mapratama02.expensetracker.adapters.DBHelper;

public class MainActivity extends AppCompatActivity {
    Button btnSaldo, btnListExpenses, btnTentang;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        btnSaldo = findViewById(R.id.btn_saldo);
        btnListExpenses = findViewById(R.id.btn_list_expenses);
        btnTentang = findViewById(R.id.btn_tentang);

        btnSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SaldoActivity.class);
                startActivity(intent);
            }
        });

        btnListExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListExpensesActivity.class);
                startActivity(intent);
            }
        });
    }

    // <a href='https://www.freepik.com/vectors/money-illustration'>Money illustration vector created by pch.vector - www.freepik.com</a>
}