package com.r0shka.visitedcountries.features.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.r0shka.visitedcountries.data.LocalDataSource
import com.r0shka.visitedcountries.data.MainRepository
import com.r0shka.visitedcountries.data.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: MainRepository,
    private val mapper: UiModelMapper,
) : ViewModel() {

    init {
        load()
    }

    private val countries = mutableListOf<CountryUiModel>()

    private val viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    fun viewState(): StateFlow<ViewState> = viewState.asStateFlow()

    private fun load() = viewModelScope.launch {
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
                    viewState.value = ViewState.Success(
                        visitedCountries = countries
                            .toList()
                            .sortedBy {
                                it.localizedDisplayName
                            }
                            .filter {
                                it.visited
                            },
                        availableCountries = countries
                            .toList()
                            .sortedBy {
                                it.localizedDisplayName
                            }
                            .filter {
                                it.visited.not()
                            },
                    )
                } else {
                    viewState.value = ViewState.Empty
                }
            }

            else -> viewState.value = ViewState.Error
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])

                return MainViewModel(
                    repo = MainRepository(
                        dataSource = LocalDataSource(context = application.applicationContext)
                    ),
                    mapper = UiModelMapper(context = application.applicationContext),
                ) as T
            }
        }
    }

}

sealed class ViewState {
    data object Loading : ViewState()
    data object Empty : ViewState()
    data class Success(
        val visitedCountries: List<CountryUiModel>,
        val availableCountries: List<CountryUiModel>,
    ) : ViewState()

    data object Error : ViewState()
}