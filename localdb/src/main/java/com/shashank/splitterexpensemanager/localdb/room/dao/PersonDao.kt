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
    fun loadAllPersonFlow(): Flow<List<Person>>

    @Query("Select * from Person")
    fun loadAllPerson(): List<Person>

    @Query("Select * from Person where Person.emailId = :email")
    fun loadPersonByEmail(email: String): Flow<Person>

    @Query("Select * from Person where Person.id = :personId")
    fun loadPersonByIdFlow(personId: Long): Flow<Person>

    @Query("Select * from Person where Person.id = :personId")
    fun loadPersonById(personId: Long): Person

    @Query("SELECT * FROM Person WHERE Person.id != :personId")
    fun getAllPersonsExceptFlow(personId: Long): Flow<List<Person>>

    @Query("SELECT * FROM Person WHERE Person.id != :personId")
    fun getAllPersonsExcept(personId: Long): List<Person>
}