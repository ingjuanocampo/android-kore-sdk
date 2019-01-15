package kore.botssdk.bot.ai.di

import android.app.Application
import dagger.Component
import kore.botssdk.activity.BotChatActivity
import kore.botssdk.bot.ai.kore.KoreSdkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [KoreSdkModule::class])
interface BotComponent {

    fun provideApplication(): Application

    fun provideIBotManager(): IBotManager

    fun inject(activity: BotChatActivity)

}