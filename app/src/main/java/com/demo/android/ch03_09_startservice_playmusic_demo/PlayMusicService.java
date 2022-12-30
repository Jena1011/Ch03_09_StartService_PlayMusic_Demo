package com.demo.android.ch03_09_startservice_playmusic_demo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class PlayMusicService extends Service {

    IBinder sBinder;
    MediaPlayer player;

    class ServiceBinder extends Binder {
        PlayMusicService getService(){return PlayMusicService.this;}
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sBinder = new ServiceBinder();
        player = MediaPlayer.create(this,R.raw.holynight);
        Log.v("jena","PlayMusicService:onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player!=null) player=null;
        if(sBinder!=null) sBinder=null;
        Log.v("jena","PlayMusicService:onDestroy");
    }

    public void playMusic(){
        if(player!=null)
            player.start();
        Log.v("jena","PlayMusicService:playMusic");
    }

    public void pauseMusic(){
        if(player!=null)
            player.pause();
        Log.v("jena","PlayMusicService:pauseMusic");
    }

    public void stopMusic(){
        if(player!=null){
            player.pause();
            player.seekTo(0);
        }
        Log.v("jena","PlayMusicService:stopMusic");
    }
}