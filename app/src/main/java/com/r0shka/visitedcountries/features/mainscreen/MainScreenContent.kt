package com.r0shka.visitedcountries.features.mainscreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.r0shka.visitedcountries.R
import com.r0shka.visitedcountries.features.mainscreen.filter.CountryFilterCategoryUiModel
import com.r0shka.visitedcountries.ui.theme.Size
import com.r0shka.visitedcountries.ui.theme.Spacing


private val filter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(
    state: ViewState,
    paddingValues: PaddingValues,
    onCountrySelected: (Boolean, String) -> Unit,
    onFilterSelected: (FilterCategory) -> Unit,
) {
    when (state) {
        ViewState.Error -> {}
        ViewState.Loading -> {}
        is ViewState.Success -> {
            val visitedCountryCount: Int by animateIntAsState(targetValue = state.filteredVisitedCountriesNumber, label = "")
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .animateContentSize(),

                ) {
                item {
                    Column {
                        Spacer(modifier = Modifier.height(Size.size8))
                        CountryFilter(state.filters, onFilterSelected)
                        Spacer(modifier = Modifier.height(Size.size16))
                        MapOverview(state)
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
    }
}

@Composable
private fun CountryFilter(
    filters: List<CountryFilterCategoryUiModel>,
    onFilterSelected: (FilterCategory) -> Unit,
) {
    val scrollState = rememberScrollState()
    Row(modifier = Modifier.horizontalScroll(scrollState)) {
        Spacer(modifier = Modifier.width(Size.size16))
        filters.forEach {
            FilterChip(
                selected = it.selected,
                onClick = { onFilterSelected(it.filterCategory) },
                label = {
                    Text(text = it.filterName)
                },
                leadingIcon = if (it.selected) {
                    {
                        Icon(
                            imageVector = Icons.Rounded.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
            Spacer(modifier = Modifier.width(Size.size8))
        }
        Spacer(modifier = Modifier.width(Size.size8))
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

@Composable
@Preview(showBackground = true)
fun MainScreenContentSuccessPreview() {
    MainScreenContent(
        ViewState.Success(
            filteredCountries = PreviewData.countries,
            allCountries = PreviewData.countries,
            allVisitedCountriesNumber = 3,
            filteredVisitedCountriesNumber = 4,
            filters = listOf(
                CountryFilterCategoryUiModel(
                    filterCategory = FilterCategory.ALL,
                    selected = true,
                    filterName = "All"
                ),
                CountryFilterCategoryUiModel(
                    filterCategory = FilterCategory.AFRICA,
                    selected = false,
                    filterName = "Africa"
                ),
            ),
        ),
        paddingValues = PaddingValues(),
        onCountrySelected = { _, _ -> },
        onFilterSelected = { _ -> }
    )
}
