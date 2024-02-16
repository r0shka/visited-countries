package com.r0shka.visitedcountries.features.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r0shka.visitedcountries.data.MainRepository
import com.r0shka.visitedcountries.data.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: MainRepository,
    private val mapper: UiModelMapper
) : ViewModel() {

    private val countries = mutableListOf<CountryUiModel>()

    private val viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    fun viewState(): StateFlow<ViewState> = viewState.asStateFlow()

    fun load() = viewModelScope.launch {
        val countriesResult = async { repo.getAllCountries() }.await()
        val visitsResult = async { repo.getAllVisits() }.await()

        when {
            countriesResult is Result.Success && visitsResult is Result.Success -> {
                countriesResult.value.map { country ->
                    countries.add(
                        mapper.mapCountry(
                            country = country,
                            visit = visitsResult.value.find { visit ->
                                visit.countryCodeCca3 == country.codeCca3
                            })
                    )
                }
                if (countries.isNotEmpty()) {
                    viewState.value = ViewState.Success(countries)
                } else {
                    viewState.value = ViewState.Empty
                }
            }

            else -> viewState.value = ViewState.Error
        }
    }


}

sealed class ViewState {
    data object Loading : ViewState()
    data object Empty : ViewState()
    data class Success(
        val countries: List<CountryUiModel>
    ) : ViewState()

    data object Error : ViewState()
}