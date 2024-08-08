package youtube.devxraju.catsforever.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponseItem
import youtube.devxraju.catsforever.presentation.Dimens.CatImageHeight
import youtube.devxraju.catsforever.presentation.Dimens.MediumPadding1
import youtube.devxraju.catsforever.presentation.details.components.DetailsTopBar
import youtube.devxraju.catsforever.theme.CatsAppTheme
import youtube.devxraju.catsforever.util.DataState

@Composable
fun DetailsScreen(
    cat: CatBreedsResponseItem,
    event: (DetailsEvent) -> Unit,
    favUnfav: DataState?,
    isFavoritedAlready: Boolean,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current

    println("DetailsScreen fun")

    val isFav = remember { mutableStateOf(isFavoritedAlready) }

    LaunchedEffect(key1 = favUnfav) {
        favUnfav?.let {
            when (favUnfav) {
                is DataState.FavoriteUnfav -> {
                    println("LaunchedEffect3: ${favUnfav.isFavorited}")
                    isFav.value = favUnfav.isFavorited
                }

                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            isFav = isFav.value,
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
                        .data("https://cdn2.thecatapi.com/images/${cat.reference_image_id}.jpg")
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
                name = "Cornish Rexx",
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
                wikipedia_url = "http://www.bing.com/search?q=altera"
            ),
            isFavoritedAlready = false,
            event = {},
            favUnfav = null
        ) {

        }
    }
}