package ir.nilva.pushechallenge.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import ir.nilva.pushechallenge.db.data.MessageDao
import ir.nilva.pushechallenge.db.data.Message

const val DATABASE_VERSION = 1

@Database(entities = [Message::class], version = DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract val messageDao: MessageDao

    companion object {
        private const val databaseName = "app-db"

        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}