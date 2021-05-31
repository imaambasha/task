package com.example.all_in_one;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button add, upd, del, show;
    EditText name, age, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Dbhelper dh = new Dbhelper(this);

        add = findViewById(R.id.add_std);
        del = findViewById(R.id.del_stu);
        upd = findViewById(R.id.upd_stu);
        show = findViewById(R.id.show_all_stu);

        name = findViewById(R.id.et_name);
        age = findViewById(R.id.et_age);
        id = findViewById(R.id.et_id);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dh.insert(name.getText().toString(), Integer.parseInt(id.getText().toString()), Integer.parseInt(age.getText().toString())) > 0) {
                    Toast.makeText(MainActivity.this, "Succesfully inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Unable to insert", Toast.LENGTH_SHORT).show();
                }
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dh.delete(Integer.parseInt(id.getText().toString()));
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = dh.show();
                StringBuffer sb = new StringBuffer();
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        sb.append("ID: " + c.getString(0) + "\n");
                        sb.append("Name: " + c.getString(1) + "\n");
                        sb.append("AGE: " + c.getString(2) + "\n\n");

                        showMsg("Info", sb);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No Info to show", Toast.LENGTH_SHORT).show();
                }
            }
        });

        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dh.update(name.getText().toString(), Integer.parseInt(id.getText().toString()), Integer.parseInt(age.getText().toString()));
            }
        });


    }

    private void showMsg(String info, StringBuffer sb) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this)
                .setTitle(info)
                .setMessage(sb.toString())
                .setCancelable(true);
        AlertDialog showad = ad.create();
        showad.show();
    }
}
