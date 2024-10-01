package youtube.devxraju.catsforever.presentation.app_navigator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import youtube.devxraju.catsforever.R
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem
import youtube.devxraju.catsforever.presentation.Dimens.MediumPadding1
import youtube.devxraju.catsforever.presentation.app_navigator.components.BottomNavigationItem
import youtube.devxraju.catsforever.presentation.app_navigator.components.CatsBottomNavigation
import youtube.devxraju.catsforever.presentation.cart.CartScreen
import youtube.devxraju.catsforever.presentation.cart.CartViewModel
import youtube.devxraju.catsforever.presentation.common.CommonViewModel
import youtube.devxraju.catsforever.presentation.details.DetailsEvent
import youtube.devxraju.catsforever.presentation.details.DetailsScreen
import youtube.devxraju.catsforever.presentation.details.DetailsViewModel
import youtube.devxraju.catsforever.presentation.favourites.FavouritesScreen
import youtube.devxraju.catsforever.presentation.favourites.FavouritesViewModel
import youtube.devxraju.catsforever.presentation.home.HomeRoute
import youtube.devxraju.catsforever.presentation.home.HomeViewModel
import youtube.devxraju.catsforever.presentation.navgraph.Route
import youtube.devxraju.catsforever.presentation.search.SearchRoute
import youtube.devxraju.catsforever.presentation.search.SearchViewModel
import youtube.devxraju.catsforever.theme.cartColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigator() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.img_paw, text = "Explore"),
            BottomNavigationItem(icon = R.drawable.img_fav, text = "Favourites"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    val commonViewModel: CommonViewModel = hiltViewModel()

    selectedItem = when (backStackState?.destination?.route) {
        Route.HomeRoute.route -> 0
        Route.FavouriteRoute.route -> 1
        Route.SearchRoute.route -> 2
        else -> 0
    }

    //Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeRoute.route ||
                backStackState?.destination?.route == Route.SearchRoute.route ||
                backStackState?.destination?.route == Route.FavouriteRoute.route
    }


    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background
                            (color = colorResource(id = R.color.placeholder))
                )
                CatsBottomNavigation(
                    items = bottomNavigationItems,
                    selectedItem = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.HomeRoute.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.FavouriteRoute.route
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                route = Route.SearchRoute.route
                            )
                        }
                    }
                )

            }
        }
    },
        floatingActionButton = {

            if(isBottomBarVisible)
            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                containerColor = cartColor,
                onClick = { navController.navigate(Route.CartRoute.route) },
            ){
                Icon(Icons.Filled.ShoppingCart, "")
            }
        }

    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeRoute.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeRoute.route) { backStackEntry ->
                val viewModel: HomeViewModel = hiltViewModel()
                val cats = viewModel.catsData.collectAsLazyPagingItems()
                HomeRoute(
                    cats = cats,
                    navigateToDetails = { cat ->
                        commonViewModel.viewModelScope.launch {
                            commonViewModel.onCatClicked(cat)
                            navigateToDetails(navController = navController)
                        }
                    }
                )
            }
            composable(route = Route.SearchRoute.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                SearchRoute(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = { cat ->
                        commonViewModel.viewModelScope.launch {
                            commonViewModel.onCatClicked(cat)
                            navigateToDetails(navController = navController)
                        }
                    }
                )
            }
            composable(route = Route.DetailsRoute.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                val openCartEvent by viewModel.openCart.collectAsState(initial = false)

                LaunchedEffect(key1 = Unit) {
                    viewModel.openCart.collectLatest {
                        println("openCart:$it")
                    }
                }
                if(openCartEvent){
                    navController.navigate(Route.CartRoute.route)
                    viewModel.onEvent(DetailsEvent.RemoveOpenCartSideEf)
                }
//                navController.previousBackStackEntry?.savedStateHandle?.get<CatBreedsResponseItem?>("cat")
                commonViewModel.currentCat?.let { cat ->
                    DetailsScreen(
                        cat = cat,
                        event = viewModel::onEvent,
                        navigateUp = {
                            navController.navigateUp()
                        },
                        isFavoritedAlready = commonViewModel.isCurrentCatFav,
                        favUnfav = viewModel.favoriteUnfav,
                        onAddToCart = {
                                viewModel.onEvent(DetailsEvent.AddToCart(it))
                                //navController.navigate(Route.CartRoute.route)
                        }
                    )
                }
            }
            composable(route = Route.FavouriteRoute.route) {
                val viewModel: FavouritesViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                FavouritesScreen(
                    state = state,
                    navigateToDetails = { cat ->
                        commonViewModel.viewModelScope.launch {
                            commonViewModel.onCatClicked(cat)
                            navigateToDetails(navController = navController)
                        }
                    }
                )
            }
            composable(route = Route.CartRoute.route) { backStackEntry ->
                val viewModel: CartViewModel = hiltViewModel()

                CartScreen(
                    viewModel = viewModel,
                    onBack = { navController.navigateUp()},
                    onClick = {
                    commonViewModel.viewModelScope.launch {
                        commonViewModel.onCatClicked(it)
                        navigateToDetails(navController = navController)
                    }
                })
            }
        }
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = Route.HomeRoute.route
        )
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}


private fun navigateToDetails(navController: NavController) {
    navController.navigate(
        route = Route.DetailsRoute.route
    )
}

private fun navDetails(navController: NavController, cat: CatBreedsResponseItem) {
    navController.currentBackStackEntry?.savedStateHandle?.set("cat", cat)
    navController.navigate(
        route = Route.DetailsRoute.route
    )
}