package youtube.devxraju.catsforever.presentation.cart

import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem

sealed class CartEvent {
    data class IncreaseQuantity(val item: CatBreedsResponseItem): CartEvent()
    data class DecreaseQuantity(val item: CatBreedsResponseItem): CartEvent()
    data class RemoveItem(val item: CatBreedsResponseItem): CartEvent()
}
