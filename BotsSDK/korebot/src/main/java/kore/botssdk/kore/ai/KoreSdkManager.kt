package kore.botssdk.kore.ai

import android.content.Context
import kore.botssdk.bot.BotClient
import kore.botssdk.net.SDKConfiguration
import kore.botssdk.websocket.SocketWrapper

class KoreSdkManager(val context: Context, val client: SDKConfiguration.Client) {

    private var botClient : BotClient
    private var socket : SocketWrapper

    init {
        // Lines added to support the socket connection (From Michel's POC)
        java.lang.System.setProperty("java.net.preferIPv4Stack", "true")
        java.lang.System.setProperty("java.net.preferIPv6Addresses", "false")

        botClient = BotClient(context)
        socket = SocketWrapper.getInstance(context)
    }

}