package com.creativcored.headsetdetect;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

/**
 * Created by ASUS on 3/22/2018.
 */

public class HeadsetReceiver extends BroadcastReceiver {

    AudioManager audioManager;
    boolean headsetConnected;

    public void onReceive(Context context, Intent intent){
        audioManager = (AudioManager)context.getSystemService(Service.AUDIO_SERVICE);
    }
}
