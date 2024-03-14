package com.r0shka.visitedcountries.features.mainscreen

import android.annotation.SuppressLint
import android.content.Context
import com.r0shka.visitedcountries.R
import com.r0shka.visitedcountries.domain.entities.Country
import com.r0shka.visitedcountries.domain.entities.Visit
import java.util.Locale

class UiModelMapper(context: Context) {

    private val context = context.applicationContext

    fun mapCountry(country: Country, visit: Visit?): CountryUiModel = CountryUiModel(
        localizedDisplayName = getLocalizedDisplayName(country.codeCca2),
        flag = getFlag(country.codeCca2),
        visited = visit != null,
        countryCodeCca3 = country.codeCca3,
        continent = country.continent,
    )

    private fun getLocalizedDisplayName(countryCode: String): String {
        val locale = Locale(Locale.getDefault().isO3Language, countryCode)
        return locale.displayCountry
    }

    @SuppressLint("DiscouragedApi")
    private fun getFlag(countryCode: String): Int {
        val validCountryCode = if (countryCode == "DO") {   // DO is not valid xml name, so icon was renamed to dom.xml
            "dom"
        } else {
            countryCode.lowercase()
        }

        // ideally we should use resource identifier rather than the name, but who has the time mapping 200 flags?
        val result = context.resources.getIdentifier(validCountryCode, "drawable", context.packageName)

        return if (result == 0) {
            println("flag not found")
            R.drawable.notfound
        } else {
            result
        }
    }
}