package com.example.roomdatabasetutorial.database

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.roomdatabasetutorial.database.entities.MyTable
import com.example.roomdatabasetutorial.database.daos.MyTableDao

@Database(
    entities = [MyTable::class],
    version = 1,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getMyTableDao(): MyTableDao

    companion object {

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {

            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_db"
                ).build().also {

                    INSTANCE = it
                }
            }
        }
    }
}
