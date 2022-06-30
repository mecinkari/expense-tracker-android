package com.mapratama02.expensetracker.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "db_expense";
    public static final String TBL_NAME = "tbl_expense";

    public static  final String ROW_ID = "_id";
    public static  final String ROW_NAME = "nama";
    public static  final String ROW_JUMLAH = "jumlah";
    public static  final String ROW_JENIS = "jenis";

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TBL_NAME + "(" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ROW_NAME + " TEXT, "
                + ROW_JUMLAH + " INTEGER, "
                + ROW_JENIS + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
    }

    // Ambil semua data
    public Cursor allData(){
        return db.rawQuery("SELECT * FROM " + TBL_NAME + " ORDER BY " + ROW_ID + " DESC", null);
    }

    public Cursor getLimitData(int limit){
        return db.rawQuery("SELECT * FROM " + TBL_NAME + " ORDER BY " + ROW_ID + " DESC LIMIT " + limit, null);
    }

    // Ambil data berdasarkan ID
    public Cursor getOneData(int id){
        return db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE " + ROW_ID + "=" + id, null);
    }

    // Insert data
    public void insertData(ContentValues values){
        db.insert(TBL_NAME, null, values);
    }

    // Update data
    public void updateData(ContentValues values, int id){
        db.update(TBL_NAME, values, ROW_ID + "=" + id, null);
    }

    // Delete data
    public void deleteData(int id){
        db.delete(TBL_NAME, ROW_ID+"="+id, null);
    }

    // Hitung seluruh data Income
    public Cursor getAllIncomesOrExpenses(String jenis){
        return db.rawQuery("SELECT SUM(" + ROW_JUMLAH + ") FROM " + TBL_NAME + " WHERE " + ROW_JENIS + "='" + jenis + "'", null);
    }
}
