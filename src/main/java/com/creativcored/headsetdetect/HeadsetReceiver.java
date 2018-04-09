package com.creativcored.headsetdetect;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Log;

/**
 * Created by Ashen De Silva on 3/22/2018.
 */

public class HeadsetReceiver extends BroadcastReceiver {

    static AudioManager audioManager;
    private boolean wiredHeadsetConnected;
    private boolean bluetoothHeadsetConnected;

    final static String LOG_TAG = "HeadsetReceiver";

    private BroadcastReceiver mReceiver;

    public void onReceive(Context context, Intent intent){

        /*Log.i(LOG_TAG, "onReceive");
        if(!intent.getAction().equals(Intent.ACTION_HEADSET_PLUG ) && !bluetoothHeadsetConnected) {
            return;
        }*/
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    Log.d(LOG_TAG, "Headset is unplugged");
                    wiredHeadsetConnected = false;
                    break;
                case 1:
                    Log.d(LOG_TAG, "Headset is plugged");
                    wiredHeadsetConnected = true;
                    break;
                default:
                    Log.d(LOG_TAG, "I have no idea what the headset state is");
            }
        }else{
            return;
        }
    }

    public boolean detectHeadphones(){
        bluetoothHeadsetConnected =  (audioManager.isBluetoothA2dpOn() || audioManager.isBluetoothScoOn());
        return (wiredHeadsetConnected || bluetoothHeadsetConnected);
    }

    @TargetApi(23)
    public boolean detectHeadphones2(){
        AudioDeviceInfo[] devInfo = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
        for(AudioDeviceInfo info: devInfo){
            if(info.getType() == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP || info.getType() == AudioDeviceInfo.TYPE_WIRED_HEADPHONES){
                return true;
            }
        }
        return false;
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
