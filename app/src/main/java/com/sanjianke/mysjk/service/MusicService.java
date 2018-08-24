package com.sanjianke.mysjk.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sanjianke.mysjk.R;

import java.io.IOException;

public class MusicService extends Service {


    private MediaPlayer mediaPlayer;
    private String mp3;
    private String musicState;
    private int pos;

    private Handler handler = new Handler();
    private Runnable runnable;
    /**
     * 只执行一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.nobody);
        runnable=new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent("com.dd.music.broad");
                intent.putExtra("max",getTotalTime());
                intent.putExtra("currentTime",getCurrentTime());
                sendBroadcast(intent);
                handler.postDelayed(runnable,1000);
            }
        };
    }

    /**
     * ----默认运行在主线程
     * 每次开启都执行
     *
     * @param intent
     * @param flags   正常情况都是0，服务被系统意外回收后，自动重启后，不为0（用于判断正常启动还是系统自动重启）
     * @param startId 本次启动唯一标志（表示是第几次调用了方法）
     * @return start_sticky     会自动重启，intent对象中不携带数据
     * START_NOT_STIVKY  不会自动重启
     * start_sticky_compatibility 会自动重启，不携带数据，重启不一定会成功
     * start-redeliver_INTENT 会自动重启，对象中会携带数据
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp3 = intent.getStringExtra("mp3");
        musicState = intent.getStringExtra("music");
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, Uri.parse(mp3));
        } else {
            try {
                mediaPlayer.prepare();
                mediaPlayer.setDataSource(this, Uri.parse(mp3));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (musicState.equals("start")) {
            startMusic();
        } else if (musicState.equals("stop")) {
            stopMusic();
        } else if (musicState.equals("pause")) {
            pauseMusic();
        } else if (musicState.equals("next")) {
            nextMusic();
        } else if (musicState.equals("last")) {
            lastMusic();
        } else if (musicState.equals("seekTo")) {
            pos = intent.getIntExtra("pos", 0);
            seekMusic(pos);
            startHandler();
        }else if (musicState.equals("stophandler")) {
            stopHandler();
        }

//        if (mediaPlayer != null) {
//            mediaPlayer = MediaPlayer.create(this, Uri.parse(mp3));
//            mediaPlayer.start();
//        } else {
//            mediaPlayer = MediaPlayer.create(this, Uri.parse(mp3));
//        }

        return START_REDELIVER_INTENT;
    }

    /**
     * stop时执行
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    public void startMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            handler.post(runnable);
        }else {
            mediaPlayer = MediaPlayer.create(this,R.raw.nobody);
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            handler.removeCallbacks(runnable);
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer = null;
        }
    }

    public void nextMusic() {

    }

    public void lastMusic() {

    }

    public void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            handler.removeCallbacks(runnable);
        }
    }

    //获取音乐时长
    public int getTotalTime() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        } else {
            return 0;
        }
    }

    //获取音乐当前位置
    public int getCurrentTime() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }


    //快进或者快退
    public void seekMusic(int pos) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(pos);
        }
    }

    public void stopHandler(){
        handler.removeCallbacks(runnable);
    }
    public void startHandler(){
        handler.post(runnable);
    }


    //只和bindservice有关
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }


    class MyBind extends Binder {

    }
}
