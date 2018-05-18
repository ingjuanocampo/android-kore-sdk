package kore.botssdk.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import kore.botssdk.models.BaseBotMessage;
import kore.botssdk.models.BotInfoModel;
import kore.botssdk.models.BotResponse;
import kore.botssdk.models.BotResponseMessage;
import kore.botssdk.models.ComponentModel;
import kore.botssdk.models.PayloadOuter;

/**
 * Created by Pradeep Mahato on 06-Jun-16.
 * Copyright (c) 2014 Kore Inc. All rights reserved.
 */
public class Utils {

    /**
     * Retrieve The version name of this package, as specified by the manifest
     *
     * @param context
     * @return 
     */
    public static String getVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Get the package version
     * @param context
     * @return
     */
    public static String getBuildVersion(Context context) {
        return "v" + getVersion(context);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
    public static boolean isWebURL(String url) {
        return android.util.Patterns.WEB_URL.matcher(url).matches();
    }

    public static BotResponse buildBotMessage(String msg, String icon){
        BotResponse botResponse = new BotResponse();

        botResponse.setType("bot_response");
        botResponse.setFrom("bot");
        botResponse.setIcon(icon);

        Calendar calendar = Calendar.getInstance();
        long date = System.currentTimeMillis();
        int offset = TimeZone.getDefault().getOffset(date);
        calendar.setTimeInMillis(date - offset);
        botResponse.setCreatedOn(BaseBotMessage.isoFormatter.format(calendar.getTime()).toString());

        BotInfoModel bInfo = new BotInfoModel("KoraAssistantNew","st-9a1144d3-9f30-526e-8e64-e065154b92b9");
        botResponse.setBotInfo(bInfo);

        BotResponseMessage botResponseMessage = new BotResponseMessage();
        botResponseMessage.setType(BotResponse.COMPONENT_TYPE_TEXT);

        ComponentModel cModel = new ComponentModel();
        cModel.setType(BotResponse.COMPONENT_TYPE_TEXT);

        PayloadOuter pOuter = new PayloadOuter();
        pOuter.setText(msg);

        cModel.setPayload(pOuter);
        botResponseMessage.setComponent(cModel);
        ArrayList<BotResponseMessage> message = new ArrayList<>(1);
        message.add(botResponseMessage);
        botResponse.setMessage(message);


        return botResponse;
    }

}
