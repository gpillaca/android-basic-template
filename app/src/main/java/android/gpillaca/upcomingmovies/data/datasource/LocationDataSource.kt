package android.gpillaca.upcomingmovies.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}
