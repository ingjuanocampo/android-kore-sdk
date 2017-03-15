package kore.botssdk.net;

/**
 * Created by Ramachandra on 30-May-16.
 * Copyright (c) 2014 Kore Inc. All rights reserved.
 */

/**
 * This class is for defining properties
 */
public class SDKConfiguration {

    //    struct botConfig {
//        static let baseUrl = "https://qa1-bots.kore.com/"
//        static let clientId = "cs-2f6084da-33d0-5b0f-9c66-5b0bbef514f2"
//        static let clientSecret = "T2+PyTibG6f29DcYrhhkoVpD3VSgH/zizJlK8+yMglg="
//        static let identity = "riz1@testadmin3.xyz"
//
//        static let chatBotName = "Asana_Emp1"
//        static let taskBotId = "st-fb2f3ef8-bca1-5fc3-95b9-ec0f3b40f090"
//    }

    /*struct botConfig {
        static let baseUrl = "https://pilot-bots.kore.com/"
        static let clientId = "cs-a3d69bdb-996e-5551-b78e-996deb62039f"
        static let clientSecret = "1Jm6IHQt8PfunGjgtV444wfEff3YnwtYd1tjmZElXIY="
        static let identity = "riz@testadmin3.xyz"

        static let chatBotName = "BBCConsu"
        static let taskBotId = "st-a03b7d94-8823-568f-b3aa-a0bf80a91e16"
    }*/

    //This is related to JWT Token
    public static class Demo{
        public static final String JWT_SERVER_URL = "http://50.19.64.173:4000";
    }

    public static class Server{
        public static final String KORE_BOT_SERVER_URL = "https://qa1-bots.kore.com";//"https://qabots.kore.com";
        public static final boolean IS_ANONYMOUS_USER = false;
    }

    public static class Client {
        public static final String demo_client_id = "cs-31d07db4-c7b0-5460-8193-bf68aeea81a3";//"cs-2f6084da-33d0-5b0f-9c66-5b0bbef514f2";
        public static final String demo_user_id = "u-73364365-f98d-571d-8e8e-022186cde3bc";
        public static final String clientSecret = "jPT8HDU4YfSa+oBoAoTtflvXm+TZP9FjWbcNYMb0d88=";//"T2+PyTibG6f29DcYrhhkoVpD3VSgH/zizJlK8+yMglg=";
        public static final String identity = "riz@testadmin3.xyz";


        public static final String chatBotName = "Shopbot_2";
        public static final String taskBotId = "st-75bdb67c-894d-520e-a98b-2927a485f604";

        //Not needed for anonymous user
        public static final String demo_auth_token = "mO6emCF8p-rOpDR-cJbQCKg9yTFLqxF_nHEe_d6ZcyBtDnDf3DYpPs9RG3qnqZlS";
    }

}
