package youtube.devxraju.catsforever.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import youtube.devxraju.catsforever.data.remote.dto.CatBreedsResponseItem
import youtube.devxraju.catsforever.presentation.Dimens.MediumPadding1
import youtube.devxraju.catsforever.presentation.common.CatsList


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeRoute(
    cats: LazyPagingItems<CatBreedsResponseItem>,
    navigateToDetails: (CatBreedsResponseItem) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ) {

        CatsList(
            modifier = Modifier.padding(horizontal = MediumPadding1),
            cats = cats,
            onClick = navigateToDetails
        )
    }
}