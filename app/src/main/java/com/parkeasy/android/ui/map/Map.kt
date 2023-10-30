package com.parkeasy.android.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

/**
 * A composable function that displays a Google Map with specified properties and UI settings.
 *
 * @param modifier The modifier to be applied to the map.
 */
@Composable
fun Map(modifier: Modifier) {
    GoogleMap(
        modifier = Modifier,
        properties = MapProperties(
            isMyLocationEnabled = true,
            mapType = MapType.NORMAL
        ),
        uiSettings = MapUiSettings(myLocationButtonEnabled = true)
    )
}