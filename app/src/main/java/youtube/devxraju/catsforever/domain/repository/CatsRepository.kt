package youtube.devxraju.catsforever.domain.repository

import androidx.paging.PagingData
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem
import kotlinx.coroutines.flow.Flow

interface CatsRepository {

    fun getCats(): Flow<PagingData<CatBreedsResponseItem>>

    fun searchCats(searchQuery: String): Flow<PagingData<CatBreedsResponseItem>>

}