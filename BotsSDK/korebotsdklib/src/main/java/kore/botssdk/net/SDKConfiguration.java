package kore.botssdk.net;

/**
 * Created by Ramachandra on 30-May-16.
 * Copyright (c) 2014 Kore Inc. All rights reserved.
 */

/**
 * This class is for defining properties
 */
public class SDKConfiguration {

    /**
     * bot init text  and related settings
     */
    public static  boolean TRIGGER_INIT_MESSAGE = false;
    public static  String INIT_MESSAGE = "Your init message here";
    public static final String BOT_ICON_URL = "";
    //JWTServer related configurations
    public static class JWTServer{
        public static void setJwtServerUrl(String jwtServerUrl) {
            JWT_SERVER_URL = jwtServerUrl;
        }

        public static  String JWT_SERVER_URL = "Your JWT Server URL here";
    }

    //Server related configurations
    public static class Server{


        public static void setKoreBotServerUrl(String koreBotServerUrl) {
            KORE_BOT_SERVER_URL = koreBotServerUrl;
        }

        public static void setServerUrl(String serverUrl) {
            SERVER_URL = serverUrl;
        }

        public static  String KORE_BOT_SERVER_URL = "https://bots.kore.ai";//https://qa-bots.kore.ai";
        public static final String SPEECH_SERVER_BASE_URL = "wss://speech.kore.ai/stream/kore/decode";
        public static final String TTS_WS_URL = "wss://speech.kore.ai/tts/ws";
        public static final boolean IS_ANONYMOUS_USER = false;
        public static   String SERVER_URL ="https://bots.kore.ai";
    }

    public static class Client {
        public static final String client_id = "Your client id here";
        public static final String client_secret = "Your Client secret here";
        public static final String identity = "Your identity";
        public static final String bot_name = "Bot name here";
        public static final String bot_id = "Bot Id here";
    }


}
