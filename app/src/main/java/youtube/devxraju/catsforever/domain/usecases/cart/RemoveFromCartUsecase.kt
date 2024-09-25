package youtube.devxraju.catsforever.domain.usecases.cart

import youtube.devxraju.catsforever.data.local.CatsDao
import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponseItem
import javax.inject.Inject

class RemoveFromCartUsecase @Inject constructor(
    private val catsDao: CatsDao
) {

    suspend operator fun invoke(cat: CatBreedsResponseItem){
        catsDao.removeAddToCart(id = cat.id)
    }

}