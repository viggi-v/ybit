package com.example.user.ybit;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    int k, i=0;
    int positionAr[]=new int[10000];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        k=0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateList();
        Button button1 = (Button) findViewById(R.id.button1);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),newNoteActivity.class);
                i1.putExtra("noteId",-1);

                startActivity(i1);
            }
        });


        Button clear_db = (Button)findViewById(R.id.button);
        clear_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SQLiteDatabase db = openOrCreateDatabase("notes",MODE_PRIVATE,null);
                String sqlStr = "CREATE TABLE IF NOT EXISTS `allNotes` ( `id` INT ,"+
                        "noteName VARCHAR(20) NOT NULL ,content TEXT NOT NULL , `createdOn` VARCHAR(20) NOT NULL );";
                db.execSQL(sqlStr);
                db.delete("allNotes",null,null);
                updateList();

            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateList();
    }


    public void updateList() {
        final SQLiteDatabase db = openOrCreateDatabase("notes", MODE_PRIVATE, null);
        final String sqlStr = "CREATE TABLE IF NOT EXISTS `allNotes` ( `id` INT ," +
                "noteName VARCHAR(20) NOT NULL ,content TEXT NOT NULL , `createdOn` VARCHAR(20) NOT NULL );";
        db.execSQL(sqlStr);

        String Query = "SELECT * FROM allNotes;";
        final Cursor c = db.rawQuery(Query, null);
        final ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        final ListView lv = (ListView) findViewById(R.id.listView);
        if (c.moveToFirst()) {
            lv.setAdapter(ad);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, final long l) {
                Intent i2 = new Intent(MainActivity.this,newNoteActivity.class);
                    i2.putExtra("noteId",positionAr[(int)l]);
                    startActivity(i2);
                }
            });
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, final long l) {
                    AlertDialog adb = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Caution!")
                            .setMessage("Do you want to delete this Note?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    String sqlStr2 = "DELETE FROM allNotes where id="+positionAr[(int)l]+";";
                                    Toast.makeText(MainActivity.this,"Note Deleted!", Toast.LENGTH_SHORT).show();
                                    db.execSQL(sqlStr2);
                                    updateList();
                                }
                            })
                            .setNegativeButton("No",null)
                            .create();
                    adb.show();
                    return true;
                }
            });
            int pos=0;
            do {
                String sub;
                positionAr[pos]=Integer.parseInt(c.getString(0));
                pos++;
                if(c.getString(2).length()>20) {
                     sub = c.getString(2).substring(0,30) + "...";
                }
                else
                {
                    sub = c.getString(2);
                }
                ad.add(c.getString(1) + "\n\n " + sub);
            } while (c != null && c.moveToNext());
        }

        else{
            String ar[]={};
            ArrayAdapter<String> ad1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ar);
            lv.setAdapter(ad1);

            Toast.makeText(MainActivity.this,"No Notes Present!", Toast.LENGTH_SHORT).show();
        }

    }
}