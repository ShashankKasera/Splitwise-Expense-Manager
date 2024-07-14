package com.shashank.splitterexpensemanager.authentication.personmapper

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.extension.EMPTY
import com.shashank.splitterexpensemanager.core.mapper.Mapper
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.Person as PersonEntity

class PersonMapper @Inject constructor() : Mapper<PersonEntity?, Person> {
    override fun map(input: PersonEntity?) = Person(
        id = input?.id ?: 0,
        name = input?.name ?: String.EMPTY,
        emailId = input?.emailId ?: String.EMPTY,
        number = input?.number ?: String.EMPTY,
        gender = input?.gender ?: String.EMPTY
    )
}