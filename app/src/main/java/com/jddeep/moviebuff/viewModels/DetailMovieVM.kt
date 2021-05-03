package com.jddeep.moviebuff.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jddeep.moviebuff.data.models.DetailsData
import com.jddeep.moviebuff.repositories.DetailMovieRepo
import com.jddeep.moviebuff.utils.Resource
import com.jddeep.moviebuff.utils.Status
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailMovieVM : ViewModel() {

    private val repo = DetailMovieRepo()

    private val _detailsData = MutableLiveData<Resource<DetailsData>>()
    val detailsData: LiveData<Resource<DetailsData>>
        get() = _detailsData

    private val detailMovieExceptionHandler = CoroutineExceptionHandler { _, t ->
        viewModelScope.launch(Dispatchers.Main) {
            _detailsData.value = Resource.error(data = null, message = t.message!!)
        }
    }

    fun getMovieDetails(movieId: Int) {
        if (_detailsData.value?.status == Status.SUCCESS) return

        _detailsData.value = Resource.loading(data = null)
        viewModelScope.launch(Dispatchers.IO + detailMovieExceptionHandler) {
            repo.getMovieDetails(movieId).let {
                withContext(Dispatchers.Main) {
                    _detailsData.value = Resource.success(data = it)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}