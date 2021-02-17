package com.example.roombasicapplication.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roombasicapplication.services.dao.UserDao
import com.example.roombasicapplication.services.entities.History
import com.example.roombasicapplication.services.entities.User

@Database(
    entities = [
        User::class,
        History::class
    ],
    version = 2
)

abstract class LocalDatabase : RoomDatabase(){
    abstract fun getUserDao() : UserDao

    companion object{
        @Volatile
        private var instance: LocalDatabase? = null
        private var lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock){
            instance ?: buildDatabase(context)
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE history(id INTEGER NOT NULL, name TEXT NOT NULL, PRIMARY KEY(id))")
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LocalDatabase::class.java,
                "local.db"
            ).addMigrations(MIGRATION_1_2).build()
    }
}