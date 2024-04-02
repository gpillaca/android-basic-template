package com.gpillaca.upcomingmovies

import com.gpillaca.upcomingmovies.data.PermissionChecker
import com.gpillaca.upcomingmovies.data.PermissionChecker.Permission.COARSE_LOCATION
import com.gpillaca.upcomingmovies.data.datasource.LocationDataSource
import com.gpillaca.upcomingmovies.data.repository.RegionRepository
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

    lateinit var regionRepository: RegionRepository

    @Before
    fun setUp() {
        regionRepository = RegionRepository(permissionChecker, locationDataSource)
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
        whenever(locationDataSource.findLastRegion()).thenReturn(ES_REGION)

        val region = regionRepository.findLastRegion()

        assertEquals(ES_REGION, region)
    }
}
