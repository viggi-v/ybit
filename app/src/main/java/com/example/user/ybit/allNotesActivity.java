package com.example.user.ybit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class allNotesActivity extends MainActivity {

    String notes[] = new String[10];
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes);

        SQLiteDatabase db = openOrCreateDatabase("notes", MODE_PRIVATE, null);
        String sqlStr = "CREATE TABLE IF NOT EXISTS `allNotes` ( `id` INT ," +
                "noteName VARCHAR(20) NOT NULL ,content TEXT NOT NULL , `createdOn` VARCHAR(20) NOT NULL );";
        db.execSQL(sqlStr);

        String Query = "SELECT * FROM allNotes;";
        final Cursor c = db.rawQuery(Query, null);
        if (c.moveToFirst()) {
            do {

                notes[i] = c.getString(0) + ": " + c.getString(1) + "\n    " + c.getString(2);
                Toast.makeText(allNotesActivity.this, notes[i], Toast.LENGTH_SHORT).show();
                i++;
            } while (c != null && c.moveToNext());


            ListView lv = (ListView) findViewById(R.id.listView);
            ArrayAdapter<String> ad = new ArrayAdapter<String>(allNotesActivity.this, android.R.layout.simple_list_item_1, notes);
            lv.setAdapter(ad);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(allNotesActivity.this, c.getString(2), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void updateList() {
        SQLiteDatabase db = openOrCreateDatabase("notes", MODE_PRIVATE, null);
        String sqlStr = "CREATE TABLE IF NOT EXISTS `allNotes` ( `id` INT ," +
                "noteName VARCHAR(20) NOT NULL ,content TEXT NOT NULL , `createdOn` VARCHAR(20) NOT NULL );";
        db.execSQL(sqlStr);

        String Query = "SELECT * FROM allNotes;";
        final Cursor c = db.rawQuery(Query, null);
        if (c.moveToFirst()) {
            do {

                notes[i] = c.getString(0) + ": " + c.getString(1) + "\n    " + c.getString(2);
                Toast.makeText(allNotesActivity.this, notes[i], Toast.LENGTH_SHORT).show();
                i++;
            } while (c != null && c.moveToNext());


            ListView lv = (ListView) findViewById(R.id.listView);
            ArrayAdapter<String> ad = new ArrayAdapter<String>(allNotesActivity.this, android.R.layout.simple_list_item_1, notes);
            lv.setAdapter(ad);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(allNotesActivity.this, c.getString(2), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}