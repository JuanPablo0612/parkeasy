package com.parkeasy.android.data.location

import com.parkeasy.android.data.location.remote.LocationRemoteDataSource
import javax.inject.Inject

class LocationRepository @Inject constructor(private val remoteDataSource: LocationRemoteDataSource) {
    fun getCurrentLocation() = remoteDataSource.getCurrentLocation()
}