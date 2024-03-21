package com.shashank.splitterexpensemanager.localdb.di

import android.content.Context
import androidx.room.Room
import com.shashank.splitterexpensemanager.core.ROOM_DB
import com.shashank.splitterexpensemanager.localdb.room.SplitterDatabase
import com.shashank.splitterexpensemanager.localdb.room.dao.CategoryDao
import com.shashank.splitterexpensemanager.localdb.room.dao.ExpensesDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import com.shashank.splitterexpensemanager.localdb.room.dao.RepayDao
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
        Room.databaseBuilder(context, SplitterDatabase::class.java, name)
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun getPersonDao(db: SplitterDatabase): PersonDao = db.getPersonDao()

    @Singleton
    @Provides
    fun getCategoryDao(db: SplitterDatabase): CategoryDao = db.getCategoryDao()

    @Singleton
    @Provides
    fun getGroupDao(db: SplitterDatabase): GroupDao = db.getGroupDao()

    @Singleton
    @Provides
    fun getGroupMemberDao(db: SplitterDatabase): GroupMemberDao = db.getGroupMemberDao()

    @Singleton
    @Provides
    fun getExpensesDao(db: SplitterDatabase): ExpensesDao = db.getExpensesDao()

    @Singleton
    @Provides
    fun getOweOrOwedDao(db: SplitterDatabase): OweOrOwedDao = db.getOweOrOwedDao()

    @Singleton
    @Provides
    fun getRepayDao(db: SplitterDatabase): RepayDao = db.getRepayDao()
}

