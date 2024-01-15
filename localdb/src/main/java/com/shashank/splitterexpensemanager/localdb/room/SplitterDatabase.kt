package com.shashank.splitterexpensemanager.localdb.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shashank.splitterexpensemanager.localdb.model.Person
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao

@Database(entities = [Person::class], version = 2, exportSchema = false)
abstract class SplitterDatabase : RoomDatabase() {

    abstract fun getPersonDao(): PersonDao
}