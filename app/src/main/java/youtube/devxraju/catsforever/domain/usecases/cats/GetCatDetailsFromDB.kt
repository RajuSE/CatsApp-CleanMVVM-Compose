package youtube.devxraju.catsforever.domain.usecases.cats

import youtube.devxraju.catsforever.data.local.CatsDao
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem
import javax.inject.Inject

class GetCatDetailsFromDB @Inject constructor(
    private val catsDao: CatsDao
) {

    suspend operator fun invoke(id: String): CatBreedsResponseItem?{
        return catsDao.getCat(id = id)
    }

}