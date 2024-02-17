package com.r0shka.visitedcountries.domain.entities

import com.r0shka.visitedcountries.DbCountry

data class Country(
    val codeCca3: String,
    val codeCca2: String,
    val isIndependent: Boolean,
    val isUnMember: Boolean,
    val population: Long,
    val drivingSide: DrivingSide,
    val continent: Continent,
    val region: Region
) {
    fun toDatabase(): DbCountry = DbCountry(
        country_code_cca3 = codeCca3,
        country_code_cca2 = codeCca2,
        is_independent = if (isIndependent) 1 else 0,
        is_un_member = if (isUnMember) 1 else 0,
        population = population,
        driving_side = drivingSide.value,
        continent = continent.value,
        region = region.value,
        flag_svg_url = "",
    )
}

fun DbCountry.toDomain(): Country = Country(
    codeCca3 = country_code_cca3,
    codeCca2 = country_code_cca2,
    isIndependent = is_independent == 1L,
    isUnMember = is_un_member == 1L,
    population = population,
    drivingSide = DrivingSide.from(driving_side),
    continent = Continent.from(continent),
    region = Region.from(region),
)

enum class DrivingSide(val value: String) {
    LEFT("left"),
    RIGHT("right");

    companion object {
        infix fun from(value: String): DrivingSide = entries.first { it.value == value }
    }
}

enum class Continent(val value: String) {
    EUROPE("Europe"),
    ASIA("Asia"),
    NORTH_AMERICA("North America"),
    SOUTH_AMERICA("South America"),
    AFRICA("Africa"),
    OCEANIA("Oceania"),
    ANTARCTICA("Antarctica");

    companion object {
        infix fun from(value: String): Continent = entries.first { it.value == value }
    }
}

enum class Region(val value: String) {
    SOUTHERN_EUROPE("Southern Europe"),
    NORTHERN_EUROPE("Northern Europe"),
    WESTERN_EUROPE("Western Europe"),
    SOUTHEAST_EUROPE("Southeast Europe"),
    EASTERN_EUROPE("Eastern Europe"),
    CENTRAL_EUROPE("Central Europe"),
    NORTH_AMERICA("North America"),
    CENTRAL_AMERICA("Central America"),
    CARIBBEAN("Caribbean"),
    SOUTH_AMERICA("South America"),
    WESTERN_AFRICA("Western Africa"),
    EASTERN_AFRICA("Eastern Africa"),
    MIDDLE_AFRICA("Middle Africa"),
    SOUTHERN_AFRICA("Southern Africa"),
    NORTHERN_AFRICA("Northern Africa"),
    SOUTH_EASTERN_ASIA("South-Eastern Asia"),
    WESTERN_ASIA("Western Asia"),
    SOUTHERN_ASIA("Southern Asia"),
    EASTERN_ASIA("Eastern Asia"),
    CENTRAL_ASIA("Central Asia"),
    MELANESIA("Melanesia"),
    POLYNESIA("Polynesia"),
    MICRONESIA("Micronesia"),
    AUSTRALIA_AND_NEW_ZEALAND("Australia and New Zealand"),
    UNDEFINED("");

    companion object {
        infix fun from(value: String): Region = entries.firstOrNull { it.value == value } ?: UNDEFINED
    }
}