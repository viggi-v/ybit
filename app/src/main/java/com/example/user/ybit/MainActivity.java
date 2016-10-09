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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),newNoteActivity.class);
                startActivity(i1);
            }
        });
    }
    protected void onStart(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        showDBresults();
    }
    public void showDBresults(){
        SQLiteDatabase db = openOrCreateDatabase("notes",MODE_PRIVATE,null);
        String sqlStr = "CREATE TABLE IF NOT EXISTS `allNotes` ( `id` INT ,"+
                "noteName VARCHAR(20) NOT NULL ,content TEXT NOT NULL , `createdOn` VARCHAR(20) NOT NULL );";
        db.execSQL(sqlStr);

        String Query = "SELECT * FROM allNotes;";
        Cursor c = db.rawQuery(Query,null);
        TextView output = (TextView) findViewById(R.id.allNotes);
        String result = output.getText().toString();
        while(c != null && c.moveToNext()){
                result+=c.getString(0)+"-"+c.getString(1)+"-"+c.getString(2)+"-"+c.getString(3)+';';
        }
        output.setText(result);


    }
}
