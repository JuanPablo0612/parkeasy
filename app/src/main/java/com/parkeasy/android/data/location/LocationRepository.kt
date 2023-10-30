package com.parkeasy.android.data.location

import com.google.android.gms.maps.model.LatLng
import com.parkeasy.android.data.location.remote.LocationRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * LocationRepository class.
 */
class LocationRepository @Inject constructor(private val remoteDataSource: LocationRemoteDataSource) {
    /**
     * Retrieves the current location from the remote data source.
     *
     * @return A [Flow] emitting the [LatLng] object representing the current location.
     */
    fun getCurrentLocation() = remoteDataSource.getCurrentLocation()
}