package com.example.hgfhgh.wifiterm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by hgfhgh on 09.08.2017.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class AlarmSound extends Activity implements SoundPool.OnLoadCompleteListener {
    SoundPool sp;
    int soundId;

    @TargetApi(Build.VERSION_CODES.FROYO)
    public AlarmSound(Context context){
        this.sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        this.sp.setOnLoadCompleteListener(this);
        this.soundId = sp.load(context, R.raw.alarm, 1);
    }

    public void play (int times){
        this.sp.play(this.soundId, 1,1,0,times,1);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }
}
