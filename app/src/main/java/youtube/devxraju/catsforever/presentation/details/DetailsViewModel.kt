package youtube.devxraju.catsforever.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem
import youtube.devxraju.catsforever.domain.usecases.cats.UnFavouriteCat
import youtube.devxraju.catsforever.domain.usecases.cats.GetCatDetailsFromDB
import youtube.devxraju.catsforever.domain.usecases.cats.UpsertCat
import youtube.devxraju.catsforever.util.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import youtube.devxraju.catsforever.domain.usecases.cart.AddToCartCat
import youtube.devxraju.catsforever.domain.usecases.cats.FavoritCatDetailsUseCase
import youtube.devxraju.catsforever.util.DataState
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsUseCase: GetCatDetailsFromDB,
    private val unFavouriteCatUseCase: UnFavouriteCat,
    private val upsertCatUseCase: UpsertCat,
    private val addToCartCat: AddToCartCat
) : ViewModel() {

    var favoriteUnfav by mutableStateOf<DataState?>(null)
        private set

    private val _openCart = MutableSharedFlow<Boolean>()
    val openCart = _openCart.asSharedFlow()

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteCat -> {
                viewModelScope.launch {
                    println("UpsertDelete event: ${event.cat.id}")
                    val cat = detailsUseCase.invoke(id = event.cat.id)
                    println(cat)
                    if (cat==null|| cat.isFavourited==0){
                        println("favoring:"+cat)
                        upsertCat(cat = event.cat.copy(price = event.cat.price, isFavourited = 1))
                        favoriteUnfav = DataState.FavoriteUnfav(true)
                    }else{
                        println("unfailing:"+cat)
                        //  deleteCat(cat = event.cat)
                        upsertCat(cat = event.cat.copy(price = event.cat.price, isFavourited = 0))
                        favoriteUnfav = DataState.FavoriteUnfav(false)
                    }
                }
            }
            is DetailsEvent.AddToCart ->{
                viewModelScope.launch {
                    val cat = detailsUseCase(id = event.cat.id)
                    if (cat == null){
                        upsertCatUseCase(cat = event.cat.copy(isAddedToCart = 1,price = event.cat.price))
                    }else{
                      println("ADD:" +addToCartCat.invoke(cat = event.cat.copy(isAddedToCart = 1,price = event.cat.price)))
                    }
                    _openCart.emit(true)
                }
            }

            DetailsEvent.RemoveSideEffect -> {
//                sideEffect = null
            }
            DetailsEvent.RemoveOpenCartSideEf -> {
                viewModelScope.launch {
                    _openCart.emit(false)
                }
            }
        }
    }

    private suspend fun deleteCat(cat: CatBreedsResponseItem) {
        unFavouriteCatUseCase(cat = cat)
        favoriteUnfav = DataState.FavoriteUnfav(false)
    }

    private suspend fun upsertCat(cat: CatBreedsResponseItem) {
        upsertCatUseCase(cat = cat)
    }

}