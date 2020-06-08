package top.hanyue.movieflex.content.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import top.hanyue.movieflex.network.MovieFlexApi
import javax.inject.Inject

/**
 * Created by Buzz on 2020/4/23.
 * Email :lmx2060918@126.com
 */
class MovieDetailViewModel @Inject constructor(
    private val movieFlexApi: MovieFlexApi
) : ViewModel() {

    private val movieDetailLiveData: MutableLiveData<MovieDetailViewState> = MutableLiveData()


    fun getMovieDetailViewState(): LiveData<MovieDetailViewState> {
        return movieDetailLiveData
    }


    fun onScreenCreated(vodId:String){
        movieDetailLiveData.value = MovieDetailViewState.Loading
        viewModelScope.launch(navigationExceptionHandler) {
            val response = movieFlexApi.getMovieDetail("6954", vodId)
            movieDetailLiveData.value = MovieDetailViewState.Success(response)
        }
    }


    private val navigationExceptionHandler = CoroutineExceptionHandler { _, exception ->
        movieDetailLiveData.value = MovieDetailViewState.NetworkError(exception.message)
        Timber.e(exception)
    }
}