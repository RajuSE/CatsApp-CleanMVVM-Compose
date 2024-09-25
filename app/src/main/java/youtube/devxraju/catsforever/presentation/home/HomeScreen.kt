package youtube.devxraju.catsforever.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import youtube.devxraju.catsforever.R
import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponseItem
import youtube.devxraju.catsforever.presentation.Dimens.MediumPadding0
import youtube.devxraju.catsforever.presentation.Dimens.MediumPadding1
import youtube.devxraju.catsforever.presentation.Dimens.MediumPaddingSmall
import youtube.devxraju.catsforever.presentation.common.CatsList
import youtube.devxraju.catsforever.theme.CatsAppTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeRoute(
    cats: LazyPagingItems<CatBreedsResponseItem>,
    navigateToDetails: (CatBreedsResponseItem) -> Unit
) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {

            CatsList(
                modifier = Modifier.padding(horizontal = MediumPaddingSmall),
                cats = cats,
                onClick = navigateToDetails
            )

        }

}