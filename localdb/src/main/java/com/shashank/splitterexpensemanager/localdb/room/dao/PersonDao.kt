package com.shashank.splitterexpensemanager.localdb.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shashank.splitterexpensemanager.localdb.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Insert
    suspend fun insertPerson(person: Person)

    @Update
    suspend fun upDatePerson(person: Person)

    @Delete
    suspend fun deletePerson(person: Person)

    @Insert
    fun insertAllPerson(vararg person: Person)

    @Query("Select * from Person")
    fun loadAllPerson(): Flow<List<Person>>

    @Query("Select * from Person where Person.emailId = :email")
    fun loadPersonByEmail(email: String): Flow<Person>
}