package com.parkeasy.android.data.location.remote

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/**
 * LocationRemoteDataSource class.
 */
class LocationRemoteDataSource @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
) {
    /**
     * Retrieves the current location using the device's fused location provider.
     * This method uses a callback flow to asynchronously emit the current location.
     *
     * @return A callback flow that emits the current location as a [LatLng] object.
     *
     * @see [callbackFlow]
     * @see [ContextCompat.checkSelfPermission]
     * @see [PackageManager.PERMISSION_GRANTED]
     * @see [LocationRequest.Builder]
     * @see [LocationCallback]
     * @see [LocationResult]
     * @see [Location]
     * @see [LatLng]
     * @see [fusedLocationClient.requestLocationUpdates]
     * @see [Looper.getMainLooper]
     * @see [awaitClose]
     * @see [fusedLocationClient.removeLocationUpdates]
     */
    fun getCurrentLocation() = callbackFlow {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            val locationRequest = LocationRequest.Builder(3000).build()

            val locationCallback = object : LocationCallback() {
                /**
                 * Overrides the onLocationResult method from the LocationResult class.
                 *
                 * This method is called when a location update is received. It retrieves the last known location
                 * from the LocationResult object and converts it into a LatLng object. The LatLng object is then
                 * passed to the trySend method to send the location data.
                 *
                 * @param p0 The LocationResult object containing the location update.
                 */
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)

                    p0.lastLocation?.let { location ->
                        val latLng = LatLng(location.latitude, location.longitude)

                        trySend(latLng)
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose { fusedLocationClient.removeLocationUpdates(locationCallback) }
        }
    }
}