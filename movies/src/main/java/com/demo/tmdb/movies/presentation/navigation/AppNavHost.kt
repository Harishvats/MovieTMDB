package com.demo.tmdb.movies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.demo.movietmdb.common.AppConstants
import com.demo.movietmdb.common.AppConstants.Companion.MOVIE_DETAIL_SCREEN_DESTINATION
import com.demo.movietmdb.common.AppConstants.Companion.MOVIE_LIST_SCREEN_DESTINATION
import com.demo.tmdb.movies.R
import com.demo.tmdb.movies.presentation.moviedetails.ui.MovieDetailsScreen
import com.demo.tmdb.movies.presentation.moviedetails.viewmodel.MovieDetailsViewModel
import com.demo.tmdb.movies.presentation.movielist.ui.MovieListScreen
import com.demo.tmdb.movies.presentation.movielist.viewmodel.MovieListViewModel

@Composable
fun AppNavHost(
    navHostController: NavHostController/* = rememberNavController()*/,
    movieListViewModel: MovieListViewModel,
    movieDetailsViewModel: MovieDetailsViewModel,
    modifier: Modifier,
    toolBarTitle: MutableState<String>,
    secondaryScreenHeader: MutableState<Boolean>,
) {
    NavHost(navController = navHostController, startDestination = MOVIE_LIST_SCREEN_DESTINATION) {
        composable(route = MOVIE_LIST_SCREEN_DESTINATION) {
            toolBarTitle.value = stringResource(id = R.string.app_name)
            secondaryScreenHeader.value = false
            MovieListScreen(
                movieListViewModel = movieListViewModel,
                selectedMovie = { movieID: Int, title: String ->
                    navHostController.navigate("$MOVIE_DETAIL_SCREEN_DESTINATION/$movieID/$title")

                },
                modifier
            )
        }
        composable(
            route = "$MOVIE_DETAIL_SCREEN_DESTINATION/{${AppConstants.SELECTED_MOVIE_ID}}/{${AppConstants.SELECTED_MOVIE_TITLE}}",
            arguments = listOf(
                navArgument(AppConstants.SELECTED_MOVIE_ID) {
                    type = NavType.IntType
                }, navArgument(AppConstants.SELECTED_MOVIE_TITLE) {
                    type = NavType.StringType
                }
            )
        ) {

            secondaryScreenHeader.value = true
            toolBarTitle.value =
                it.arguments?.getString(AppConstants.SELECTED_MOVIE_TITLE).toString()

            it.arguments?.getInt(AppConstants.SELECTED_MOVIE_ID)?.let { it1 ->
                MovieDetailsScreen(
                    movieDetailsViewModel = movieDetailsViewModel, selectedMovieId = it1, modifier
                )
            }
        }
    }

}