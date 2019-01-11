package kore.botssdk.kore.ai.wrapper

import android.arch.lifecycle.MutableLiveData
import android.support.annotation.StringDef
import kore.botssdk.autobahn.WebSocket
import kore.botssdk.bot.BotClient
import kore.botssdk.websocket.SocketConnectionListener

class BotClientWrapperImplementatio(val botClient :BotClient): BotClientWrapper {

    @StringDef(CONNECTED, DISCONNECTED)
    annotation class Connection

    companion object {
        const val CONNECTED = "Connected"
        const val DISCONNECTED = "Disconnected"
    }

    val connectionStatusObserver : MutableLiveData<String> = MutableLiveData()
    val textMessageObserver: MutableLiveData<Any> = MutableLiveData()

    override fun connect(jwtToken: String, clientId: String, chatBotName: String, taskBotId: String) {

        if (!botClient.isConnected) {
            botClient.connectAsAnonymousUser(jwtToken, clientId, chatBotName, taskBotId, object : SocketConnectionListener {
                override fun onOpen() {
                    connectionStatusObserver.postValue(CONNECTED)
                }

                override fun onClose(code: WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification?, reason: String?) {
                    connectionStatusObserver.postValue(CONNECTED)
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
        }
    }

    override fun getAccessToken(): String = botClient.accessToken

    override fun getUserId() :String = botClient.userId

    override fun disconnect() = botClient.disconnect()

    override fun isConnected() = botClient.isConnected

    override fun sendMessage(message :String, chatBotName :String, taskBotId :String) = botClient.sendMessage(message, chatBotName, taskBotId)

    override fun sendFormData(payLoad: String, chatBotName: String, taskBotId: String, message: String) = botClient.sendFormData(payLoad, chatBotName, taskBotId, message)


}