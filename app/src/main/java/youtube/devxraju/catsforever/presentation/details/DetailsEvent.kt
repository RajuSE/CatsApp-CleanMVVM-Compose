package youtube.devxraju.catsforever.presentation.details

import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem

sealed class DetailsEvent {
    data class UpsertDeleteCat(val cat: CatBreedsResponseItem) : DetailsEvent()

    object RemoveSideEffect : DetailsEvent()
    data class AddToCart(val cat: CatBreedsResponseItem) : DetailsEvent()
    object RemoveOpenCartSideEf: DetailsEvent()

}