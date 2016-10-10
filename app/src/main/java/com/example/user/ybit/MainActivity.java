package com.example.user.ybit;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    int k;// = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        k=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);
        Button dbButton = (Button) findViewById(R.id.btn3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),newNoteActivity.class);
                startActivity(i1);
            }
        });
        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDBresults();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(k!=0) {
            Toast.makeText(this, "restart!!!", Toast.LENGTH_LONG).show();
            showDBresults();
        }
        else k++;
    }

    /*
        {
            super.onCreate(savedInstanceState);
            showDBresults();
        }*/
    /*
     * Prints the data simply as a string, to check if insertion is proper.
     * if no output is obtained also verify if the text is visible..
     * I tried logging the result but no use :(
     */
    public void showDBresults(){

        SQLiteDatabase db = openOrCreateDatabase("notes",MODE_PRIVATE,null);
        String sqlStr = "CREATE TABLE IF NOT EXISTS `allNotes` ( `id` INT ,"+
                "noteName VARCHAR(20) NOT NULL ,content TEXT NOT NULL , `createdOn` VARCHAR(20) NOT NULL );";
        db.execSQL(sqlStr);

        String Query = "SELECT * FROM allNotes;";
        Cursor c = db.rawQuery(Query,null);
        c.moveToFirst();
        TextView output = (TextView) findViewById(R.id.allNotes);
        String result = output.getText().toString();
        do{
                result+=c.getString(2);
                Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        }while(c!=null && c.moveToNext());
     //Toast.makeText(this,result,Toast.LENGTH_LONG).show();
        output.setText(result);
    }
}
