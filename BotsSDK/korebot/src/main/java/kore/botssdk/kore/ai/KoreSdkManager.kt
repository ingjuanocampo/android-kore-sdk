package kore.botssdk.kore.ai

import android.arch.lifecycle.LiveData
import kore.botssdk.kore.ai.di.BotClientData
import kore.botssdk.kore.ai.di.IBotManager

class KoreSdkManager(private val IBotClient: IBotManager, private val botClientData: BotClientData) {

    companion object {
        const val JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL2lkcHJveHkua29yZS5haS9hdXRob3JpemUiLCJzdWIiOiJhbnlyYW5kb21zdHJpbmciLCJpc0Fub255bW91cyI6ZmFsc2UsImlzcyI6ImNzLTliMGE3MTIwLTM3OWUtNTc5My1iYTE3LWZmZGQ1ZGNkODg4YSIsImV4cCI6OTk5OTk5OTk5OSwiaWF0IjoxNTQ1ODMyOTc5fQ.EhhCJZVnqS_ZlTnRm6-crmiUItAbsW8TXB8VNDOpzO8"
    }

    init {
        // Lines added to support the socket connection (From Michel's POC)
        java.lang.System.setProperty("java.net.preferIPv4Stack", "true")
        java.lang.System.setProperty("java.net.preferIPv6Addresses", "false")
    }

    fun connect() {
        IBotClient.connect(JWT, botClientData.clientId, botClientData.botName, botClientData.botId)
    }

    fun disconnect() {
        IBotClient.disconnect()
    }

    fun sendMessage(message: String) {
        IBotClient.sendMessage(message, botClientData.botName, botClientData.botId)
    }

    fun getConnectionStatus(): LiveData<String> {
        return IBotClient.getConnectionStatusObserver()
    }

    fun getTextMessage(): LiveData<Any> {
        return IBotClient.getTextMessageObserver()
    }

}
