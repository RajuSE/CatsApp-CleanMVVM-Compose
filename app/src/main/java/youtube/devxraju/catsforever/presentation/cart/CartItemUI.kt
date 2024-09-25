package youtube.devxraju.catsforever.presentation.cart

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import youtube.devxraju.catsforever.R
import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponseItem
import youtube.devxraju.catsforever.presentation.Dimens.CatCardSize
import youtube.devxraju.catsforever.presentation.Dimens.ExtraSmallPadding
import youtube.devxraju.catsforever.theme.CatsAppTheme
import youtube.devxraju.catsforever.theme.cartColor
import youtube.devxraju.catsforever.theme.green
import youtube.devxraju.catsforever.util.IMG_URL


@Composable
fun CartCatCard(
    modifier: Modifier = Modifier,
    viewModel:CartViewModel,
    cat: CatBreedsResponseItem,
    onClick: (() -> Unit)? = null
) {

    val context = LocalContext.current
    Row(
        modifier = modifier.clickable { onClick?.invoke() },

        ) {
        AsyncImage(
            modifier = Modifier
                .size(CatCardSize)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context)
                .data("$IMG_URL${cat.reference_image_id}.jpg")
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(width = 10.dp))
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(horizontal = ExtraSmallPadding)
                .height(CatCardSize)
        ) {
            Spacer(modifier = Modifier.width(width = 5.dp))
            Row() {
                Text(
                    modifier = Modifier.weight(8f),
                    text = cat.name,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = colorResource(id = R.color.text_title),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Surface(
                    modifier = Modifier.wrapContentWidth(),
                    color = cartColor,
                    shape = RoundedCornerShape(15.dp)
                )
                {
                    Text(
                        text = cat.price.toString() + " Rs",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic
                        ),
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(3f)
                            .padding(start = 10.dp, end = 10.dp, top = 2.dp, bottom = 2.dp),
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

                CartItemRow(cat,viewModel)
            }
            Spacer(modifier = Modifier.width(width = 15.dp))
        }
    }
}


@Composable
fun CartItemRow(
    cartItem: CatBreedsResponseItem,
    viewModel: CartViewModel,
) {
    println("CartItemRow")
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Qty:",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic
            ), modifier = Modifier.padding(start = 5.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))

        IconButton(modifier = Modifier
            .border(1.dp, green, shape = CircleShape),onClick = { viewModel.increaseQty(cartItem)}) {
            Icon(Icons.Filled.KeyboardArrowUp, tint = green, contentDescription = "Increase Quantity")
        }
        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = "${if(cartItem.quantity<=0) 1 else cartItem.quantity}",

            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic
            ), modifier = Modifier.padding(3.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))

        IconButton(modifier = Modifier
            .border(1.dp, Color.Red, shape = CircleShape),
            onClick = { viewModel.decreaseQty(cartItem) }) {
            Icon(Icons.Filled.KeyboardArrowDown, tint = Color.Red, contentDescription = "Decrease Quantity")
        }
        Spacer(modifier = Modifier.width(5.dp))
        IconButton(onClick = { viewModel.removeItem(cartItem) }) {
            Icon(Icons.Default.Delete, contentDescription = "Remove Item")
        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CatCardPreview() {
    CatsAppTheme(dynamicColor = false) {
        CartCatCard(
            cat = CatBreedsResponseItem(
                adaptability = 6625,
                affection_level = 7976,
                alt_names = null,
                cfa_url = null,
                child_friendly = 4734,
                country_code = "New Zealand",
                country_codes = "Algeria",
                description = "This is description to test the ui with multiple lines to see how it appears devxraju",
                dog_friendly = 9450,
                energy_level = 5296,
                experimental = 3199,
                grooming = 8102,
                hairless = 2415,
                health_issues = 9693,
                hypoallergenic = 5754,
                id = "dicant",
                indoor = 6308,
                intelligence = 3328,
                lap = 6785,
                life_span = null,
                name = "Cornish Rex",
                natural = 9979,
                origin = "sagittis",
                rare = 2512,
                reference_image_id = "0XYvRd7oD",
                rex = 9258,
                shedding_level = 1900,
                short_legs = 2328,
                social_needs = 1863,
                stranger_friendly = 1502,
                suppressed_tail = 2258,
                temperament = "erat",
                vcahospitals_url = null,
                vetstreet_url = null,
                vocalisation = 7496,
                wikipedia_url = "http://www.bing.com/search?q=altera",
                quantity = 5733,
                price = 840,
                isAddedToCart = 7722,
                isFavourited = 0
            ), modifier =Modifier,
            viewModel = hiltViewModel(), onClick = {}
        )
    }
}