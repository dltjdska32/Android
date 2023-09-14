package com.example.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;


public class MusicService extends Service {
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        // 음악파일생성 , 음악을 다운받고 res폴더에 raw를 추가해주고 다운로드 받은 음악을 추가한다. (음악 제목은 대문자 불가, 띄어쓰기 불가 only 소문자.mp3)
        mediaPlayer = MediaPlayer.create(this,R.raw.bodyofwatertracktribe);
        //반복재생 true , 일반재생 false
        mediaPlayer.setLooping(false);

    }


    //실질적으로 음악시작
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
