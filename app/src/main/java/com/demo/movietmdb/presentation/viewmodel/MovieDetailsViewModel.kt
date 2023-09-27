package com.demo.movietmdb.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.movietmdb.common.ApiResponse
import com.demo.movietmdb.domain.model.MovieDetails
import com.demo.movietmdb.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _movieDetailsStateFlow =
        MutableStateFlow<ApiResponse<MovieDetails>>(ApiResponse.Loading)
    val movieDetailsStateFlow: StateFlow<ApiResponse<MovieDetails>>
        get() = _movieDetailsStateFlow


    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailsUseCase(movieId).collect() {
                when (it) {
                    is ApiResponse.Loading -> _movieDetailsStateFlow.value = ApiResponse.Loading
                    is ApiResponse.Error -> _movieDetailsStateFlow.value =
                        ApiResponse.Error(it.message)

                    is ApiResponse.Success -> _movieDetailsStateFlow.value =
                        ApiResponse.Success(it.data)
                }
            }
        }
    }
}