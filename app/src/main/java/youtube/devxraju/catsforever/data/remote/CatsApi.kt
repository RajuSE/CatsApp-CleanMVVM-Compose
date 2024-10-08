package youtube.devxraju.catsforever.data.remote

import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import youtube.devxraju.catsforever.data.remote.dto.CatsItem
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem

interface CatsApi {

    @GET("v1/breeds")
    suspend fun getCatsApi(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
    ): List<CatsItem>

    @GET("v1/breeds/search")
    suspend fun searchCats(
        @Query("q") searchQuery: String,
    ): List<CatsItem>
}