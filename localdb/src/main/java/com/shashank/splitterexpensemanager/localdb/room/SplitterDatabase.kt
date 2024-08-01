package com.shashank.splitterexpensemanager.localdb.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shashank.splitterexpensemanager.localdb.model.Category
import com.shashank.splitterexpensemanager.localdb.model.Expenses
import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed
import com.shashank.splitterexpensemanager.localdb.model.Person
import com.shashank.splitterexpensemanager.localdb.model.Repay
import com.shashank.splitterexpensemanager.localdb.room.dao.CategoryDao
import com.shashank.splitterexpensemanager.localdb.room.dao.ExpensesDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import com.shashank.splitterexpensemanager.localdb.room.dao.RepayDao

@Database(
    entities = [
        Person::class,
        Category::class,
        Group::class,
        GroupMember::class,
        Expenses::class,
        OweOrOwed::class,
        Repay::class
    ],
    version = 19,
    exportSchema = false
)
abstract class SplitterDatabase : RoomDatabase() {

    abstract fun getPersonDao(): PersonDao

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getGroupDao(): GroupDao

    abstract fun getGroupMemberDao(): GroupMemberDao

    abstract fun getExpensesDao(): ExpensesDao

    abstract fun getOweOrOwedDao(): OweOrOwedDao

    abstract fun getRepayDao(): RepayDao
}