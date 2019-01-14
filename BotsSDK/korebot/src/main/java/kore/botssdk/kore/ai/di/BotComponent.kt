package kore.botssdk.kore.ai.di

import android.app.Application
import dagger.Component
import kore.botssdk.activity.BotChatActivity
import kore.botssdk.kore.ai.KoreSdkManager
import javax.inject.Singleton

@Singleton
@Component(modules = [KoreSdkModule::class])
interface BotComponent {

    fun provideApplication(): Application

    fun provideKoreSdkManager(): KoreSdkManager

    fun inject(activity: BotChatActivity)

}