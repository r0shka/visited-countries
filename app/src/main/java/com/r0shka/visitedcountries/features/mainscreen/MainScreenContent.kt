package com.r0shka.visitedcountries.features.mainscreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.r0shka.visitedcountries.R
import com.r0shka.visitedcountries.ui.theme.Size
import com.r0shka.visitedcountries.ui.theme.Spacing

private val topRoundedCornerShape = RoundedCornerShape(topStart = Size.size16, topEnd = Size.size16)
private val bottomRoundedCornerShape = RoundedCornerShape(bottomStart = Size.size16, bottomEnd = Size.size16)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(state: ViewState, paddingValues: PaddingValues) {
    when (state) {
        ViewState.Empty -> {}
        ViewState.Error -> {}
        ViewState.Loading -> {}
        is ViewState.Success -> {
            val visitedCountryCount: Int by animateIntAsState(targetValue = state.visitedCountries.size, label = "")
            var expanded by remember { mutableStateOf(false) }
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .animateContentSize(),
                contentPadding = PaddingValues(Spacing.spacing16)
            ) {
                item {
                    Row(
                        modifier = Modifier.padding(Spacing.spacing16),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            modifier = Modifier.alignByBaseline(),
                            text = "3",
                            style = MaterialTheme.typography.displayLarge,
                        )
                        Text(
                            modifier = Modifier.alignByBaseline(),
                            text = " / 3",
                            style = MaterialTheme.typography.headlineMedium,
                        )
                    }

                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainer,
                                shape = topRoundedCornerShape,
                            )
                            .padding(Spacing.spacing16),
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Visited",
                            style = MaterialTheme.typography.headlineLarge,
                        )
                        if (state.visitedCountries.isNotEmpty()) {
                            Text(
                                text = visitedCountryCount.toString(),
                                style = MaterialTheme.typography.headlineLarge,
                            )
                        }
                    }

                }
                if (state.visitedCountries.isEmpty()) {
                    item {
                        EmptyState()
                    }
                } else {
                    itemsIndexed(state.visitedCountries) { index, country ->
                        CountryItem(
                            country,
                            when (index) {
                                state.visitedCountries.size - 1 -> bottomRoundedCornerShape
                                else -> RectangleShape
                            },
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(Spacing.spacing16))
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainer,
                                shape = topRoundedCornerShape,
                            )
                            .padding(Spacing.spacing16)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                expanded = !expanded
                            }
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Available",
                            style = MaterialTheme.typography.headlineLarge,
                        )
                        Text(
                            text = state.availableCountries.size.toString(),
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }
                }

                itemsIndexed(
                    state.availableCountries
                        .take(
                            if (expanded)
                                state.availableCountries.size
                            else
                                7
                        ),
                    key = { _, country -> country.localizedDisplayName }
                ) { index, country ->
                    CountryItem(
                        country,
                        when (index) {
                            state.availableCountries.size - 1 -> bottomRoundedCornerShape
                            else -> RectangleShape
                        },
                    )
                }
                item {
                    if (expanded.not()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceContainer,
                                    shape = bottomRoundedCornerShape,
                                ),
                            text = "SHOW MORE"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CountryItem(
    model: CountryUiModel,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = shape,
            )
            .padding(horizontal = Spacing.spacing16, vertical = Spacing.spacing4)

    ) {
        Image(
            modifier = Modifier.size(Size.size24),
            painter = painterResource(id = model.flag),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(Spacing.spacing16))
        Text(
            text = model.localizedDisplayName,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = bottomRoundedCornerShape,
            )
            .padding(horizontal = Spacing.spacing16),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.mountain_flag_48),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            )
            Text(
                modifier = Modifier.padding(top = Spacing.spacing8, bottom = Spacing.spacing24),
                text = "Click on \"+\" to add your first visited place",
                style = MaterialTheme.typography.labelMedium,
            )
        }

    }
}

@Composable
@Preview
fun MainScreenContentSuccessPreview() {
    MainScreenContent(
        ViewState.Success(
            visitedCountries = PreviewData.countriesVisited,
            availableCountries = PreviewData.countriesAvailable,
        ),
        paddingValues = PaddingValues()
    )
}

@Composable
@Preview
fun MainScreenContentSuccessEmptyStatePreview() {
    MainScreenContent(
        ViewState.Success(
            visitedCountries = emptyList(),
            availableCountries = PreviewData.countriesAvailable,
        ),
        paddingValues = PaddingValues()
    )
}