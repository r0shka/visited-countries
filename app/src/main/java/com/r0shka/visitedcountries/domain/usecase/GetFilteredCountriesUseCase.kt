package com.r0shka.visitedcountries.domain.usecase

import com.r0shka.visitedcountries.domain.entities.Continent
import com.r0shka.visitedcountries.features.mainscreen.CountryUiModel
import com.r0shka.visitedcountries.features.mainscreen.FilterCategory

class GetFilteredCountriesUseCase() {
    private val euCca3CountryCodes = listOf(
        "AUT",
        "BEL",
        "BGR",
        "HRV",
        "CYP",
        "CZE",
        "DNK",
        "EST",
        "FIN",
        "FRA",
        "DEU",
        "GRC",
        "HUN",
        "IRL",
        "ITA",
        "LVA",
        "LTU",
        "LUX",
        "MLT",
        "NLD",
        "POL",
        "PRT",
        "ROU",
        "SVK",
        "SVN",
        "ESP",
        "SWE",
    )

    operator fun invoke(selectedFilter: FilterCategory, fullCountryList: List<CountryUiModel>): List<CountryUiModel> {
        return when (selectedFilter) {
            FilterCategory.ALL -> fullCountryList
            FilterCategory.EUROPE -> fullCountryList.filter {
                it.continent == Continent.EUROPE
            }

            FilterCategory.ASIA -> fullCountryList.filter {
                it.continent == Continent.ASIA
            }

            FilterCategory.AFRICA -> fullCountryList.filter {
                it.continent == Continent.AFRICA
            }

            FilterCategory.NORTH_AMERICA -> fullCountryList.filter {
                it.continent == Continent.NORTH_AMERICA
            }

            FilterCategory.SOUTH_AMERICA -> fullCountryList.filter {
                it.continent == Continent.SOUTH_AMERICA
            }

            FilterCategory.OCEANIA -> fullCountryList.filter {
                it.continent == Continent.OCEANIA
            }

            FilterCategory.EU -> fullCountryList.filter { country ->
                euCca3CountryCodes.any {
                    it == country.countryCodeCca3
                }
            }
        }
    }
}