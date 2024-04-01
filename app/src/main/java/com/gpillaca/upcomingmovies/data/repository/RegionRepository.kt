package com.gpillaca.upcomingmovies.data.repository

import com.gpillaca.upcomingmovies.data.datasource.LocationDataSource
import com.gpillaca.upcomingmovies.data.PermissionChecker
import com.gpillaca.upcomingmovies.data.PermissionChecker.Permission.COARSE_LOCATION

class RegionRepository(
    private val permissionChecker: PermissionChecker,
    private val locationDataSource: LocationDataSource
) {

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    suspend fun findLastRegion(): String {
        return if (permissionChecker.check(COARSE_LOCATION)) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}
