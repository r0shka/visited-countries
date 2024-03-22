package com.r0shka.visitedcountries.features.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.r0shka.visitedcountries.data.LocalDataSource
import com.r0shka.visitedcountries.data.MainRepository
import com.r0shka.visitedcountries.data.Result
import com.r0shka.visitedcountries.domain.entities.Visit
import com.r0shka.visitedcountries.domain.usecase.GetFilteredCountriesUseCase
import com.r0shka.visitedcountries.features.mainscreen.filter.CountryFilterCategoryUiModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: MainRepository,
    private val mapper: UiModelMapper,
    private val getFilteredCountriesUseCase: GetFilteredCountriesUseCase,
) : ViewModel() {

    init {
        load()
    }

    private val fullCountryList = mutableListOf<CountryUiModel>()
    private var selectedFilter: FilterCategory = FilterCategory.ALL

    private val viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    fun viewState(): StateFlow<ViewState> = viewState.asStateFlow()

    fun onVisitUpdated(countryCodeCca3: String, visited: Boolean) {
        val indexOfCountry = fullCountryList
            .indexOfFirst { it.countryCodeCca3 == countryCodeCca3 }
        fullCountryList[indexOfCountry] = fullCountryList[indexOfCountry].copy(visited = visited)
        updateVisit(countryCodeCca3 = countryCodeCca3, visited = visited)
        updateState()
    }

    fun onFilterSelected(filter: FilterCategory) {
        selectedFilter = filter
        updateState()
    }

    private fun load() = viewModelScope.launch {
        val countriesResult = async { repo.getAllCountries() }.await()
        val visitsResult = async { repo.getAllVisits() }.await()

        when {
            countriesResult is Result.Success && visitsResult is Result.Success -> {
                countriesResult.value.map { country ->
                    fullCountryList.add(
                        mapper.mapCountry(
                            country = country,
                            visit = visitsResult.value.find { visit ->
                                visit.countryCodeCca3 == country.codeCca3
                            })
                    )
                }
                updateState()
            }

            else -> viewState.value = ViewState.Error
        }
    }

    private fun updateState() {
        val filteredCountries = getFilteredCountriesUseCase(selectedFilter, fullCountryList)
        viewState.value = ViewState.Success(
            filteredCountries = filteredCountries
                .sortedWith(
                    compareByDescending<CountryUiModel> { it.visited }
                        .thenBy { it.localizedDisplayName }
                ),
            allCountries = fullCountryList,
            filteredVisitedCountriesNumber = filteredCountries.filter { it.visited }.size,
            allVisitedCountriesNumber = fullCountryList.filter { it.visited }.size,
            filters = getFilters(),
        )
    }

    private fun updateVisit(countryCodeCca3: String, visited: Boolean) = viewModelScope.launch {
        if (visited) {
            repo.addVisit(Visit(countryCodeCca3 = countryCodeCca3, visitedAt = System.currentTimeMillis()))
        } else {
            repo.removeVisit(countryCodeCca3 = countryCodeCca3)
        }
    }

    private fun getFilters(): List<CountryFilterCategoryUiModel> = listOf(
        CountryFilterCategoryUiModel(
            filterCategory = FilterCategory.ALL,
            selected = selectedFilter == FilterCategory.ALL,
            filterName = "All"
        ),
        CountryFilterCategoryUiModel(
            filterCategory = FilterCategory.AFRICA,
            selected = selectedFilter == FilterCategory.AFRICA,
            filterName = "Africa"
        ),
        CountryFilterCategoryUiModel(
            filterCategory = FilterCategory.ASIA,
            selected = selectedFilter == FilterCategory.ASIA,
            filterName = "Asia"
        ),
        CountryFilterCategoryUiModel(
            filterCategory = FilterCategory.EUROPE,
            selected = selectedFilter == FilterCategory.EUROPE,
            filterName = "Europe"
        ),
        CountryFilterCategoryUiModel(
            filterCategory = FilterCategory.NORTH_AMERICA,
            selected = selectedFilter == FilterCategory.NORTH_AMERICA,
            filterName = "North America"
        ),
        CountryFilterCategoryUiModel(
            filterCategory = FilterCategory.SOUTH_AMERICA,
            selected = selectedFilter == FilterCategory.SOUTH_AMERICA,
            filterName = "South America"
        ),
        CountryFilterCategoryUiModel(
            filterCategory = FilterCategory.OCEANIA,
            selected = selectedFilter == FilterCategory.OCEANIA,
            filterName = "Oceania"
        ),
        CountryFilterCategoryUiModel(
            filterCategory = FilterCategory.EU,
            selected = selectedFilter == FilterCategory.EU,
            filterName = "EU"
        ),
    )

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
                    getFilteredCountriesUseCase = GetFilteredCountriesUseCase()
                ) as T
            }
        }
    }
}

sealed class ViewState {
    data object Loading : ViewState()
    data class Success(
        val filters: List<CountryFilterCategoryUiModel>,
        val allCountries: List<CountryUiModel>,
        val filteredCountries: List<CountryUiModel>,
        val allVisitedCountriesNumber: Int,
        val filteredVisitedCountriesNumber: Int,
    ) : ViewState()

    data object Error : ViewState()
}

enum class FilterCategory {
    ALL,
    EUROPE,
    ASIA,
    AFRICA,
    NORTH_AMERICA,
    SOUTH_AMERICA,
    OCEANIA,
    EU,
}