package kore.botssdk.application;

import android.app.Application;

import kore.botssdk.bot.ai.di.ApplicationModule;
import kore.botssdk.bot.ai.di.BotComponent;
import kore.botssdk.bot.ai.di.DaggerBotComponent;
import kore.botssdk.bot.ai.kore.KoreSdkModule;

/**
 * Created by Pradeep Mahato on 31-May-16.
 * Copyright (c) 2014 Kore Inc. All rights reserved.
 */
public class BotApplication extends Application {

    AppControl appControl;
    private BotComponent botComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appControl = new AppControl(getApplicationContext());

        botComponent = DaggerBotComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .koreSdkModule(new KoreSdkModule())
                .build();

    }

    public BotComponent getBotComponent() {
        return botComponent;
    }
}
