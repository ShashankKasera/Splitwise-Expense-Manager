package com.example.localdb.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.localdb.model.Person
import com.example.localdb.room.dao.PersonDao

@Database(entities = [Person::class], version = 2, exportSchema = false)
abstract class SplitterDatabase : RoomDatabase() {

    abstract fun getPersonDao(): PersonDao
}