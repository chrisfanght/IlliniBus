package com.chris.illinibus.Notification;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by chrisfang on 11/30/16.
 */

public class BusService extends IntentService{
    private static int NOTIFICATION_ID = 10;

    public BusService() {
        super("BusService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
