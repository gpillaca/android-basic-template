package com.gpillaca.upcomingmovies.framework

import android.annotation.SuppressLint
import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.gpillaca.upcomingmovies.data.datasource.LocationDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class PlayServicesLocationDataSource @Inject constructor(
    application: Application
) : LocationDataSource {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private val geocoder = Geocoder(application)

    override suspend fun findLastRegion(): String? = findLastLocation().toRegion()

    @SuppressLint("MissingPermission")
    private suspend fun findLastLocation(): Location =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result)
                }
        }

    private suspend fun Location?.toRegion(): String? {
        val addresses = this?.let {
            getFromLocationCompat(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode
    }

    private suspend fun getFromLocationCompat(
        @FloatRange(from = -90.0, to = 90.0) latitude: Double,
        @FloatRange(from = -180.0, to = 180.0) longitude: Double,
        @IntRange maxResults: Int
    ): List<Address> = suspendCancellableCoroutine { continuation ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(latitude, longitude, maxResults) {
                continuation.resume(it)
            }
        } else {
            continuation.resume(geocoder.getFromLocation(latitude, longitude, maxResults) ?: emptyList())
        }
    }
}
