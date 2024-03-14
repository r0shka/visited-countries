package com.r0shka.visitedcountries.features.mainscreen

import com.r0shka.visitedcountries.R
import com.r0shka.visitedcountries.domain.entities.Continent

object PreviewData {
    val countries = listOf(
        CountryUiModel(
            localizedDisplayName = "France",
            flag = R.drawable.fr,
            visited = true,
            countryCodeCca3 = "",
            continent = Continent.EUROPE,
        ),
        CountryUiModel(
            localizedDisplayName = "Austria",
            flag = R.drawable.at,
            visited = true,
            countryCodeCca3 = "",
            continent = Continent.EUROPE,
        ),
        CountryUiModel(
            localizedDisplayName = "Denmark",
            flag = R.drawable.dk,
            visited = true,
            countryCodeCca3 = "",
            continent = Continent.EUROPE,
        ),
        CountryUiModel(
            localizedDisplayName = "Japan",
            flag = R.drawable.jp,
            visited = false,
            countryCodeCca3 = "",
            continent = Continent.ASIA,
        ),
        CountryUiModel(
            localizedDisplayName = "Australia",
            flag = R.drawable.au,
            visited = false,
            countryCodeCca3 = "",
            continent = Continent.OCEANIA,
        ),
        CountryUiModel(
            localizedDisplayName = "Canada",
            flag = R.drawable.ca,
            visited = false,
            countryCodeCca3 = "",
            continent = Continent.NORTH_AMERICA,
        )
    )
}