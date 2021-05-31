package com.example.style_and_themes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    EditText editTextName,editTextNumber;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = (EditText)findViewById(R.id.editTexText);
        editTextNumber = (EditText)findViewById(R.id.editTextNumber);
        send = (Button)findViewById(R.id.button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editTextNumber.getText().toString();
                String sms = editTextName.getText().toString();
                try{
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number,null,sms,null,null);
                    Toast.makeText(MainActivity.this,"Sent",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
