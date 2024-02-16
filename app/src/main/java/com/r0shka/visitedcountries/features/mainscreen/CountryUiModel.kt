package com.r0shka.visitedcountries.features.mainscreen

import androidx.annotation.DrawableRes

data class CountryUiModel(
    val localizedDisplayName: String,
    @DrawableRes
    val flag: Int,
    val visited: Boolean,
)
