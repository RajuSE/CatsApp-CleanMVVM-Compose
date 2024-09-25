package youtube.devxraju.catsforever.domain.usecases.cats

import youtube.devxraju.catsforever.data.local.CatsDao
import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponseItem
import javax.inject.Inject

class FavoritCatDetailsUseCase @Inject constructor(
    private val catsDao: CatsDao
) {

    suspend operator fun invoke(id: String): CatBreedsResponseItem?{
        return catsDao.getFavoritedCat(id = id)
    }

}