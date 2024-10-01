package youtube.devxraju.catsforever.domain.usecases.cart

import youtube.devxraju.catsforever.data.local.CatsDao
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class  GetAllCartItemsUseCase @Inject constructor(
    private val catsDao: CatsDao
) {

    operator fun invoke(): Flow<List<CatBreedsResponseItem>>{
        return catsDao.getCartCats()
    }

}