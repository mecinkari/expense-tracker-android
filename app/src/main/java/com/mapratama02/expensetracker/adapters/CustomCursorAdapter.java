package com.mapratama02.expensetracker.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.mapratama02.expensetracker.R;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Currency;

public class CustomCursorAdapter extends CursorAdapter {
    private LayoutInflater inflater;
    private SparseBooleanArray mSelectedItem;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSelectedItem = new SparseBooleanArray();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = inflater.inflate(R.layout.row_data, parent, false);
        MyHolder holder = new MyHolder();

        holder.rowId = (TextView) v.findViewById(R.id.etRowId);
        holder.rowNama = (TextView) v.findViewById(R.id.etRowNama);
        holder.rowJumlah = (TextView) v.findViewById(R.id.etRowJumlah);
        holder.rowJenis = (TextView) v.findViewById(R.id.etRowJenis);

        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder) view.getTag();

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("IDR"));

        holder.rowId.setText(cursor.getString(0));
        holder.rowNama.setText(cursor.getString(1).toUpperCase());
        holder.rowJenis.setText(cursor.getString(3));

        if (cursor.getString(3).equals("Income")){
            holder.rowJumlah.setTextColor(ContextCompat.getColor(context, R.color.green_400));
            holder.rowJumlah.setText("+" + format.format(Integer.parseInt(cursor.getString(2))));
        } else {
            holder.rowJumlah.setTextColor(ContextCompat.getColor(context, R.color.red_400));
            holder.rowJumlah.setText("-" + format.format(Integer.parseInt(cursor.getString(2))));
        }
    }

    class MyHolder{
        TextView rowId;
        TextView rowNama;
        TextView rowJumlah;
        TextView rowJenis;
    }


}
