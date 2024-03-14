package com.r0shka.visitedcountries.features.mainscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.r0shka.visitedcountries.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = viewModel.viewState().collectAsState()
            AppTheme {
                MainScreen(
                    state = state.value,
                    onCountrySelected = { checked, countryCodeCca3 ->
                        viewModel.onVisitUpdated(countryCodeCca3 = countryCodeCca3, visited = checked)
                    },
                    onFilterSelected = {
                        viewModel.onFilterSelected(it)
                    }
                )
            }
        }
    }
}
