package com.shashank.splitterexpensemanager.localdb.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shashank.splitterexpensemanager.localdb.model.Category
import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.localdb.model.Person
import com.shashank.splitterexpensemanager.localdb.room.dao.CategoryDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao

@Database(
    entities = [Person::class, Category::class, Group::class],
    version = 4,
    exportSchema = false
)
abstract class SplitterDatabase : RoomDatabase() {

    abstract fun getPersonDao(): PersonDao

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getGroupDao(): GroupDao
}