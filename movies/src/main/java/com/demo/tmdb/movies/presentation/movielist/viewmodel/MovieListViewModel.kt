package com.demo.tmdb.movies.presentation.movielist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.movietmdb.common.Response
import com.demo.movietmdb.common.ViewState
import com.demo.movietmdb.domain.model.Movie
import com.demo.movietmdb.domain.usecase.GetMoviesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val getMoviesListUseCase: GetMoviesListUseCase) :
    ViewModel() {

    private val _movieListStateFlow =
        MutableStateFlow<ViewState<List<Movie>>>(ViewState.LoadingState)
    val movieListStateFlow: StateFlow<ViewState<List<Movie>>>
        get() = _movieListStateFlow

    init {
        getMovieList()
    }

    fun getMovieList() {
        viewModelScope.launch {
            getMoviesListUseCase().collect() {
                when (it) {
                    is Response.Error -> _movieListStateFlow.value =
                        ViewState.ErrorState(it.message)

                    Response.Loading -> _movieListStateFlow.value = ViewState.LoadingState
                    is Response.Success -> _movieListStateFlow.value =
                        ViewState.SuccessState(it.data.movies)
                }
            }
        }
    }
}