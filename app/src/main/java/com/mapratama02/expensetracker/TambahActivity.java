package com.mapratama02.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mapratama02.expensetracker.adapters.DBHelper;

public class TambahActivity extends AppCompatActivity {
    Spinner spinnerJenis;
    Button btnTambah;
    EditText etNama, etJumlah;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        setTitle("Tambah Data Expense");

        dbHelper = new DBHelper(this);

        // Masukkan id pada tiap variable
        spinnerJenis = (Spinner) findViewById(R.id.spinnerJenis);
        btnTambah = (Button) findViewById(R.id.btnTambah);
        etNama = (EditText) findViewById(R.id.etNama);
        etJumlah = (EditText) findViewById(R.id.etJumlah);

        // Menambahkan list pada Spinner atau drpo-dwon
        String[] items = new String[]{"Income", "Expense"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.jenis_expenses));
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerJenis.setAdapter(adapter);

        // Membuat clickListener pada btntambah
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String jumlah = etJumlah.getText().toString();
                String jenis = spinnerJenis.getSelectedItem().toString();
                String hasil = "Nama: " + nama + ", Jumlah: " + jumlah + ", Jenis: " + jenis;

                if (nama.isEmpty() || jumlah.isEmpty()){
                    Toast.makeText(TambahActivity.this, "Field tidak boleh kosong!", Toast.LENGTH_LONG).show();
                    return;
                }
                insertData();
            }
        });
    }

    public void insertData(){
        String nama = etNama.getText().toString().trim();
        String jumlah = etJumlah.getText().toString().trim();
        String jenis = spinnerJenis.getSelectedItem().toString().trim();

        ContentValues values = new ContentValues();

        values.put(DBHelper.ROW_NAME, nama);
        values.put(DBHelper.ROW_JUMLAH, jumlah);
        values.put(DBHelper.ROW_JENIS, jenis);

        dbHelper.insertData(values);
        Toast.makeText(TambahActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show();
        finish();
    }

}