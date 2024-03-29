package com.gpillaca.upcomingmovies.model.repository

import android.Manifest
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.gpillaca.upcomingmovies.model.datasource.LocationDataSource
import com.gpillaca.upcomingmovies.model.PermissionChecker
import com.gpillaca.upcomingmovies.model.datasource.PlayServicesLocationDataSource

class RegionRepository(
    private val application: Application
) {

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    private val locationDataSource: LocationDataSource by lazy {
        PlayServicesLocationDataSource(application)
    }

    private val coarsePermissionChecker = PermissionChecker(
        application,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    suspend fun findLastRegion() = getRegionFromLocation(findLastLocation())

    private suspend fun findLastLocation(): Location? {
        val isGranted = coarsePermissionChecker.check()
        return if (isGranted) locationDataSource.findLastLocation() else null
    }

    private fun getRegionFromLocation(location: Location?): String {
        val geocoder = Geocoder(application)
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
