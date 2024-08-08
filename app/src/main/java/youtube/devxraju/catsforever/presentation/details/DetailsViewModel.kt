package youtube.devxraju.catsforever.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponseItem
import youtube.devxraju.catsforever.domain.usecases.cats.UnFavouriteCat
import youtube.devxraju.catsforever.domain.usecases.cats.GetCatDetailsFromDB
import youtube.devxraju.catsforever.domain.usecases.cats.UpsertCat
import youtube.devxraju.catsforever.util.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import youtube.devxraju.catsforever.util.DataState
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getCatDetailsFromDBUseCase: GetCatDetailsFromDB,
    private val unFavouriteCatUseCase: UnFavouriteCat,
    private val upsertCatUseCase: UpsertCat
) : ViewModel() {

    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

    var favoriteUnfav by mutableStateOf<DataState?>(null)
        private set

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteCat -> {
                viewModelScope.launch {
                    println("UpsertDelete event: ${event.cat.id}")
                    val cat = getCatDetailsFromDBUseCase(id = event.cat.id)
                    if (cat == null){
                        upsertCat(cat = event.cat)
                    }else{
                        deleteCat(cat = event.cat)
                    }
                }
            }
            is DetailsEvent.RemoveSideEffect ->{
                sideEffect = null
            }
        }
    }

    private suspend fun deleteCat(cat: CatBreedsResponseItem) {
        unFavouriteCatUseCase(cat = cat)
        favoriteUnfav = DataState.FavoriteUnfav(false)
    }

    private suspend fun upsertCat(cat: CatBreedsResponseItem) {
        upsertCatUseCase(cat = cat)
        favoriteUnfav = DataState.FavoriteUnfav(true)
    }

}