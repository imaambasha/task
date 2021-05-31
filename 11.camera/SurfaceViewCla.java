package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Camera;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SurfaceViewCla extends AppCompatActivity implements SurfaceHolder.Callback,Camera.PictureCallback{

    Button takePic;
    SurfaceView sv;
    SurfaceHolder sh;
    Camera cam ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_surface_view);
        takePic = findViewById(R.id.btnSurPic);
        sv = findViewById(R.id.sfV);
        sh = sv.getHolder();
        sh.addCallback(this);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cam.takePicture(null,null,SurfaceViewCla.this);
            }
        });
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        cam = Camera.open();
        cam.setDisplayOrientation(90);
        try {
            cam.setPreviewDisplay(sh);
            cam.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (cam!=null) {
            cam.stopPreview();
            try {
                cam.setPreviewDisplay(sh);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cam.startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cam.stopPreview();
        cam.release();
        cam = null;

    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Toast.makeText(this, "Picture has been captured", Toast.LENGTH_SHORT).show();
        FileOutputStream fileOutputStream;
        try{
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"CameraAppSS");
            if (!file.exists()){
                boolean Made = file.mkdir();
                if (!Made)
                {
                    Toast.makeText(this, "Failed to Make directory", Toast.LENGTH_SHORT).show();
                }
            }
            File mediaFile = new File(file.getPath()+File.separator+"IMG_"+System.currentTimeMillis()+".jpg");
            Toast.makeText(this, ""+mediaFile, Toast.LENGTH_SHORT).show();
            fileOutputStream = new FileOutputStream(mediaFile);
            fileOutputStream.write(data);
            fileOutputStream.close();

            camera.stopPreview();
            Thread.sleep(1000);
            camera.startPreview();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "FileNot Found!!!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "IO EXCEPTION!!!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
