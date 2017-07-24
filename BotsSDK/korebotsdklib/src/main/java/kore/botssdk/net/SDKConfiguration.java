package kore.botssdk.net;

/**
 * Created by Ramachandra on 30-May-16.
 * Copyright (c) 2014 Kore Inc. All rights reserved.
 */

/**
 * This class is for defining properties
 */
public class SDKConfiguration {

    //JWTServer related configurations
    public static class JWTServer{
        public static final String JWT_SERVER_URL = "http://demo.kore.net:3000";
    }

    //Server related configurations
    public static class Server{
        public static final String KORE_BOT_SERVER_URL = "https://qa1-bots.kore.com";//"https://qabots.kore.com";
        public static final boolean IS_ANONYMOUS_USER = false;
    }

    public static class Client {
        public static final String client_id = "cs-31d07db4-c7b0-5460-8193-bf68aeea81a3";//"cs-2f6084da-33d0-5b0f-9c66-5b0bbef514f2";
        public static final String client_secret = "jPT8HDU4YfSa+oBoAoTtflvXm+TZP9FjWbcNYMb0d88=";//"T2+PyTibG6f29DcYrhhkoVpD3VSgH/zizJlK8+yMglg=";
        public static final String identity = "riz@testadmin3.xyz";


        public static final String bot_name = "Shopbot_2";
        public static final String bot_id = "st-75bdb67c-894d-520e-a98b-2927a485f604";
    }

}
