package com.r0shka.visitedcountries.features.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.r0shka.visitedcountries.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: ViewState,
    onCountrySelected: (Boolean, String) -> Unit,
    onFilterSelected: (FilterCategory) -> Unit,
) {
    when (state) {
        ViewState.Error -> {}
        ViewState.Loading -> {}
        is ViewState.Success -> {

            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberStandardBottomSheetState()
            )

            BottomSheetScaffold(
                sheetContent = {
                    CountryListBottomSheet(
                        state = state,
                        onCountrySelected = onCountrySelected,
                    )
                },
                topBar = {
                    TopAppBar(
                        colors = topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text(
                                stringResource(id = R.string.app_name),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        scrollBehavior = scrollBehavior,
                    )
                },
                scaffoldState = bottomSheetScaffoldState,
                sheetPeekHeight = LocalConfiguration.current.screenHeightDp.dp / 3,
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                ) {
                    MapOverview(
                        state = state,
                        modifier = Modifier.height(
                            LocalConfiguration.current.screenHeightDp.dp * 2 / 3,
                        )
                    )
                    CountryFilter(filters = state.filters, onFilterSelected = onFilterSelected)
                }

            }
        }
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