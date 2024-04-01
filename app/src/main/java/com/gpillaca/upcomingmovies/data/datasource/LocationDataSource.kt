package com.gpillaca.upcomingmovies.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}
