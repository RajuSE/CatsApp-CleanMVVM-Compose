package youtube.devxraju.catsforever.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import youtube.devxraju.catsforever.R
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem
import youtube.devxraju.catsforever.presentation.Dimens.CatImageHeight
import youtube.devxraju.catsforever.presentation.Dimens.MediumPadding1
import youtube.devxraju.catsforever.presentation.details.components.DetailsTopBar
import youtube.devxraju.catsforever.theme.CatsAppTheme
import youtube.devxraju.catsforever.util.DataState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import youtube.devxraju.catsforever.presentation.Dimens.MediumPadding3
import youtube.devxraju.catsforever.theme.cartColor
import youtube.devxraju.catsforever.util.IMG_URL

@Composable
fun DetailsScreen(
    cat: CatBreedsResponseItem,
    event: (DetailsEvent) -> Unit,
    favUnfav: DataState?,
    isFavoritedAlready: Boolean,
    navigateUp: () -> Unit,
    onAddToCart: (CatBreedsResponseItem) -> Unit
) {
    val context = LocalContext.current

    println("DetailsScreen fun $isFavoritedAlready $favUnfav")

    val isFav by remember(favUnfav) {
        mutableStateOf(
        favUnfav?.let {
            if(it is DataState.FavoriteUnfav) it.isFavorited else isFavoritedAlready
        } ?: isFavoritedAlready)
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            DetailsTopBar(
                isFav = isFav,
                onBrowsingClick = {
                    Intent(Intent.ACTION_VIEW).also {
                        it.data = Uri.parse(cat.wikipedia_url)
                        if (it.resolveActivity(context.packageManager) != null) {
                            context.startActivity(it)
                        }
                    }
                },
                onShareClick = {
                    Intent(Intent.ACTION_SEND).also {
                        it.putExtra(Intent.EXTRA_TEXT, cat.wikipedia_url)
                        it.type = "text/plain"
                        if (it.resolveActivity(context.packageManager) != null) {
                            context.startActivity(it)
                        }
                    }
                },
                onFavouriteClick = {

                    event(DetailsEvent.UpsertDeleteCat(cat))
                },
                onBackClick = navigateUp
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    start = MediumPadding1,
                    end = MediumPadding1,
                    top = MediumPadding1
                )
            ) {
                item {
                    AsyncImage(
                        model = ImageRequest.Builder(context = context)
                            .data("$IMG_URL${cat.reference_image_id}.jpg")
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(CatImageHeight)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(MediumPadding1))
                    Text(
                        text = cat.name,
                        style = MaterialTheme.typography.displaySmall,
                        color = colorResource(
                            id = R.color.text_title
                        )
                    )
                    Text(
                        text = cat.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(
                            id = R.color.body
                        )
                    )
                }
            }
        }

        /*Surface(
            modifier = Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 15.dp, start = MediumPadding3, end = MediumPadding3)
                .clickable {
                    onAddToCart(cat)
                },
            color = Color(0xffc0e3f8),
            shape = RoundedCornerShape(38.dp),
            shadowElevation = 10.dp,
        ) {
            Text(text = "Add to Cart",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(12.dp)
                    .align(Alignment.Center)
            )
        }*/
        Row(modifier = Modifier.align(Alignment.BottomCenter)) {


            ExtendedFloatingActionButton(
                modifier = Modifier
                    .weight(4f)
                    .padding(bottom = 15.dp, start = MediumPadding1),
                containerColor = cartColor,
                text = {
                    Text(
                        text = "${cat.price} Rs",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                            .align(Alignment.CenterVertically)
                    )
                },
                onClick = {  },
                icon = { }
            )



            ExtendedFloatingActionButton(
                modifier = Modifier
                    .weight(7f)
                    .padding(bottom = 15.dp,start = MediumPadding1, end = MediumPadding1),
                containerColor = cartColor,
                text = {
                    Text(
                        text = "Add to Cart",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = Color.Black,
                        modifier = Modifier.padding(12.dp)
                            .align(Alignment.CenterVertically)
                    )
                },
                onClick = { onAddToCart(cat) },
                icon = { Icon(Icons.Filled.Add, "") }
            )
        }
    }
}







@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    CatsAppTheme(dynamicColor = false) {
        DetailsScreen(
            cat = CatBreedsResponseItem(
                adaptability = 6625,
                affection_level = 7976,
                alt_names = null,
                cfa_url = null,
                child_friendly = 4734,
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
                name = "Cornish Rexx",
                natural = 9979,
                origin = "sagittis",
                rare = 2512,
                reference_image_id = "0XYvRd7oD",
                short_legs = 2328,
                social_needs = 1863,
                stranger_friendly = 1502,
                suppressed_tail = 2258,
                temperament = "erat",
                vcahospitals_url = null,
                vetstreet_url = null,
                vocalisation = 7496,
                wikipedia_url = "http://www.bing.com/search?q=altera",
                quantity = 2820,
                price = 8570,
                isAddedToCart = 5537, isFavourited = 0
            ),
            isFavoritedAlready = false,
            event = {},
            favUnfav = null, navigateUp = {},
        ) {

        }
    }
}