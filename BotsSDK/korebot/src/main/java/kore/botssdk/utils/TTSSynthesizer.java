package kore.botssdk.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by Pradeep Mahato on 19-May-17.
 * Copyright (c) 2014 Kore Inc. All rights reserved.
 */

public class TTSSynthesizer {

    private TextToSpeech textToSpeech;

    private Context context;
    private MediaPlayer mediaPlayer;

    public TTSSynthesizer(Context context) {
        this.context = context;
        initNative(context);
    }

    public TextToSpeech initNative(Context context) {
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        return textToSpeech;
    }

    public void speak(String textualMessage) {
        if (Constants.ENABLE_SDK) {
            speakViaSDK(textualMessage);
        } else {
            speakViaNative(textualMessage);
        }
    }

    private void speakViaSDK(String textualMessage) {
        String modifiedTextualMessage = textualMessage.replace("\r\n","").replace(" ", "+").replace("++","+");
        String url = "https://speech.kore.ai/tts/cgi-bin/speech?voice=salli&lang=en_us&text=" + modifiedTextualMessage;

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(mediaPlayerOnPreparedListener);
        }

        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            stopTextToSpeechSDK();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MediaPlayer.OnPreparedListener mediaPlayerOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (mp != null) {
                stopTextToSpeechSDK();
                mp.start();
            }
        }
    };

    private void speakViaNative(String textualMessage) {
       // stopTextToSpeechNative();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(textualMessage, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            textToSpeech.speak(textualMessage, TextToSpeech.QUEUE_ADD, null);
        }
    }

    public void stopTextToSpeech() {
        if (Constants.ENABLE_SDK) {
            stopTextToSpeechSDK();
        } else {
            stopTextToSpeechNative();
        }
    }

    private void stopTextToSpeechSDK() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    private void stopTextToSpeechNative() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
    }

}
