package youtube.devxraju.catsforever.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import youtube.devxraju.catsforever.data.remote.dto.CartItem
import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponseItem
import youtube.devxraju.catsforever.domain.usecases.cart.GetAllCartItemsUseCase
import youtube.devxraju.catsforever.domain.usecases.cart.RemoveFromCartUsecase
import youtube.devxraju.catsforever.domain.usecases.cart.UpdateQtyCartUsecase
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val updateQtyCartUsecase: UpdateQtyCartUsecase,
    private val removeFromCartUsecase: RemoveFromCartUsecase,
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CatBreedsResponseItem>>(emptyList())
    val cartItems: StateFlow<List<CatBreedsResponseItem>> = _cartItems.asStateFlow()

    private val _totalCost = MutableStateFlow(0)
    val totalCost: StateFlow<Int> = _totalCost.asStateFlow()

    init {
        viewModelScope.launch {
            println("init cvm")
            getAllCartItemsUseCase().collectLatest { items ->
                println("collect getCartItems $items")
                _cartItems.value = items
                _totalCost.value = items.sumOf { it.price*(if(it.quantity==0) 1 else it.quantity) }
            }
        }
    }




    fun increaseQty(cartItem: CatBreedsResponseItem) {
        viewModelScope.launch {

            updateQtyCartUsecase.invoke(
                cartItem,
                if (cartItem.quantity <= 0) 2 else cartItem.quantity + 1
            )
            _totalCost.value += cartItem.price
        }
    }

    fun decreaseQty(cartItem: CatBreedsResponseItem) {
        viewModelScope.launch {
            if(cartItem.quantity <= 1) return@launch

            updateQtyCartUsecase.invoke(
                cartItem,
                cartItem.quantity - 1
            )
            _totalCost.value -= cartItem.price

        }
    }

    fun removeItem(cartItem: CatBreedsResponseItem) {
        viewModelScope.launch {

            removeFromCartUsecase.invoke(
                cartItem,
            )

            getAllCartItemsUseCase().collectLatest { items ->
                println("collect getCartItems $items")
                _cartItems.value = items
                _totalCost.value = items.sumOf { it.price*(if(it.quantity==0) 1 else it.quantity) }
            }
        }
    }
}