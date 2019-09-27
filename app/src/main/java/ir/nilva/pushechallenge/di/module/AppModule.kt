package ir.nilva.pushechallenge.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ir.nilva.pushechallenge.MainApplication
import ir.nilva.pushechallenge.db.AppDatabase
import ir.nilva.pushechallenge.db.data.MessageDao
import ir.nilva.pushechallenge.db.repository.MessageDataSource
import ir.nilva.pushechallenge.db.repository.MessageRepository
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesAppDatabase(context: Context): AppDatabase {
        return AppDatabase.buildDatabase(context)
    }

    @Singleton
    @Provides
    internal fun providesMessageDao(database: AppDatabase): MessageDao {
        return database.messageDao
    }

    @Singleton
    @Provides
    internal fun messageRepository(
        context: Context,
        dao: MessageDao
    ): MessageRepository {
        return MessageDataSource(context, dao)
    }


}
