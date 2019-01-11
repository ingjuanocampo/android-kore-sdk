package kore.botssdk.kore.ai.wrapper

interface BotClientWrapper {
    fun connect(jwtToken : String, clientId :String, chatBotName :String, taskBotId: String)
    fun getAccessToken(): String
    fun getUserId() :String
    fun disconnect()
    fun isConnected() :Boolean
    fun sendMessage(message :String, chatBotName :String, taskBotId :String)
    fun sendFormData(payLoad: String, chatBotName: String, taskBotId: String, message: String)

}