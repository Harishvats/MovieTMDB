package com.demo.movietmdb.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.demo.movietmdb.common.AppConstants
import com.demo.movietmdb.common.AppConstants.Companion.MOVIE_DETAIL_SCREEN_DESTINATION
import com.demo.movietmdb.common.AppConstants.Companion.MOVIE_LIST_SCREEN_DESTINATION
import com.demo.movietmdb.presentation.viewmodel.MovieDetailsViewModel
import com.demo.movietmdb.presentation.viewmodel.MovieListViewModel

@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController(),
    movieListViewModel: MovieListViewModel,
    movieDetailsViewModel: MovieDetailsViewModel, modifier: Modifier
) {
    NavHost(navController = navHostController, startDestination = MOVIE_LIST_SCREEN_DESTINATION) {
        composable(route = MOVIE_LIST_SCREEN_DESTINATION) {
            MovieListScreen(
                movieListViewModel = movieListViewModel, selectedMovie = {
                    navHostController.navigate("$MOVIE_DETAIL_SCREEN_DESTINATION/$it")
                }, modifier
            )
        }
        composable(
            route = "$MOVIE_DETAIL_SCREEN_DESTINATION/{${AppConstants.SELECTED_MOVIE_ID}}",
            arguments = listOf(navArgument(AppConstants.SELECTED_MOVIE_ID) {
                type = NavType.IntType
            })
        ) {
            it.arguments?.getInt(AppConstants.SELECTED_MOVIE_ID)?.let { it1 ->
                MovieDetailsScreen(
                    movieDetailsViewModel = movieDetailsViewModel,
                    selectedMovieId = it1,
                    modifier
                )
            }
        }
    }

}