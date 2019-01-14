package kore.botssdk.application;

import android.app.Application;

import kore.botssdk.kore.ai.di.BotComponent;
import kore.botssdk.kore.ai.di.DaggerBotComponent;

/**
 * Created by Pradeep Mahato on 31-May-16.
 * Copyright (c) 2014 Kore Inc. All rights reserved.
 */
public class BotApplication extends Application {

    AppControl appControl;

    @Override
    public void onCreate() {
        super.onCreate();
        appControl = new AppControl(getApplicationContext());

        BotComponent botComponent = DaggerBotComponent().builder();

    }



}
