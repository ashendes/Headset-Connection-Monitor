package com.creativcored.headsetdetect;

import android.content.Context;
import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ashen De Silva on 4/4/2018.
 */
public class HeadsetReceiverTest {

    private Context context;
    private HeadsetReceiver receiver;

    @Before
    public void setUp() throws Exception {
        context = new TestContext();
        receiver = HeadsetReceiver.initializeHeadsetReceiver(context);
    }

    @After
    public void tearDown() throws Exception {
        receiver = null;
        context = null;
    }

    @Test
    public void testReceiver() throws Exception {
        Intent plugIntent = new Intent(Intent.ACTION_HEADSET_PLUG);

        receiver.onReceive(context, plugIntent);

        assertEquals(true, receiver.detectHeadphones());
    }



}