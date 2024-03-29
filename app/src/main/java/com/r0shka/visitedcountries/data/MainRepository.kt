package com.r0shka.visitedcountries.data

import com.r0shka.visitedcountries.domain.entities.Country
import com.r0shka.visitedcountries.domain.entities.Visit
import com.r0shka.visitedcountries.domain.entities.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(
    private val dataSource: LocalDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getAllCountries(): Result<List<Country>> = withContext(dispatcher) {
        try {
            Result.Success(dataSource.getAllCountries(independentOnly = true).executeAsList().map {
                it.toDomain()
            })
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    suspend fun getAllVisits(): Result<List<Visit>> = withContext(dispatcher) {
        try {
            Result.Success(dataSource.getAllVisits().executeAsList().map {
                it.toDomain()
            })
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    suspend fun addVisit(visit: Visit): Result<Unit> = withContext(dispatcher) {
        try {
            Result.Success(dataSource.addVisit(visit.toDatabase()))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    suspend fun removeVisit(countryCodeCca3: String): Result<Unit> = withContext(dispatcher) {
        try {
            Result.Success(dataSource.removeVisit(countryCodeCca3))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

}