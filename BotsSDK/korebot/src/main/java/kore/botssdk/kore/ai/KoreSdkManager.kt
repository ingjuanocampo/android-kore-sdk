package kore.botssdk.kore.ai

import kore.botssdk.kore.ai.wrapper.BotClientData
import kore.botssdk.kore.ai.wrapper.BotClientWrapper


class KoreSdkManager(private val botClient: BotClientWrapper, private val botClientData: BotClientData) {

    companion object {
        const val JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL2lkcHJveHkua29yZS5haS9hdXRob3JpemUiLCJzdWIiOiJhbnlyYW5kb21zdHJpbmciLCJpc0Fub255bW91cyI6ZmFsc2UsImlzcyI6ImNzLTliMGE3MTIwLTM3OWUtNTc5My1iYTE3LWZmZGQ1ZGNkODg4YSIsImV4cCI6OTk5OTk5OTk5OSwiaWF0IjoxNTQ1ODMyOTc5fQ.EhhCJZVnqS_ZlTnRm6-crmiUItAbsW8TXB8VNDOpzO8"
    }

    init {
        // Lines added to support the socket connection (From Michel's POC)
        java.lang.System.setProperty("java.net.preferIPv4Stack", "true")
        java.lang.System.setProperty("java.net.preferIPv6Addresses", "false")
    }

    fun connect(jwt :String) {

            botClient.connect(jwt, botClientData.clientId, botClientData.botName, botClientData.botId)

    }

    fun disconnect() {
        botClient.disconnect()

    }

    fun sendMessage(message :String) {
        botClient.sendMessage(message)
    }

    fun getConnectionStatus() {

    }


}