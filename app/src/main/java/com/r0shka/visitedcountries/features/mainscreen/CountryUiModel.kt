package com.r0shka.visitedcountries.features.mainscreen

import androidx.annotation.DrawableRes
import com.r0shka.visitedcountries.domain.entities.Continent

data class CountryUiModel(
    val localizedDisplayName: String,
    @DrawableRes
    val flag: Int,
    val visited: Boolean,
    val countryCodeCca3: String,
    val continent: Continent,
)
