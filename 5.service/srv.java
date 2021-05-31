package com.example.demoservice;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.List;

public class srv extends Service {

    static MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player = new MediaPlayer().create(this, Settings.System.DEFAULT_NOTIFICATION_URI);
        player.setLooping(true);
        player.start();
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(10);
        Log.d("NOTE:", "Curr Act: " + taskInfo.get(0).topActivity.getClassName());
        Log.d("NOTE:", "Curr Act: " + taskInfo.get(1).topActivity.getClassName());

        ComponentName componentInfo = taskInfo.get(0).topActivity;
        componentInfo.getPackageName();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        player.stop();
        Toast.makeText(this, "Service has been stopeed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}