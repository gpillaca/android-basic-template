package android.gpillaca.upcomingmovies

import android.gpillaca.upcomingmovies.data.PermissionChecker
import android.gpillaca.upcomingmovies.data.PermissionChecker.Permission.COARSE_LOCATION
import android.gpillaca.upcomingmovies.data.datasource.LocationDataSource
import android.gpillaca.upcomingmovies.data.repository.RegionRepository
import android.gpillaca.upcomingmovies.ui.common.InternetConnectionChecker
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

/**
 * Test class of [RegionRepository]
 */
@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    @Mock
    lateinit var permissionChecker: PermissionChecker

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var internetConnectionChecker: InternetConnectionChecker

    private lateinit var regionRepository: RegionRepository

    @Before
    fun setUp() {
        regionRepository = RegionRepository(permissionChecker, locationDataSource, internetConnectionChecker)
    }

    @Test
    fun `Returns default region when coarse permission not granted`(): Unit = runBlocking {
        whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(false)
        val region = regionRepository.findLastRegion()

        assertEquals(DEFAULT_REGION, region)
    }

    @Test
    fun `Returns region from location data source when permission granted`(): Unit = runBlocking {
        whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(true)
        whenever(internetConnectionChecker.isInternetAvailable()).thenReturn(true)
        whenever(locationDataSource.findLastRegion()).thenReturn(ES_REGION)

        val region = regionRepository.findLastRegion()

        assertEquals(ES_REGION, region)
    }
}
