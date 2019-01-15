package kore.botssdk.bot.ai.di

import android.arch.lifecycle.LiveData

interface IBotManager {
    fun connect()
    fun getAccessToken(): String
    fun getUserId(): String
    fun disconnect()
    fun isConnected(): Boolean
    fun sendMessage(message: String): Boolean
    fun sendFormData(payLoad: String, message: String)
    fun getConnectionStatusObserver(): LiveData<Pair<String, String>>
    fun getTextMessageObserver(): LiveData<Any>

}