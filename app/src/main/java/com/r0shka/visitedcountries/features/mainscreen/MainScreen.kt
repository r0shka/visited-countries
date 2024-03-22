package com.r0shka.visitedcountries.features.mainscreen

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.r0shka.visitedcountries.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: ViewState,
    onCountrySelected: (Boolean, String) -> Unit,
    onFilterSelected: (FilterCategory) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        stringResource(id = R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { paddingValues ->
        MainScreenContent(
            state = state,
            paddingValues = paddingValues,
            onCountrySelected = { checked, countryCodeCca3 ->
                onCountrySelected(checked, countryCodeCca3)
            },
            onFilterSelected = { onFilterSelected(it) }
        )
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    MainScreen(
        ViewState.Success(
            allCountries = PreviewData.countries,
            filteredCountries = PreviewData.countries,
            allVisitedCountriesNumber = 196,
            filteredVisitedCountriesNumber = 30,
            filters = emptyList(),
        ),
        onCountrySelected = { _, _ -> Pair(true, "test") },
        onFilterSelected = { _ -> }
    )
}