package kore.botssdk.bot.ai.kore

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.StringDef
import com.octo.android.robospice.persistence.exception.SpiceException
import kore.botssdk.autobahn.WebSocket
import kore.botssdk.bot.BotClient
import kore.botssdk.bot.ai.di.BotClientData
import kore.botssdk.bot.ai.di.IBotManager
import kore.botssdk.websocket.SocketConnectionListener

class KoreManager(private val botClient: BotClient, private val botClientData: BotClientData) : IBotManager {

    @Target(AnnotationTarget.TYPE)
    @StringDef(CONNECTED, CONNECTING, DISCONNECTED)
    annotation class Connection

    companion object {
        const val CONNECTING = "Connecting"
        const val CONNECTED = "Connected"
        const val DISCONNECTED = "Disconnected"

        const val JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL2lkcHJveHkua29yZS5haS9hdXRob3JpemUiLCJzdWIiOiJhbnlyYW5kb21zdHJpbmciLCJpc0Fub255bW91cyI6ZmFsc2UsImlzcyI6ImNzLTliMGE3MTIwLTM3OWUtNTc5My1iYTE3LWZmZGQ1ZGNkODg4YSIsImV4cCI6OTk5OTk5OTk5OSwiaWF0IjoxNTQ1ODMyOTc5fQ.EhhCJZVnqS_ZlTnRm6-crmiUItAbsW8TXB8VNDOpzO8"

    }

    private val connectionStatusObserver: MutableLiveData<Pair<@Connection String, String>> = MutableLiveData()
    private val textMessageObserver: MutableLiveData<Any> = MutableLiveData()

    init {
        // Lines added to support the socket connection (From Michel's POC)
        java.lang.System.setProperty("java.net.preferIPv4Stack", "true")
        java.lang.System.setProperty("java.net.preferIPv6Addresses", "false")
    }

    override fun connect() {
        connectionStatusObserver.postValue(Pair(CONNECTING, ""))

        if (!botClient.isConnected) {
            botClient.connectAsAnonymousUser(JWT, botClientData.clientId, botClientData.botName, botClientData.botId, object : SocketConnectionListener {

                override fun onOpen() {
                    connectionStatusObserver.postValue(Pair(CONNECTED, ""))
                }

                override fun onClose(code: WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification?, reason: String?) {
                    connectionStatusObserver.postValue(Pair(DISCONNECTED, "$code.toString() - $reason"))
                }

                override fun onConnectionError(e: SpiceException?) {
                    connectionStatusObserver.postValue(Pair(DISCONNECTED, "Exception ${e?.message}"))
                }

                override fun onTextMessage(payload: String?) {
                    textMessageObserver.postValue(payload)
                }

                override fun onRawTextMessage(payload: ByteArray?) {
                    textMessageObserver.postValue(payload)
                }

                override fun onBinaryMessage(payload: ByteArray?) {
                    textMessageObserver.postValue(payload)
                }

                override fun refreshJwtToken() {
                }

            })
        } else {
            connectionStatusObserver.postValue(Pair(CONNECTED, ""))
        }
    }

    override fun getAccessToken(): String = botClient.accessToken

    override fun getUserId(): String = botClient.userId

    override fun disconnect() = botClient.disconnect()

    override fun isConnected() = botClient.isConnected

    override fun sendMessage(message: String) = botClient.sendMessage(message, botClientData.botName, botClientData.botId)

    override fun sendFormData(payLoad: String, message: String) = botClient.sendFormData(payLoad, botClientData.botName, botClientData.botId, message)

    override fun getConnectionStatusObserver(): LiveData<Pair<@Connection String, String>> {
        return connectionStatusObserver
    }

    override fun getTextMessageObserver(): LiveData<Any> {
        return textMessageObserver
    }

}