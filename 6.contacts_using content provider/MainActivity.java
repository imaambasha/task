package com.example.contactslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = findViewById(R.id.lv);
        ArrayList<String> items = fetchContats();
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items));
    }

    private ArrayList<String> fetchContats(){

        ArrayList<String> items = new ArrayList<String>();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        ContentResolver resolver = getContentResolver();
        Cursor c = resolver.query(uri,new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},null,null,null);
        int count = 0;
        while (c.moveToNext()){
            String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            count++;
            items.add(count+"\n"+name+"\n"+number);
        }

        return items;
    }

}