package kore.botssdk.bot.ai.kore

import android.content.Context
import dagger.Module
import dagger.Provides
import kore.botssdk.bot.BotClient
import kore.botssdk.bot.ai.di.ApplicationModule
import kore.botssdk.bot.ai.di.BotClientData
import kore.botssdk.bot.ai.di.IBotManager
import kore.botssdk.net.SDKConfiguration

@Module(includes = [ApplicationModule::class])
class KoreSdkModule {

    @Provides
    fun provideBotClientData(): BotClientData =
            BotClientData(SDKConfiguration.Client.client_id,
                    SDKConfiguration.Client.client_secret,
                    SDKConfiguration.Client.identity,
                    SDKConfiguration.Client.bot_name,
                    SDKConfiguration.Client.bot_id)

    @Provides
    fun provideBotClientWrapper(context: Context, botClientData: BotClientData): IBotManager
            = KoreManager(BotClient(context), botClientData)

}