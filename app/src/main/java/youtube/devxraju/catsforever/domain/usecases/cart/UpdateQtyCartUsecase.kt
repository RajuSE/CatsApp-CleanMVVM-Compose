package youtube.devxraju.catsforever.domain.usecases.cart

import youtube.devxraju.catsforever.data.local.CatsDao
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem
import javax.inject.Inject

class UpdateQtyCartUsecase @Inject constructor(
    private val catsDao: CatsDao
) {

    suspend operator fun invoke(cat: CatBreedsResponseItem, q:Int){
        catsDao.updateQty(id = cat.id, qty = q)
    }

}