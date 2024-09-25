package youtube.devxraju.catsforever.presentation.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import youtube.devxraju.catsforever.R
import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponseItem
import youtube.devxraju.catsforever.presentation.Dimens.ExtraSmallPadding
import youtube.devxraju.catsforever.presentation.Dimens.MediumPadding0
import youtube.devxraju.catsforever.presentation.Dimens.MediumPadding1
import youtube.devxraju.catsforever.presentation.common.EmptyScreen
import youtube.devxraju.catsforever.presentation.details.components.DetailsTopBar
import youtube.devxraju.catsforever.presentation.home.CatCard


@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    onClick: (CatBreedsResponseItem) -> Unit,
    onBack:()->Unit
) {
    val cartItems by viewModel.cartItems.collectAsState(initial = emptyList())
    val totalCost by viewModel.totalCost.collectAsState(initial = 0.0)


    SideEffect {
        println("CartScreen ${cartItems.size}")
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()) {



        if (cartItems.isEmpty()) {
            Column {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = null,
                    )
                }
                EmptyScreen(noItemsUI = true)
            }
        } else {
            Column {
                Row {

                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = null,
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Total Cost: $totalCost Rs",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = colorResource(id = R.color.text_title),
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = MediumPadding0),
                    verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                ) {
                    items(
                        count = cartItems.size,
                    ) {
                        cartItems[it].let { cat ->
                            CartCatCard(cat = cat, viewModel = viewModel, onClick = {
                                //onClick(cat)
                            })
                        }
                    }
                }
            }
        }
    }


}

