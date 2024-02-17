package com.shashank.splitterexpensemanager.authentication.personmapper

import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.localdb.model.Person as PersonEntity
import com.shashank.splitterexpensemanager.authentication.model.Person
import javax.inject.Inject

class PersonMapper @Inject constructor() : Mapper<PersonEntity, Person> {
    override fun map(input: PersonEntity) = Person(
        id = input.id ?: 0,
        name = input.name ?: "",
        emailId = input.emailId ?: "",
        number = input.number ?: ""
    )
}