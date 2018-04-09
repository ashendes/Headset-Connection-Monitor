package com.creativcored.headsetdetect;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.Log;

/**
 * Created by Ashen De Silva on 3/22/2018.
 */

public class HeadsetReceiver extends BroadcastReceiver {

    static AudioManager audioManager;
    private boolean headsetConnected = false;
    final static String LOG_TAG = "HeadsetReceiver";

    private BroadcastReceiver mReceiver;

    public void onReceive(Context context, Intent intent){
        boolean bluetoothHeadsetConnected =  (audioManager.isBluetoothA2dpOn() || audioManager.isBluetoothScoOn());
        Log.i(LOG_TAG, "onReceive");
        if(!intent.getAction().equals(Intent.ACTION_HEADSET_PLUG ) && !bluetoothHeadsetConnected) {
            return;
        }
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    Log.d(LOG_TAG, "Headset is unplugged");
                    headsetConnected = false;
                    break;
                case 1:
                    Log.d(LOG_TAG, "Headset is plugged");
                    headsetConnected = true;
                    break;
                default:
                    Log.d(LOG_TAG, "I have no idea what the headset state is");
            }
        }
    }

    public boolean detectHeadphones(){
        return headsetConnected;
    }

    private HeadsetReceiver(Context context){
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        mReceiver = this;
        context.registerReceiver(mReceiver, filter);
    }

    public static HeadsetReceiver initializeHeadsetReceiver(Context context){
        audioManager = (AudioManager)context.getSystemService(Service.AUDIO_SERVICE);
        if(audioManager != null){
            Log.i(LOG_TAG, "Audio manager created");
        }else {
            Log.i(LOG_TAG, "No Audio manager");
        }
        return new HeadsetReceiver(context);
    }
}
