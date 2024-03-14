package com.r0shka.visitedcountries.data

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.r0shka.visitedcountries.Database
import com.r0shka.visitedcountries.DbVisit

class LocalDataSource(
    context: Context,
    database: Database = Database(
        driver = AndroidSqliteDriver(
            schema = Database.Schema,
            context = context.applicationContext,
            name = "visited_countries"
        ),
    )
) {
    private val countryQueries = database.countryQueries
    private val visitQueries = database.visitQueries

    fun getAllCountries(independentOnly: Boolean) = if (independentOnly) {
        countryQueries.selectAllIndependent()
    } else {
        countryQueries.selectAll()
    }

    fun getAllVisits() = visitQueries.selectAll()

    fun addVisit(visit: DbVisit) {
        visitQueries.insert(visit)
    }

    fun removeVisit(countryCodeCca3: String) {
        visitQueries.deleteOneByCountryCode(countryCodeCca3)
    }
}