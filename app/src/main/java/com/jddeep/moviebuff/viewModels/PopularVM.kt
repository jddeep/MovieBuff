package com.jddeep.moviebuff.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jddeep.moviebuff.data.cache.MemoryMoviesCache
import com.jddeep.moviebuff.data.models.MovieData
import com.jddeep.moviebuff.repositories.PopularRepo
import com.jddeep.moviebuff.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularVM : ViewModel() {

    private val repo = PopularRepo()
    private val cache = MemoryMoviesCache()

    private val _movies = MutableLiveData<Resource<List<MovieData>>>()
    val movies: LiveData<Resource<List<MovieData>>>
        get() = _movies

    private val popularMoviesExceptionHandler = CoroutineExceptionHandler { _, t ->
        viewModelScope.launch(Dispatchers.Main) {
            _movies.value = Resource.error(data = null, message = t.message!!)
        }
    }

    fun getPopularMovies() {
        _movies.value = Resource.loading(data = null)
        if (cache.getAll().isNotEmpty()) {
            _movies.value = Resource.success(data = cache.getAll())
            return
        }

        viewModelScope.launch(Dispatchers.IO + popularMoviesExceptionHandler) {
            repo.getPopularMovies().let {
                withContext(Dispatchers.Main) {
                    _movies.value = Resource.success(data = it.movies)
                }
                cache.saveAll(it.movies)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}