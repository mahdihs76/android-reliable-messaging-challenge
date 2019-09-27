package ir.nilva.pushechallenge.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ir.nilva.pushechallenge.MainApplication

@Module
class AppModule {

    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

//    @Singleton
//    @Provides
//    fun provideReliableMessaging(): ReliableMessaging = ReliableMessaging()


}
