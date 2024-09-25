package youtube.devxraju.catsforever.presentation.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponseItem
import youtube.devxraju.catsforever.domain.usecases.cats.FavoritCatDetailsUseCase
import youtube.devxraju.catsforever.domain.usecases.cats.GetCatDetailsFromDB
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor(
    private val favUseCase: FavoritCatDetailsUseCase,
) : ViewModel() {

    var currentCat by mutableStateOf<CatBreedsResponseItem?>(null)
        private set

    var isCurrentCatFav by mutableStateOf<Boolean>(false)
        private set


    init {
        println("VMVM: init isFav:${isCurrentCatFav} id:${currentCat?.id} this:$this")
    }

    suspend fun onCatClicked(catBreedsResponseItem: CatBreedsResponseItem) {
        currentCat = catBreedsResponseItem
        println("currentCat:${currentCat}")
        viewModelScope.async {
            isCurrentCatFav = favUseCase.invoke(currentCat!!.id) != null
        println("isCurrentCatFav:$isCurrentCatFav")
        }.await()
        println("VMVM: ${currentCat?.id}");
    }

//    suspend fun isFavorited(cat: CatBreedsResponseItem): Boolean {
//        println("isFavorited called ${cat.id}");
//        return getCatDetailsFromDBUseCase.invoke(cat.id) != null
//    }

}