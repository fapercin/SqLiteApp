package com.example.bilimselinsan.sqliteapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    EditText name;
    EditText surname;
    EditText marks;
    Button viewall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DatabaseHelper(this);
        name = (EditText)findViewById(R.id.editText);
        surname = (EditText)findViewById(R.id.editText2);
        marks = (EditText)findViewById(R.id.editText3);
        viewall = (Button)findViewById(R.id.button3);

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name == null || surname == null || marks == null)
                {
                    Toast.makeText(MainActivity.this, "Don't leave anything empty.", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean isInserted = myDb.InsertData(name.getText().toString(),
                            surname.getText().toString(),
                            marks.getText().toString());

                    if (isInserted)
                        Toast.makeText(MainActivity.this, "Successfully saved", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "Something is wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button clear = (Button)findViewById(R.id.button2);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText(null);
                surname.setText(null);
                marks.setText(null);
            }
        });

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor returnCursor = myDb.getAll();
                if(returnCursor.getCount() == 0)
                {
                    Toast.makeText(MainActivity.this, "DB Empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    StringBuffer buff = new StringBuffer();
                    while(returnCursor.moveToNext())
                    {
                        buff.append("ID:" + returnCursor.getString(0) + "\n");
                        buff.append("Name:" + returnCursor.getString(1) + "\n");
                        buff.append("Surname:" + returnCursor.getString(2) + "\n");
                        buff.append("Marks:" + returnCursor.getString(3) + "\n\n");
                    }

                    ShowMessage("Students", buff.toString());
                }

            }
        });

    }
    public void ShowMessage(String a, String b)
    {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setCancelable(true);
        build.setTitle(a);
        build.setMessage(b);
        build.show();
    }
}
