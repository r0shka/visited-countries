package com.r0shka.visitedcountries.features.mainscreen.filter

import com.r0shka.visitedcountries.features.mainscreen.FilterCategory

data class CountryFilterCategoryUiModel(
    val filterCategory: FilterCategory,
    val selected: Boolean,
    val filterName: String,
)
