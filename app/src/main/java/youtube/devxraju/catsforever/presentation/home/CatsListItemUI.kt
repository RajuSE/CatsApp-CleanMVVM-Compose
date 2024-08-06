package youtube.devxraju.catsforever.presentation.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import youtube.devxraju.catsforever.R
import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponseItem
import youtube.devxraju.catsforever.presentation.Dimens.CatCardSize
import youtube.devxraju.catsforever.presentation.Dimens.ExtraSmallPadding
import youtube.devxraju.catsforever.theme.CatsAppTheme

@Composable
fun CatCard(
    modifier: Modifier = Modifier,
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
            model = ImageRequest.Builder(context).data("https://cdn2.thecatapi.com/images/${cat.reference_image_id}.jpg").build(),
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
            Text(
                text = cat.name,
                style = MaterialTheme.typography.bodyMedium.copy(),
                color = colorResource(id = R.color.text_title),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cat.description,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            }
            Spacer(modifier = Modifier.width(width = 15.dp))
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CatCardPreview() {
    CatsAppTheme(dynamicColor = false) {
        CatCard(
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
                wikipedia_url = "http://www.bing.com/search?q=altera"
            )
        )
    }
}