package com.shashank.splitterexpensemanager.authentication.personmapper

import com.shashank.splitterexpensemanager.core.mapper.ListMapper
import com.shashank.splitterexpensemanager.localdb.model.Person as PersonEntity
import com.shashank.splitterexpensemanager.authentication.model.Person
import javax.inject.Inject

class PersonListMapper @Inject constructor(
    private val personMapper: PersonMapper
) : ListMapper<PersonEntity, Person> {
    override fun map(input: List<PersonEntity>): List<Person> {
        return input.map { personMapper.map(it) }
    }
}