package com.demo.android.ch03_09_startservice_playmusic_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.telecom.ConnectionService;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bt_playOrPause, bt_stop;
    boolean isPlaying, isBounded;
    ServiceConnection sConn;
    PlayMusicService pService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_playOrPause = findViewById(R.id.bt_playOrPause);
        bt_stop = findViewById(R.id.bt_stop);
        isPlaying = false;
        isBounded = false;
        sConn = buildServiceConnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayMusicService.class);
        bindService(intent,sConn,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isBounded){
            unbindService();
        }
    }

    private ServiceConnection buildServiceConnect() {
        ServiceConnection conn;
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                pService = ((PlayMusicService.ServiceBinder)service).getService();
                isBounded = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isBounded = false;
            }
        };
        return conn;
    }

    public void playOrPauseMusic(View view) {

    }

    public void stopMusic(View view) {
        isPlaying = false;
        bt_playOrPause.setText("Play");
        Intent intent = new Intent(this, PlayMusicService.class);
        stopService(intent);
    }
}