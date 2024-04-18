package com.r0shka.visitedcountries.features.mainscreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.r0shka.visitedcountries.features.mainscreen.filter.CountryFilterCategoryUiModel
import com.r0shka.visitedcountries.ui.theme.Size

@Composable
fun CountryFilter(
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
@Preview()
fun CountryFilterPreview() {
    CountryFilter(filters = listOf(
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
    ), onFilterSelected = {})
}
