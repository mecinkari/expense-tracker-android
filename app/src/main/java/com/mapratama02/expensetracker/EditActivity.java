package com.mapratama02.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mapratama02.expensetracker.adapters.DBHelper;

public class EditActivity extends AppCompatActivity {
    EditText etNamaEdit, etJumlahEdit;
    Spinner spinnerJenisEdit;
    Button btnEdit, btnHapus;

    DBHelper dbHelper;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dbHelper = new DBHelper(this);

        // Ambil nilai intent yg dikirim tadi
        id = getIntent().getIntExtra(DBHelper.ROW_ID, 0);

        // Tampung ID ke variable
        etNamaEdit = (EditText) findViewById(R.id.etNamaEdit);
        etJumlahEdit = (EditText) findViewById(R.id.etJumlahEdit);
        spinnerJenisEdit = (Spinner) findViewById(R.id.spinnerJenisEdit);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnHapus = (Button) findViewById(R.id.btnHapus); 

        // Menambahkan list pada Spinner atau drpo-dwon
        String[] items = new String[]{"Income", "Expense"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.jenis_expenses));
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerJenisEdit.setAdapter(adapter);

        getData();
        
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(id);
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(id);
            }
        });
    }

    private void getData(){
        Cursor cur = dbHelper.getOneData(id);
        if(cur.moveToFirst()){
            String id = cur.getString(0);
            String nama = cur.getString(1);
            String jumlah = cur.getString(2);
            String jenis = cur.getString(3);

            ArrayAdapter spinnerAdapter = (ArrayAdapter) spinnerJenisEdit.getAdapter();
            int iSelectedItem = spinnerAdapter.getPosition(jenis);

            etNamaEdit.setText(nama);
            etJumlahEdit.setText(jumlah);
            spinnerJenisEdit.setSelection(iSelectedItem);
        }
    }

    private void deleteData(int id){
        dbHelper.deleteData(id);
        Toast.makeText(this, "Data berhasil di hapus!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateData(int id){
        String nama = etNamaEdit.getText().toString().trim();
        String jumlah = etJumlahEdit.getText().toString().trim();
        String jenis = spinnerJenisEdit.getSelectedItem().toString().trim();

        ContentValues values = new ContentValues();

        values.put(DBHelper.ROW_NAME, nama);
        values.put(DBHelper.ROW_JUMLAH, jumlah);
        values.put(DBHelper.ROW_JENIS, jenis);

        dbHelper.updateData(values, id);
        Toast.makeText(this, "Data berhasil di-update!", Toast.LENGTH_SHORT).show();
        finish();
    }
}