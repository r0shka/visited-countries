package com.r0shka.visitedcountries.features.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.r0shka.visitedcountries.ui.theme.Size
import com.r0shka.visitedcountries.ui.theme.Spacing

@Composable
fun MainScreenContent(state: ViewState) {
    when (state) {
        ViewState.Empty -> {}
        ViewState.Error -> {}
        ViewState.Loading -> {}
        is ViewState.Success -> {
            LazyColumn {
                items(state.availableCountries) { country ->
                    Row {
                        Image(
                            modifier = Modifier.size(Size.size24),
                            painter = painterResource(id = country.flag),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.height(Spacing.spacing16))
                        Text(text = country.localizedDisplayName)
                    }
                }
            }
        }
    }
}