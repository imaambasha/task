package com.example.camera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.MediaController;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button a;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
    }

    public void ChangeAct(View view) {
        startActivity(new Intent(getBaseContext(),SurfaceViewCla.class));
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)+ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)+ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Premission needed", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length>0 ){
            if (grantResults[0]+grantResults[1]+grantResults[2]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void TakePhoto(View view) {
        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cInt,1);
    }

    public void TakeVideo(View view){
        Intent cInt = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(cInt,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        VideoView videoCapture = findViewById(R.id.capturedVideo);
        File mediaFile = Environment.getExternalStorageDirectory();
        Uri videoUri = Uri.parse(mediaFile.getAbsolutePath()+"/sample.mp4");
        MediaController mediaController = new MediaController(this);
        if (resultCode == RESULT_OK) {
             if (requestCode == 1) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                ImageView imgCapture = findViewById(R.id.imageView1);
                imgCapture.setImageBitmap(bp);
            }
            if (requestCode == 200) {
                try {
                    Toast.makeText(this, "Video has been saved to:\n" + mediaFile, Toast.LENGTH_LONG).show();
                    videoCapture.setMediaController(mediaController);
                    videoCapture.setVideoURI(data.getData());
                    videoCapture.requestFocus();
                    videoCapture.start();
                    Toast.makeText(this, "Video Uri: "+videoUri, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            Toast.makeText(this, "some problem occured", Toast.LENGTH_LONG).show();
        }
    }

}