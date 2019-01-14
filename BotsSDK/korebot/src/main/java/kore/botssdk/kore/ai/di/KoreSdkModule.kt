package kore.botssdk.kore.ai.di

import android.content.Context
import dagger.Module
import dagger.Provides
import kore.botssdk.bot.BotClient
import kore.botssdk.kore.ai.KoreSdkManager
import kore.botssdk.kore.ai.wrapper.BotManager
import kore.botssdk.net.SDKConfiguration

@Module(includes = [ApplicationModule::class])
class KoreSdkModule(private val context: Context) {

    @Provides
    fun provideBotClientData(): BotClientData =
            BotClientData(SDKConfiguration.Client.client_id,
                    SDKConfiguration.Client.client_secret,
                    SDKConfiguration.Client.identity,
                    SDKConfiguration.Client.bot_name,
                    SDKConfiguration.Client.bot_id)

    @Provides
    fun provideBotClientWrapper(context: Context): IBotManager
            = BotManager(BotClient(context))

    @Provides
    fun providesKoreSdkManager(botManager: IBotManager, botClientData: BotClientData): KoreSdkManager
            = KoreSdkManager(botManager, botClientData)


}