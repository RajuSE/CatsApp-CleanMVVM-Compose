package youtube.devxraju.catsforever.domain.usecases.cart

import youtube.devxraju.catsforever.data.local.CatsDao
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem
import javax.inject.Inject

class AddToCartCat @Inject constructor(
    private val catsDao: CatsDao
) {
    suspend operator fun invoke(cat: CatBreedsResponseItem): Int {
        println("marking cat: ${cat.isAddedToCart}  id:${cat.id}")
        return catsDao.markAddToCart(id = cat.id)
    }
}