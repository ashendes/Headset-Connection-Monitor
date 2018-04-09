package com.creativcored.headsetdetect;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.test.mock.MockContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashen De Silva on 4/4/2018.
 */

public class TestContext extends MockContext {
    private List<Intent> receivedIntents = new ArrayList<Intent>();

    @Override
    public String getPackageName()
    {
        return "com.creativcored.headsetdetect";
    }

    @Override
    public void startActivity(Intent intent)
    {
        receivedIntents.add(intent);
        startService(new Intent(IntentService.AUDIO_SERVICE));
    }

    public List<Intent> getReceivedIntents()
    {
        return receivedIntents;
    }
}
