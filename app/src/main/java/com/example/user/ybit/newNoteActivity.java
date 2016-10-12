package com.example.user.ybit;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class newNoteActivity extends AppCompatActivity {

    int mode,id;//1 for new and 0 for modifying
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Intent i = getIntent();
        Bundle intentData = i.getExtras();
        if(intentData!=null) {
            id = intentData.getInt("noteId");

            EditText et1 = (EditText) findViewById(R.id.editText2);
            TextView textView = (TextView) findViewById(R.id.textView2);
            EditText et2 = (EditText) findViewById((R.id.editText));
            if (id == -1) {
                Calendar c = Calendar.getInstance();
               SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
                String dateTime = df.format(c.getTime());

                textView.setText(dateTime);
                mode = 1;
            }
            else {
                mode=0;

                SQLiteDatabase db = openOrCreateDatabase("notes", MODE_PRIVATE, null);
                String sqlStr = "CREATE TABLE IF NOT EXISTS `allNotes` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "noteName VARCHAR(20) NOT NULL ,content TEXT NOT NULL , `createdOn` VARCHAR(20) NOT NULL );";
                db.execSQL(sqlStr);
                String sqlStr2 ="SELECT * FROM allNotes WHERE id=" + id + ";";


                Cursor c = db.rawQuery(sqlStr2, null);
                c.moveToFirst();
                String title = c.getString(1);
                String noteContent = c.getString(2);
                String date = c.getString(3);
                et1.setText(noteContent);
                et2.setText(title);
                textView.setText("Last updated on "+date);
                }
        }

    }
    public void onBackPressed(){
        EditText et1 = (EditText) findViewById(R.id.editText2);
        String content = et1.getText().toString();
        EditText et2 = (EditText)findViewById((R.id.editText));
        String title  = et2.getText().toString();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");

        String dateTime = df.format(c.getTime());

        SQLiteDatabase db = openOrCreateDatabase("notes",MODE_PRIVATE,null);
        String sqlStr = "CREATE TABLE IF NOT EXISTS `allNotes` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "noteName VARCHAR(20) NOT NULL ,content TEXT NOT NULL , `createdOn` VARCHAR(20) NOT NULL );";

        db.execSQL(sqlStr);
            ContentValues cv = new ContentValues();
            cv.put("noteName", title);

            cv.put("content", content);
            cv.put("createdOn", dateTime);
        if(mode==1) {
            db.insert("allNotes", null, cv);
        }
        else{

            db.update("allNotes",cv,"id=?",new String[]{Integer.toString(id)});
        }
        finish();
    }
}
