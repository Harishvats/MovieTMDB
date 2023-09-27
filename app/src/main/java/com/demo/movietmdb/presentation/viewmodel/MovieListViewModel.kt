package com.demo.movietmdb.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.Movie
import com.demo.movietmdb.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    ViewModel() {

    private val _movieListStateFlow =
        MutableStateFlow<ApiResponse<List<Movie>>>(ApiResponse.Loading)
    val movieListStateFlow: StateFlow<ApiResponse<List<Movie>>>
        get() = _movieListStateFlow

    init {
        getMovieList()
    }

    fun getMovieList() {
        viewModelScope.launch {
            getMoviesUseCase().collect() {
                when (it) {
                    is ApiResponse.Error -> _movieListStateFlow.value =
                        ApiResponse.Error(it.message)

                    ApiResponse.Loading -> _movieListStateFlow.value = ApiResponse.Loading
                    is ApiResponse.Success -> _movieListStateFlow.value =
                        ApiResponse.Success(it.data.movies)
                }
            }
        }
    }
}