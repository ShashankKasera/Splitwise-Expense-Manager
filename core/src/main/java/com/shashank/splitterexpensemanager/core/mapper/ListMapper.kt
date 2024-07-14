package com.shashank.splitterexpensemanager.core.mapper

interface ListMapper<I, O> : Mapper<List<I>, List<O>>

interface NullableInputListMapper<I, O> : Mapper<List<I>?, List<O>>