package ir.nilva.reliablemessaging.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import ir.nilva.reliablemessaging.ApplicationContext
import javax.inject.Singleton

@Module
class WorkModule {

    @Provides
    fun provideContext(): Context {
        return ApplicationContext.context
    }

    @Singleton
    @Provides
    fun provideWorkManager(context: Context): WorkManager =
        WorkManager.getInstance(context)

}