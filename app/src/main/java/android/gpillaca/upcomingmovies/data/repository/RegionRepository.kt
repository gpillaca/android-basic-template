package android.gpillaca.upcomingmovies.data.repository

import android.gpillaca.upcomingmovies.data.datasource.LocationDataSource
import android.gpillaca.upcomingmovies.data.PermissionChecker
import android.gpillaca.upcomingmovies.data.PermissionChecker.Permission.COARSE_LOCATION
import android.gpillaca.upcomingmovies.ui.common.InternetConnectionChecker
import javax.inject.Inject

/**
 * Test class [RegionRepositoryTest]
 */
class RegionRepository @Inject constructor(
    private val permissionChecker: PermissionChecker,
    private val locationDataSource: LocationDataSource,
    private  val internetConnectionChecker: InternetConnectionChecker
) {

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    suspend fun findLastRegion(): String {
        return if (permissionChecker.check(COARSE_LOCATION) && internetConnectionChecker.isInternetAvailable()) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}
