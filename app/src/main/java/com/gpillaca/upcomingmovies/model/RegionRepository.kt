package com.gpillaca.upcomingmovies.model

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class RegionRepository(
    private val activity: AppCompatActivity
) {

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    private val locationDataSource: LocationDataSource by lazy {
        PlayServicesLocationDataSource(activity)
    }

    private val coarsePermissionChecker = PermissionChecker(
        activity,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    suspend fun findLastRegion() = getRegionFromLocation(findLastLocation())

    private suspend fun findLastLocation(): Location? {
        val isGranted = coarsePermissionChecker.requestPermission()
        return if (isGranted) locationDataSource.findLastLocation() else null
    }

    private fun getRegionFromLocation(location: Location?): String {
        val geocoder = Geocoder(activity)
        val fromLocation = location?.let {
            geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )
        }
        return fromLocation?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }
}
