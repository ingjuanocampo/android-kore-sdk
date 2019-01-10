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

        public static  String JWT_SERVER_URL = "https://54.221.73.19/";
    }

    //Server related configurations
    public static class Server{


        public static void setKoreBotServerUrl(String koreBotServerUrl) {
            KORE_BOT_SERVER_URL = koreBotServerUrl;
        }

        public static void setServerUrl(String serverUrl) {
            SERVER_URL = serverUrl;
        }

        public static  String KORE_BOT_SERVER_URL = "http://10.16.4.23";//"https://bots.kore.ai";//https://qa-bots.kore.ai";
        public static final String SPEECH_SERVER_BASE_URL = "wss://speech.kore.ai/stream/kore/decode";
        public static final String TTS_WS_URL = "wss://speech.kore.ai/tts/ws";
        public static final boolean IS_ANONYMOUS_USER = false;
        public static   String SERVER_URL ="http://10.16.4.23";
    }

    public static class Client {
        public static final String client_id = "cs-9b0a7120-379e-5793-ba17-ffdd5dcd888a";//"cs-9b0a7120-379e-5793-ba17-ffdd5dcd888a";
        public static final String client_secret = "1kw6tamrZ7fiAOE18gErvEHlQkeE7Bo06Gx2nbwurX0=";//"AHSubkG09DRdcz9xlzxUXfrxyRx9V0Yhd+6SnXtjYe4=";
        public static final String identity = "33339848313";
        public static final String bot_name = "FirstBot";//"Twitter_Auth";
        public static final String bot_id = "st-76eb2a5f-e615-52fb-bee3-c676f96dd82a";//"st-76eb2a5f-e615-52fb-bee3-c676f96dd82a";
    }


}
