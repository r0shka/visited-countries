package com.r0shka.visitedcountries.domain.entities

import java.util.UUID

data class Visit(
    val id: String = UUID.randomUUID().toString(),
    val countryCodeCca3: String,
    val visitedAt: Long,
)
