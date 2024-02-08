package com.r0shka.visitedcountries.domain.entities

data class Country(
    val codeCca3: String,
    val codeCca2: String,
    val isIndependent: Boolean,
    val isUnMember: Boolean,
    val population: Long,
    val drivingSide: DrivingSide,
    val continent: Continent,
    val region: Region
    )

enum class DrivingSide {
    LEFT,
    RIGHT,
}

enum class Continent {
    EUROPE,
    ASIA,
    NORTH_AMERICA,
    SOUTH_AMERICA,
    AFRICA,
    OCEANIA,
    ANTARCTICA,
}

enum class Region {
    SOUTHERN_EUROPE,
    NORTHERN_EUROPE,
    WESTERN_EUROPE,
    SOUTHEAST_EUROPE,
    EASTERN_EUROPE,
    CENTRAL_EUROPE,
    NORTH_AMERICA,
    CENTRAL_AMERICA,
    CARIBBEAN,
    SOUTH_AMERICA,
    WESTERN_AFRICA,
    EASTERN_AFRICA,
    MIDDLE_AFRICA,
    SOUTHERN_AFRICA,
    SOUTH_EASTERN_ASIA,
    WESTERN_ASIA,
    SOUTHERN_ASIA,
    EASTERN_ASIA,
    CENTRAL_ASIA,
    MELANESIA,
    POLYNESIA,
    MICRONESIA,
    AUSTRALIA_AND_NEW_ZEALAND
}