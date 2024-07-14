package com.shashank.splitterexpensemanager.feature.repaydetails.repository

import com.shashank.splitterexpensemanager.localdb.room.dao.RepayDao
import com.shashank.splitterexpensemanager.mapper.repaywithpersonmapper.RepayWithPersonMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepayDetailsRepositoryImp @Inject constructor(
    private val repayDao: RepayDao,
    private val repayWithPersonMapper: RepayWithPersonMapper
) : RepayDetailsRepository {


    override suspend fun deleteRepay(repayId: Long) = withContext(Dispatchers.IO) {
        repayDao.deleteRepay(repayId)
    }

    override fun loadRepayByRepayId(repayId: Long) = repayDao.loadRepayByRepayId(repayId).map {
        repayWithPersonMapper.map(it)
    }
}