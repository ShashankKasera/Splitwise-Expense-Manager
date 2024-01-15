package com.example.localdb.di

import android.content.Context
import androidx.room.Room
import com.example.core.ROOM_DB
import com.example.localdb.room.SplitterDatabase
import com.example.localdb.room.dao.PersonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun getDbName(): String = ROOM_DB

    @Singleton
    @Provides
    fun getRoomDb(@ApplicationContext context: Context, name: String): SplitterDatabase =
        Room.databaseBuilder(context, SplitterDatabase::class.java, name).build()

    @Singleton
    @Provides
    fun getPersonDao(db: SplitterDatabase): PersonDao = db.getPersonDao()
}