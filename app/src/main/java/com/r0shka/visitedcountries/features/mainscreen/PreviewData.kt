package com.r0shka.visitedcountries.features.mainscreen

import com.r0shka.visitedcountries.R

object PreviewData {
    val countriesVisited = listOf(
        CountryUiModel(
            localizedDisplayName = "France",
            flag = R.drawable.fr,
            visited = true,
        ),
        CountryUiModel(
            localizedDisplayName = "Austria",
            flag = R.drawable.at,
            visited = true,
        ),
        CountryUiModel(
            localizedDisplayName = "Denmark",
            flag = R.drawable.dk,
            visited = true,
        ),
    )

    val countriesAvailable = listOf(
        CountryUiModel(
            localizedDisplayName = "Japan",
            flag = R.drawable.jp,
            visited = false,
        ),
        CountryUiModel(
            localizedDisplayName = "Australia",
            flag = R.drawable.au,
            visited = false,
        ),
        CountryUiModel(
            localizedDisplayName = "Canada",
            flag = R.drawable.ca,
            visited = false,
        )
    )
}