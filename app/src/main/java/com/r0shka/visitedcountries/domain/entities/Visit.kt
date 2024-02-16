package com.r0shka.visitedcountries.domain.entities

import com.r0shka.visitedcountries.DbVisit
import java.util.UUID

data class Visit(
    val id: String = UUID.randomUUID().toString(),
    val countryCodeCca3: String,
    val visitedAt: Long,
) {
    fun toDatabase(): DbVisit = DbVisit(
        id = id,
        country_code_cca3 = countryCodeCca3,
        date = visitedAt,
    )
}

fun DbVisit.toDomain(): Visit = Visit(
    countryCodeCca3 = country_code_cca3,
    visitedAt = date,
)
