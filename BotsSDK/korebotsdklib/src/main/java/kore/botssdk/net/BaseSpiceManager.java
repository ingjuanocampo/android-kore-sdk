package kore.botssdk.net;

import android.content.Context;

import com.octo.android.robospice.SpiceManager;

/**
 * Created by Pradeep Mahato on 13-Jun-16.
 * Copyright (c) 2014 Kore Inc. All rights reserved.
 */
public class BaseSpiceManager implements SpiceManagerLifeCycle {

    private SpiceManager spiceManager = new SpiceManager(BotRestService.class);

    /**
     * @return Spice Manager's connection state
     */
    @Override
    public boolean isConnected() {
        return spiceManager.isStarted();
    }

    /**
     * Start spice manager service
     *
     * @param context
     */
    @Override
    public void start(Context context) {
        try {
            spiceManager.start(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop spice manager service
     */
    @Override
    public void stop() {
        try {
            spiceManager.shouldStop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the spice manager object
     * @return SpiceManger
     */
    public SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
