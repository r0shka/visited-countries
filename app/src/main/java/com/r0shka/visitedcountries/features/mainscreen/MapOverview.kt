package com.r0shka.visitedcountries.features.mainscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle
import com.r0shka.visitedcountries.R

private const val POSITION_ANIMATION_DURATION = 1000

private val africaLatLng = LatLng(0.0, 10.2966665)
private val europeLatLng = LatLng(50.0, 20.0)
private val asiaLatLng = LatLng(30.0, 85.0)
private val oceaniaLatLng = LatLng(-26.0, 150.0)
private val northAmericaLatLng = LatLng(40.0, -90.0)
private val southAmericaLatLng = LatLng(-20.0, -65.0)

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapOverview(
    state: ViewState.Success,
    modifier: Modifier,
) {
    val visitedCountryFillColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f).toArgb()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(europeLatLng, 0f)
    }

    val visitedCountriesCodeList: List<String> = state.allCountries.mapNotNull {
        if (it.visited) {
            it.countryCodeCca3
        } else {
            null
        }
    }

    var layer: GeoJsonLayer? by remember {
        mutableStateOf(null)
    }
    val selectedFilter = state.filters.first { it.selected }

    // move camera when a region filter changes
    LaunchedEffect(selectedFilter) {
        when (selectedFilter.filterCategory) {
            FilterCategory.EUROPE -> cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLng(europeLatLng),
                durationMs = POSITION_ANIMATION_DURATION,
            )

            FilterCategory.ASIA -> cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLng(asiaLatLng),
                durationMs = POSITION_ANIMATION_DURATION,
            )

            FilterCategory.AFRICA -> cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLng(africaLatLng),
                durationMs = POSITION_ANIMATION_DURATION,
            )

            FilterCategory.NORTH_AMERICA -> cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLng(northAmericaLatLng),
                durationMs = POSITION_ANIMATION_DURATION,
            )

            FilterCategory.SOUTH_AMERICA -> cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLng(southAmericaLatLng),
                durationMs = POSITION_ANIMATION_DURATION,
            )

            FilterCategory.OCEANIA -> cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLng(oceaniaLatLng),
                durationMs = POSITION_ANIMATION_DURATION,
            )

            FilterCategory.EU -> cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLng(europeLatLng),
                durationMs = POSITION_ANIMATION_DURATION,
            )

            else -> {}
        }
    }

    // update the fill color when country becomes (un)visited
    LaunchedEffect(state.allVisitedCountriesNumber) {
        layer?.let {
            it.features.forEach { feature ->
                feature.polygonStyle = GeoJsonPolygonStyle().apply {
                    this.strokeWidth = 3f
                    if (visitedCountriesCodeList.contains(feature.getProperty("ISO_A3"))) {
                        this.fillColor = visitedCountryFillColor
                    } else {
                        this.fillColor = 0
                    }
                }
            }
        }
    }
    val context = LocalContext.current

    GoogleMap(
        modifier = modifier.then(
            Modifier
                .fillMaxSize()
        ),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            compassEnabled = false,
            tiltGesturesEnabled = false,
            rotationGesturesEnabled = false,
            myLocationButtonEnabled = false,
            mapToolbarEnabled = false,
            zoomControlsEnabled = false,
            zoomGesturesEnabled = false,
        ),
        properties = MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_dark),
        ),
        // TODO uncomment in case of publishing to show Google logo on maps
        // contentPadding = PaddingValues(bottom = Spacing.spacing48)
    ) {
        MapEffect { map ->
            layer = GeoJsonLayer(map, R.raw.borders, context)

            layer?.features?.forEach { feature ->
                feature.polygonStyle = GeoJsonPolygonStyle().apply {
                    this.strokeWidth = 3f
                    if (visitedCountriesCodeList.contains(feature.getProperty("ISO_A3"))) {
                        this.fillColor = visitedCountryFillColor
                    }
                }
            }
            layer?.addLayerToMap()
        }
    }
}