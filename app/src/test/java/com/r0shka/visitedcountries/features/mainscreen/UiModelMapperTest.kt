package com.r0shka.visitedcountries.features.mainscreen

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.r0shka.visitedcountries.R
import com.r0shka.visitedcountries.domain.entities.Continent
import com.r0shka.visitedcountries.domain.entities.Country
import com.r0shka.visitedcountries.domain.entities.DrivingSide
import com.r0shka.visitedcountries.domain.entities.Region
import com.r0shka.visitedcountries.domain.entities.Visit
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Locale


@RunWith(RobolectricTestRunner::class)
class UiModelMapperTest {

    private val context: Application = ApplicationProvider.getApplicationContext()
    private val mapper = UiModelMapper(context)

    @Test
    fun `Given english locale When visited AT country mapped Then name and flag are correct`() {
        // Given
        Locale.setDefault(Locale("en", "US"))

        // When
        val actual = mapper.mapCountry(country, visit)
        val expected = CountryUiModel(
            localizedDisplayName = "Austria",
            flag = R.drawable.at,
            visited = true
        )

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `Given country french locale When not visited US country mapped Then name and flag are correct`() {
        // Given
        Locale.setDefault(Locale("fr", "FR"))

        // When
        val actual = mapper.mapCountry(country.copy(codeCca2 = "us"), null)
        val expected = CountryUiModel(
            localizedDisplayName = "États-Unis",
            flag = R.drawable.us,
            visited = false
        )

        // Then
        Assert.assertEquals(expected, actual)
    }

    companion object {
        val country = Country(
            codeCca2 = "AT",
            codeCca3 = "AUT",
            isIndependent = true,
            isUnMember = true,
            population = 9000000,
            drivingSide = DrivingSide.LEFT,
            continent = Continent.EUROPE,
            region = Region.CENTRAL_EUROPE,
        )
        val visit = Visit(
            countryCodeCca3 = "AUT",
            visitedAt = 12345678,
        )
    }
}