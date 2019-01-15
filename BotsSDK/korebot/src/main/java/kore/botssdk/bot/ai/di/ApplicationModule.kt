package kore.botssdk.bot.ai.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(val application: Application) {

    @Provides
    fun provideApplication(): Application = application

    @Provides
    fun provideApplicationContext(): Context = application.applicationContext
}