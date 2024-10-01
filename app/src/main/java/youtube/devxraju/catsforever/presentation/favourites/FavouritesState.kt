package youtube.devxraju.catsforever.presentation.favourites

import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem

data class FavouritesState(
    val cats: List<CatBreedsResponseItem> = emptyList()
)