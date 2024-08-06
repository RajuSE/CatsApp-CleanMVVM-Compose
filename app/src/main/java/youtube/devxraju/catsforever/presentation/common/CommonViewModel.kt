package youtube.devxraju.catsforever.presentation.common

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
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class CommonViewModel @Inject constructor(
    private val getCatDetailsFromDBUseCase: GetCatDetailsFromDB,
    ) : ViewModel() {

    var currentCat by mutableStateOf<CatBreedsResponseItem?>(null)
        private set

    init {
        println("VMVM: init ${currentCat?.id}")
    }

    fun onCatClicked(catBreedsResponseItem: CatBreedsResponseItem){
        currentCat = catBreedsResponseItem
        println("VMVM: ${currentCat?.id}");
    }

   suspend fun isFavorited(cat: CatBreedsResponseItem):Boolean{
       println("isFavorited called ${cat.id}");
        return getCatDetailsFromDBUseCase.invoke(cat.id)!=null
    }

}