package com.mapratama02.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapratama02.expensetracker.adapters.CustomCursorAdapter;
import com.mapratama02.expensetracker.adapters.DBHelper;

public class ListExpensesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    ListView lv;
    DBHelper dbHelper;
    Context ctx;
    CardView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_expenses);

        setTitle("List Expenses");

        dbHelper = new DBHelper(this);

        // Cari id
        FloatingActionButton addBtn = findViewById(R.id.addBtn);
        lv = (ListView) findViewById(R.id.listView);

        // Fungsi untuk klik pada addBtn
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListExpensesActivity.this, TambahActivity.class);
                startActivity(intent);
            }
        });

        lv.setOnItemClickListener(this);
        setupListView();
    }

    private void setupListView(){
        Cursor cur = dbHelper.allData();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cur, 1);
        lv.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView getID = (TextView) view.findViewById(R.id.etRowId);
        final int row_id = Integer.parseInt(getID.getText().toString());
        Intent intent = new Intent(ListExpensesActivity.this, EditActivity.class);
        intent.putExtra(DBHelper.ROW_ID, row_id);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }
}