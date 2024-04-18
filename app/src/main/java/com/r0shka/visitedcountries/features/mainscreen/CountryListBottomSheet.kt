package com.r0shka.visitedcountries.features.mainscreen

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.r0shka.visitedcountries.R
import com.r0shka.visitedcountries.ui.theme.Size
import com.r0shka.visitedcountries.ui.theme.Spacing

private val filter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryListBottomSheet(
    state: ViewState.Success,
    onCountrySelected: (Boolean, String) -> Unit,
) {
    val visitedCountryCount: Int by animateIntAsState(targetValue = state.filteredVisitedCountriesNumber, label = "")
    LazyColumn {
        item {
            Column {

                Text(
                    modifier = Modifier.padding(horizontal = Spacing.spacing16),
                    text = stringResource(id = R.string.main_screen_you_visited),
                    style = MaterialTheme.typography.titleMedium,
                )
                Row(modifier = Modifier.padding(horizontal = Spacing.spacing16)) {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = visitedCountryCount.toString(),
                        style = MaterialTheme.typography.displayLarge,
                    )
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = " countries",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
                Spacer(modifier = Modifier.height(Size.size16))
            }
        }

        items(
            items = state.filteredCountries,
            key = { it.countryCodeCca3 }
        ) { country ->

            CountryItem(
                modifier = Modifier.animateItemPlacement(),
                model = country,
                onCountrySelected = { checked, countryCodeCca3 ->
                    onCountrySelected(checked, countryCodeCca3)
                },
            )
        }

        item {
            Spacer(modifier = Modifier.height(Spacing.spacing8))
        }
    }
}

@Composable
private fun CountryItem(
    modifier: Modifier = Modifier,
    model: CountryUiModel,
    onCountrySelected: (Boolean, String) -> Unit
) {
    Card(
        onClick = {},
        modifier = Modifier
            .padding(vertical = Spacing.spacing4, horizontal = Spacing.spacing16)
            .then(modifier),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (model.visited) Size.size4 else 0.dp
        ),
        enabled = model.visited
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.spacing16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(Size.size24),
                painter = painterResource(id = model.flag),
                contentDescription = "",
                colorFilter = if (model.visited) null else filter,
            )
            Spacer(modifier = Modifier.width(Spacing.spacing16))
            Text(
                modifier = Modifier.weight(1f),
                text = model.localizedDisplayName,
                style = MaterialTheme.typography.bodyMedium,
            )
            Checkbox(
                checked = model.visited,
                onCheckedChange = {
                    onCountrySelected(it, model.countryCodeCca3)
                }
            )
        }
    }
}