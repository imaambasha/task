package com.example.deletemenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tb = findViewById(R.id.tb);
        tb.setTitle("shanmukha");
        setSupportActionBar(tb);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menufirst,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        switch (id){
            case R.id.item1:
                Toast.makeText(this, "item1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "item2", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "item3", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4:
                Toast.makeText(this, "item4", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subit1:
                Toast.makeText(this, "subitem1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subit2:
                Toast.makeText(this, "subitem1", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
