package com.shashank.splitterexpensemanager.core.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}